package banco;

import entidades.Consulta;

import java.time.LocalDateTime;
import java.util.List;

public interface GerenciadorConsultas extends GerenciadorBase<Consulta> {
    List<Consulta> listarConsultasPorPet(Integer idPet);

    List<Consulta> listarConsultasPorCliente(String cpfCliente);

    List<Consulta> buscarConsultaPorVeterinario(String cpfVeterinario);

    List<Consulta> buscarConsultaPorData(LocalDateTime data);

    List<Consulta> buscarConsultaPorStatus(Consulta.Status status);
}
