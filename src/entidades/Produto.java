package entidades;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Produto {
    private String codigo;
    private String nome;
    private String descricao;
    private Integer quantidade;
    private Double valor;
    private LocalDateTime ultimaAtualizacao;
    private TipoProduto tipo;

    public enum TipoProduto {
        SERVICO,
        ALIMENTO,
        MEDICAMENTO,
        HIGIENE,
        ACESSORIO,
        BRINQUEDO
    }

    public Produto(String nome, String descricao, Integer quantidade, Double valor, TipoProduto tipo) {
        this.codigo = String.valueOf((int) (Math.random() * 1001));
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.ultimaAtualizacao = LocalDateTime.now(ZoneId.systemDefault());
        this.tipo = tipo;
    }

    public static TipoProduto procurarTipoDeProdutoPorNome(String nome) {
        for (TipoProduto tipo : TipoProduto.values()) {
            if (tipo.name().equalsIgnoreCase(nome)) {
                return tipo;
            }
        }
        return null;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void atualizarEstoque(Integer quantia) {
        if(tipo != TipoProduto.SERVICO) {
            this.quantidade = quantidade - quantia;
        }
    }

    @Override
    public String toString() {
        return String.format("Produto[Código: %s, Nome: %s, Quantidade disponível: %d, Valor: %.2f]", codigo, nome, quantidade, valor);
    }
}
