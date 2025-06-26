package servicos.impl;

import banco.GerenciadorClientes;
import banco.GerenciadorPets;
import entidades.Cliente;
import entidades.Pet;
import servicos.Leitor;
import servicos.ServicoPet;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoPetImpl implements ServicoPet {
    private Leitor leitor;
    private GerenciadorPets gerenciadorPets;
    private GerenciadorClientes gerenciadorClientes;

    public ServicoPetImpl(Leitor leitor, GerenciadorPets gerenciadorPets, GerenciadorClientes gerenciadorClientes) {
        this.leitor = leitor;
        this.gerenciadorPets = gerenciadorPets;
        this.gerenciadorClientes = gerenciadorClientes;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - PET ===============================
                
                1. Cadastrar pet.
                2. Cadastrar observação de pet.
                3. Listar observações de pet.
                4. Listar pets.
                5. Listar pets do tutor.
                6. Atualizar pet.
                7. Atualizar data de nascimento do pet.
                8. Excluir pet.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0:
                System.out.println("Retornando ao menu principal...");
                break;
            case 1:
                cadastrarPet();
                break;
            case 4:
                listarPets();
                break;
            default:
                System.err.println("Opção inválida. Tente novamente.");
                break;
        }


    }

    @Override
    public void cadastrarPet() {
        System.out.println("Boas vindas ao cadastro de pet!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoTutor);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        String nome = leitor.lerString("Digite o nome do pet");
        Pet.Especie especie = leitor.lerEspecieDePet("""
                ==========================================
                Selecione a espécie do pet:
                
                CACHORRO
                GATO
                PEIXE
                PASSARO
                REPTIL
                ROEDOR
                OUTRO
                ==========================================
                
                """);

        String raca = leitor.lerString("Digite a raça do pet (Opcional)");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do pet");
        Pet.Sexo sexo = leitor.lerSexoDePet("Digite o sexo do pet");

        Pet novoPet = new Pet(nome, especie, raca, dataDeNascimento, sexo, cliente);

        boolean desejaAdicionarObservacao = leitor.lerBoolean("Deseja adicionar observações ao pet?");

        while (desejaAdicionarObservacao) {
            String observacao = leitor.lerString("Digite a observação");
            novoPet.adicionarObservacao(observacao);
            desejaAdicionarObservacao = leitor.lerBoolean("Deseja adicionar mais observações?");
        }

        Pet pet = gerenciadorPets.inserir(novoPet);

        if (pet == null) {
            System.err.println("Erro ao cadastrar o pet. Tente novamente.");
            return;
        }

        System.out.println("Pet " + pet.getNome() + " cadastrado com sucesso!");
    }

    @Override
    public void cadastrarObservacaoPet() {

    }

    @Override
    public void listarObservacoesPet() {
    }

    @Override
    public void listarPets() {
        List<Pet> pets = gerenciadorPets.listarTodos();

        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
        } else {
            System.out.println("Lista de pets cadastrados:");
            System.out.println("=========== LISTA DE FUNCIONÁRIOS =============");
            pets.forEach(System.out::println);
            System.out.println("===============================================");
        }
    }

    @Override
    public void listarPetsDoTutor(String cpfTutor) {
    }

    @Override
    public void atualizarPet() {

    }

    @Override
    public void atualizarDataDeNascimentoPet() {

    }

    @Override
    public void excluirPet() {

    }
}
