package banco.impls;

import banco.GerenciadorProdutos;
import entidades.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutosImpl implements GerenciadorProdutos {
    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            try (var pstmt = conn.prepareStatement("SELECT * FROM produtos;")) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Produto produto = mapearResultSet(rs);
                    produtos.add(produto);
                }

                return produtos;
            } catch (SQLException ex) {
                System.err.println("Não foi possível buscar os produtos cadastrados: " + ex.getMessage());
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível conectar ao banco de dados: " + ex.getMessage());
            return null;
        }
    }

    private Produto mapearResultSet(ResultSet rs) throws SQLException {
        Produto produto = new Produto();

        produto.setCodigo(rs.getString(1));
        produto.setNome(rs.getString(2));
        produto.setDescricao(rs.getString(3));
        produto.setQuantidade(rs.getInt(4));
        produto.setValor(rs.getDouble(5));
        produto.setUltimaAtualizacao(LocalDateTime.parse(rs.getString(6)));
        produto.setTipo(Produto.TipoProduto.valueOf(rs.getString(7)));

        return produto;
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
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlAtualizacaoProduto = "UPDATE produtos SET nome = ?, descricao = ?, quantidade = ?, valor = ?, ultima_atualizacao = ?, tipo = ? WHERE codigo = ?;";
            try (var pstmt = conn.prepareStatement(sqlAtualizacaoProduto)) {
                pstmt.setString(1, entidade.getNome());
                pstmt.setString(2, entidade.getDescricao());
                pstmt.setInt(3, entidade.getQuantidade());
                pstmt.setDouble(4, entidade.getValor());
                pstmt.setString(5, entidade.getUltimaAtualizacao().toString());
                pstmt.setString(6, entidade.getTipo().name());
                pstmt.setString(7, entidade.getCodigo());
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Não foi possível atualizar o produto: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
        }
    }

    @Override
    public void excluir(Produto entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlExclusaoProduto = "DELETE FROM produtos WHERE codigo = ?;";
            try (var pstmt = conn.prepareStatement(sqlExclusaoProduto)) {
                pstmt.setString(1, entidade.getCodigo());
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Não foi possível excluir o produto: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
        }
    }

    @Override
    public Produto buscarPorCodigo(String codigo) {
        Produto produto = null;

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscaProduto = "SELECT * FROM produtos WHERE codigo = ?;";
            try (var pstmt = conn.prepareStatement(sqlBuscaProduto)) {
                pstmt.setString(1, codigo);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    produto = mapearResultSet(rs);
                }
            } catch (SQLException ex) {
                System.err.println("Não foi possível buscar o produto: " + ex.getMessage());
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível conectar ao banco de dados: " + ex.getMessage());
            return null;
        }

        return produto;
    }

    @Override
    public List<Produto> listarProdutoContendoPalavra(String palavra) {
        List<Produto> produtos = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscaProdutos = "SELECT * FROM produtos WHERE nome LIKE ? OR descricao LIKE ?;";
            try (var pstmt = conn.prepareStatement(sqlBuscaProdutos)) {
                pstmt.setString(1, "%" + palavra + "%");
                pstmt.setString(2, "%" + palavra + "%");
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Produto produto = mapearResultSet(rs);
                    produtos.add(produto);
                }

                return produtos;
            } catch (SQLException ex) {
                System.err.println("Não foi possível buscar os produtos: " + ex.getMessage());
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível conectar ao banco de dados: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Produto> listarPorTipo(Produto.TipoProduto tipo) {
        List<Produto> produtos = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscaProdutosPorTipo = "SELECT * FROM produtos WHERE tipo = ?;";
            try (var pstmt = conn.prepareStatement(sqlBuscaProdutosPorTipo)) {
                pstmt.setString(1, tipo.name());
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Produto produto = mapearResultSet(rs);
                    produtos.add(produto);
                }

                return produtos;
            } catch (SQLException ex) {
                System.err.println("Não foi possível buscar os produtos do tipo " + tipo + ": " + ex.getMessage());
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível conectar ao banco de dados: " + ex.getMessage());
            return null;
        }
    }
}
