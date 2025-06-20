package banco.impls;

import banco.GerenciadorPets;
import entidades.Pet;

import java.sql.SQLException;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class GerenciadorPetsImpl implements GerenciadorPets {
    @Override
    public List<Pet> listarPorTutor(String cpfTutor) {
        return List.of();
    }

    @Override
    public Pet buscarPorNome(String nome, String cpfTutor) {
        return null;
    }

    @Override
    public void atualizarDataDeNascimento(String nome, String cpfTutor, String novaDataDeNascimento) {

    }

    @Override
    public List<Pet> listarTodos() {
        return List.of();
    }

    @Override
    public Pet inserir(Pet entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO pets (nome, especie, raca, data_nascimento, sexo, tutor) VALUES (?, ?, ?, ?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, entidade.getNome());
                preparedStatement.setString(2, entidade.getEspecie().name());
                preparedStatement.setString(3, entidade.getRaca());
                preparedStatement.setString(4, entidade.getDataDeNascimento().toString());
                preparedStatement.setString(5, entidade.getSexo().name());
                preparedStatement.setString(6, entidade.getTutor().getCpf());

                preparedStatement.executeUpdate();

                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    entidade.setId(id);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao inserir pet: " + ex.getMessage());
                conn.rollback();
                return null;
            }

            for (String observacao : entidade.getObservacoes()) {
                String sqlObs = "INSERT INTO observacoes_pets (pet_id, observacao) VALUES (?, ?)";
                try (var preparedStatementObs = conn.prepareStatement(sqlObs)) {
                    preparedStatementObs.setInt(1, entidade.getId());
                    preparedStatementObs.setString(2, observacao);
                    preparedStatementObs.executeUpdate();
                } catch (SQLException ex) {
                    System.err.println("Erro ao inserir observação do pet: " + ex.getMessage());
                    conn.rollback();
                    return null;
                }
            }

            conn.commit();

            return entidade;
        } catch (SQLException e) {
            System.out.println("Não foi possível inserir o pet: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void atualizar(Pet entidade) {

    }

    @Override
    public void excluir(Pet entidade) {

    }
}
