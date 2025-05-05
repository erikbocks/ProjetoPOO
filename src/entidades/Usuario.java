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

    public Usuario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome) {
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataDeCadastro = LocalDateTime.now(ZoneId.systemDefault());
        this.dataDeNascimento = dataDeNascimento;
        this.email = email;
        this.nome = nome;
    }
}
