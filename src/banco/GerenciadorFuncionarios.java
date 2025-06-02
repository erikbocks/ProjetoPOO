package banco;

import entidades.Funcionario;

public interface GerenciadorFuncionarios extends GerenciadorBase<Funcionario> {
    Funcionario buscarPorCpf(String cpf);
    boolean funcionarioCadastrado(String cpf);
}
