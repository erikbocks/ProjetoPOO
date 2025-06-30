package servicos;

import entidades.Funcionario;

import java.util.List;

public interface ServicoFuncionario {
    /**
     * Mostra o menu de opções.
     */
    void mostrarMenu();

    /**
     * Encontra o funcionário pelo CPF digitado e o autentica por meio de sua senha.
     *
     * @return o funcionário encontrado, null caso não encontre.
     */
    Funcionario autenticarFuncionario();

    /**
     * Cadastra um novo funcionário ao sistema.
     * @return o novo funcionário cadastrado.
     */
    Funcionario cadastrarFuncionario();

    /**
     * Retorna uma lista de todos os funcionários cadastrados, que têm o atributo ativo = true.
     * @return uma lista de funcionarios.
     */
    List<Funcionario> listarTodosAtivos();

    /**
     * Busca um funcionario cadastrado no banco, e então atualiza ele com os novos atributos informados pelo usuário.
     */
    void atualizarFuncionario();

    /**
     * Busca um funcionário cadastrado no banco e atualiza seu endereço.
     */
    void atualizarEndereco();

    /**
     * Busca um funcionário cadastrado no banco e atualiza sua data de nascimento.
     */
    void atualizarDataDeNascimento();

    /**
     * Desativa um funcionário cadastrado.
     */
    void desativarFuncionario();

    /**
     * Remove um funcionário cadastrado.
     */
    void removerFuncionario();
}
