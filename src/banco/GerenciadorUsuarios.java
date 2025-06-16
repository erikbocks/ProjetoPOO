package banco;

import entidades.Endereco;
import entidades.Usuario;

public interface GerenciadorUsuarios<T extends Usuario> extends GerenciadorBase<T> {
    T buscarPorCpf(String cpf);

    boolean estaCadastrado(String cpf);

    void atualizarEndereco(String cpf, Endereco endereco);

    void desativar(String cpf);
}
