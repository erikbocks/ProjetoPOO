package banco.impls;

import banco.GerenciadorVendas;
import entidades.Cliente;
import entidades.Funcionario;
import entidades.ItemVenda;
import entidades.Venda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorVendasImpl implements GerenciadorVendas {
    @Override
    public Venda buscarVendaPorCodigo(String codigo) {
        Venda venda = new Venda();

        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT v.*, c.nome, f.nome FROM vendas v INNER JOIN clientes c on v.cpf_cliente = c.cpf INNER JOIN funcionarios f on v.funcionario_responsavel = f.cpf WHERE v.codigo = ?";
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, codigo);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int index = 1;
                    venda = new Venda();
                    index = mapearResultSetParaEntidade(venda, rs, index);
                    venda.getCliente().setNome(rs.getString(index++));
                    venda.getResponsavel().setNome(rs.getString(index));
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao buscar venda por código: " + ex.getMessage());
                return null;
            }

            String sqlItens = "SELECT * FROM produtos_vendas WHERE codigo_venda = ?";
            try (var pstmtItens = conn.prepareStatement(sqlItens)) {
                pstmtItens.setString(1, venda.getCodigo());
                ResultSet rsItens = pstmtItens.executeQuery();

                while (rsItens.next()) {
                    int index = 1;
                    ItemVenda item = new ItemVenda();
                    item.setCodigoProduto(rsItens.getString(index++));
                    item.setQuantidadeEscolhida(rsItens.getInt(index++));
                    item.setPrecoUnitario(rsItens.getDouble(index));
                    venda.getItens().add(item);
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao listar itens da venda: " + ex.getMessage());
                return null;
            }
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
            return null;
        }

        return venda;
    }

    @Override
    public List<Venda> listarVendas() {
        try (var conn = getConnectionWithFKEnabled()) {
            List<Venda> vendas = new ArrayList<>();

            String sql = "SELECT v.*, c.nome, f.nome FROM vendas v INNER JOIN clientes c on v.cpf_cliente = c.cpf INNER JOIN funcionarios f on v.funcionario_responsavel = f.cpf";
            try (var pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();


                while (rs.next()) {
                    int index = 1;
                    Venda venda = new Venda();

                    index = mapearResultSetParaEntidade(venda, rs, index);
                    venda.getCliente().setNome(rs.getString(index++));
                    venda.getResponsavel().setNome(rs.getString(index));

                    vendas.add(venda);
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao listar vendas: " + ex.getMessage());
                return null;
            }

            for (Venda venda : vendas) {
                String sqlItens = "SELECT * FROM produtos_vendas WHERE codigo_venda = ?";
                try (var pstmtItens = conn.prepareStatement(sqlItens)) {
                    pstmtItens.setString(1, venda.getCodigo());
                    ResultSet rsItens = pstmtItens.executeQuery();

                    while (rsItens.next()) {
                        ItemVenda item = new ItemVenda();
                        item.setCodigoProduto(rsItens.getString("codigo_produto"));
                        item.setQuantidadeEscolhida(rsItens.getInt("quantidade"));
                        item.setPrecoUnitario(rsItens.getDouble("preco_unitario"));
                        venda.getItens().add(item);
                    }
                } catch (SQLException ex) {
                    System.err.println("Erro ao listar itens da venda: " + ex.getMessage());
                    return null;
                }
            }

            return vendas;
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Venda> listarVendasPorStatus(Venda.Status status) {
        try (var conn = getConnectionWithFKEnabled()) {
            List<Venda> vendas = new ArrayList<>();

            String sql = "SELECT v.*, c.nome, f.nome FROM vendas v INNER JOIN clientes c on v.cpf_cliente = c.cpf INNER JOIN funcionarios f on v.funcionario_responsavel = f.cpf WHERE status = ?";
            try (var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, status.name());
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int index = 1;
                    Venda venda = new Venda();

                    index = mapearResultSetParaEntidade(venda, rs, index);
                    venda.getCliente().setNome(rs.getString(index++));
                    venda.getResponsavel().setNome(rs.getString(index));

                    vendas.add(venda);
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao listar vendas: " + ex.getMessage());
                return null;
            }

            for (Venda venda : vendas) {
                String sqlItens = "SELECT * FROM produtos_vendas WHERE codigo_venda = ?";
                try (var pstmtItens = conn.prepareStatement(sqlItens)) {
                    pstmtItens.setString(1, venda.getCodigo());
                    ResultSet rsItens = pstmtItens.executeQuery();

                    while (rsItens.next()) {
                        int index = 1;
                        ItemVenda item = new ItemVenda();
                        item.setCodigoProduto(rsItens.getString(index++));
                        item.setQuantidadeEscolhida(rsItens.getInt(index++));
                        item.setPrecoUnitario(rsItens.getDouble(index));
                        venda.getItens().add(item);
                    }
                } catch (SQLException ex) {
                    System.err.println("Erro ao listar itens da venda: " + ex.getMessage());
                    return null;
                }
            }

            return vendas;
        } catch (SQLException ex) {
            System.err.println("Não foi possível estabelecer uma conexão com o banco de dados: " + ex.getMessage());
            return null;
        }
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
        entidade.setCodigo(rs.getString(index++));
        entidade.setValor(rs.getDouble(index++));
        entidade.setData(LocalDateTime.parse(rs.getString(index++)));

        Cliente cliente = new Cliente();
        cliente.setCpf(rs.getString(index++));
        entidade.setCliente(cliente);

        Funcionario responsavel = new Funcionario();
        responsavel.setCpf(rs.getString(index++));
        entidade.setResponsavel(responsavel);

        entidade.setItens(new ArrayList<>());
        entidade.setStatus(Venda.Status.valueOf(rs.getString(index++)));

        return index;
    }
}
