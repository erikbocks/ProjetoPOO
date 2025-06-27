package servicos.impl;

import banco.GerenciadorProdutos;
import entidades.Produto;
import servicos.Leitor;
import servicos.ServicoProduto;

import java.time.LocalDateTime;
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
            case 0:
                System.out.println("Retornando ao menu principal...");
                return;
            case 1:
                cadastrarProduto();
                break;
            case 2:
                buscarProdutoPorCodigo();
                break;
            case 3:
                listarProdutos();
                break;
            case 4:
                listarProdutosContendoPalavra();
                break;
            case 5:
                listarProdutosPorTipo();
                break;
            case 6:
                atualizarProduto();
                break;
            case 7:
                excluirProduto();
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
    public void buscarProdutoPorCodigo() {
        System.out.println("Boas vindas à busca de produto por código!");

        String codigo = leitor.lerString("Digite o código do produto que deseja buscar");

        Produto produto = gerenciadorProdutos.buscarPorCodigo(codigo);

        if (produto == null) {
            System.out.println("Nenhum produto encontrado com o código digitado");
            return;
        }

        System.out.println("Produto encontrado. Detalhes:");
        System.out.println(produto);
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
        System.out.println("Boas vindas à listagem de produtos por palavra!");

        String palavra = leitor.lerString("Digite a palavra que deseja buscar nos produtos");

        List<Produto> produtos = gerenciadorProdutos.listarProdutoContendoPalavra(palavra);

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com a palavra digitada");
            return;
        }

        System.out.println("====================== LISTA DE PRODUTOS ==========================");
        produtos.forEach(System.out::println);
        System.out.println("===================================================================");
    }

    @Override
    public void listarProdutosPorTipo() {
        System.out.println("Boas vindas à listagem de produtos por tipo!");

        Produto.TipoProduto tipo = leitor.lerTipoDeProduto(String.format("""
                ==========================================
                Selecione o tipo do produto:
                
                %s
                
                ==========================================
                """, Arrays.toString(Produto.TipoProduto.values())));

        List<Produto> produtos = gerenciadorProdutos.listarPorTipo(tipo);

        if (produtos == null || produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o tipo digitado");
            return;
        }

        System.out.println("====================== LISTA DE PRODUTOS ==========================");
        produtos.forEach(System.out::println);
        System.out.println("===================================================================");
    }

    @Override
    public void atualizarProduto() {
        System.out.println("Boas vindas à atualização de produto!");

        String codigo = leitor.lerString("Digite o código do produto que deseja atualizar");

        Produto produto = gerenciadorProdutos.buscarPorCodigo(codigo);

        if (produto == null) {
            System.out.println("Nenhum produto encontrado com o código digitado");
            return;
        }

        String nome = leitor.lerString("Digite o novo nome do produto (Deixe em branco para não alterar)");

        if (!nome.isBlank() && !nome.equals(produto.getNome())) {
            produto.setNome(nome);
        }

        int quantidade = leitor.lerInt("Digite a nova quantidade do produto (Digite -1 para não alterar)");

        if (quantidade != -1) {
            produto.setQuantidade(quantidade);
        } else {
            System.out.println("Quantidade não alterada, mantendo o valor atual: " + produto.getQuantidade());
        }

        double valor = leitor.lerDouble("Digite o novo valor do produto (Deixe em branco para não alterar)");

        if (valor != -1) {
            produto.setValor(valor);
        } else {
            System.out.println("Valor não alterado, mantendo o valor atual.");
        }

        String novoTipo = leitor.lerString(String.format("""
                ==========================================
                Selecione o novo tipo do produto (Deixe em branco para não alterar):
                
                %s
                
                ==========================================
                """, Arrays.toString(Produto.TipoProduto.values())));

        if (!novoTipo.isBlank()) {
            Produto.TipoProduto tipoProduto = Produto.procurarTipoDeProdutoPorNome(novoTipo);
            if (tipoProduto != null) {
                produto.setTipo(tipoProduto);
            } else {
                System.err.println("Tipo de produto inválido. Mantendo o tipo atual");
            }
        }

        String descricao = leitor.lerString("Digite a nova descrição do produto (OPCIONAL, deixe em branco para não alterar)");

        if (!descricao.isBlank()) {
            produto.setDescricao(descricao);
        }

        produto.setUltimaAtualizacao(LocalDateTime.now());
        gerenciadorProdutos.atualizar(produto);

        System.out.println("Produto atualizado com sucesso!");
    }

    @Override
    public void excluirProduto() {
        System.out.println("Boas vindas à exclusão de produto!");

        String codigo = leitor.lerString("Digite o código do produto que deseja excluir");
        Produto produto = gerenciadorProdutos.buscarPorCodigo(codigo);

        if (produto == null) {
            System.out.println("Nenhum produto encontrado com o código digitado");
            return;
        }

        boolean confirmacao = leitor.lerBoolean("Tem certeza que deseja excluir o produto ");

        if (!confirmacao) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        gerenciadorProdutos.excluir(produto);
        System.out.println("Produto excluído com sucesso!");
    }
}
