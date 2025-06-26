package banco;

import entidades.ObservacaoPet;
import entidades.Pet;

import java.util.List;

public interface GerenciadorPets extends GerenciadorBase<Pet> {
    List<Pet> listarPorTutor(String cpfTutor);

    void adicionarObservacoes(Pet pet, List<ObservacaoPet> observacoes);

    void excluirObservacao(Integer id);
}
