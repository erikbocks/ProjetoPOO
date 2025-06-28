package banco;

import entidades.Endereco;
import entidades.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public interface GerenciadorUsuarios<T extends Usuario> extends GerenciadorBase<T> {
    T buscarPorCpf(String cpf);

    boolean estaCadastrado(String cpf);

    void atualizarEndereco(String cpf, Endereco endereco);

    void desativar(String cpf);

    default Integer mapearDadosUsuarioDoResultSet(T entidade, ResultSet rs, Integer index) throws SQLException {
        entidade.setCpf(rs.getString(index++));
        entidade.setNome(rs.getString(index++));
        entidade.setEmail(rs.getString(index++));
        entidade.setTelefone(rs.getString(index++));
        entidade.setDataDeCadastro(LocalDateTime.parse(rs.getString(index++)));
        entidade.setDataDeNascimento(LocalDateTime.parse(rs.getString(index++)));
        entidade.setAtivo(rs.getBoolean(index++));

        return index;
    };

    default Endereco mapearResultSetParaEndereco(ResultSet rs, Integer index) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt(index++));
        endereco.setEstado(Endereco.procurarEstadoPorSigla(rs.getString(index++)));
        endereco.setCidade(rs.getString(index++));
        endereco.setRua(rs.getString(index++));
        endereco.setNumero(rs.getInt(index++));
        endereco.setComplemento(rs.getString(index));

        return endereco;
    };
}
