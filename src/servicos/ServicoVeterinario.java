package servicos;

public interface ServicoVeterinario {
    void mostrarMenu();

    void cadastrarVeterinario();

    void listarVeterinarios();

    void buscarVeterinariosPorEspecialidade();

    void atualizarVeterinario();

    void desativarVeterinario();

    void excluirVeterinario();
}
