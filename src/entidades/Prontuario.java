package entidades;

import java.time.LocalDateTime;

public record Prontuario(String codigo, Pet pet, Veterinario veterinario, LocalDateTime data, LocalDateTime dataFechamento, Consulta.Status status) {
    @Override
    public String toString() {
        return String.format("Prontuario[codigo=%s, pet=%s, veterinario=%s, dataConsulta=%s, dataFechamento=%s, status=%s]", codigo, pet.getNome(), veterinario.getNome(), data, dataFechamento, status);
    }
}
