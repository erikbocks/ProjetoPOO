package entidades;

import java.time.LocalDateTime;

public class Consulta {
    private Pet pet;
    private Veterinario veterinario;
    private LocalDateTime data;
    private Status status;

    public Consulta(Pet pet, Veterinario veterinario, LocalDateTime data) {
        this.pet = pet;
        this.veterinario = veterinario;
        this.data = data;
        this.status = Status.ABERTA;
    }

    public enum Status {
        FINALIZADA,
        CANCELADA,
        ABERTA
    }
}
