package servicos.impl;

import banco.GerenciadorVeterinarios;
import entidades.Endereco;
import entidades.Veterinario;
import servicos.Leitor;
import servicos.ServicoVeterinario;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoVeterinarioImpl implements ServicoVeterinario {
    private Leitor leitor;
    private GerenciadorVeterinarios gerenciadorVeterinarios;

    public ServicoVeterinarioImpl(Leitor leitor, GerenciadorVeterinarios gerenciadorVeterinarios) {
        this.leitor = leitor;
        this.gerenciadorVeterinarios = gerenciadorVeterinarios;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - VETERINARIO ===============================
                
                1. Cadastrar veterinário.
                2. Listar veterinários.
                3. Buscar veterinários por especialidade.
                4. Atualizar veterinário.
                5. Desativar veterinário.
                6. Excluir veterinário.
                0. Voltar.
                
                ====================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0:
                System.out.println("Retornando ao menu principal...");
                break;
            case 1:
                cadastrarVeterinario();
                break;
            case 2:
                listarVeterinarios();
                break;
            case 3:
                buscarVeterinariosPorEspecialidade();
                break;
            case 4:
                atualizarVeterinario();
                break;
            case 5:
                desativarVeterinario();
                break;
            case 6:
                excluirVeterinario();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }

    }

    @Override
    public void cadastrarVeterinario() {
        System.out.println("Boas vindas ao cadastro de veterinário!");

        String cpf;
        while (true) {
            cpf = leitor.lerCPF("Digite o CPF do novo funcionário");

            if (gerenciadorVeterinarios.estaCadastrado(cpf)) {
                System.err.println("CPF já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        String nome = leitor.lerString("Digite o nome do veterinário");
        String email = leitor.lerString("Digite o email do veterinário");
        String telefone = leitor.lerString("Digite o telefone do veterinário");
        String especialidade = leitor.lerString("Digite a especialidade do veterinário");
        LocalDateTime dataNascimento = leitor.lerData("Digite a data de nascimento do veterinário");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do veterinário");

        String crmv;
        while (true) {
            crmv = leitor.lerCRMV("Digite o CRMV do veterinário:");

            if (gerenciadorVeterinarios.verificarCRMV(crmv)) {
                System.err.println("CRMV já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        Veterinario veterinario = new Veterinario(cpf, telefone, dataNascimento, email, nome, endereco, especialidade, crmv);

        gerenciadorVeterinarios.inserir(veterinario);
        System.out.println("Veterinário cadastrado com sucesso!");
    }

    @Override
    public void listarVeterinarios() {
        List<Veterinario> veterinarios = gerenciadorVeterinarios.listarTodos();
        System.out.println("=================================== LISTA DE VETERINÁRIOS ===================================");
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado.");
        } else {
            for (Veterinario veterinario : veterinarios) {
                System.out.println(veterinario);
            }
        }
        System.out.println("===============================================================================================");
    }

    @Override
    public void buscarVeterinariosPorEspecialidade() {
        System.out.println("Boas vindas à busca de veterinários por especialidade!");

        String especialidade = leitor.lerString("Digite a especialidade que deseja buscar");

        List<Veterinario> veterinarios = gerenciadorVeterinarios.buscarPorEspecialidade(especialidade);

        System.out.println("=================================== RESULTADOS DA BUSCA ===================================");
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário encontrado com a especialidade: " + especialidade);
        } else {
            for (Veterinario veterinario : veterinarios) {
                System.out.println(veterinario);
            }
        }
        System.out.println("===============================================================================================");
    }

    @Override
    public void atualizarVeterinario() {

    }

    @Override
    public void desativarVeterinario() {

    }

    @Override
    public void excluirVeterinario() {

    }
}
