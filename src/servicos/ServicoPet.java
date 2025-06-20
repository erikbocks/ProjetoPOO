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
    List<String > listarObservacoesPet();

    /**
     * Lista todos os pets cadastrados.
     */
    List<Pet> listarPets();

    /**
     * Lista todos os pets de um tutor específico.
     */
    List<Pet> listarPetsDoTutor(String cpfTutor);

    /**
     * Atualiza os dados de um pet.
     */
    void atualizarPet();

    /**
     * Atualiza a data de nascimento de um pet.
     */
    void atualizarDataDeNascimentoPet();

    /**
     * Exclui um pet do sistema.
     */
    void excluirPet();
}
