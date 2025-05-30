package entidades;

import java.time.LocalDateTime;

public class Funcionario extends Usuario {
    private String senha;

    public Funcionario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, String senha, Endereco endereco) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
        this.senha = senha;
    }

    public Funcionario() {
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return String.format("Funcionario: [cpf=%s, nome=%s]", super.cpf, super.nome);
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}
