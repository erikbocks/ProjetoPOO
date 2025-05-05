import entidades.Cliente;
import entidades.Funcionario;
import servicos.Leitor;

import java.util.ArrayList;
import java.util.List;

public class Principal {
    public static Leitor leitor = new Leitor();

    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        int opcao = -1;

        while (true) {
            mostrarMenu();

            opcao = leitor.lerInt("Qual opção você gostaria de selecionar?");

            if(opcao < 0 || opcao > 2) {
                System.err.println("Escolha um número entre 1 e 1.");
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarFuncionario();
                    break;
            }
        }



    }

    private static void mostrarMenu() {
        System.out.println("""
                ====================== PET SHOP BOCK E VINI =====================
                
                1. Cadastrar funcionário.
                
                =================================================================
                """);
    }
    private static void cadastrarFuncionario() {
        System.out.println("Boas vindas ao cadastro de funcionário!");
        String nome = leitor.lerString("Primeiro, qual é o nome do funcionário?");
        String cpf = leitor.lerString("CPF do funcionário:");
//        return new Funcionario();
    }
}
