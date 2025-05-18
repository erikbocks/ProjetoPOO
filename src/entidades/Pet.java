package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String nome;
    private Especie especie;
    private String raca;
    private LocalDateTime dataDeNascimento;
    private Sexo sexo;
    private List<String> observacoes;
    private Cliente tutor;

    public Pet(String nome, Especie especie, String raca, LocalDateTime dataDeNascimento, Sexo sexo, Cliente tutor) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataDeNascimento = dataDeNascimento;
        this.sexo = sexo;
        this.observacoes = new ArrayList<>();
        this.tutor = tutor;
    }

    public String getNome() {
        return nome;
    }

    public Cliente getTutor() {
        return tutor;
    }

    public enum Especie {
        CACHORRO,
        GATO,
        PEIXE,
        PASSARO,
        REPTIL,
        ROEDOR,
        OUTRO
    }

    public enum Sexo {
        MACHO,
        FEMEA;
    }

    public void adicionarObservacao(String observacao) {
        this.observacoes.add(observacao.trim());
    }

    public static Especie procurarEspeciePorNome(String nome) {
        for (Especie especie : Especie.values()) {
            if (especie.name().equalsIgnoreCase(nome)) {
                return especie;
            }
        }

        return null;
    }

    public static Sexo procurarSexoPorChar(char sexo) {
        return (sexo == 'M' || sexo == 'm') ? Sexo.MACHO :
                (sexo == 'F' || sexo == 'f') ? Sexo.FEMEA : null;
    }
}
