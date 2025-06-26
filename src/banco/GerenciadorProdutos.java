package banco;

import entidades.Produto;

import java.util.List;

public interface GerenciadorProdutos extends GerenciadorBase<Produto> {
    Produto buscarPorCodigo(String codigo);

    List<Produto> listarProdutoContendoPalavra(String palavra);
}
