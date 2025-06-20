package banco.impls;

import banco.GerenciadorClientes;
import entidades.Cliente;
import entidades.Endereco;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class GerenciadorClientesImpl implements GerenciadorClientes {

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return null;
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

    }

    @Override
    public void desativar(String cpf) {

    }

    @Override
    public List<Cliente> listarTodos() {
        return List.of();
    }

    @Override
    public Cliente inserir(Cliente entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);
            entidade.setDataDeCadastro(LocalDateTime.now());

            String sqlCliente = "INSERT INTO clientes (cpf, nome, email, telefone, data_cadastro, data_nascimento, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlCliente)) {
                pstmt.setString(1, entidade.getCpf());
                pstmt.setString(2, entidade.getNome());
                pstmt.setObject(3, entidade.getEmail());
                pstmt.setString(4, entidade.getTelefone());
                pstmt.setString(5, entidade.getDataDeCadastro().toString());
                pstmt.setString(6, entidade.getDataDeNascimento().toString());
                pstmt.setObject(7, entidade.getAtivo());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Erro ao inserir cliente: " + e.getMessage());
                return null;
            }

            String sqlEndereco = "INSERT INTO enderecos_clientes (estado, cidade, rua, numero, complemento, cpf_cliente) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco, PreparedStatement.RETURN_GENERATED_KEYS)) {
                Endereco endereco = entidade.getEndereco();
                pstmtEndereco.setString(1, endereco.getEstado().toString());
                pstmtEndereco.setString(2, endereco.getCidade());
                pstmtEndereco.setString(3, endereco.getRua());
                pstmtEndereco.setInt(4, endereco.getNumero());
                pstmtEndereco.setString(5, endereco.getComplemento());
                pstmtEndereco.setString(6, entidade.getCpf());
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

    }

    @Override
    public void excluir(Cliente entidade) {

    }
}
