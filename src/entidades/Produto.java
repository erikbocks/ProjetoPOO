package entidades;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Produto {
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
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.ultimaAtualizacao = LocalDateTime.now(ZoneId.systemDefault());
        this.tipo = tipo;
    }

    public static TipoProduto procurarProdutoPorNome(String nome) {
        for (TipoProduto tipo : TipoProduto.values()) {
            if (tipo.name().equalsIgnoreCase(nome)) {
                return tipo;
            }
        }
        return null;
    }

    public String getNome() {
        return nome;
    }
}
