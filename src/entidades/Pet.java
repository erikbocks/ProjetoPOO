package entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pet {
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public LocalDateTime getDataDeNascimento() {
        return dataDeNascimento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public List<String> getObservacoes() {
        return observacoes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setDataDeNascimento(LocalDateTime dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public void setObservacoes(List<String> observacoes) {
        this.observacoes = observacoes;
    }

    public void addObservacao(String observacao) {
        if (observacao != null && !observacao.trim().isBlank()) {
            this.observacoes.add(observacao.trim());
        }
    }

    public void setTutor(Cliente tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return String.format("Pet[nome=%s, especie=%s, raca=%s, dataDeNascimento=%s, sexo=%s, observacoes=%s, tutor=%s]", nome, especie, raca, dataDeNascimento, sexo, observacoes, tutor.getNome());
    }
}
