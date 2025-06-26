package servicos.impl;

import banco.GerenciadorClientes;
import banco.GerenciadorPets;
import entidades.Cliente;
import entidades.Pet;
import servicos.Leitor;
import servicos.ServicoPet;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
            case 2:
                cadastrarObservacaoPet();
                break;
            case 3:
                listarObservacoesPet();
                break;
            case 4:
                listarPets();
                break;
            case 5:
                listarPetsDoTutor();
                break;
            case 6:
                atualizarPet();
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
        Pet.Especie especie = leitor.lerEspecieDePet(String.format("""
                ==========================================
                Selecione a espécie do pet:
                
                %s
                
                ==========================================
                
                """, Arrays.toString(Pet.Especie.values())));

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
        System.out.println("Boas vindas ao cadastro de observação de pet!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoTutor);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        List<Pet> petsDoCliente = gerenciadorPets.listarPorTutor(cpfDoTutor);

        if (petsDoCliente.isEmpty()) {
            System.out.println("Nenhum pet encontrado para o tutor com CPF: " + cpfDoTutor);
            return;
        }

        System.out.println("Selecione o pet para o qual deseja adicionar uma observação:\n");
        for (int i = 0; i < petsDoCliente.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, petsDoCliente.get(i).getNome());
        }
        int indicePet = leitor.lerInt("Digite o número do pet escolhido") - 1;

        if (indicePet < 0 || indicePet >= petsDoCliente.size()) {
            System.out.println("Opção inválida. Tente novamente.");
            return;
        }

        Optional<Pet> optPet = Optional.ofNullable(petsDoCliente.get(indicePet));

        if (optPet.isEmpty()) {
            System.out.println("Pet não encontrado. Tente novamente.");
            return;
        }

        Pet pet = optPet.get();

        boolean desejaAdicionarObservacao = true;
        List<String> observacoes = pet.getObservacoes();

        while (desejaAdicionarObservacao) {
            String observacao = leitor.lerString("Digite a observação");

            if (observacao.isBlank()) {
                System.out.println("Observação não pode ser vazia. Tente novamente.");
                continue;
            }

            observacoes.add(observacao);
            desejaAdicionarObservacao = leitor.lerBoolean("Deseja adicionar mais observações?");
        }

        gerenciadorPets.adicionarObservacoes(pet, observacoes);
        System.out.println("Observações adicionadas com sucesso ao pet " + pet.getNome() + "!");
    }

    @Override
    public void listarObservacoesPet() {
        System.out.println("Boas vindas à listagem de observações de pet!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoTutor);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        List<Pet> petsDoTutor = gerenciadorPets.listarPorTutor(cpfDoTutor);

        if (petsDoTutor.isEmpty()) {
            System.out.println("Nenhum pet encontrado para o tutor com CPF: " + cpfDoTutor);
            return;
        }

        System.out.println("Selecione o pet para o qual deseja listar as observações:\n");
        for (int i = 0; i < petsDoTutor.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, petsDoTutor.get(i).getNome());
        }
        int indicePet = leitor.lerInt("Digite o número do pet escolhido") - 1;

        if (indicePet < 0 || indicePet >= petsDoTutor.size()) {
            System.out.println("Opção inválida. Tente novamente.");
            return;
        }

        Pet pet = petsDoTutor.get(indicePet);
        List<String> observacoes = pet.getObservacoes();

        if (observacoes.isEmpty()) {
            System.out.println("Nenhuma observação encontrada para o pet " + pet.getNome());
        } else {
            System.out.println("Observações do pet " + pet.getNome() + ":\n");
            observacoes.forEach(System.out::println);
        }
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
    public void listarPetsDoTutor() {
        System.out.println("Boas vindas à listagem de pets do tutor!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoTutor);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        List<Pet> petsDoTutor = gerenciadorPets.listarPorTutor(cpfDoTutor);

        if (petsDoTutor.isEmpty()) {
            System.out.println("Nenhum pet encontrado para o tutor com CPF: " + cpfDoTutor);
        } else {
            System.out.println("Lista de pets do tutor " + cliente.getNome() + ":");
            petsDoTutor.forEach(System.out::println);
        }
    }

    @Override
    public void atualizarPet() {
        System.out.println("Boas vindas à atualização de pet!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoTutor);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        List<Pet> petsDoTutor = gerenciadorPets.listarPorTutor(cpfDoTutor);

        if (petsDoTutor.isEmpty()) {
            System.out.println("Nenhum pet encontrado para o tutor com CPF: " + cpfDoTutor);
            return;
        }

        System.out.println("Selecione o pet que deseja atualizar:\n");
        for (int i = 0; i < petsDoTutor.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, petsDoTutor.get(i).getNome());
        }
        int indicePet = leitor.lerInt("Digite o número do pet escolhido") - 1;

        if (indicePet < 0 || indicePet >= petsDoTutor.size()) {
            System.out.println("Opção inválida. Tente novamente.");
            return;
        }

        Pet pet = petsDoTutor.get(indicePet);

        String novoNome = leitor.lerString("Digite o novo nome do pet (ou deixe em branco para manter o atual)");
        if (!novoNome.isBlank()) {
            pet.setNome(novoNome);
        }

        String novaEspecieInput = leitor.lerString(String.format("""
                ==========================================
                Selecione a espécie do pet:
                
                %s
                
                ==========================================
                
                """, Arrays.toString(Pet.Especie.values())));

        if (!novaEspecieInput.isBlank()) {
            Pet.Especie novaEspecie = Pet.procurarEspeciePorNome(novaEspecieInput);

            if (novaEspecie != null && novaEspecie != pet.getEspecie()) {
                pet.setEspecie(novaEspecie);
            } else {
                System.out.println("Espécie inválida ou não alterada. Mantendo a espécie atual.");
            }
        }

        String novaRaca = leitor.lerString("Digite a nova raça do pet (ou deixe em branco para manter a atual)");
        if (!novaRaca.isBlank()) {
            pet.setRaca(novaRaca);
        }

        boolean desejaAtualizarNascimento = leitor.lerBoolean("Deseja atualizar a data de nascimento do pet");

        if (desejaAtualizarNascimento) {
            LocalDateTime novaDataDeNascimento = leitor.lerData("Digite a data de nascimento do pet");

            if (!novaDataDeNascimento.isEqual(pet.getDataDeNascimento())) {
                pet.setDataDeNascimento(novaDataDeNascimento);
            } else {
                System.out.println("Data de nascimento inalterada.");
            }
        }

        boolean desejaAtualizarSexo = leitor.lerBoolean("Deseja atualizar o sexo do pet");

        if (desejaAtualizarSexo) {
            Pet.Sexo novoSexo = leitor.lerSexoDePet("Digite o novo sexo do pet (ou deixe em branco para manter o atual)");
            if (novoSexo != pet.getSexo()) {
                pet.setSexo(novoSexo);
            } else {
                System.out.println("Sexo do pet inalterado.");
            }
        }

        gerenciadorPets.atualizar(pet);
        System.out.println("Pet " + pet.getNome() + " atualizado com sucesso!");
    }

    @Override
    public void atualizarDataDeNascimentoPet() {

    }

    @Override
    public void excluirPet() {

    }
}
