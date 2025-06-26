package servicos;

public interface ServicoProduto {
    void mostrarMenu();

    void cadastrarProduto();

    void buscarProdutoPorCodigo();

    void listarProdutos();

    void listarProdutosContendoPalavra();

    void listarProdutosPorTipo();

    void atualizarProduto();

    void excluirProduto();
}
