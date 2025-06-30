package banco;

import entidades.Venda;

import java.time.LocalDateTime;
import java.util.List;

public interface GerenciadorVendas extends GerenciadorBase<Venda> {
    /**
     * Busca uma venda pelo seu código.
     *
     * @param codigo o código da venda a ser buscada
     * @return a venda correspondente ao código, se encontrada; null caso contrário
     */
    Venda buscarVendaPorCodigo(String codigo);

    /**
     * Lista todas as vendas.
     *
     * @return uma lista de todas as vendas registradas
     */
    List<Venda> listarVendas();

    /**
     * Lista as vendas filtradas por status.
     *
     * @param status o status das vendas a serem listadas
     * @return uma lista de vendas com o status especificado
     */
    List<Venda> listarVendasPorStatus(Venda.Status status);

    /**
     * Lista as vendas filtradas por cliente.
     *
     * @param cpfCliente o CPF do cliente cujas vendas serão listadas
     * @return uma lista de vendas associadas ao cliente especificado
     */
    List<Venda> listarVendasPorCliente(String cpfCliente);

    /**
     * Lista as vendas filtradas por funcionário.
     *
     * @param cpfFuncionario o CPF do funcionário cujas vendas serão listadas
     * @return uma lista de vendas associadas ao funcionário especificado
     */
    List<Venda> listarVendasPorFuncionario(String cpfFuncionario);

    /**
     * Lista as vendas filtradas por data.
     *
     * @param data a data das vendas a serem listadas
     * @return uma lista de vendas realizadas na data especificada
     */
    List<Venda> listarVendasPorData(LocalDateTime data);

    /**
     * Fecha uma venda em aberto.
     *
     * @param venda a venda a ser fechada
     */
    void fecharVenda(Venda venda);

    /**
     * Cancela uma venda em aberto.
     *
     * @param venda a venda a ser cancelada
     */
    void cancelarVenda(Venda venda);
}
