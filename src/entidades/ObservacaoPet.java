package entidades;

public class ObservacaoPet {
    private Integer id;
    private Integer petId;
    private String observacao;

    public ObservacaoPet() {
    }

    public ObservacaoPet(Integer petId, String observacao) {
        this.petId = petId;
        this.observacao = observacao;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPetId() {
        return petId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return observacao;
    }
}
