package servicos;

import entidades.Cliente;

import java.util.List;

public interface ServicoCliente {

    /**
     * Mostra o menu de opções.
     */
    void mostrarMenu();

    /**
     * Cadastra um novo cliente ao sistema.
     * @return o novo cliente cadastrado.
     */
    Cliente cadastrarCliente();

    /**
     * Retorna uma lista de todos os clientes cadastrados, que têm o atributo ativo = true.
     * @return uma lista de clientes.
     */
    List<Cliente> listarTodosAtivos();

    /**
     * Busca um cliente cadastrado no banco, e então atualiza ele com os novos atributos informados.
     */
    void atualizarCliente();

    /**
     * Busca um cliente cadastrado no banco e atualiza seu endereço.
     */
    void atualizarEndereco();

    /**
     * Busca um cliente cadastrado no banco e atualiza sua data de nascimento.
     */
    void atualizarDataDeNascimento();

    /**
     * Desativa um cliente cadastrado.
     */
    void desativarCliente();

    /**
     * Remove um cliente cadastrado.
     */
    void removerCliente();
}
