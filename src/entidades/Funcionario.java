package entidades;

import utils.Formatadores;

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
        return String.format("Funcionario: [cpf=%s, nome=%s, email=%s, telefone=%s, dataDeNascimento=%s]", super.cpf, super.nome, super.email, super.telefone, Formatadores.formatarData(super.dataDeNascimento));
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public void atualizarDados(String nome, String telefone, String email, String senha) {
        if (!nome.isBlank() && !this.nome.equals(nome)) {
            this.nome = nome;
        }
        if (!telefone.isBlank() && !this.telefone.equals(telefone)) {
            this.telefone = telefone;
        }
        if (!email.isBlank() && !this.email.equals(email)) {
            this.email = email;
        }
        if (!senha.isBlank() && !this.senha.equals(senha)) {
            this.senha = senha;
        }
    }
}
