package banco.impls;

import banco.GerenciadorClientes;
import entidades.Cliente;
import entidades.Endereco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorClientesImpl implements GerenciadorClientes {

    @Override
    public Cliente buscarPorCpf(String cpf) {
        Cliente cliente = null;

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscarCliente = "SELECT * FROM clientes c INNER JOIN enderecos_clientes ec ON c.cpf = ec.cpf_cliente WHERE c.cpf = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlBuscarCliente);
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int index = 1;
                cliente = new Cliente();
                index = mapearResultSetParaEntidade(cliente, rs, index);

                Endereco endereco = mapearResultSetParaEndereco(rs, index);
                cliente.setEndereco(endereco);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
        }

        return cliente;
    }

    @Override
    public boolean estaCadastrado(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT 1 FROM clientes WHERE cpf = ?");

            pstmt.setString(1, cpf);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException exception) {
            System.err.println("Não foi possível conectar ao banco de dados: " + exception.getMessage());
            return false;
        }
    }

    @Override
    public void atualizarEndereco(String cpf, Endereco endereco) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlEndereco = "UPDATE enderecos_clientes SET estado = ?, cidade = ?, rua = ?, numero = ?, complemento = ? WHERE cpf_cliente = ?";

            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco)) {
                int index = 1;
                pstmtEndereco.setString(index++, endereco.getEstado().toString());
                pstmtEndereco.setString(index++, endereco.getCidade());
                pstmtEndereco.setString(index++, endereco.getRua());
                pstmtEndereco.setInt(index++, endereco.getNumero());
                pstmtEndereco.setString(index++, endereco.getComplemento());
                pstmtEndereco.setString(index, cpf);
                pstmtEndereco.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar endereço de cliente: " + e.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void desativar(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlDesativarCliente = "UPDATE clientes SET ativo=? WHERE cpf=?";
            try (var pstmt = conn.prepareStatement(sqlDesativarCliente)) {
                pstmt.setBoolean(1, false);
                pstmt.setString(2, cpf);
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                System.err.println("Erro ao desativar cliente. Revertendo...");
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM clientes c INNER JOIN enderecos_clientes ec ON ec.cpf_cliente = c.cpf WHERE ativo = true;");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int index = 1;
                Cliente cliente = new Cliente();
                index = mapearResultSetParaEntidade(cliente, rs, index);

                Endereco endereco = mapearResultSetParaEndereco(rs, index);
                cliente.setEndereco(endereco);

                clientes.add(cliente);
            }

        } catch (SQLException exception) {
            System.err.println("Não foi possível listar os clientes: " + exception.getMessage());
        }

        return clientes;
    }

    @Override
    public Cliente inserir(Cliente entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);
            entidade.setDataDeCadastro(LocalDateTime.now());

            String sqlCliente = "INSERT INTO clientes (cpf, nome, email, telefone, data_cadastro, data_nascimento, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCliente)) {
                int index = 1;
                pstmt.setString(index++, entidade.getCpf());
                pstmt.setString(index++, entidade.getNome());
                pstmt.setObject(index++, entidade.getEmail());
                pstmt.setString(index++, entidade.getTelefone());
                pstmt.setString(index++, entidade.getDataDeCadastro().toString());
                pstmt.setString(index++, entidade.getDataDeNascimento().toString());
                pstmt.setObject(index, entidade.getAtivo());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erro ao inserir cliente: " + e.getMessage());
                return null;
            }

            String sqlEndereco = "INSERT INTO enderecos_clientes (estado, cidade, rua, numero, complemento, cpf_cliente) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, PreparedStatement.RETURN_GENERATED_KEYS)) {
                Endereco endereco = entidade.getEndereco();
                int index = 1;
                pstmtEndereco.setString(index++, endereco.getEstado().toString());
                pstmtEndereco.setString(index++, endereco.getCidade());
                pstmtEndereco.setString(index++, endereco.getRua());
                pstmtEndereco.setInt(index++, endereco.getNumero());
                pstmtEndereco.setString(index++, endereco.getComplemento());
                pstmtEndereco.setString(index, entidade.getCpf());
                pstmtEndereco.executeUpdate();

                ResultSet idEndereco = pstmtEndereco.getGeneratedKeys();
                if (idEndereco.next()) {
                    int enderecoId = idEndereco.getInt(1);
                    entidade.getEndereco().setId(enderecoId);
                } else {
                    throw new SQLException("Erro ao obter o ID do endereço inserido.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao inserir endereço: " + e.getMessage());
                conn.rollback();
                return null;
            }

            conn.commit();

            return entidade;
        } catch (SQLException exception) {
            System.err.println("Não foi possível inserir o cliente: " + exception.getMessage());
            return null;
        }
    }

    @Override
    public void atualizar(Cliente entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlAtualizarCliente = "UPDATE clientes SET nome = ?, email = ?, telefone = ?, data_nascimento = ?, ativo = ? WHERE cpf = ?";
            try (PreparedStatement pstmtCliente = conn.prepareStatement(sqlAtualizarCliente)) {
                pstmtCliente.setString(1, entidade.getNome());
                pstmtCliente.setString(2, entidade.getEmail());
                pstmtCliente.setString(3, entidade.getTelefone());
                pstmtCliente.setString(4, entidade.getDataDeNascimento().toString());
                pstmtCliente.setBoolean(5, entidade.getAtivo());
                pstmtCliente.setString(6, entidade.getCpf());
                pstmtCliente.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar cliente: " + e.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void excluir(Cliente entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlExcluirCliente = "DELETE FROM clientes WHERE cpf = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlExcluirCliente)) {
                pstmt.setString(1, entidade.getCpf());
                pstmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                System.err.println("Erro ao excluir cliente: " + e.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public int mapearResultSetParaEntidade(Cliente entidade, ResultSet rs, Integer index) throws SQLException {
        index = mapearDadosUsuarioDoResultSet(entidade, rs, index);

        return index;
    }
}
