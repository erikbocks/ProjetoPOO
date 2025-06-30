package servicos;

public interface ServicoVenda {
    void mostrarMenu();

    void cadastrarVenda();

    void listarVendas();

    void buscarVendaPorCodigo();

    void fecharVenda();

    void cancelarVenda();

    void listarVendasPorStatus();

    void listarVendasPorCliente();

    void listarVendasPorFuncionario();

    void listarVendasPorData();
}
