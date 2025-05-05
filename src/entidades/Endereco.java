package entidades;

public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private Estado estado;

    public enum Estado {
        SC();

        public Estado procurarPorSigla(String sigla) {
            for(Estado es : Estado.values()) {
                if(es.name().equalsIgnoreCase(sigla)) {
                    return es;
                }
            }

            throw new IllegalArgumentException("Nenhuma estado encontrado para a sigla: " + sigla);
        }
    }
}
