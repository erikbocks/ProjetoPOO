package entidades;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {
    private String codigo;
    private Pet pet;
    private Veterinario veterinario;
    private LocalDateTime data;
    private Status status;

    public Consulta(Pet pet, Veterinario veterinario, LocalDateTime data) {
        this.codigo = String.valueOf((int) (Math.random() * 1001));
        this.pet = pet;
        this.veterinario = veterinario;
        this.data = data;
        this.status = Status.ABERTA;
    }

    public Status getStatus() {
        return status;
    }

    public String getCodigo() {
        return codigo;
    }

    public Prontuario gerarProntuario() {
        System.out.println("Gerando prontuário...");

        this.status = Status.FINALIZADA;
        LocalDateTime dataFechamento = LocalDateTime.now();

        System.out.println("============= PRONTUARIO =============");
        System.out.println("Código da consulta: " + this.codigo);
        System.out.println("Pet: " + this.pet.getNome());
        System.out.println("Veterinário: " + this.veterinario.getNome());
        System.out.println("Data da Consulta: " + this.data.format(DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")));
        System.out.println("Data de Fechamento: " + dataFechamento.format(DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")));
        System.out.println("Status: " + this.status);
        System.out.println("=======================================");

        return new Prontuario(this.codigo, this.pet, this.veterinario, this.data, dataFechamento, this.status);
    }

    public enum Status {
        FINALIZADA,
        CANCELADA,
        ABERTA
    }

    @Override
    public String toString() {
        return String.format("Consulta[código=%s, pet=%s, veterinario=%s, data=%s, status=%s]", codigo, pet.getNome(), veterinario.getNome(), data.format(DateTimeFormatter.ofPattern("dd MMM yyyy - HH:mm")), status);
    }
}
