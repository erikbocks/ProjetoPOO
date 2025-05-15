package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
  private List<Pet> pets;

    public Cliente(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
        this.pets = new ArrayList<>();
    }
}
