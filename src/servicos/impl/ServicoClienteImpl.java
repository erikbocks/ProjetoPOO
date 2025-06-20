package servicos.impl;

import banco.GerenciadorClientes;
import entidades.Cliente;
import entidades.Endereco;
import servicos.Leitor;
import servicos.ServicoCliente;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoClienteImpl implements ServicoCliente {
    private Leitor leitor;
    private GerenciadorClientes gerenciadorClientes;

    public ServicoClienteImpl(Leitor leitor, GerenciadorClientes gerenciadorClientes) {
        this.leitor = leitor;
        this.gerenciadorClientes = gerenciadorClientes;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - CLIENTE ===============================
                
                1. Cadastrar cliente.
                2. Listar todos os clientes ativos.
                3. Atualizar cliente.
                4. Atualizar endereço de cliente.
                5. Atualizar data de nascimento de cliente.
                6. Desativar cliente.
                7. Excluir cliente.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0:
                System.out.println("Retornando ao menu principal...");
                break;
            case 1:
                cadastrarCliente();
                break;
            case 2:
                List<Cliente> clientes = listarTodosAtivos();
                System.out.println("=========== LISTA DE CLIENTES =============");
                clientes.forEach(System.out::println);
                System.out.println("============================================");
                break;
            case 3:
                atualizarCliente();
                break;
            case 4:
                atualizarEndereco();
                break;
            case 5:
                atualizarDataDeNascimento();
                break;
            case 6:
                desativarCliente();
                break;
            case 7:
                removerCliente();
                break;
            default:
                System.err.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public Cliente cadastrarCliente() {
        String cpfDoCliente;
        while (true) {
            cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

            if (gerenciadorClientes.estaCadastrado(cpfDoCliente)) {
                System.err.println("CPF já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        String celular = leitor.lerCelular("Digite o celular do cliente");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do cliente");
        String nome = leitor.lerString("Digite o nome do cliente");
        String email = leitor.lerString("Digite o email do cliente");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do cliente");

        Cliente novoCliente = new Cliente(cpfDoCliente, celular, dataDeNascimento, email, nome, endereco);

        Cliente cliente = gerenciadorClientes.inserir(novoCliente);

        System.out.printf("Cliente [%s] cadastrado com sucesso!\n", cliente.getNome());

        return cliente;
    }

    @Override
    public List<Cliente> listarTodosAtivos() {
        return gerenciadorClientes.listarTodos();
    }

    @Override
    public void atualizarCliente() {
        String cpf = leitor.lerCPF("Digite o CPF do cliente à atualizar");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para o CPF digitado.");
            return;
        }

        String nome = leitor.lerString("Digite o novo nome para o cliente (caso queira manter o anterior, pressione ENTER)");
        String telefone = leitor.lerString("Digite o nome número de telefone para o cliente (caso queira manter o anterior, pressione ENTER)");
        String email = leitor.lerString("Digite o novo email do cliente (caso queira manter o anterior, pressione ENTER)");

        cliente.atualizarDados(nome, telefone, email);


        gerenciadorClientes.atualizar(cliente);

        System.out.println("Funcionário atualizado com sucesso!");
    }

    @Override
    public void atualizarEndereco() {
        String cpf = leitor.lerCPF("Digite o CPF do cliente à atualizar o endereço");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para o CPF digitado.");
            return;
        }

        Endereco novoEndereco = leitor.lerEndereco("Digite o novo endereço do cliente:");

        cliente.getEndereco().atualizarEndereco(novoEndereco);

        gerenciadorClientes.atualizarEndereco(cliente.getCpf(), cliente.getEndereco());

        System.out.println("Endereço do usuário atualizado com sucesso!!");
    }

    @Override
    public void atualizarDataDeNascimento() {
        String cpf = leitor.lerCPF("Digite o CPF do cliente a atualizar");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para o CPF digitado.");
            return;
        }

        LocalDateTime novaData = leitor.lerData("Digite a nova data de nascimento do cliente.");

        if (!cliente.getDataDeNascimento().isEqual(novaData)) {
            cliente.setDataDeNascimento(novaData);
        }

        gerenciadorClientes.atualizar(cliente);

        System.out.println("Data de nascimento atualizada com sucesso.");
    }

    @Override
    public void desativarCliente() {
        String cpf = leitor.lerCPF("Digite o CPF do cliente à desativar");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para o CPF digitado.");
            return;
        }

        gerenciadorClientes.desativar(cpf);
        System.out.println("Cliente desativado com sucesso!");
    }

    @Override
    public void removerCliente() {
        String cpf = leitor.lerCPF("Digite o CPF do cliente à remover");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpf);

        if (cliente == null) {
            System.err.println("Nenhum cliente encontrado para o CPF digitado.");
            return;
        }

        gerenciadorClientes.excluir(cliente);
        System.out.println("Cliente removido com sucesso!");

    }
}
