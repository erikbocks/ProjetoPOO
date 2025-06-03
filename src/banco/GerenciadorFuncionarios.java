package banco;

import entidades.Endereco;
import entidades.Funcionario;

public interface GerenciadorFuncionarios extends GerenciadorBase<Funcionario> {
    Funcionario buscarPorCpf(String cpf);

    boolean funcionarioCadastrado(String cpf);

    void atualizarEndereco(String cpf, Endereco endereco);
}
