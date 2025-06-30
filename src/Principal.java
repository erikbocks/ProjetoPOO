import banco.impls.GerenciadorClientesImpl;
import banco.impls.GerenciadorConsultasImpl;
import banco.impls.GerenciadorFuncionariosImpl;
import banco.impls.GerenciadorPetsImpl;
import banco.impls.GerenciadorProdutosImpl;
import banco.impls.GerenciadorVendasImpl;
import banco.impls.GerenciadorVeterinariosImpl;
import entidades.Consulta;
import entidades.Funcionario;
import entidades.Prontuario;
import entidades.Usuario;
import servicos.Leitor;
import servicos.ServicoCliente;
import servicos.ServicoConsulta;
import servicos.ServicoFuncionario;
import servicos.ServicoPet;
import servicos.ServicoProduto;
import servicos.ServicoVenda;
import servicos.ServicoVeterinario;
import servicos.impl.ServicoClienteImpl;
import servicos.impl.ServicoConsultaImpl;
import servicos.impl.ServicoFuncionarioImpl;
import servicos.impl.ServicoPetImpl;
import servicos.impl.ServicoProdutoImpl;
import servicos.impl.ServicoVendaImpl;
import servicos.impl.ServicoVeterinarioImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Principal {
    public Leitor leitor = new Leitor();
    private ServicoFuncionario servicoFuncionario = new ServicoFuncionarioImpl(leitor, new GerenciadorFuncionariosImpl());
    private ServicoCliente servicoCliente = new ServicoClienteImpl(leitor, new GerenciadorClientesImpl());
    private ServicoProduto servicoProduto = new ServicoProdutoImpl(leitor, new GerenciadorProdutosImpl());
    private ServicoPet servicoPet = new ServicoPetImpl(leitor, new GerenciadorPetsImpl(), new GerenciadorClientesImpl());
    private ServicoVeterinario servicoVeterinario = new ServicoVeterinarioImpl(leitor, new GerenciadorVeterinariosImpl());
    private ServicoVenda servicoVenda = new ServicoVendaImpl(leitor, new GerenciadorVendasImpl(), new GerenciadorClientesImpl(), new GerenciadorFuncionariosImpl(), new GerenciadorProdutosImpl());
    private ServicoConsulta servicoConsulta = new ServicoConsultaImpl(leitor, new GerenciadorConsultasImpl(), new GerenciadorPetsImpl(), new GerenciadorClientesImpl(), new GerenciadorVeterinariosImpl());
    public List<Prontuario> prontuarios = new ArrayList<>();

    public void executar() {
        Funcionario funcionarioLogado = null;

        System.out.println("Boas vindas ao Gerenciador de PetShops do Böck!!");
        while (funcionarioLogado == null) {
            try {
                funcionarioLogado = servicoFuncionario.autenticarFuncionario();
            } catch (RuntimeException e) {
                System.err.println("Não foi possível autenticar o funcionário.");
            }
        }

        int opcaoEntidade;

        while (true) {
            mostrarMenuEntidades();

            opcaoEntidade = leitor.lerInt("Qual entidade você gostaria de selecionar?");

            switch (opcaoEntidade) {
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                case 1:
                    servicoFuncionario.mostrarMenu();
                    break;
                case 2:
                    servicoCliente.mostrarMenu();
                    break;
                case 3:
                    servicoPet.mostrarMenu();
                    break;
                case 4:
                    servicoProduto.mostrarMenu();
                    break;
                case 5:
                    servicoVeterinario.mostrarMenu();
                    break;
                case 6:
                    servicoConsulta.mostrarMenu();
                    break;
                case 7:
                    servicoVenda.mostrarMenu();
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void mostrarMenuEntidades() {
        System.out.println("""
                ============================= MENU - ENTIDADES ==================================
                
                1. Funcionário
                2. Cliente
                3. Pet
                4. Produto
                5. Veterinário
                6. Consulta
                7. Venda
                8. Prontuário
                0. Sair do Programa.
                
                =================================================================================
                """);
    }

    private void gerarProntuario() {
        System.out.println("Boas vindas à geração de Prontuário!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        String codigoDaConsulta = leitor.lerString("Digite o código da consulta escolhida");

        Optional<Consulta> possivelConsulta = Optional.empty();

        if (possivelConsulta.isEmpty()) {
            System.err.println("Consulta não encontrada. Tente novamente.");
            return;
        }

        Consulta consulta = possivelConsulta.get();

        Prontuario prontuario = consulta.gerarProntuario();

        prontuarios.add(prontuario);
    }

    private void listarProntuarios() {
        System.out.println("=========== LISTA DE PRONTUÁRIOS =============");
        prontuarios.forEach(System.out::println);
        System.out.println("===============================================");
    }

    /**
     * Encontra o funcionário pelo CPF digitado e o autentica por meio de sua senha.
     *
     * @return o funcionário encontrado, null caso não encontre.
     */
    private Funcionario autenticarFuncionario() {
        return null;
    }

    /**
     * Valida se o CPF já foi cadastrado em alguma entidade da lista.
     *
     * @param cpf a ser validado.
     * @param lista lista com as entidades.
     * @return true se o CPF já foi cadastrado, false caso contrário.
     */
    private boolean cpfCadastrado(String cpf, List<? extends Usuario> lista) {
        return lista.stream().anyMatch(u -> u.getCpf().equals(cpf));
    }
}
