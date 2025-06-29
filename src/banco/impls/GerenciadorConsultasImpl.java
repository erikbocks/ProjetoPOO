package banco.impls;

import banco.GerenciadorConsultas;
import entidades.Cliente;
import entidades.Consulta;
import entidades.Pet;
import entidades.Veterinario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorConsultasImpl implements GerenciadorConsultas {
    @Override
    public List<Consulta> listarConsultasPorPet(Integer idPet) {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf WHERE pet_id = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, idPet);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int index = 1;
                        Consulta consulta = new Consulta();
                        index = mapearResultSetParaEntidade(consulta, rs, index);

                        Pet pet = consulta.getPet();
                        pet.setId(rs.getInt(index++));
                        pet.setNome(rs.getString(index++));
                        pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                        pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                        pet.setRaca(rs.getString(index++));
                        pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                        Cliente cliente = new Cliente();
                        cliente.setCpf(rs.getString(index++));
                        cliente.setNome(rs.getString(index++));

                        Veterinario veterinario = consulta.getVeterinario();
                        veterinario.setNome(rs.getString(index));

                        pet.setTutor(cliente);
                        consulta.setPet(pet);

                        consultas.add(consulta);
                    }
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar consultas por pet: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Consulta> listarConsultasPorCliente(String cpfCliente) {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf WHERE cl.cpf = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cpfCliente);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int index = 1;
                        Consulta consulta = new Consulta();
                        index = mapearResultSetParaEntidade(consulta, rs, index);

                        Pet pet = consulta.getPet();
                        pet.setId(rs.getInt(index++));
                        pet.setNome(rs.getString(index++));
                        pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                        pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                        pet.setRaca(rs.getString(index++));
                        pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                        Cliente cliente = new Cliente();
                        cliente.setCpf(rs.getString(index++));
                        cliente.setNome(rs.getString(index++));

                        Veterinario veterinario = consulta.getVeterinario();
                        veterinario.setNome(rs.getString(index));

                        pet.setTutor(cliente);
                        consulta.setPet(pet);

                        consultas.add(consulta);
                    }
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar consultas por cliente: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível listar as consultas por pet: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Consulta> buscarConsultaPorVeterinario(String cpfVeterinario) {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf WHERE cpf_veterinario = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cpfVeterinario);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int index = 1;
                        Consulta consulta = new Consulta();
                        index = mapearResultSetParaEntidade(consulta, rs, index);

                        Pet pet = consulta.getPet();
                        pet.setId(rs.getInt(index++));
                        pet.setNome(rs.getString(index++));
                        pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                        pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                        pet.setRaca(rs.getString(index++));
                        pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                        Cliente cliente = new Cliente();
                        cliente.setCpf(rs.getString(index++));
                        cliente.setNome(rs.getString(index++));

                        Veterinario veterinario = consulta.getVeterinario();
                        veterinario.setNome(rs.getString(index));

                        pet.setTutor(cliente);
                        consulta.setPet(pet);

                        consultas.add(consulta);
                    }
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar consultas por veterinário: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Consulta> buscarConsultaPorData(LocalDateTime data) {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf WHERE data_consulta = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, data.toString());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int index = 1;
                        Consulta consulta = new Consulta();
                        index = mapearResultSetParaEntidade(consulta, rs, index);

                        Pet pet = consulta.getPet();
                        pet.setId(rs.getInt(index++));
                        pet.setNome(rs.getString(index++));
                        pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                        pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                        pet.setRaca(rs.getString(index++));
                        pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                        Cliente cliente = new Cliente();
                        cliente.setCpf(rs.getString(index++));
                        cliente.setNome(rs.getString(index++));

                        Veterinario veterinario = consulta.getVeterinario();
                        veterinario.setNome(rs.getString(index));

                        pet.setTutor(cliente);
                        consulta.setPet(pet);

                        consultas.add(consulta);
                    }
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar consultas por data: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Consulta> buscarConsultaPorStatus(Consulta.Status status) {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf WHERE status = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status.name());
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int index = 1;
                        Consulta consulta = new Consulta();
                        index = mapearResultSetParaEntidade(consulta, rs, index);

                        Pet pet = consulta.getPet();
                        pet.setId(rs.getInt(index++));
                        pet.setNome(rs.getString(index++));
                        pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                        pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                        pet.setRaca(rs.getString(index++));
                        pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                        Cliente cliente = new Cliente();
                        cliente.setCpf(rs.getString(index++));
                        cliente.setNome(rs.getString(index++));

                        Veterinario veterinario = consulta.getVeterinario();
                        veterinario.setNome(rs.getString(index));

                        pet.setTutor(cliente);
                        consulta.setPet(pet);

                        consultas.add(consulta);
                    }
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar consultas por status: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Consulta> listarTodos() {
        List<Consulta> consultas = new ArrayList<>();

        try (var conn = getConnectionWithFKEnabled()) {
            var sql = "SELECT c.*, p.*, cl.nome as nome_tutor, vet.nome as nome_veterinario FROM consultas c INNER JOIN pets p on c.pet_id = p.id INNER JOIN clientes cl on p.tutor = cl.cpf INNER JOIN veterinarios vet on c.cpf_veterinario = vet.cpf";
            try (var stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int index = 1;
                    Consulta consulta = new Consulta();
                    index = mapearResultSetParaEntidade(consulta, rs, index);

                    Pet pet = consulta.getPet();
                    pet.setId(rs.getInt(index++));
                    pet.setNome(rs.getString(index++));
                    pet.setEspecie(Pet.Especie.valueOf(rs.getString(index++).toUpperCase()));
                    pet.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
                    pet.setRaca(rs.getString(index++));
                    pet.setSexo(Pet.Sexo.valueOf(rs.getString(index++).toUpperCase()));

                    Cliente cliente = new Cliente();
                    cliente.setCpf(rs.getString(index++));
                    cliente.setNome(rs.getString(index++));

                    Veterinario veterinario = consulta.getVeterinario();
                    veterinario.setNome(rs.getString(index));

                    pet.setTutor(cliente);
                    consulta.setPet(pet);

                    consultas.add(consulta);
                }

                return consultas;
            } catch (SQLException e) {
                System.out.println("Erro ao listar todas as consultas: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Consulta inserir(Consulta entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            var sql = "INSERT INTO consultas (codigo, pet_id, cpf_veterinario, data_consulta, data_fechamento, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (var stmt = conn.prepareStatement(sql)) {
                int index = 1;
                stmt.setString(index++, entidade.getCodigo());
                stmt.setInt(index++, entidade.getPet().getId());
                stmt.setString(index++, entidade.getVeterinario().getCpf());
                stmt.setString(index++, entidade.getData().toString());
                stmt.setString(index++, entidade.getDataFechamento() != null ? entidade.getDataFechamento().toString() : null);
                stmt.setString(index, entidade.getStatus().name());

                stmt.executeUpdate();

                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                System.out.println("Erro ao inserir consulta: " + ex.getMessage());
                return null;
            }

            return entidade;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void atualizar(Consulta entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            var sql = "UPDATE consultas SET pet_id = ?, cpf_veterinario = ?, data_consulta = ?, data_fechamento = ?, status = ? WHERE codigo = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                int index = 1;
                stmt.setInt(index++, entidade.getPet().getId());
                stmt.setString(index++, entidade.getVeterinario().getCpf());
                stmt.setString(index++, entidade.getData().toString());
                stmt.setString(index++, entidade.getDataFechamento() != null ? entidade.getDataFechamento().toString() : null);
                stmt.setString(index++, entidade.getStatus().name());
                stmt.setString(index, entidade.getCodigo());

                stmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro ao atualizar consulta: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Override
    public void excluir(Consulta entidade) {
        try (var conn = getConnectionWithFKEnabled()) {
            conn.setAutoCommit(false);

            var sql = "DELETE FROM consultas WHERE codigo = ?";
            try (var stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, entidade.getCodigo());
                stmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Erro ao excluir consulta: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Override
    public int mapearResultSetParaEntidade(Consulta entidade, ResultSet rs, Integer index) throws SQLException {
        entidade.setCodigo(rs.getString(index++));
        entidade.setPet(new Pet());
        entidade.getPet().setId(rs.getInt(index++));
        entidade.setVeterinario(new Veterinario());
        entidade.getVeterinario().setCpf(rs.getString(index++));
        entidade.setData(LocalDateTime.parse(rs.getString(index++)));
        entidade.setDataFechamento(rs.getString(index++) != null ? LocalDateTime.parse(rs.getString(index++)) : null);
        entidade.setStatus(Consulta.procurarStatusPorNome(rs.getString(index++)));
        return index;
    }
}
