package banco;

import java.util.List;

public interface GerenciadorBase<T> {
    String STRING_CONEXAO = "jdbc:sqlite:petshop.db";

    /**
     * Lista todas as entidades cadastradas na tabela.
     * @return lista de todas as entidades encontradas;
     */
    List<T> listarTodos();

    /**
     * Insere a entidade do Gerenciador no banco de dados.
     * @param entidade A entidade a ser inserida.
     * @return A entidade salva no banco.
     */

    T inserir(T entidade);

    void atualizar(T entidade);

    void excluir(int id);

    String getNomeTabela();
}
