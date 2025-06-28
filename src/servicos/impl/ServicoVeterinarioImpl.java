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
                4. Buscar veterinário por CPF.
                5. Buscar veterinário por CRMV.
                6. Atualizar veterinário.
                7. Desativar veterinário.
                8. Excluir veterinário.
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
                buscarVeterinarioPorCPF();
                break;
            case 5:
                buscarVeterinarioPorCRMV();
                break;
            case 6:
                atualizarVeterinario();
                break;
            case 7:
                desativarVeterinario();
                break;
            case 8:
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
    public void buscarVeterinarioPorCPF() {
        System.out.println("Boas vindas à busca de veterinário por CPF!");

        String cpf = leitor.lerCPF("Digite o CPF do veterinário que deseja buscar");
        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCpf(cpf);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado com o CPF: " + cpf);
            return;
        }

        System.out.println("Veterinário encontrado.");
        System.out.println(veterinario);
    }

    @Override
    public void buscarVeterinarioPorCRMV() {
        System.out.println("Boas vindas à busca de veterinário por CRMV!");

        String crmv = leitor.lerCRMV("Digite o CRMV do veterinário que deseja buscar");
        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCRMV(crmv);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado com o CRMV: " + crmv);
            return;
        }

        System.out.println("Veterinário encontrado.");
        System.out.println(veterinario);
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
        System.out.println("Boas vindas à atualização de veterinário!");

        String cpf = leitor.lerCPF("Digite o CPF do veterinário que deseja atualizar");
        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCpf(cpf);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado com o CPF: " + cpf);
            return;
        }

        String nome = leitor.lerString("Digite o novo nome do veterinário (deixe em branco para não alterar)");
        if (!nome.isBlank() && !nome.equals(veterinario.getNome())) {
            veterinario.setNome(nome);
        }
        ;

        String email = leitor.lerString("Digite o novo email do veterinário (deixe em branco para não alterar)");
        if (!email.isBlank() && !email.equals(veterinario.getEmail())) {
            veterinario.setEmail(email);
        }

        String telefone = leitor.lerString("Digite o novo telefone do veterinário (deixe em branco para não alterar)");

        if (telefone.length() != 11) {
            System.err.println("Telefone inválido. Deve conter 11 dígitos.");
            return;
        }

        if (!telefone.isBlank() && !telefone.equals(veterinario.getTelefone())) {
            veterinario.setTelefone(telefone);
        }

        String especialidade = leitor.lerString("Digite a nova especialidade do veterinário (deixe em branco para não alterar)");
        if (!especialidade.isBlank() && !especialidade.equals(veterinario.getEspecialidade())) {
            veterinario.setEspecialidade(especialidade);
        }

        boolean desejaAtualizarNascimento = leitor.lerBoolean("Deseja atualizar a data de nascimento do veterinário");
        if (desejaAtualizarNascimento) {
            LocalDateTime dataNascimento = leitor.lerData("Digite a nova data de nascimento do veterinário");
            if (!dataNascimento.isEqual(veterinario.getDataDeNascimento())) {
                veterinario.setDataDeNascimento(dataNascimento);
            }
        }

        boolean desejaAtualizarEndereco = leitor.lerBoolean("Deseja atualizar o endereço do veterinário");
        if (desejaAtualizarEndereco) {
            Endereco endereco = leitor.lerEndereco("Digite o novo endereço do veterinário");
            if (!endereco.equals(veterinario.getEndereco())) {
                veterinario.setEndereco(endereco);
            }
        }

        gerenciadorVeterinarios.atualizar(veterinario);
        System.out.println("Veterinário atualizado com sucesso!");
    }

    @Override
    public void desativarVeterinario() {
        System.out.println("Boas vindas a desativação de veterinário!");

        String cpf = leitor.lerCPF("Digite o CPF do veterinário que deseja desativar");
        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCpf(cpf);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado com o CPF: " + cpf);
            return;
        }

        gerenciadorVeterinarios.desativar(veterinario.getCpf());
        System.out.println("Veterinário desativado com sucesso!");
    }

    @Override
    public void excluirVeterinario() {
        System.out.println("Boas vindas à exclusão de veterinário!");

        String cpf = leitor.lerCPF("Digite o CPF do veterinário que deseja excluir");
        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCpf(cpf);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado com o CPF: " + cpf);
            return;
        }
        boolean confirmacao = leitor.lerBoolean("Tem certeza que deseja excluir o veterinário");
        if (confirmacao) {
            gerenciadorVeterinarios.excluir(veterinario);
            System.out.println("Veterinário excluído com sucesso!");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }
}
