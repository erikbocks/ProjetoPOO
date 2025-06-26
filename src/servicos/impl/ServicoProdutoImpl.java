package servicos.impl;

import banco.GerenciadorProdutos;
import entidades.Produto;
import servicos.Leitor;
import servicos.ServicoProduto;

import java.util.Arrays;
import java.util.List;

public class ServicoProdutoImpl implements ServicoProduto {
    private Leitor leitor;
    private GerenciadorProdutos gerenciadorProdutos;

    public ServicoProdutoImpl(Leitor leitor, GerenciadorProdutos gerenciadorProdutos) {
        this.leitor = leitor;
        this.gerenciadorProdutos = gerenciadorProdutos;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - FUNCIONÁRIO ===========================
                
                1. Cadastrar produto.
                2. Buscar produto por código.
                3. Listar todos os produtos.
                4. Listar produtos por palavra.
                5. Listar produtos por tipo.
                6. Atualizar produto.
                7. Excluir produto.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Digite a opção desejada");

        switch (opcaoOperacao) {
            case 1:
                cadastrarProduto();
                break;
            case 3:
                listarProdutos();
                break;
            default:
                System.err.println("Operação inválida.");
        }
    }

    @Override
    public void cadastrarProduto() {
        System.out.println("Boas vindas ao cadastro de produto!");

        String nome = leitor.lerString("Digite o nome do produto");
        int quantidade = leitor.lerInt("Digite a quantidade do produto (Se for do tipo SERVIÇO, inserir 1)");
        double valor = leitor.lerDouble("Digite o valor do produto (Ex: 10.99)");
        Produto.TipoProduto tipo = leitor.lerTipoDeProduto(String.format("""
                ==========================================
                Selecione o tipo do produto:
                
                %s
                
                ==========================================
                
                """, Arrays.toString(Produto.TipoProduto.values())));
        String descricao = leitor.lerString("Digite a descrição do produto (OPCIONAL)");

        Produto novoProduto = new Produto(nome, descricao, quantidade, valor, tipo);
        gerenciadorProdutos.inserir(novoProduto);

        System.out.println("Produto " + novoProduto.getNome() + " cadastrado com sucesso!");
    }

    @Override
    public void listarProdutos() {
        List<Produto> produtos = gerenciadorProdutos.listarTodos();

        if(produtos == null)  {
            return;
        }

        System.out.println("========= LISTAGEM DE PRODUTOS ========================================");
        produtos.forEach(System.out::println);
        System.out.println("=======================================================================");
    }

    @Override
    public void listarProdutosContendoPalavra() {
    }

    @Override
    public void listarProdutosPorTipo() {
    }

    @Override
    public void atualizarProduto() {

    }

    @Override
    public void excluirProduto() {

    }


}
