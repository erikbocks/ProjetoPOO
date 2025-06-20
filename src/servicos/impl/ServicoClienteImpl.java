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
                2. Listar todos os sclientes ativos.
                3. Atualizar cliente.
                4. Atualizar endereço de cliente.
                5. Atualizar data de nascimento de cliente.
                6. Desativar cliente.
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

    }

    @Override
    public void atualizarEndereco() {

    }

    @Override
    public void atualizarDataDeNascimento() {

    }

    @Override
    public void desativarCliente() {

    }

    @Override
    public void removerCliente() {

    }
}
