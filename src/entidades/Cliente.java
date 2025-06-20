package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Cliente extends Usuario {
    public Cliente(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
    }

    public Cliente() {
    }

    @Override
    public String toString() {
        return String.format("Cliente[cpf=%s, telefone=%s, dataDeNascimento=%s, email=%s, nome=%s, endereco=%s]", cpf, telefone, dataDeNascimento.format(DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.ROOT)), email, nome, endereco);
    }

    public void atualizarDados(String nome, String telefone, String email) {
        if (!nome.isBlank() && !this.nome.equals(nome)) {
            this.nome = nome;
        }
        if (!telefone.isBlank() && !this.telefone.equals(telefone)) {
            this.telefone = telefone;
        }
        if (!email.isBlank() && !this.email.equals(email)) {
            this.email = email;
        }
    }
}
