import entidades.Cliente;
import entidades.Endereco;
import entidades.Funcionario;
import entidades.ItemVenda;
import entidades.Pet;
import entidades.Produto;
import entidades.Usuario;
import entidades.Venda;
import servicos.Leitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static Leitor leitor = new Leitor();
    public static List<Funcionario> funcionarios = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<Pet> pets = new ArrayList<>();
    public static List<Produto> produtos = new ArrayList<>();
    public static List<Venda> vendas = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

        funcionarios.add(new Funcionario("11111111111", null, null, null, "Roberson", "senha", null));
        clientes.add(new Cliente("22222222222", null, null, null, "Bock", null));
        pets.add(new Pet("Tobby", Pet.Especie.CACHORRO, null, null, Pet.Sexo.MACHO, clientes.get(0)));
        produtos.add(new Produto("Ração", "Ração para cachorro", 10, 50.0, Produto.TipoProduto.ALIMENTO));
        produtos.add(new Produto("Banho", "Banho para cachorro", 1, 100.0, Produto.TipoProduto.SERVICO));
        produtos.add(new Produto("Vacina", "Vacina para cachorro", 1, 200.0, Produto.TipoProduto.MEDICAMENTO));
        produtos.add(new Produto("Coleira", "Coleira para cachorro", 10, 20.0, Produto.TipoProduto.ACESSORIO));
        produtos.add(new Produto("Brinquedo", "Brinquedo para cachorro", 10, 30.0, Produto.TipoProduto.BRINQUEDO));
        vendas.add(new Venda(LocalDateTime.now(), clientes.get(0), funcionarios.get(0)));

        while (true) {
            mostrarMenu();

            opcao = leitor.lerInt("Qual opção você gostaria de selecionar?");

            switch (opcao) {
                case 0:
                    System.out.println("Saindo do sistema...");
                    return;
                case 1:
                    cadastrarFuncionario();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    cadastrarPet();
                    break;
                case 4:
                    cadastrarProduto();
                    break;
                case 5:
                    cadastrarVenda();
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("""
                ====================== PET SHOP BOCK E VINI =====================
                
                1. Cadastrar funcionário.
                2. Cadastrar cliente.
                3. Cadastrar pet.
                4. Cadastrar produto.
                5. Cadastrar venda.
                0. Sair
                
                =================================================================
                """);
    }

    private static void cadastrarFuncionario() {
        System.out.println("Boas vindas ao cadastro de funcionário!");

        String cpf;
        while (true) {
            cpf = leitor.lerCPF("Digite o CPF do funcionário");

            if (cpfCadastrado(cpf, funcionarios)) {
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

        Funcionario novoFuncionario = new Funcionario(cpf, celular, dataDeNascimento, email, nome, senha, endereco);
        funcionarios.add(novoFuncionario);

        System.out.println("Funcionário " + novoFuncionario.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        System.out.println("Boas vindas ao cadastro de cliente!");
        System.out.println("Por favor, se autentique.");

        Funcionario funcionario;

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

        funcionario = funcionarios.stream().filter(f -> f.getCpf().equals(cpfDoFuncionario)).findFirst().orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        if (!autenticarFuncionario(funcionario)) {
            System.err.println("Número máximo de tentativas atingido. Cancelando cadastro de cliente.");
            return;
        }

        System.out.println("Boas vindas, " + funcionario.getNome() + "!");

        String cpf = leitor.lerCPF("Digite o CPF do cliente");
        String celular = leitor.lerCelular("Digite o celular do cliente");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do cliente");
        String nome = leitor.lerString("Digite o nome do cliente");
        String email = leitor.lerString("Digite o email do cliente");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do cliente");

        Cliente novoCliente = new Cliente(cpf, celular, dataDeNascimento, email, nome, endereco);
        clientes.add(novoCliente);

        System.out.println("Cliente " + novoCliente.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarPet() {
        System.out.println("Boas vindas ao cadastro de pet!");
        System.out.println("Por favor, se autentique.");

        Funcionario funcionario;

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

        funcionario = funcionarios.stream().filter(f -> f.getCpf().equals(cpfDoFuncionario)).findFirst().orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        if (!autenticarFuncionario(funcionario)) {
            System.err.println("Número máximo de tentativas atingido. Cancelando cadastro de pet.");
            return;
        }

        System.out.println("Boas vindas, " + funcionario.getNome() + "!");

        String cpfDoTutor = leitor.lerCPF("Digite o CPF do tutor");

        Cliente cliente = clientes.stream().filter(c -> c.getCpf().equals(cpfDoTutor)).findFirst().orElse(null);

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

        pets.add(novoPet);
        System.out.println("Pet " + novoPet.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarProduto() {
        System.out.println("Boas vindas ao cadastro de produto!");
        System.out.println("Por favor, se autentique.");

        Funcionario funcionario;

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

        funcionario = funcionarios.stream().filter(f -> f.getCpf().equals(cpfDoFuncionario)).findFirst().orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        if (!autenticarFuncionario(funcionario)) {
            System.err.println("Número máximo de tentativas atingido. Cancelando cadastro de pet.");
            return;
        }

        System.out.println("Boas vindas, " + funcionario.getNome() + "!");

        String nome = leitor.lerString("Digite o nome do produto");
        int quantidade = leitor.lerInt("Digite a quantidade do produto (Se for do tipo SERVIÇO, inserir 1)");
        double valor = leitor.lerDouble("Digite o valor do produto (Ex: 10.99)");
        Produto.TipoProduto tipo = leitor.lerTipoDeProduto("""
                ==========================================
                Selecione o tipo do produto:
                
                SERVIÇO
                ALIMENTO
                MEDICAMENTO
                HIGIENE
                ACESSÓRIO
                BRINQUEDO
                
                ==========================================
                
                """);
        String descricao = leitor.lerString("Digite a descrição do produto");

        Produto novoProduto = new Produto(nome, descricao, quantidade, valor, tipo);
        produtos.add(novoProduto);

        System.out.println("Produto " + novoProduto.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarVenda() {
        System.out.println("Boas vindas ao cadastro de vendas!");
        System.out.println("Por favor, se autentique.");

        Funcionario funcionario;

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

        funcionario = funcionarios.stream().filter(f -> f.getCpf().equals(cpfDoFuncionario)).findFirst().orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        if (!autenticarFuncionario(funcionario)) {
            System.err.println("Número máximo de tentativas atingido. Cancelando cadastro de pet.");
            return;
        }

        System.out.println("Boas vindas, " + funcionario.getNome() + "!");

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        Cliente cliente = clientes.stream().filter(c -> c.getCpf().equals(cpfDoCliente)).findFirst().orElse(null);

        if (cliente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        LocalDateTime data = leitor.lerData("Digite a data da venda:");

        Venda venda = new Venda(data, cliente, funcionario);
        List<ItemVenda> itensSelecionados = new ArrayList<>();
        boolean vendaAberta = true;

        while (vendaAberta) {
            listarProdutosCadastrados();
            String codigoDoProduto = leitor.lerString("Digite o nome do produto que deseja adicionar à venda (ou 'sair' para finalizar):");

            if (codigoDoProduto.equalsIgnoreCase("sair") && itensSelecionados.isEmpty()) {
                System.out.println("Venda não pode ser finalizada sem produtos. Adicione pelo menos um produto.");
                continue;
            }

            if (codigoDoProduto.equalsIgnoreCase("sair")) {
                break;
            }

            Produto produto = produtos.stream().filter(p -> p.getCodigo().equalsIgnoreCase(codigoDoProduto)).findFirst().orElse(null);

            if (produto == null) {
                System.out.println("Produto não encontrado. Tente novamente.");
                continue;
            }

            System.out.printf("Produto [%s] selecionado!!\n", produto.getNome());

            int quantidade = leitor.lerInt("Digite a quantidade do produto que deseja adicionar à venda:");

            if (produto.getQuantidade() < quantidade) {
                System.out.println("Quantidade solicitada maior que a disponível. Tente novamente.");
                continue;
            }

            itensSelecionados.add(new ItemVenda(quantidade, produto.getCodigo()));
            produto.atualizarEstoque(quantidade);

            vendaAberta = leitor.lerBoolean("Deseja adicionar mais produtos à venda");
        }

        venda.fecharVenda(itensSelecionados, produtos);
        vendas.add(venda);
    }

    private static void listarProdutosCadastrados() {
        System.out.println("=========== LISTA DE PRODUTOS =============");
        produtos.stream().filter(p -> p.getQuantidade() > 0).forEach(System.out::println);
        System.out.println("============================================");
    }

    /**
     * Dá ao funcionário 3 chances de digitar a senha correta.
     *
     * @param funcionario que será autenticado.
     * @return true se a senha digitada estiver correta, false caso contrário.
     */
    private static boolean autenticarFuncionario(Funcionario funcionario) {
        for (int i = 0; i < 3; i++) {
            System.out.println("Tentativa " + (i + 1) + " de 3");
            String senha = leitor.lerString("Digite a sua senha");

            if (funcionario.autenticar(senha)) {
                return true;
            } else {
                System.err.println("Senha incorreta. Tente novamente.");
            }
        }

        return false;
    }

    /**
     * Valida se o CPF já foi cadastrado em alguma entidade da lista.
     *
     * @param cpf   a ser validado.
     * @param lista lista com as entidades.
     * @return true se o CPF já foi cadastrado, false caso contrário.
     */
    private static boolean cpfCadastrado(String cpf, List<? extends Usuario> lista) {
        return lista.stream().anyMatch(u -> u.getCpf().equals(cpf));
    }
}
