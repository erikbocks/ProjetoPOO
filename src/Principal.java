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

        String cpf = obterCPF();
        String celular = obterCelular();
        LocalDateTime dataDeNascimento = obterData();
        String nome = leitor.lerString("Digite o nome do funcionário");
        String email = leitor.lerString("Digite o email do funcionário");

        Funcionario novoFuncionario = new Funcionario(cpf, celular, dataDeNascimento, email, nome);
        funcionarios.add(novoFuncionario);

        System.out.println("Funcionário cadastrado com sucesso!");
        System.out.println(novoFuncionario);
    }

    private static String obterCPF() {
        while (true) {
            String cpf = leitor.lerString("Digite o CPF (somente números)");
            if (!validador.validarCPF(cpf)) {
                System.err.println("CPF inválido. Tente novamente.");
                continue;
            }
            return cpf;
        }
    }

    private static String obterCelular() {
        while (true) {
            String celular = leitor.lerString("Digite o celular (DDD + 9 + numero");
            if (!validador.validarCelular(celular)) {
                System.err.println("Celular inválido. Tente novamente.");
                continue;
            }
            return celular;
        }
    }

    private static LocalDateTime obterData() {
        System.out.println("Digite a data");
        while (true) {
            int dia = leitor.lerInt("Dia");
            int mes = leitor.lerInt("Mês");
            int ano = leitor.lerInt("Ano");

            if (!validador.dataValida(dia, mes, ano)) {
                System.err.println("Data inválida. Tente novamente.");
                continue;
            }

            return LocalDateTime.of(ano, mes, dia, 0, 0);
        }
    }
}
