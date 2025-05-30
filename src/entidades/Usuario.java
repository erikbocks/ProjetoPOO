package entidades;

import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Usuario {
    protected String cpf;
    protected String telefone;
    protected LocalDateTime dataDeCadastro;
    protected LocalDateTime dataDeNascimento;
    protected String email;
    protected String nome;
    protected Endereco endereco;
    protected Boolean ativo;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Usuario() {
    }

    public Usuario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco) {
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataDeCadastro = LocalDateTime.now(ZoneId.systemDefault());
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.ativo = true;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDateTime getDataDeCadastro() {
        return dataDeCadastro;
    }

    public LocalDateTime getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setDataDeCadastro(LocalDateTime dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public void setDataDeNascimento(LocalDateTime dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
