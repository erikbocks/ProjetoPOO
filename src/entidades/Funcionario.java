package entidades;

import java.time.LocalDateTime;

public class Funcionario extends Usuario {
    private String senha;

    public Funcionario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, String senha) {
        super(cpf, telefone, dataDeNascimento, email, nome);
        this.senha = senha;
    }

    public boolean autenticar(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    @Override
    public String toString() {
        return String.format("Funcionario: [cpf=%s, nome=%s]", super.cpf, super.nome);
    }
}
