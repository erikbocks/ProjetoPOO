package entidades;

import java.time.LocalDateTime;

public record Prontuario(String codigo, Pet pet, Veterinario veterinario, LocalDateTime data, LocalDateTime dataFechamento, Consulta.Status status) {
}
