package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Venda {
    private String codigo;
    private Double valor;
    private LocalDateTime data;
    private Cliente cliente;
    private Funcionario responsavel;
    private List<ItemVenda> itens;
    private Status status;

    public Status getStatus() {
        return status;
    }

    public enum Status {
        ABERTA,
        FECHADA,
        CANCELADA
    }

    public static Status procurarStatusPorNome(String nome) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(nome)) {
                return status;
            }
        }

        return null;
    }

    public Venda(LocalDateTime data, Cliente cliente, Funcionario responsavel) {
        this.status = Status.ABERTA;
        this.codigo = String.valueOf((int) (Math.random() * 1001));
        this.valor = 0.0;
        this.data = data;
        this.cliente = cliente;
        this.responsavel = responsavel;
        this.itens = new ArrayList<>();
    }

    public void fecharVenda(List<ItemVenda> itensSelecionados, List<Produto> produtos) {
        this.itens.addAll(itensSelecionados);

        List<String> codigosDeProdutos = this.itens.stream().map(ItemVenda::getCodigoProduto).toList();

        this.valor = produtos.stream().filter(p -> codigosDeProdutos.contains(p.getCodigo())).map(Produto::getValor).reduce(0.0, Double::sum);

        this.status = Status.FECHADA;

        System.out.println("Venda fechada com sucesso!");
        System.out.println("==========================");
        System.out.println("Código da venda: " + this.codigo);
        System.out.println("Valor total: " + this.valor);
        System.out.println("==========================");
    }

    @Override
    public String toString() {
        return "Venda[codigo=%s, valor=%.2f, data=%s, cliente=%s, responsavel=%s, itens=%s, status=%s]".formatted(codigo, valor, data.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.ROOT)), cliente.getNome(), responsavel.getNome(), itens.toString(), status);
    }
}
