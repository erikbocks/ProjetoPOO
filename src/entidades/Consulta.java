package entidades;

import java.time.LocalDateTime;

public class Consulta {
    private Pet pet;
    private Veterinario veterinario;
    private LocalDateTime data;
    private Status status;

    public enum Status {
        FINALIZADA,
        CANCELADA,
        ABERTA
    }
}
