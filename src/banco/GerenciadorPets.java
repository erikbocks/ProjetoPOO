package banco;

import entidades.Pet;

import java.util.List;

public interface GerenciadorPets extends GerenciadorBase<Pet> {
    List<Pet> listarPorTutor(String cpfTutor);

    void adicionarObservacoes(Pet pet, List<String> observacoes);

    Pet buscarPorNome(String nome, String cpfTutor);

    void atualizarDataDeNascimento(String nome, String cpfTutor, String novaDataDeNascimento);
}
