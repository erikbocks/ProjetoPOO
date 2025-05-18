package validadores;

import java.time.LocalDateTime;
import java.util.List;

public class Validador {
    public boolean dataValida(Integer dia, Integer mes, Integer ano) {
        final LocalDateTime dataAtual = LocalDateTime.now();
        if (ano < 1900 || ano > dataAtual.getYear()) {
            System.err.println("Ano inválido. O ano deve estar entre 1900 e " + dataAtual.getYear() + ".");
            return false;
        }

        if (mes < 1 || mes > 12) {
            System.err.println("Mês inválido. O mês deve estar entre 1 e 12.");
            return false;
        }

        if (dia < 1 || dia > 31) {
            System.err.println("Dia inválido. O dia deve estar entre 1 e 31.");
            return false;
        }

        if (List.of(4, 6, 9, 11).contains(mes) && dia == 31) {
            System.err.println("Esse mês não tem 31 dias.");
            return false;
        }

        if (mes == 2) {
            if (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) {
                if (dia > 29) {
                    System.err.println("Fevereiro só tem até 29 dias em anos bissextos.");
                    return false;
                }
            } else {
                if (dia > 28) {
                    System.err.println("Fevereiro só tem até 28 dias em anos normais.");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean validarCPF(String cpf) {
        if (cpf.length() != 11) {
            System.err.println("CPF inválido. O CPF deve ter 11 dígitos.");
            return false;
        }

        for (char c : cpf.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.err.println("CPF inválido. O CPF deve conter apenas dígitos.");
                return false;
            }
        }

        return true;
    }

    public boolean validarCelular(String celular) {
        if (celular.length() != 11) {
            System.err.println("Celular inválido. O celular deve ter 11 dígitos.");
            return false;
        }

        for (char c : celular.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.err.println("Celular inválido. O celular deve conter apenas dígitos.");
                return false;
            }
        }

        return true;
    }

    public boolean senhaValida(String senha, String segundaSenha) {
        boolean valido = senha.equals(segundaSenha);

        if(!valido) {
            System.err.println("As senhas não coincidem. Tente novamente.");
        }

        return valido;
    }

    public boolean validarCRMV(String crmv) {
        if (crmv.length() != 7) {
            System.err.println("CRMV inválido. O CRMV deve ter 7 dígitos.");
            return false;
        }

        for (char c : crmv.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.err.println("CRMV inválido. O CRMV deve conter apenas dígitos.");
                return false;
            }
        }

        return true;
    }
}

