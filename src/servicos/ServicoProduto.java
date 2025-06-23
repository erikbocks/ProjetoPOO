package servicos;

import entidades.Produto;

import java.util.List;

public interface ServicoProduto {
    void mostrarMenu();

    void cadastrarProduto();

    List<Produto> listarProdutos();

    List<Produto> listarProdutosContendoPalavra();

    List<Produto> listarProdutosPorTipo();

    void atualizarProduto();

    void excluirProduto();
}
