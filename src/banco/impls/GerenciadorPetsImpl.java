package banco.impls;

import banco.GerenciadorPets;
import entidades.Cliente;
import entidades.ObservacaoPet;
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
        List<Pet> pets = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT p.*, c.nome FROM pets p INNER JOIN clientes c on c.cpf = p.tutor WHERE p.tutor = ? ";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, cpfTutor);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer index = 1;
                    Pet pet = new Pet();
                    index = mapearResultSetParaEntidade(pet, resultSet, index);

                    Cliente cliente = new Cliente();
                    cliente.setCpf(resultSet.getString(index++));
                    cliente.setNome(resultSet.getString(index));

                    pet.setTutor(cliente);
                    pets.add(pet);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar pets por tutor: " + ex.getMessage());
                return null;
            }

            String sqlObs = "SELECT * FROM observacoes_pets WHERE pet_id = ?";
            try (var preparedStatementObs = conn.prepareStatement(sqlObs)) {
                for (Pet pet : pets) {
                    preparedStatementObs.setInt(1, pet.getId());
                    ResultSet rs = preparedStatementObs.executeQuery();

                    while (rs.next()) {
                        Integer index = 1;
                        ObservacaoPet observacao = new ObservacaoPet();
                        observacao.setId(rs.getInt(index++));
                        observacao.setPetId(rs.getInt(index++));
                        observacao.setObservacao(rs.getString(index++));
                        pet.adicionarObservacao(observacao);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar observações dos pets: " + ex.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }

        return pets;
    }

    @Override
    public void adicionarObservacoes(Pet pet, List<ObservacaoPet> observacoes) {
        String sql = "INSERT INTO observacoes_pets (pet_id, observacao) VALUES (?, ?)";
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            try (var preparedStatement = conn.prepareStatement(sql)) {
                for (ObservacaoPet obs : observacoes) {
                    Integer index = 1;
                    preparedStatement.setInt(index++, pet.getId());
                    preparedStatement.setString(index, obs.getObservacao());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();

                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao inserir observações do pet: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível inserir as observações do pet: " + e.getMessage());
        }
    }

    @Override
    public List<Pet> listarTodos() {
        List<Pet> pets = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            String sql = "SELECT p.*, c.nome FROM pets p INNER JOIN clientes c on c.cpf = p.tutor;";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Integer index = 1;
                    Pet pet = new Pet();
                    index = mapearResultSetParaEntidade(pet, resultSet, index);

                    Cliente cliente = new Cliente();
                    cliente.setCpf(resultSet.getString(index++));
                    cliente.setNome(resultSet.getString(index));

                    pet.setTutor(cliente);
                    pets.add(pet);
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar pets: " + ex.getMessage());
                return null;
            }

            String sqlObs = "SELECT * FROM observacoes_pets WHERE pet_id = ?";
            try (var preparedStatementObs = conn.prepareStatement(sqlObs)) {
                for (Pet pet : pets) {
                    preparedStatementObs.setInt(1, pet.getId());
                    ResultSet rs = preparedStatementObs.executeQuery();

                    while (rs.next()) {
                        Integer index = 1;
                        ObservacaoPet observacao = new ObservacaoPet();
                        observacao.setId(rs.getInt(index++));
                        observacao.setPetId(rs.getInt(index++));
                        observacao.setObservacao(rs.getString(index));
                        pet.adicionarObservacao(observacao);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao listar observações dos pets: " + ex.getMessage());
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
                Integer index = 1;
                preparedStatement.setString(index++, entidade.getNome());
                preparedStatement.setString(index++, entidade.getEspecie().name());
                preparedStatement.setString(index++, entidade.getRaca());
                preparedStatement.setString(index++, entidade.getDataDeNascimento().toString());
                preparedStatement.setString(index++, entidade.getSexo().name());
                preparedStatement.setString(index, entidade.getTutor().getCpf());

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

            for (ObservacaoPet observacao : entidade.getObservacoes()) {
                String sqlObs = "INSERT INTO observacoes_pets (pet_id, observacao) VALUES (?, ?)";
                try (var preparedStatementObs = conn.prepareStatement(sqlObs)) {
                    Integer index = 1;
                    preparedStatementObs.setInt(index++, entidade.getId());
                    preparedStatementObs.setString(index, observacao.getObservacao());
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
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sql = "UPDATE pets SET nome = ?, especie = ?, raca = ?, data_nascimento = ?, sexo = ? WHERE id = ?";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                Integer index = 1;
                preparedStatement.setString(index++, entidade.getNome());
                preparedStatement.setString(index++, entidade.getEspecie().name());
                preparedStatement.setString(index++, entidade.getRaca());
                preparedStatement.setString(index++, entidade.getDataDeNascimento().toString());
                preparedStatement.setString(index++, entidade.getSexo().name());
                preparedStatement.setInt(index, entidade.getId());

                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Erro ao atualizar pet: " + ex.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Não foi possível atualizar o pet: " + e.getMessage());
        }

    }

    @Override
    public void excluir(Pet entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM pets WHERE id = ?";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, entidade.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Erro ao excluir pet: " + ex.getMessage());
                conn.rollback();
                return;
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Não foi possível excluir o pet: " + e.getMessage());
        }
    }

    @Override
    public void excluirObservacao(Integer id) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM observacoes_pets WHERE id = ?";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Erro ao excluir observação do pet: " + ex.getMessage());
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível excluir a observação do pet: " + e.getMessage());
        }
    }

    @Override
    public int mapearResultSetParaEntidade(Pet entidade, ResultSet rs, Integer index) throws SQLException {
        entidade.setId(rs.getInt(index++));
        entidade.setNome(rs.getString(index++));
        entidade.setEspecie(Pet.Especie.valueOf(rs.getString(index++)));
        entidade.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
        entidade.setRaca(rs.getString(index++));
        entidade.setSexo(Pet.Sexo.valueOf(rs.getString(index++)));

        return index;
    }
}
