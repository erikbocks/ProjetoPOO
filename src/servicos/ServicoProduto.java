package servicos;

import entidades.Produto;

import java.util.List;

public interface ServicoProduto {
    void mostrarMenu();

    void cadastrarProduto();

    void listarProdutos();

    void listarProdutosContendoPalavra();

    void listarProdutosPorTipo();

    void atualizarProduto();

    void excluirProduto();
}
