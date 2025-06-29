package servicos;

public interface ServicoConsulta {
    void mostrarMenu();

    void inserirConsulta();

    void listarConsultas();

    void listarConsultasPorPet();

    void listarConsultasPorCliente();

    void buscarConsultaPorVeterinario();

    void buscarConsultaPorData();

    void buscarConsultaPorStatus();

    void excluirConsulta();

    void atualizarConsulta();
}
