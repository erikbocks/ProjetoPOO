package banco.impls;

import banco.GerenciadorFuncionarios;
import entidades.Endereco;
import entidades.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorFuncionariosImpl implements GerenciadorFuncionarios {
    @Override
    public Funcionario buscarPorCpf(String cpf) {
        Funcionario funcionario = null;

        try (var conn = getConnectionWithFKEnabled()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM funcionarios f INNER JOIN enderecos_funcionarios ef ON f.cpf = ef.cpf_funcionarios WHERE f.cpf = ?");
            pstmt.setString(1, cpf);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int index = 1;
                funcionario = new Funcionario();
                index = mapearResultSetParaEntidade(funcionario, rs, index);

                Endereco endereco = mapearResultSetParaEndereco(rs, index);
                funcionario.setEndereco(endereco);
            }

            return funcionario;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por CPF: " + e.getMessage());
            return funcionario;
        }
    }

    @Override
    public boolean estaCadastrado(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT 1 FROM funcionarios WHERE cpf = ?");

            pstmt.setString(1, cpf);

            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            System.err.println("Erro ao verificar se funcionário está cadastrado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void atualizarEndereco(String cpf, Endereco endereco) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlEndereco = "UPDATE enderecos_funcionarios SET estado = ?, cidade = ?, rua = ?, numero = ?, complemento = ? WHERE cpf_funcionarios = ?";

            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco)) {
                pstmtEndereco.setString(1, endereco.getEstado().toString());
                pstmtEndereco.setString(2, endereco.getCidade());
                pstmtEndereco.setString(3, endereco.getRua());
                pstmtEndereco.setInt(4, endereco.getNumero());
                pstmtEndereco.setString(5, endereco.getComplemento());
                pstmtEndereco.setString(6, cpf);
                pstmtEndereco.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar endereço de funcionário: " + e.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void desativar(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlDesativarFuncionario = "UPDATE funcionarios SET ativo=? WHERE cpf=?";
            try (var pstmt = conn.prepareStatement(sqlDesativarFuncionario)) {
                pstmt.setBoolean(1, false);
                pstmt.setString(2, cpf);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao desativar funcionário. Revertendo...");
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public List<Funcionario> listarTodos() {
        List<Funcionario> funcionarios = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM funcionarios f INNER JOIN enderecos_funcionarios ef ON f.cpf = ef.cpf_funcionarios WHERE ativo = true");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int index = 1;
                Funcionario funcionario = new Funcionario();
                index = mapearResultSetParaEntidade(funcionario, rs, index);

                Endereco endereco = mapearResultSetParaEndereco(rs, index);
                funcionario.setEndereco(endereco);

                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

        return funcionarios;
    }

    @Override
    public Funcionario inserir(Funcionario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);
            entidade.setDataDeCadastro(LocalDateTime.now());

            String sqlFuncionario = "INSERT INTO funcionarios (cpf, nome, email, telefone, data_cadastro, data_nascimento, ativo, senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
                pstmtFuncionario.setString(1, entidade.getCpf());
                pstmtFuncionario.setString(2, entidade.getNome());
                pstmtFuncionario.setString(3, entidade.getEmail());
                pstmtFuncionario.setString(4, entidade.getTelefone());
                pstmtFuncionario.setString(5, entidade.getDataDeCadastro().toString());
                pstmtFuncionario.setString(6, entidade.getDataDeNascimento().toString());
                pstmtFuncionario.setBoolean(7, entidade.getAtivo());
                pstmtFuncionario.setString(8, entidade.getSenha());
                pstmtFuncionario.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao inserir funcionário: " + e.getMessage());
                conn.rollback();
                return null;
            }

            String sqlEndereco = "INSERT INTO enderecos_funcionarios (estado, cidade, rua, numero, complemento, cpf_funcionarios) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco)) {
                Endereco endereco = entidade.getEndereco();
                pstmtEndereco.setString(1, endereco.getEstado().toString());
                pstmtEndereco.setString(2, endereco.getCidade());
                pstmtEndereco.setString(3, endereco.getRua());
                pstmtEndereco.setInt(4, endereco.getNumero());
                pstmtEndereco.setString(5, endereco.getComplemento());
                pstmtEndereco.setString(6, entidade.getCpf());
                pstmtEndereco.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao inserir endereço: " + e.getMessage());
                conn.rollback();
                return null;
            }

            conn.commit();

            String sqlIdEndereco = "SELECT id FROM enderecos_funcionarios WHERE cpf_funcionarios = ?";
            try (PreparedStatement pstmtIdEndereco = conn.prepareStatement(sqlIdEndereco)) {
                pstmtIdEndereco.setString(1, entidade.getCpf());
                ResultSet rs = pstmtIdEndereco.executeQuery();
                if (rs.next()) {
                    entidade.getEndereco().setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar ID do endereço: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
            return null;
        }

        return entidade;
    }

    @Override
    public void atualizar(Funcionario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlFuncionario = "UPDATE funcionarios SET nome = ?, email = ?, telefone = ?, data_nascimento = ?, ativo = ?, senha = ? WHERE cpf = ?";
            try (PreparedStatement pstmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
                pstmtFuncionario.setString(1, entidade.getNome());
                pstmtFuncionario.setString(2, entidade.getEmail());
                pstmtFuncionario.setString(3, entidade.getTelefone());
                pstmtFuncionario.setString(4, entidade.getDataDeNascimento().toString());
                pstmtFuncionario.setBoolean(5, entidade.getAtivo());
                pstmtFuncionario.setString(6, entidade.getSenha());
                pstmtFuncionario.setString(7, entidade.getCpf());
                pstmtFuncionario.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void excluir(Funcionario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlFuncionario = "DELETE FROM funcionarios WHERE cpf = ?";
            try (PreparedStatement pstmtFuncionario = conn.prepareStatement(sqlFuncionario)) {
                pstmtFuncionario.setString(1, entidade.getCpf());
                pstmtFuncionario.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao excluir funcionário: " + e.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.err.println("Erro conectar com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public int mapearResultSetParaEntidade(Funcionario entidade, ResultSet rs, Integer index) throws SQLException {
        index = mapearDadosUsuarioDoResultSet(entidade, rs, index);
        entidade.setSenha(rs.getString(index++));

        return index;
    }
}
