package servicos;

import entidades.Pet;

import java.util.List;

public interface ServicoPet {
    /**
     * Mostra o menu de opções.
     */
    void mostrarMenu();

    /**
     * Cadastra um novo pet ao sistema.
     */
    void cadastrarPet();

    /**
     * Cadastra uma observação para um pet.
     */
    void cadastrarObservacaoPet();

    /**
     * Lista as observações de um pet.
     */
    void listarObservacoesPet();

    /**
     * Lista todos os pets cadastrados.
     */
    void listarPets();

    /**
     * Lista todos os pets de um tutor específico.
     */
    void listarPetsDoTutor();

    /**
     * Atualiza os dados de um pet.
     */
    void atualizarPet();

    /**
     * Exclui um pet do sistema.
     */
    void excluirPet();
}
