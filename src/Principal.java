import entidades.Endereco;
import entidades.Funcionario;
import servicos.Leitor;
import validadores.Validador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static Leitor leitor = new Leitor();
    public static Validador validador = new Validador();
    public static List<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;

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
                0. Sair
                
                =================================================================
                """);
    }

    private static void cadastrarFuncionario() {
        System.out.println("Boas vindas ao cadastro de funcionário!");

        String cpf = leitor.lerCPF();
        String celular = leitor.lerCelular();
        LocalDateTime dataDeNascimento = leitor.lerData();
        String nome = leitor.lerString("Digite o nome do funcionário");
        String email = leitor.lerString("Digite o email do funcionário");
        String senha = leitor.lerSenha();
        Endereco endereco = leitor.lerEndereco();

        Funcionario novoFuncionario = new Funcionario(cpf, celular, dataDeNascimento, email, nome, senha, endereco);
        funcionarios.add(novoFuncionario);

        System.out.println("Funcionário " + novoFuncionario.getNome() + " cadastrado com sucesso!");
        System.out.println(novoFuncionario);
    }
}
