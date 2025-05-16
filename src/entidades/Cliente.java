package entidades;

import java.time.LocalDateTime;

public class Cliente extends Usuario {
    public Cliente(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
    }
}
