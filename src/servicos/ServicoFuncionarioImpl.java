package servicos;

import banco.GerenciadorFuncionarios;
import entidades.Endereco;
import entidades.Funcionario;

import java.time.LocalDateTime;
import java.util.List;

public class ServicoFuncionarioImpl implements ServicoFuncionario {
    private Leitor leitor;
    private GerenciadorFuncionarios gerenciadorFuncionarios;

    public ServicoFuncionarioImpl(Leitor leitor, GerenciadorFuncionarios gerenciadorFuncionarios) {
        this.leitor = leitor;
        this.gerenciadorFuncionarios = gerenciadorFuncionarios;
    }

    @Override
    public Funcionario autenticarFuncionario() {
        System.out.println("Por favor, se autentique.");

        String cpf = leitor.lerCPF("Digite o CPF do funcionário");

        Funcionario funcionario = gerenciadorFuncionarios.buscarPorCpf(cpf);

        if (funcionario == null) {
            throw new RuntimeException("Nenhum funcionário encontrado para esse CPF.");
        }

        int tentativasDeLogin = 0;

        while (tentativasDeLogin != 3) {
            System.out.println("Tentativa " + (tentativasDeLogin + 1) + " de 3");
            String senha = leitor.lerString("Digite a sua senha");

            if (funcionario.autenticar(senha)) {
                System.out.println("Boas vindas, " + funcionario.getNome() + "!");
                return funcionario;
            } else {
                System.err.println("Senha incorreta. Tente novamente.");
                tentativasDeLogin++;
            }
        }

        throw new RuntimeException("Número máximo de tentativas atingido. Cancelando operação.");
    }

    @Override
    public Funcionario cadastrarFuncionario() {
        String cpfNovoFuncionario;
        while (true) {
            cpfNovoFuncionario = leitor.lerCPF("Digite o CPF do novo funcionário");

            if (gerenciadorFuncionarios.funcionarioCadastrado(cpfNovoFuncionario)) {
                System.err.println("CPF já cadastrado. Tente novamente.");
            } else {
                break;
            }
        }

        String celular = leitor.lerCelular("Digite o celular do funcionário");
        LocalDateTime dataDeNascimento = leitor.lerData("Digite a data de nascimento do funcionário");
        String nome = leitor.lerString("Digite o nome do funcionário");
        String email = leitor.lerString("Digite o email do funcionário");
        String senha = leitor.lerSenha("Digite a nova senha do funcionário");
        Endereco endereco = leitor.lerEndereco("Digite o endereço do funcionário");

        Funcionario novoFuncionario = new Funcionario(cpfNovoFuncionario, celular, dataDeNascimento, email, nome, senha, endereco);

        return gerenciadorFuncionarios.inserir(novoFuncionario);
    }

    @Override
    public List<Funcionario> listarTodosAtivos() {
        return gerenciadorFuncionarios.listarTodos();
    }
}
