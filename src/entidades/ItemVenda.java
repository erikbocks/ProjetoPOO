package entidades;

public class ItemVenda {
    String codigoProduto;
    Integer quantidadeEscolhida;

    public ItemVenda(Integer quantidadeEscolhida, String codigoProduto) {
        this.quantidadeEscolhida = quantidadeEscolhida;
        this.codigoProduto = codigoProduto;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }
}
