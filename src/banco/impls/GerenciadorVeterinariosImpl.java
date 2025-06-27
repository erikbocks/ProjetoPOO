package banco.impls;

import banco.GerenciadorVeterinarios;
import entidades.Endereco;
import entidades.Veterinario;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public Veterinario buscarPorCpf(String cpf) {
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
        return List.of();
    }

    @Override
    public Veterinario inserir(Veterinario entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            String sqlInsercaoVeterinarios = "INSERT INTO veterinarios (cpf, nome, email, telefone, data_cadastro, data_nascimento, ativo, especialidade, crmv) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (var stmt = conn.prepareStatement(sqlInsercaoVeterinarios)) {
                stmt.setString(1, entidade.getCpf());
                stmt.setString(2, entidade.getNome());
                stmt.setString(3, entidade.getEmail());
                stmt.setString(4, entidade.getTelefone());
                stmt.setString(5, entidade.getDataDeCadastro().toString());
                stmt.setString(6, entidade.getDataDeNascimento().toString());
                stmt.setBoolean(7, entidade.getAtivo());
                stmt.setString(8, entidade.getEspecialidade());
                stmt.setString(9, entidade.getCRMV());

                stmt.executeUpdate();
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Erro ao inserir veterinário " + ex.getMessage());
                return null;
            }

            String sqlEndereco = "INSERT INTO enderecos_veterinarios (estado, cidade, rua, numero, complemento, cpf_veterinario) VALUES (?, ?, ?, ?, ?, ?)";
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

            return entidade;
        } catch (Exception e) {
            System.out.println("Erro ao estabelecer conexão com o banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void atualizar(Veterinario entidade) {

    }

    @Override
    public void excluir(Veterinario entidade) {

    }
}
