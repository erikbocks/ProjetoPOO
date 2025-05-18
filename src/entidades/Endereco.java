package entidades;

public class Endereco {
    private String rua;
    private int numero;
    private String complemento;
    private String cidade;
    private Estado estado;

    public Endereco(String rua, int numero, String complemento, String cidade, Estado estadoEnum) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estadoEnum;
    }

    public enum Estado {
        SC,
        RS;
    }

    public static Estado procurarEstadoPorSigla(String sigla) {
        for (Estado es : Estado.values()) {
            if (es.name().equalsIgnoreCase(sigla)) {
                return es;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return String.format("Endereco[rua=%s, numero=%d, complemento=%s, cidade=%s, estado=%s]", rua, numero, complemento, cidade, estado);
    }
}
