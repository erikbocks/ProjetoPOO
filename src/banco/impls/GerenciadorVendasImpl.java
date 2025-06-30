package banco.impls;

import banco.GerenciadorVendas;
import entidades.ItemVenda;
import entidades.Venda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class GerenciadorVendasImpl implements GerenciadorVendas {
    @Override
    public Venda buscarVendaPorCodigo(String codigo) {
        return null;
    }

    @Override
    public List<Venda> listarVendas() {
        return List.of();
    }

    @Override
    public List<Venda> listarVendasPorStatus(Venda.Status status) {
        return List.of();
    }

    @Override
    public List<Venda> listarVendasPorCliente(String cpfCliente) {
        return List.of();
    }

    @Override
    public List<Venda> listarVendasPorFuncionario(String cpfFuncionario) {
        return List.of();
    }

    @Override
    public List<Venda> listarVendasPorData(LocalDateTime data) {
        return List.of();
    }

    @Override
    public void fecharVenda(Venda venda) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlInsert = "INSERT INTO vendas (codigo, total, data_venda, cpf_cliente, funcionario_responsavel, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (var pstmt = conn.prepareStatement(sqlInsert)) {
                pstmt.setString(1, venda.getCodigo());
                pstmt.setDouble(2, venda.getValor());
                pstmt.setString(3, venda.getData().toString());
                pstmt.setString(4, venda.getCliente().getCpf());
                pstmt.setString(5, venda.getResponsavel().getCpf());
                pstmt.setString(6, venda.getStatus().name());

                pstmt.executeUpdate();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Erro ao salvar a venda: " + ex.getMessage());
                return;
            }

            String sqlInsertItem = "INSERT INTO produtos_vendas (codigo_produto, codigo_venda, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
            try (var pstmt = conn.prepareStatement(sqlInsertItem)) {
                for (ItemVenda item : venda.getItens()) {
                    pstmt.setString(1, item.getCodigoProduto());
                    pstmt.setString(2, venda.getCodigo());
                    pstmt.setInt(3, item.getQuantidadeEscolhida());
                    pstmt.setDouble(4, item.getPrecoUnitario());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Erro ao salvar os itens da venda: " + ex.getMessage());
                return;
            }

            String sqlUpdateProdutos = "UPDATE produtos SET quantidade = quantidade - ? WHERE codigo = ? AND tipo != 'SERVICO'";
            try (var pstmt = conn.prepareStatement(sqlUpdateProdutos)) {
                for (ItemVenda item : venda.getItens()) {
                    pstmt.setInt(1, item.getQuantidadeEscolhida());
                    pstmt.setString(2, item.getCodigoProduto());
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            } catch (SQLException ex) {
                conn.rollback();
                System.err.println("Erro ao atualizar o estoque dos produtos: " + ex.getMessage());
                return;
            }

            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
        }
    }

    @Override
    public void cancelarVenda(Venda venda) {

    }

    @Override
    public List<Venda> listarTodos() {
        return List.of();
    }

    @Override
    public Venda inserir(Venda entidade) {
        return null;
    }

    @Override
    public void atualizar(Venda entidade) {

    }

    @Override
    public void excluir(Venda entidade) {

    }

    @Override
    public int mapearResultSetParaEntidade(Venda entidade, ResultSet rs, Integer index) throws SQLException {
        return 0;
    }
}
