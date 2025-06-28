package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /**
     * Atualiza a entidade do Gerenciador no banco de dados.
     * @param entidade a entidade a ser inserida.
     */
    void atualizar(T entidade);

    /**
     * Remove a entidade do Gerenciador no banco de dados.
     * @param entidade a entidade a ser removida.
     */
    void excluir(T entidade);

    /**
     * Faz o mapeamento dos campos do ResultSet para a entidade.
     * @param rs o ResultSet retornado da consulta no banco.
     * @param entidade a entidade a ser populada.
     * @param index o índice da coluna no ResultSet.
     * @return o índice atual do ResultSet
     * @throws SQLException caso a coluna a ser consultada não exista.
     */
    int mapearResultSetParaEntidade(T entidade, ResultSet rs, Integer index) throws SQLException;

    default Connection getConnectionWithFKEnabled() throws SQLException {
        Connection conn = DriverManager.getConnection(GerenciadorBase.STRING_CONEXAO);

        try (var stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }
}
