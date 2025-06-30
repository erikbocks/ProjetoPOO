package servicos.impl;

import banco.GerenciadorClientes;
import banco.GerenciadorFuncionarios;
import banco.GerenciadorProdutos;
import banco.GerenciadorVendas;
import entidades.Cliente;
import entidades.Funcionario;
import entidades.ItemVenda;
import entidades.Produto;
import entidades.Venda;
import servicos.Leitor;
import servicos.ServicoVenda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicoVendaImpl implements ServicoVenda {
    private Leitor leitor;
    private GerenciadorVendas gerenciadorVendas;
    private GerenciadorClientes gerenciadorClientes;
    private GerenciadorFuncionarios gerenciadorFuncionarios;
    private GerenciadorProdutos gerenciadorProdutos;

    public ServicoVendaImpl(Leitor leitor, GerenciadorVendas gerenciadorVendas, GerenciadorClientes gerenciadorClientes, GerenciadorFuncionarios gerenciadorFuncionarios, GerenciadorProdutos gerenciadorProdutos) {
        this.leitor = leitor;
        this.gerenciadorVendas = gerenciadorVendas;
        this.gerenciadorClientes = gerenciadorClientes;
        this.gerenciadorFuncionarios = gerenciadorFuncionarios;
        this.gerenciadorProdutos = gerenciadorProdutos;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - VENDAS ===============================
                
                1. Cadastrar venda.
                2. Listar vendas.
                3. Buscar venda por código.
                4. Fechar venda.
                5. Cancelar venda.
                6. Listar vendas por status.
                7. Listar vendas por cliente.
                8. Listar vendas por funcionário.
                9. Listar vendas por data.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0 -> System.out.println("Retornando ao menu principal...");
            case 1 -> cadastrarVenda();
            case 2 -> listarVendas();
            case 3 -> buscarVendaPorCodigo();
            case 4 -> fecharVenda();
            case 5 -> cancelarVenda();
            case 6 -> listarVendasPorStatus();
            case 7 -> listarVendasPorCliente();
            case 8 -> listarVendasPorFuncionario();
            case 9 -> listarVendasPorData();
            default -> System.err.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public void cadastrarVenda() {
        System.out.println("Boas vindas ao cadastro de vendas!");

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        String cpfDoResponsavel = leitor.lerCPF("Digite o CPF do funcionário responsável pela venda");
        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpfDoResponsavel);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        LocalDateTime data = leitor.lerData("Digite a data da venda:");

        Venda venda = new Venda(data, cliente, funcionario);
        List<ItemVenda> itensSelecionados = new ArrayList<>();

        List<Produto> produtosDisponiveis = gerenciadorProdutos.listarProdutosDisponiveis();

        if (produtosDisponiveis == null || produtosDisponiveis.isEmpty()) {
            System.out.println("Nenhum produto disponível para venda. Tente novamente mais tarde.");
            return;
        }

        boolean vendaAberta = true;

        while (vendaAberta) {
            System.out.println("========================= LISTA DE PRODUTOS ======================");
            produtosDisponiveis.stream().filter(p -> p.getQuantidade() > 0).forEach(System.out::println);
            System.out.println("==================================================================");

            String codigoDoProduto = leitor.lerString("Digite o nome do produto que deseja adicionar à venda (ou 'sair' para finalizar)");

            if (codigoDoProduto.equalsIgnoreCase("sair") && itensSelecionados.isEmpty()) {
                System.out.println("Venda não pode ser finalizada sem produtos. Adicione pelo menos um produto.");
                continue;
            }

            if (codigoDoProduto.equalsIgnoreCase("sair")) {
                break;
            }

            Produto produto;

            produto = produtosDisponiveis.stream().filter(p -> p.getCodigo().equals(codigoDoProduto)).findFirst().orElse(null);

            if (produto == null) {
                System.out.println("Produto não encontrado. Tente novamente.");
                continue;
            }

            System.out.printf("Produto [%s] selecionado!!\n", produto.getNome());

            int quantidade;

            if (produto.getTipo() == Produto.TipoProduto.SERVICO) {
                quantidade = 1;
            } else {
                quantidade = leitor.lerInt("Digite a quantidade do produto que deseja adicionar à venda");

                if (produto.getQuantidade() < quantidade) {
                    System.out.println("Quantidade solicitada maior que a disponível. Tente novamente.");
                    continue;
                }
            }

            itensSelecionados.add(new ItemVenda(quantidade, produto.getCodigo(), produto.getValor()));

            produtosDisponiveis.stream().filter(p -> p.getCodigo().equals(codigoDoProduto))
                    .findFirst()
                    .ifPresent(p -> p.atualizarEstoque(quantidade));

            vendaAberta = leitor.lerBoolean("Deseja adicionar mais produtos à venda");
        }

        venda.setItens(itensSelecionados);
        venda.calcularTotal();

        gerenciadorVendas.fecharVenda(venda);

        System.out.println("Venda fechada com sucesso!");
        System.out.println("==========================");
        System.out.println("Código da venda: " + venda.getCodigo());
        System.out.println("Valor total: R$ " + venda.getValor());
        System.out.println("==========================");
    }

    @Override
    public void listarVendas() {
        List<Venda> vendas = gerenciadorVendas.listarVendas();

        System.out.println("=========== LISTA DE VENDAS =============");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
        } else {
            vendas.forEach(System.out::println);
        }
        System.out.println("===========================================");
    }

    @Override
    public void buscarVendaPorCodigo() {
        String codigoVenda = leitor.lerString("Digite o código da venda que deseja buscar:");

        Venda venda = gerenciadorVendas.buscarVendaPorCodigo(codigoVenda);

        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        System.out.println("=========== DETALHES DA VENDA ============");
        System.out.println(venda);
        System.out.println("===========================================");
    }

    @Override
    public void fecharVenda() {
        System.out.println("Boas vindas ao fechamento de vendas!");

        String codigoVenda = leitor.lerString("Digite o código da venda que deseja fechar:");
        Venda venda = gerenciadorVendas.buscarVendaPorCodigo(codigoVenda);

        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        if (venda.getStatus() != Venda.Status.ABERTA) {
            System.out.println("Venda já está fechada ou cancelada.");
            return;
        }

        venda.setStatus(Venda.Status.FECHADA);

        gerenciadorVendas.atualizar(venda);
        System.out.println("Venda fechada com sucesso!");
    }

    @Override
    public void cancelarVenda() {
        System.out.println("Boas vindas ao cancelamento de vendas!");

        String codigoVenda = leitor.lerString("Digite o código da venda que deseja cancelar:");
        Venda venda = gerenciadorVendas.buscarVendaPorCodigo(codigoVenda);

        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        if (venda.getStatus() != Venda.Status.ABERTA) {
            System.out.println("Apenas vendas em aberto podem ser canceladas.");
            return;
        }

        venda.setStatus(Venda.Status.CANCELADA);
        gerenciadorVendas.atualizar(venda);
        System.out.println("Venda cancelada com sucesso!");
    }

    @Override
    public void listarVendasPorStatus() {
        String statusDigitado = leitor.lerString(String.format("""
                =========== STATUS DE VENDA =============
                %s
                =========================================
                """, Arrays.toString(Venda.Status.values())));

        Venda.Status status = Venda.procurarStatusPorNome(statusDigitado);

        if (status == null) {
            System.err.println("Status inválido. Tente novamente.");
            return;
        }

        List<Venda> vendas = gerenciadorVendas.listarVendasPorStatus(status);
        System.out.println("=========== LISTA DE VENDAS =============");
        if(vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada com o status: " + status);
        } else {
            vendas.forEach(System.out::println);
        }
        System.out.println("===========================================");
    }

    @Override
    public void listarVendasPorCliente() {
        System.out.println("Boas vindas à busca de vendas por cliente!");

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");
        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoCliente);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<Venda> vendas = gerenciadorVendas.listarVendasPorCliente(cliente.getCpf());

        System.out.println("=========== LISTA DE VENDAS =============");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para o cliente: " + cliente.getNome());
        } else {
            vendas.forEach(System.out::println);
        }
        System.out.println("===========================================");
    }

    @Override
    public void listarVendasPorFuncionario() {
        System.out.println("Boas vindas à busca de vendas por funcionário!");

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");
        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpfDoFuncionario);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        List<Venda> vendas = gerenciadorVendas.listarVendasPorFuncionario(funcionario.getCpf());
        System.out.println("=========== LISTA DE VENDAS =============");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para o funcionário: " + funcionario.getNome());
        } else {
            vendas.forEach(System.out::println);
        }
        System.out.println("===========================================");
    }

    @Override
    public void listarVendasPorData() {
        System.out.println("Boas vindas à busca de vendas por data!");

         LocalDateTime data = leitor.lerData("Digite a data desejada");

        List<Venda> vendas = gerenciadorVendas.listarVendasPorData(data);

        System.out.println("=========== LISTA DE VENDAS =============");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para a data: " + data);
        } else {
            vendas.forEach(System.out::println);
        }
        System.out.println("===========================================");
    }
}
