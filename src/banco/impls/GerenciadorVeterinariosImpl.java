package banco.impls;

import banco.GerenciadorVeterinarios;
import entidades.Endereco;
import entidades.Veterinario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorVeterinariosImpl implements GerenciadorVeterinarios {
    @Override
    public Veterinario buscarPorCRMV(String crmv) {
        return null;
    }

    @Override
    public boolean verificarCRMV(String crmv) {
        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT 1 FROM veterinarios WHERE crmv = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, crmv);
                var rs = stmt.executeQuery();

                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o veterinário está cadastrado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        List<Veterinario> veterinarios = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscarPorEspecialidade = "SELECT * FROM veterinarios v INNER JOIN enderecos_veterinarios ev ON v.cpf = ev.cpf_veterinario WHERE v.especialidade = ?";
            try (var pstmt = conn.prepareStatement(sqlBuscarPorEspecialidade)) {
                pstmt.setString(1, especialidade);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int index = 1;
                    Veterinario veterinario = new Veterinario();
                    index = mapearResultSetParaEntidade(veterinario, rs, index);

                    Endereco endereco = mapearResultSetParaEndereco(rs, index);
                    veterinario.setEndereco(endereco);

                    veterinarios.add(veterinario);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
            return null;
        }

        return veterinarios;
    }

    @Override
    public Veterinario buscarPorCpf(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            String sqlBuscarPorCpf = "SELECT * FROM veterinarios v INNER JOIN enderecos_veterinarios ev ON v.cpf = ev.cpf_veterinario WHERE v.cpf = ?";
            try (var pstmt = conn.prepareStatement(sqlBuscarPorCpf)) {
                pstmt.setString(1, cpf);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int index = 1;
                    Veterinario veterinario = new Veterinario();
                    index = mapearResultSetParaEntidade(veterinario, rs, index);

                    Endereco endereco = mapearResultSetParaEndereco(rs, index);
                    veterinario.setEndereco(endereco);

                    return veterinario;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
        }

        return null;
    }

    @Override
    public boolean estaCadastrado(String cpf) {
        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT 1 FROM veterinarios WHERE cpf = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cpf);
                var rs = stmt.executeQuery();

                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar se o veterinário está cadastrado: " + e.getMessage());
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
    public List<Veterinario> listarTodos() {
        List<Veterinario> veterinarios = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sqlListarTodos = "SELECT * FROM veterinarios v INNER JOIN enderecos_veterinarios ev ON v.cpf = ev.cpf_veterinario";
            try (var pstmt = conn.prepareStatement(sqlListarTodos)) {
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int index = 1;
                    Veterinario veterinario = new Veterinario();
                    index = mapearResultSetParaEntidade(veterinario, rs, index);

                    Endereco endereco = mapearResultSetParaEndereco(rs, index);
                    veterinario.setEndereco(endereco);

                    veterinarios.add(veterinario);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar ao banco de dados: " + ex.getMessage());
            return null;
        }

        return veterinarios;
    }

    @Override
    public Veterinario inserir(Veterinario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlInsercaoVeterinarios = "INSERT INTO veterinarios (cpf, nome, email, telefone, data_cadastro, data_nascimento, ativo, especialidade, crmv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (var stmt = conn.prepareStatement(sqlInsercaoVeterinarios)) {
                int index = 1;
                stmt.setString(index++, entidade.getCpf());
                stmt.setString(index++, entidade.getNome());
                stmt.setString(index++, entidade.getEmail());
                stmt.setString(index++, entidade.getTelefone());
                stmt.setString(index++, entidade.getDataDeCadastro().toString());
                stmt.setString(index++, entidade.getDataDeNascimento().toString());
                stmt.setBoolean(index++, entidade.getAtivo());
                stmt.setString(index++, entidade.getEspecialidade());
                stmt.setString(index, entidade.getCRMV());

                stmt.executeUpdate();
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Erro ao inserir veterinário " + ex.getMessage());
                return null;
            }

            String sqlEndereco = "INSERT INTO enderecos_veterinarios (estado, cidade, rua, numero, complemento, cpf_veterinario) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco)) {
                Endereco endereco = entidade.getEndereco();
                int index = 1;
                pstmtEndereco.setString(index++, endereco.getEstado().toString());
                pstmtEndereco.setString(index++, endereco.getCidade());
                pstmtEndereco.setString(index++, endereco.getRua());
                pstmtEndereco.setInt(index++, endereco.getNumero());
                pstmtEndereco.setString(index++, endereco.getComplemento());
                pstmtEndereco.setString(index, entidade.getCpf());
                pstmtEndereco.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao inserir endereço: " + e.getMessage());
                conn.rollback();
                return null;
            }

            conn.commit();

            return entidade;
        } catch (Exception e) {
            System.out.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void atualizar(Veterinario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sqlAtualizacaoVeterinarios = "UPDATE veterinarios SET nome = ?, email = ?, telefone = ?, data_nascimento = ?, especialidade = ?, crmv = ? WHERE cpf = ?";
            try (var stmt = conn.prepareStatement(sqlAtualizacaoVeterinarios)) {
                int index = 1;
                stmt.setString(index++, entidade.getNome());
                stmt.setString(index++, entidade.getEmail());
                stmt.setString(index++, entidade.getTelefone());
                stmt.setString(index++, entidade.getDataDeNascimento().toString());
                stmt.setString(index++, entidade.getEspecialidade());
                stmt.setString(index++, entidade.getCRMV());
                stmt.setString(index, entidade.getCpf());

                stmt.executeUpdate();
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Erro ao atualizar veterinário: " + ex.getMessage());
                return;
            }

            String sqlEndereco = "UPDATE enderecos_veterinarios SET estado = ?, cidade = ?, rua = ?, numero = ?, complemento = ? WHERE cpf_veterinario = ?";
            try (PreparedStatement pstmtEndereco = conn.prepareStatement(sqlEndereco)) {
                Endereco endereco = entidade.getEndereco();
                int index = 1;
                pstmtEndereco.setString(index++, endereco.getEstado().toString());
                pstmtEndereco.setString(index++, endereco.getCidade());
                pstmtEndereco.setString(index++, endereco.getRua());
                pstmtEndereco.setInt(index++, endereco.getNumero());
                pstmtEndereco.setString(index++, endereco.getComplemento());
                pstmtEndereco.setString(index, entidade.getCpf());

                pstmtEndereco.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Erro ao atualizar endereço: " + e.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (Exception e) {
            System.out.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void excluir(Veterinario entidade) {

    }

    @Override
    public int mapearResultSetParaEntidade(Veterinario entidade, ResultSet rs, Integer index) throws SQLException {
        index = mapearDadosUsuarioDoResultSet(entidade, rs, index);
        entidade.setEspecialidade(rs.getString(index++));
        entidade.setCRMV(rs.getString(index++));

        return index;
    }
}
