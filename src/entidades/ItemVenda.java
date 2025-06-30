package entidades;

public class ItemVenda {
    String codigoProduto;
    Integer quantidadeEscolhida;
    Double precoUnitario;

    public ItemVenda() {
    }

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

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public void setQuantidadeEscolhida(Integer quantidadeEscolhida) {
        this.quantidadeEscolhida = quantidadeEscolhida;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return String.format("Item[codigo = %s, quantidade = %s, preco unitario = R$ %.2f", codigoProduto, quantidadeEscolhida, precoUnitario);
    }
}
