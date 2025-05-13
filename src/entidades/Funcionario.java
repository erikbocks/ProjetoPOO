package entidades;

import java.time.LocalDateTime;

public class Funcionario extends Usuario {
    private String senha;

    public Funcionario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome) {
        super(cpf, telefone, dataDeNascimento, email, nome);
    }

    @Override
    public String toString() {
        return String.format("Funcionario: [cpf=%s, nome=%s]", super.cpf, super.nome);
    }
}
