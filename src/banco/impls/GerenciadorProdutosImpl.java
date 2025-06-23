package banco.impls;

import banco.GerenciadorProdutos;
import entidades.Produto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GerenciadorProdutosImpl implements GerenciadorProdutos {
    @Override
    public List<Produto> listarTodos() {
        return List.of();
    }

    @Override
    public Produto inserir(Produto entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlAdicaoProduto = "INSERT INTO produtos(codigo,nome,descricao,quantidade,valor,ultima_atualizacao,tipo) VALUES(?,?,?,?,?,?,?);";
            try (var ptsmt = conn.prepareStatement(sqlAdicaoProduto, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ptsmt.setString(1, entidade.getCodigo());
                ptsmt.setString(2, entidade.getNome());
                ptsmt.setString(3, entidade.getDescricao());
                ptsmt.setInt(4, entidade.getQuantidade());
                ptsmt.setDouble(5, entidade.getValor());
                ptsmt.setString(6, entidade.getUltimaAtualizacao().toString());
                ptsmt.setString(7, entidade.getTipo().name());
                ptsmt.executeUpdate();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Não foi possível adicionar o produto: " + ex.getMessage());
                return null;
            }

            conn.commit();

            return entidade;
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados:" + ex.getMessage());
            return entidade;
        }
    }

    @Override
    public void atualizar(Produto entidade) {

    }

    @Override
    public void excluir(Produto entidade) {

    }
}
