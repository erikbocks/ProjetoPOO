package banco;

import entidades.Pet;

import java.util.List;

public interface GerenciadorPets extends GerenciadorBase<Pet> {
    List<Pet> listarPorTutor(String cpfTutor);

    Pet buscarPorNome(String nome, String cpfTutor);

    void atualizarDataDeNascimento(String nome, String cpfTutor, String novaDataDeNascimento);
}
