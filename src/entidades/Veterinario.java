package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Veterinario extends Usuario {
    private String especialidade;
    private String CRMV;

    public Veterinario(String cpf, String telefone, LocalDateTime dataDeNascimento, String email, String nome, Endereco endereco, String especialidade, String CRMV) {
        super(cpf, telefone, dataDeNascimento, email, nome, endereco);
        this.especialidade = especialidade;
        this.CRMV = CRMV;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public String getCRMV() {
        return CRMV;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void setCRMV(String CRMV) {
        this.CRMV = CRMV;
    }

    @Override
    public String toString() {
        return String.format("Veterinario[cpf=%s, telefone=%s, dataDeNascimento=%s, email=%s, nome=%s, endereco=%s, especialidade=%s, CRMV=%s]", cpf, telefone, dataDeNascimento.format(DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm", Locale.ROOT)), email, nome, endereco, especialidade, CRMV);
    }
}
