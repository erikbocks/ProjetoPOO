import entidades.Cliente;
import entidades.Consulta;
import entidades.Endereco;
import entidades.Funcionario;
import entidades.ItemVenda;
import entidades.Pet;
import entidades.Produto;
import entidades.Prontuario;
import entidades.Usuario;
import entidades.Venda;
import entidades.Veterinario;
import servicos.Leitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Principal {
    public static Leitor leitor = new Leitor();
    public static List<Funcionario> funcionarios = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    public static List<Pet> pets = new ArrayList<>();
    public static List<Produto> produtos = new ArrayList<>();
    public static List<Venda> vendas = new ArrayList<>();
    public static List<Veterinario> veterinarios = new ArrayList<>();
    public static List<Consulta> consultas = new ArrayList<>();
    public static List<Prontuario> prontuarios = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

        // Dados mockados para testes
        funcionarios.add(new Funcionario("11111111111", "11111111111", LocalDateTime.now(), "roberson@email.com", "Roberson", "senha", new Endereco("Rua 1", 123, "Centro", "São Paulo", Endereco.Estado.SC)));
        clientes.add(new Cliente("22222222222", "22222222222", LocalDateTime.now(), "roberson@email.com", "Bock", new Endereco("Rua 1", 123, "Centro", "São Paulo", Endereco.Estado.SC)));
        pets.add(new Pet("Tobby", Pet.Especie.CACHORRO, "Vira Lata", LocalDateTime.now(), Pet.Sexo.MACHO, clientes.getFirst()));
        pets.add(new Pet("Mimi", Pet.Especie.GATO, "Siamês", LocalDateTime.now(), Pet.Sexo.FEMEA, clientes.getFirst()));
        produtos.add(new Produto("Ração", "Ração para cachorro", 10, 50.0, Produto.TipoProduto.ALIMENTO));
        produtos.add(new Produto("Banho", "Banho para cachorro", 1, 100.0, Produto.TipoProduto.SERVICO));
        produtos.add(new Produto("Vacina", "Vacina para cachorro", 1, 200.0, Produto.TipoProduto.MEDICAMENTO));
        produtos.add(new Produto("Coleira", "Coleira para cachorro", 10, 20.0, Produto.TipoProduto.ACESSORIO));
        produtos.add(new Produto("Brinquedo", "Brinquedo para cachorro", 10, 30.0, Produto.TipoProduto.BRINQUEDO));
        vendas.add(new Venda(LocalDateTime.now(), clientes.getFirst(), funcionarios.getFirst()));
        vendas.getFirst().fecharVenda(List.of(new ItemVenda(1, produtos.getFirst().getCodigo())), produtos);
        veterinarios.add(new Veterinario("33333333333", "33333333333", LocalDateTime.now(), "vini@email.com", "Vini", new Endereco("Rua 1", 123, "Centro", "São Paulo", Endereco.Estado.SC), "Dermatologia", "123456"));
        consultas.add(new Consulta(pets.getFirst(), veterinarios.getFirst(), LocalDateTime.now()));
        prontuarios.add(new Prontuario("123456", pets.getFirst(), veterinarios.getFirst(), LocalDateTime.now().minusDays(2), LocalDateTime.now(), Consulta.Status.ABERTA));

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
                case 6:
                    cadastrarVeterinario();
                    break;
                case 7:
                    cadastrarConsulta();
                    break;
                case 8:
                    gerarProntuario();
                    break;
                case 9:
                    listarFuncionarios();
                    break;
                case 10:
                    listarClientes();
                    break;
                case 11:
                    listarPets();
                    break;
                case 12:
                    listarPetsDoCliente();
                    break;
                case 13:
                    listarProdutos();
                    break;
                case 14:
                    listarVendas();
                    break;
                case 15:
                    listarVeterinarios();
                    break;
                case 16:
                    listarProntuarios();
                    break;
                case 17:
                    listarConsultas();
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
                6. Cadastrar veterinário.
                7. Cadastrar consulta.
                8. Gerar prontuário de consulta.
                9. Listar funcionários.
                10. Listar clientes.
                11. Listar pets.
                12. Listar pets do cliente.
                13. Listar produtos.
                14. Listar vendas.
                15. Listar veterinários.
                16. Listar prontuários.
                17. Listar consultas.
                0. Sair
                
                =================================================================
                """);
    }

    private static void cadastrarFuncionario() {
        System.out.println("Boas vindas ao cadastro de funcionário!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        String cpfDoFuncionario;
        while (true) {
            cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

            if (cpfCadastrado(cpfDoFuncionario, funcionarios)) {
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

        Funcionario novoFuncionario = new Funcionario(cpfDoFuncionario, celular, dataDeNascimento, email, nome, senha, endereco);
        funcionarios.add(novoFuncionario);

        System.out.println("Funcionário " + novoFuncionario.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarCliente() {
        System.out.println("Boas vindas ao cadastro de cliente!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        String cpfDoCliente;
        while (true) {
            cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

            if (cpfCadastrado(cpfDoCliente, clientes)) {
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
        clientes.add(novoCliente);

        System.out.println("Cliente " + novoCliente.getNome() + " cadastrado com sucesso!");
    }

    private static void cadastrarPet() {
        System.out.println("Boas vindas ao cadastro de pet!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

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

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

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

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

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
            listarProdutosCadastradosEmEstoque();
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

    private static void cadastrarVeterinario() {
        System.out.println("Boas vindas ao cadastro de veterinário!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        String cpf;
        while (true) {
            cpf = leitor.lerCPF("Digite o CPF do veterinário");

            if (cpfCadastrado(cpf, veterinarios)) {
                System.err.println("CPF já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        String celular = leitor.lerCelular("Digite o celular do veterinário");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do veterinário");
        String nome = leitor.lerString("Digite o nome do veterinário");
        String email = leitor.lerString("Digite o email do veterinário");
        String especialidade = leitor.lerString("Digite a especialidade do veterinário");
        String CRMV = leitor.lerCRMV("Digite o CRMV do veterinário");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do veterinário");

        Veterinario novoVeterinario = new Veterinario(cpf, celular, dataDeNascimento, email, nome, endereco, especialidade, CRMV);
        veterinarios.add(novoVeterinario);
    }

    private static void cadastrarConsulta() {
        System.out.println("Boas vindas ao cadastro de consultas!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        Cliente cliente = clientes.stream().filter(c -> c.getCpf().equals(cpfDoCliente)).findFirst().orElse(null);

        if (cliente == null) {
            System.err.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        String cpfDoVeterinario = leitor.lerCPF("Digite o CPF do veterinário");

        Veterinario veterinario = veterinarios.stream().filter(v -> v.getCpf().equals(cpfDoVeterinario)).findFirst().orElse(null);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado. Tente novamente.");
            return;
        }

        List<Pet> petsDoCliente = pets.stream().filter(p -> p.getTutor().getCpf().equals(cpfDoCliente)).toList();

        Pet petSelecionado;

        if (petsDoCliente.size() == 1) {
            petSelecionado = petsDoCliente.stream().findFirst().get();
        } else {
            System.out.println("Selecione o pet:");

            for (int i = 0; i < petsDoCliente.size(); i++) {
                System.out.println((i + 1) + ". " + petsDoCliente.get(i).getNome());
            }

            int opcaoPet = leitor.lerInt("Digite o número do pet");

            if (opcaoPet > petsDoCliente.size() || opcaoPet < 1) {
                System.err.println("Opção inválida. Tente novamente.");
                return;
            }

            petSelecionado = petsDoCliente.get(opcaoPet - 1);
        }

        if (petSelecionado == null) {
            System.out.println("Pet não encontrado. Tente novamente.");
            return;
        }

        LocalDateTime data = leitor.lerData("Digite a data da consulta");

        System.out.println("Consulta agendada com sucesso!");
        Consulta novaConsulta = new Consulta(petSelecionado, veterinario, data);
        consultas.add(novaConsulta);
    }

    private static void gerarProntuario() {
        System.out.println("Boas vindas à geração de Prontuário!");

        Funcionario funcionario = autenticarFuncionario();

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return;
        }

        listarConsultasEmAberto();
        String codigoDaConsulta = leitor.lerString("Digite o código da consulta escolhida");

        Optional<Consulta> possivelConsulta = consultas.stream().filter(c -> c.getCodigo().equals(codigoDaConsulta) && c.getStatus().equals(Consulta.Status.ABERTA)).findFirst();

        if (possivelConsulta.isEmpty()) {
            System.err.println("Consulta não encontrada. Tente novamente.");
            return;
        }

        Consulta consulta = possivelConsulta.get();
        int indiceConsulta = consultas.indexOf(consulta);

        Prontuario prontuario = consulta.gerarProntuario();

        consultas.set(indiceConsulta, consulta);
        prontuarios.add(prontuario);
    }

    private static void listarFuncionarios() {
        System.out.println("=========== LISTA DE FUNCIONÁRIOS =============");
        funcionarios.forEach(System.out::println);
        System.out.println("===============================================");
    }

    private static void listarClientes() {
        System.out.println("=========== LISTA DE CLIENTES ==============");
        clientes.forEach(System.out::println);
        System.out.println("============================================");
    }

    private static void listarPets() {
        System.out.println("=========== LISTA DE PETS =============");
        pets.forEach(System.out::println);
        System.out.println("========================================");
    }

    private static void listarPetsDoCliente() {
        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        Optional<Cliente> possivelCliente = clientes.stream().filter(c -> c.getCpf().equals(cpfDoCliente)).findFirst();

        if (possivelCliente.isEmpty()) {
            System.err.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        Cliente cliente = possivelCliente.get();

        System.out.println("=========== LISTA DE PETS DO " + cliente.getNome() + " =============");
        pets.stream().filter(p -> p.getTutor().getCpf().equals(cliente.getCpf())).forEach(System.out::println);
        System.out.println("===================================================");
    }

    private static void listarProdutos() {
        System.out.println("=========== LISTA DE PRODUTOS =============");
        produtos.forEach(System.out::println);
        System.out.println("============================================");
    }

    private static void listarVendas() {
        System.out.println("""
                =========== STATUS DE VENDA =============
                ABERTA
                FECHADA
                CANCELADA
                TODOS
                =========================================
                """);

        String statusSelecionado = leitor.lerString("Digite o status da venda:");
        Venda.Status status = Venda.procurarStatusPorNome(statusSelecionado);

        if (status == null && !statusSelecionado.equalsIgnoreCase("todos")) {
            System.err.println("Status inválido. Tente novamente.");
            return;
        }

        System.out.println("=========== LISTA DE VENDAS =============");
        if (statusSelecionado.equalsIgnoreCase("todos")) {
            vendas.forEach(System.out::println);
        } else {
            vendas.stream().filter(v -> v.getStatus().equals(status)).forEach(System.out::println);
        }
        System.out.println("===========================================");
    }

    private static void listarVeterinarios() {
        System.out.println("=========== LISTA DE VETERINÁRIOS =============");
        veterinarios.forEach(System.out::println);
        System.out.println("===============================================");
    }

    private static void listarProntuarios() {
        System.out.println("=========== LISTA DE PRONTUÁRIOS =============");
        prontuarios.forEach(System.out::println);
        System.out.println("===============================================");
    }

    private static void listarConsultas() {
        System.out.println("""
                =========== STATUS DE CONSULTAS ============
                ABERTA
                FINALIZADA
                CANCELADA
                ============================================
                """);
        String statusSelecionado = leitor.lerString("Digite o status da consulta:");
        Consulta.Status status = Consulta.procurarStatusPorNome(statusSelecionado);

        System.out.println("=========== LISTA DE CONSULTAS =============");
        if (statusSelecionado.equalsIgnoreCase("todos")) {
            consultas.forEach(System.out::println);
        } else {
            consultas.stream().filter(c -> c.getStatus().equals(status)).forEach(System.out::println);
        }
        System.out.println("============================================");
    }

    private static void listarConsultasEmAberto() {
        System.out.println("=========== LISTA DE CONSULTAS =============");
        consultas.stream().filter(c -> c.getStatus() == Consulta.Status.ABERTA).forEach(System.out::println);
        System.out.println("============================================");
    }

    private static void listarProdutosCadastradosEmEstoque() {
        System.out.println("=========== LISTA DE PRODUTOS =============");
        produtos.stream().filter(p -> p.getQuantidade() > 0).forEach(System.out::println);
        System.out.println("============================================");
    }

    /**
     * Encontra o funcionário pelo CPF digitado e o autentica por meio de sua senha.
     *
     * @return o funcionário encontrado, null caso não encontre.
     */
    private static Funcionario autenticarFuncionario() {
        System.out.println("Por favor, se autentique.");

        String cpfDoFuncionario = leitor.lerCPF("Digite o CPF do funcionário");

        Funcionario funcionario = funcionarios.stream().filter(f -> f.getCpf().equals(cpfDoFuncionario)).findFirst().orElse(null);

        if (funcionario == null) {
            System.out.println("Funcionário não encontrado. Tente novamente.");
            return null;
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

        System.err.println("Número máximo de tentativas atingido. Cancelando autenticação.");
        return null;
    }

    /**
     * Valida se o CPF já foi cadastrado em alguma entidade da lista.
     *
     * @param cpf a ser validado.
     * @param lista lista com as entidades.
     * @return true se o CPF já foi cadastrado, false caso contrário.
     */
    private static boolean cpfCadastrado(String cpf, List<? extends Usuario> lista) {
        return lista.stream().anyMatch(u -> u.getCpf().equals(cpf));
    }
}
