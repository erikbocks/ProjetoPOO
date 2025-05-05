package entidades;

import java.time.LocalDateTime;
import java.util.List;

public class Venda {
    private Double valor;
    private LocalDateTime data;
    private Cliente cliente;
    private Funcionario responsavel;
    List<Produto> produtos;
}
