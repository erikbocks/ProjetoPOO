package entidades;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

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

    public Produto() {
    }

    public Produto(String nome, String descricao, Integer quantidade, Double valor, TipoProduto tipo) {
        this.codigo = UUID.randomUUID().toString().split("-")[0];
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

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public TipoProduto getTipo() {
        return tipo;
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

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }

    public void atualizarEstoque(Integer quantia) {
        if(tipo != TipoProduto.SERVICO) {
            this.quantidade = quantidade - quantia;
        }
    }

    @Override
    public String toString() {
        return String.format("Produto[Código: %s, Nome: %s, Descrição: %s, Quantidade disponível: %d, Valor: %.2f, Tipo: %s]", codigo, nome, descricao, quantidade, valor, tipo);
    }
}
