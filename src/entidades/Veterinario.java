package entidades;

import java.time.LocalDateTime;

public class Veterinario extends Usuario {
    private String especialidade;
    private String CRMV;

    public Veterinario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
    }
}
