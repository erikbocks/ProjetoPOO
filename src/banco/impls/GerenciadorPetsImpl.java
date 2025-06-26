package banco.impls;

import banco.GerenciadorPets;
import entidades.Cliente;
import entidades.Pet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<Pet> pets = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT * FROM pets p INNER JOIN clientes c on c.cpf = p.tutor;";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Pet pet = new Pet();
                    pet.setId(resultSet.getInt(1));
                    pet.setNome(resultSet.getString(2));
                    pet.setEspecie(Pet.Especie.valueOf(resultSet.getString(3)));
                    pet.setDataDeNascimento(LocalDateTime.parse(resultSet.getString(4)));
                    pet.setRaca(resultSet.getString(5));
                    pet.setSexo(Pet.Sexo.valueOf(resultSet.getString(6)));

                    Cliente cliente = new Cliente();
                    cliente.setCpf(resultSet.getString(8));
                    cliente.setNome(resultSet.getString(9));
                    cliente.setEmail(resultSet.getString(10));
                    cliente.setTelefone(resultSet.getString(11));
                    cliente.setDataDeCadastro(LocalDateTime.parse(resultSet.getString(12)));
                    cliente.setDataDeNascimento(LocalDateTime.parse(resultSet.getString(13)));
                    cliente.setAtivo(resultSet.getBoolean(14));
                    pet.setTutor(cliente);

                    pets.add(pet);

                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar pets: " + ex.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }

        return pets;
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
