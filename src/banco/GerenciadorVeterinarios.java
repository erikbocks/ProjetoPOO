package banco;

import entidades.Veterinario;

import java.util.List;

public interface GerenciadorVeterinarios extends GerenciadorUsuarios<Veterinario> {

    /**
     * Busca um veterinário pelo seu CRMV.
     *
     * @param crmv o CRMV do veterinário a ser buscado
     * @return o veterinário correspondente ao CRMV, ou null se não encontrado
     */
    Veterinario buscarPorCRMV(String crmv);

    /**
     * Verifica se um CRMV já está cadastrado.
     *
     * @param crmv o CRMV a ser verificado
     * @return true se o CRMV já estiver cadastrado, false caso contrário
     */
    boolean verificarCRMV(String crmv);

    /**
     * Busca veterinários com determinada especialidade.
     * @param especialidade a especialidade a ser buscada
     * @return uma lista com todos os veterinários encontrados com aquela especialidade
     */
    List<Veterinario> buscarPorEspecialidade(String especialidade);
}
