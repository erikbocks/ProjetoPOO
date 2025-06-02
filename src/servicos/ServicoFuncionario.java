package servicos;

import entidades.Funcionario;

public interface ServicoFuncionario {

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
}
