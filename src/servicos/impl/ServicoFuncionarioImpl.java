package servicos.impl;

import banco.GerenciadorFuncionarios;
import entidades.Endereco;
import entidades.Funcionario;
import servicos.Leitor;
import servicos.ServicoFuncionario;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoFuncionarioImpl implements ServicoFuncionario {
    private Leitor leitor;
    private GerenciadorFuncionarios gerenciadorFuncionarios;

    public ServicoFuncionarioImpl(Leitor leitor, GerenciadorFuncionarios gerenciadorFuncionarios) {
        this.leitor = leitor;
        this.gerenciadorFuncionarios = gerenciadorFuncionarios;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - FUNCIONÁRIO ===========================
                
                1. Cadastrar funcionário.
                2. Listar todos os funcionários ativos.
                3. Atualizar funcionário.
                4. Atualizar endereço de funcionário.
                5. Atualizar data de nascimento de funcionário.
                6. Desativar funcionário.
                7. Excluir funcionário.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0:
                System.out.println("Retornando ao menu principal...");
                break;
            case 1:
                cadastrarFuncionario();
                break;
            case 2:
                System.out.println("=========== LISTA DE FUNCIONÁRIOS =============");
                List<Funcionario> funcionarios = listarTodosAtivos();
                funcionarios.forEach(System.out::println);
                System.out.println("===============================================");
                break;
            case 3:
                atualizarFuncionario();
                break;
            case 4:
                atualizarEndereco();
                break;
            case 5:
                atualizarDataDeNascimento();
                break;
            case 6:
                desativarFuncionario();
                break;
            case 7:
                removerFuncionario();
                break;
            default:
                System.err.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public Funcionario autenticarFuncionario() {
        System.out.println("Por favor, se autentique.");

        String cpf = leitor.lerCPF("Digite o CPF do funcionário");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            throw new RuntimeException("Nenhum funcionário encontrado para esse CPF.");
        }

        int tentativasDeLogin = 0;

        while (tentativasDeLogin != 3) {
            System.out.println("Tentativa " + (tentativasDeLogin + 1) + " de 3");
            String senha = leitor.lerString("Digite a sua senha");

            if (funcionario.autenticar(senha)) {
                System.out.println("Boas vindas, " + funcionario.getNome() + "!");
                return funcionario;
            } else {
                System.err.println("Senha incorreta. Tente novamente.");
                tentativasDeLogin++;
            }
        }

        throw new RuntimeException("Número máximo de tentativas atingido. Cancelando operação.");
    }

    @Override
    public Funcionario cadastrarFuncionario() {
        String cpfNovoFuncionario;
        while (true) {
            cpfNovoFuncionario = leitor.lerCPF("Digite o CPF do novo funcionário");

            if (gerenciadorFuncionarios.estaCadastrado(cpfNovoFuncionario)) {
                System.err.println("CPF já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        String celular = leitor.lerCelular("Digite o celular do funcionário");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do funcionário");
        String nome = leitor.lerString("Digite o nome do funcionário");
        String email = leitor.lerString("Digite o email do funcionário");
        String senha = leitor.lerSenha("Digite a nova senha do funcionário");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do funcionário");

        Funcionario novoFuncionario = new Funcionario(cpfNovoFuncionario, celular, dataDeNascimento, email, nome, senha, endereco);

        gerenciadorFuncionarios.inserir(novoFuncionario);

        System.out.printf("Funcionário [%s] cadastrado com sucesso!", novoFuncionario.getNome());

        return novoFuncionario;
    }

    @Override
    public List<Funcionario> listarTodosAtivos() {
        return gerenciadorFuncionarios.listarTodos();
    }

    @Override
    public void atualizarFuncionario() {
        String cpf = leitor.lerCPF("Digite o CPF do funcionário à atualizar");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            System.err.println("Nenhum funcionário encontrado para o CPF digitado.");
            return;
        }

        String nome = leitor.lerString("Digite o novo nome para o funcionário (caso queira manter o anterior, pressione ENTER)");
        String telefone = leitor.lerString("Digite o nome número de telefone para o funcionário (caso queira manter o anterior, pressione ENTER)");
        String email = leitor.lerString("Digite o novo email do para o funcionário (caso queira manter o anterior, pressione ENTER)");
        String senha = leitor.lerSenha("Digite a nova senha do funcionário (caso queira manter a anterior, pressione ENTER 2x");

        funcionario.atualizarDados(nome, telefone, email, senha);

        gerenciadorFuncionarios.atualizar(funcionario);

        System.out.println("Funcionário atualizado com sucesso!");
    }

    @Override
    public void atualizarEndereco() {
        String cpf = leitor.lerCPF("Digite o CPF do funcionário à atualizar");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            System.err.println("Nenhum funcionário encontrado para o CPF digitado.");
            return;
        }

        Endereco novaoEndereco = leitor.lerEndereco("Digite o novo endereço do funcionário.");

        funcionario.getEndereco().atualizarEndereco(novaoEndereco);

        gerenciadorFuncionarios.atualizarEndereco(funcionario.getCpf(), funcionario.getEndereco());

        System.out.println("Endereço do usuário atualizado com sucesso!!");
    }

    @Override
    public void atualizarDataDeNascimento() {
        String cpf = leitor.lerCPF("Digite o CPF do funcionário a atualizar");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            System.err.println("Nenhum funcionário encontrado para o CPF digitado.");
            return;
        }

        LocalDateTime novaData = leitor.lerData("Digite a nova data de nascimento do funcionário.");

        if (!funcionario.getDataDeNascimento().isEqual(novaData)) {
            funcionario.setDataDeNascimento(novaData);
        }

        gerenciadorFuncionarios.atualizar(funcionario);

        System.out.println("Data de nascimento atualizada com sucesso.");
    }

    @Override
    public void desativarFuncionario() {
        String cpf = leitor.lerCPF("Digite o CPF do funcionário a ser desativado");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            System.err.println("Nenhum funcionário encontrado para o CPF digitado.");
            return;
        }

        gerenciadorFuncionarios.desativar(funcionario.getCpf());

        System.out.printf("Funcionário [%s] desativado com sucesso!\n", funcionario.getNome());
    }

    @Override
    public void removerFuncionario() {
        String cpf = leitor.lerCPF("Digite o CPF do funcionário a ser removido");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            System.err.println("Nenhum funcionário encontrado para o CPF digitado.");
            return;
        }

        gerenciadorFuncionarios.excluir(funcionario);

        System.out.printf("Funcionário [%s] excluído com sucesso.\n", funcionario.getNome());
    }
}
