package entidades;

import java.time.LocalDateTime;

public class Produto {
    private String nome;
    private Integer quantidade;
    private Double valor;
    private LocalDateTime ultimaAtualizacao;
    private TipoProduto tipo;

    public enum TipoProduto {
        SERVICO,
        RACAO
    }
}
