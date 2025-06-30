package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    public Venda() {
    }

    public Venda(LocalDateTime data, Cliente cliente, Funcionario responsavel) {
        this.status = Status.ABERTA;
        this.codigo = UUID.randomUUID().toString().split("-")[0];
        this.valor = 0.0;
        this.data = data;
        this.cliente = cliente;
        this.responsavel = responsavel;
        this.itens = new ArrayList<>();
    }

    public void calcularTotal() {
        this.valor = itens.stream().mapToDouble(item -> item.getPrecoUnitario() * item.getQuantidadeEscolhida()).sum();
    }

    @Override
    public String toString() {
        return "Venda[codigo=%s, valor=%.2f, data=%s, cliente=%s, responsavel=%s, itens=%s, status=%s]".formatted(codigo, valor, data.format(java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.ROOT)), cliente.getNome(), responsavel.getNome(), itens.toString(), status);
    }

    public String getCodigo() {
        return codigo;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
