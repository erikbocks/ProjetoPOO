package servicos;

import entidades.Endereco;
import validadores.Validador;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Leitor {
    private final Validador validador = new Validador();
    
    private final Scanner scanner = new Scanner(System.in);
    
    public String lerString(String mensagem) {
        System.out.print(mensagem + ": ");
        return scanner.nextLine();
    }
    
    public int lerInt(String mensagem) {
        while (true) {
            System.out.println(mensagem + ": ");
            try {
                String linha = scanner.nextLine();
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número inteiro.");
            }
        }
    }
    
    public double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem + ": ");
            try {
                String linha = scanner.nextLine();
                return Double.parseDouble(linha.trim());
            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número decimal.");
            }
        }
    }
    
    public float lerFloat(String mensagem) {
        while (true) {
            System.out.print(mensagem + ": ");
            try {
                String linha = scanner.nextLine();
                return Float.parseFloat(linha.trim());
            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número decimal.");
            }
        }
    }
    
    public long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem + ": ");
            try {
                String linha = scanner.nextLine();
                return Long.parseLong(linha.trim());
            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada inválida. Por favor, digite um número inteiro longo.");
            }
        }
    }
    
    public char lerChar(String mensagem) {
        while (true) {
            System.out.print(mensagem + ": ");
            String linha = scanner.nextLine();
            if (linha != null && linha.trim().length() == 1) {
                return linha.trim().charAt(0);
            } else {
                System.err.println("Erro: Entrada inválida. Por favor, digite exatamente um caractere.");
            }
        }
    }
    
    public boolean lerBoolean(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (true/false/sim/nao/não/s/n): ");
            String linha = scanner.nextLine().trim().toLowerCase();
            if (linha.equals("true") || linha.equals("sim") || linha.equals("s")) {
                return true;
            } else if (linha.equals("false") || linha.equals("nao") || linha.equals("não") || linha.equals("n")) {
                return false;
            } else {
                System.err.println("Erro: Entrada inválida. Por favor, digite true/false, sim/nao ou s/n.");
            }
        }
    }

    public Endereco lerEndereco(String mensagem) {
        System.out.println(mensagem);
        while (true) {
            String estado = lerString("Digite o estado (sigla)");
            Endereco.Estado estadoEnum = Endereco.procurarEstadoPorSigla(estado);

            if (estadoEnum == null) {
                System.err.println("Estado não encontrado. Tente novamente.");
                continue;
            }

            String cidade = lerString("Digite a cidade");
            String rua = lerString("Digite a rua");
            int numero = lerInt("Digite o número");
            String complemento = lerString("Digite o complemento (Opcional)");

            return new Endereco(rua, numero, complemento, cidade, estadoEnum);
        }
    }

    public String lerSenha(String mensagem) {
        while (true) {
            String senha = lerString(mensagem);
            String segundaSenha = lerString("Digite novamente");
            if (!validador.senhaValida(senha, segundaSenha)) {
                continue;
            }
            return senha;
        }
    }

    public String lerCPF(String mensagem) {
        while (true) {
            String cpf = lerString(mensagem + " (somente números)");
            if (!validador.validarCPF(cpf)) {
                continue;
            }
            return cpf;
        }
    }

    public String lerCelular(String mensagem) {
        while (true) {
            String celular = lerString(mensagem + " (DDD + 9 + numero");
            if (!validador.validarCelular(celular)) {
                continue;
            }
            return celular;
        }
    }

    public LocalDateTime lerData(String mensagem) {
        System.out.println(mensagem);
        while (true) {
            int dia = lerInt("Dia");
            int mes = lerInt("Mês");
            int ano = lerInt("Ano");

            if (!validador.dataValida(dia, mes, ano)) {
                continue;
            }

            return LocalDateTime.of(ano, mes, dia, 0, 0);
        }
    }
}