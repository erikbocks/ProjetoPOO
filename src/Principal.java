import entidades.Cliente;
import entidades.Endereco;
import entidades.Funcionario;
import entidades.Pet;
import entidades.Usuario;
import servicos.Leitor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static Leitor leitor = new Leitor();
    public static List<Funcionario> funcionarios = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

        funcionarios.add(new Funcionario("11111111111", null, null, null, "Roberson", "senha", null));
        clientes.add(new Cliente("22222222222", null, null, null, "Bock", null));

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

        System.out.println("Pet " + novoPet.getNome() + " cadastrado com sucesso!");
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
