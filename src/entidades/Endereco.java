package entidades;

public class Endereco {
    private Integer id;
    private String rua;
    private int numero;
    private String complemento;
    private String cidade;
    private Estado estado;

    public Endereco() {
    }

    public Endereco(String rua, int numero, String complemento, String cidade, Estado estadoEnum) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estadoEnum;
    }

    public void atualizarEndereco(Endereco endereco) {
        if (!endereco.getRua().isBlank() && !this.rua.equals(endereco.getRua())) {
            this.rua = endereco.getRua();
        }
        if (this.numero != endereco.getNumero()) {
            this.numero = endereco.getNumero();
        }
        if (endereco.getComplemento() != null && !endereco.getComplemento().isBlank() && !this.complemento.equals(endereco.getComplemento())) {
            this.complemento = endereco.getComplemento();
        }
        if (!endereco.getCidade().isBlank() && !this.cidade.equals(endereco.getComplemento())) {
            this.cidade = endereco.getCidade();
        }
        if (!this.estado.equals(endereco.getEstado())) {
            this.estado = endereco.getEstado();
        }
    }

    public enum Estado {
        RS,
        SC,
        PR,
        SP,
        RJ,
        MG,
    }

    public static Estado procurarEstadoPorSigla(String sigla) {
        for (Estado es : Estado.values()) {
            if (es.name().equalsIgnoreCase(sigla)) {
                return es;
            }
        }

        return null;
    }

    public String getRua() {
        return rua;
    }

    public int getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return String.format("Endereco[rua=%s, numero=%d, complemento=%s, cidade=%s, estado=%s]", rua, numero, complemento, cidade, estado);
    }
}
