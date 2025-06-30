package entidades;

public class ItemVenda {
    String codigoProduto;
    Integer quantidadeEscolhida;
    Double precoUnitario;

    public ItemVenda(Integer quantidadeEscolhida, String codigoProduto, Double precoUnitario) {
        this.quantidadeEscolhida = quantidadeEscolhida;
        this.codigoProduto = codigoProduto;
        this.precoUnitario = precoUnitario;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public Integer getQuantidadeEscolhida() {
        return quantidadeEscolhida;
    }

    public Double getPrecoUnitario() {
        return precoUnitario;
    }
}
