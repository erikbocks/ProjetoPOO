package servicos.impl;

import banco.GerenciadorClientes;
import banco.GerenciadorConsultas;
import banco.GerenciadorPets;
import banco.GerenciadorVeterinarios;
import entidades.Cliente;
import entidades.Consulta;
import entidades.Pet;
import entidades.Veterinario;
import servicos.Leitor;
import servicos.ServicoConsulta;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ServicoConsultaImpl implements ServicoConsulta {
    private Leitor leitor;
    private GerenciadorConsultas gerenciadorConsultas;
    private GerenciadorPets gerenciadorPets;
    private GerenciadorClientes gerenciadorClientes;
    private GerenciadorVeterinarios gerenciadorVeterinarios;

    public ServicoConsultaImpl(Leitor leitor, GerenciadorConsultas gerenciadorConsultas, GerenciadorPets gerenciadorPets, GerenciadorClientes gerenciadorClientes, GerenciadorVeterinarios gerenciadorVeterinarios) {
        this.leitor = leitor;
        this.gerenciadorConsultas = gerenciadorConsultas;
        this.gerenciadorPets = gerenciadorPets;
        this.gerenciadorClientes = gerenciadorClientes;
        this.gerenciadorVeterinarios = gerenciadorVeterinarios;
    }

    @Override
    public void mostrarMenu() {
        System.out.println("""
                ============================= OPERAÇÕES - CONSULTAS ===========================
                
                1. Cadastrar consulta.
                2. Listar todas as consultas.
                3. Listar consultas por pet.
                4. Listar consultas por cliente.
                5. Buscar consulta por veterinário.
                6. Buscar consulta por data.
                7. Buscar consulta por status.
                8. Excluir consulta.
                9. Atualizar consulta.
                0. Voltar.
                
                =================================================================================
                """);

        int opcaoOperacao = leitor.lerInt("Qual operação deseja realizar?");

        switch (opcaoOperacao) {
            case 0:
                System.out.println("Retornando ao menu principal...");
                break;
            case 1:
                inserirConsulta();
                break;
            case 2:
                listarConsultas();
                break;
            case 3:
                listarConsultasPorPet();
                break;
            case 4:
                listarConsultasPorCliente();
                break;
            case 5:
                buscarConsultaPorVeterinario();
                break;
            case 6:
                buscarConsultaPorData();
                break;
            case 7:
                buscarConsultaPorStatus();
                break;
            case 8:
                excluirConsulta();
                break;
            case 9:
                atualizarConsulta();
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    }

    @Override
    public void inserirConsulta() {
        System.out.println("Boas vindas ao cadastro de consultas!");

        String cpfDoVeterinario = leitor.lerCPF("Digite o CPF do veterinário");

        Veterinario veterinario = gerenciadorVeterinarios.buscarPorCpf(cpfDoVeterinario);

        if (veterinario == null) {
            System.err.println("Veterinário não encontrado. Tente novamente.");
            return;
        }

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        List<Pet> petsDoCliente = gerenciadorPets.listarPorTutor(cpfDoCliente);

        if (petsDoCliente.isEmpty()) {
            System.err.println("Nenhum pet encontrado para o cliente com CPF: " + cpfDoCliente);
            return;
        }

        Pet petSelecionado;

        if (petsDoCliente.size() == 1) {
            petSelecionado = petsDoCliente.getFirst();
        } else {
            System.out.println("Selecione o pet:");

            for (int i = 0; i < petsDoCliente.size(); i++) {
                System.out.println((i + 1) + ". " + petsDoCliente.get(i).getNome());
            }

            int opcaoPet = leitor.lerInt("Digite o número do pet");

            if (opcaoPet > petsDoCliente.size() || opcaoPet < 1) {
                System.err.println("Opção inválida. Tente novamente.");
                return;
            }

            petSelecionado = petsDoCliente.get(opcaoPet - 1);
        }

        LocalDateTime data = leitor.lerData("Digite a data da consulta");

        Consulta novaConsulta = new Consulta(petSelecionado, veterinario, data);
        gerenciadorConsultas.inserir(novaConsulta);

        System.out.println("Consulta agendada com sucesso!");
    }

    @Override
    public void listarConsultas() {
        System.out.println("Boas vindas à listagem de consultas!");

        List<Consulta> consultas = gerenciadorConsultas.listarTodos();
        System.out.println("====================== LISTA DE CONSULTAS ======================");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
        } else {
            consultas.forEach(System.out::println);
        }
        System.out.println("================================================================");
    }

    @Override
    public void listarConsultasPorPet() {
        System.out.println("Boas vindas à listagem de consultas por pet!");

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        List<Pet> petsDoCliente = gerenciadorPets.listarPorTutor(cpfDoCliente);

        if (petsDoCliente.isEmpty()) {
            System.err.println("Nenhum pet encontrado para o cliente com CPF: " + cpfDoCliente);
            return;
        }

        Pet petSelecionado;

        if (petsDoCliente.size() == 1) {
            petSelecionado = petsDoCliente.getFirst();
        } else {
            System.out.println("Selecione o pet:");

            for (int i = 0; i < petsDoCliente.size(); i++) {
                System.out.println((i + 1) + ". " + petsDoCliente.get(i).getNome());
            }

            int opcaoPet = leitor.lerInt("Digite o número do pet");

            if (opcaoPet > petsDoCliente.size() || opcaoPet < 1) {
                System.err.println("Opção inválida. Tente novamente.");
                return;
            }

            petSelecionado = petsDoCliente.get(opcaoPet - 1);
        }

        List<Consulta> consultas = gerenciadorConsultas.listarConsultasPorPet(petSelecionado.getId());
        System.out.println("====================== LISTA DE CONSULTAS DO PET ======================");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o pet: " + petSelecionado.getNome());
        } else {
            consultas.forEach(System.out::println);
        }
        System.out.println("================================================================");
    }

    @Override
    public void listarConsultasPorCliente() {
        System.out.println("Boas vindas à listagem de consultas por cliente!");

        String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");

        Cliente cliente = gerenciadorClientes.buscarPorCpf(cpfDoCliente);

        if (cliente == null) {
            System.err.println("Cliente não encontrado. Tente novamente.");
            return;
        }

        List<Consulta> consultas = gerenciadorConsultas.listarConsultasPorCliente(cpfDoCliente);
        System.out.println("====================== LISTA DE CONSULTAS DO CLIENTE ======================");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o cliente: " + cliente.getNome());
        } else {
            consultas.forEach(System.out::println);
        }
        System.out.println("================================================================");
    }

    @Override
    public void buscarConsultaPorVeterinario() {
        System.out.println("Boas vindas à busca de consultas por veterinário!");
        String cpfDoVeterinario = leitor.lerCPF("Digite o CPF do veterinário");
        List<Consulta> consultas = gerenciadorConsultas.buscarConsultaPorVeterinario(cpfDoVeterinario);

        System.out.println("====================== LISTA DE CONSULTAS DO VETERINÁRIO ======================");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para o veterinário com CPF: " + cpfDoVeterinario);
        } else {
            consultas.forEach(System.out::println);
        }
        System.out.println("================================================================================");
    }

    @Override
    public void buscarConsultaPorData() {
        System.out.println("Boas vindas à busca de consultas por data!");

        LocalDateTime data = leitor.lerData("Digite a data da consulta (formato: AAAA-MM-DD HH:MM)");
        List<Consulta> consultas = gerenciadorConsultas.buscarConsultaPorData(data);
        System.out.println("====================== LISTA DE CONSULTAS POR DATA ======================");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para a data: " + data);
        } else {
            consultas.forEach(System.out::println);
        }
        System.out.println("================================================================");
    }

    @Override
    public void buscarConsultaPorStatus() {
        System.out.println("Boas vindas à busca de consultas por status!");
        String statusConsulta = leitor.lerString("Digite o status da consulta " + Arrays.toString(Consulta.Status.values()));

        Consulta.Status status = Consulta.procurarStatusPorNome(statusConsulta);
        if (status == null) {
            System.err.println("Status inválido. Tente novamente.");
            return;
        }
        List<Consulta> consultas = gerenciadorConsultas.buscarConsultaPorStatus(status);
        System.out.println("====================== LISTA DE CONSULTAS POR STATUS ======================");
        consultas.forEach(System.out::println);
        System.out.println("===========================================================================");
    }

    @Override
    public void excluirConsulta() {
        System.out.println("Boas vindas à exclusão de consultas!");

        String cpfDoVeterinario = leitor.lerCPF("Digite o CPF do veterinário");
        List<Consulta> consultas = gerenciadorConsultas.buscarConsultaPorVeterinario(cpfDoVeterinario);

        if (consultas.isEmpty()) {
            System.err.println("Nenhuma consulta encontrada para o veterinário com CPF: " + cpfDoVeterinario);
            return;
        }

        System.out.println("Selecione a consulta a ser excluída:");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println((i + 1) + ". " + consultas.get(i));
        }

        int opcaoConsulta = leitor.lerInt("Digite o número da consulta a ser excluída");
        if (opcaoConsulta < 1 || opcaoConsulta > consultas.size()) {
            System.err.println("Opção inválida. Tente novamente.");
            return;
        }

        Consulta consultaSelecionada = consultas.get(opcaoConsulta - 1);

        gerenciadorConsultas.excluir(consultaSelecionada);
        System.out.println("Consulta excluída com sucesso!");
    }

    @Override
    public void atualizarConsulta() {
        System.out.println("Boas vindas à atualização de consultas!");

        String cpfDoVeterinario = leitor.lerCPF("Digite o CPF do veterinário");
        List<Consulta> consultas = gerenciadorConsultas.buscarConsultaPorVeterinario(cpfDoVeterinario);

        if (consultas.isEmpty()) {
            System.err.println("Nenhuma consulta encontrada para o veterinário com CPF: " + cpfDoVeterinario);
            return;
        }

        System.out.println("Selecione a consulta a ser atualizada:");

        for (int i = 0; i < consultas.size(); i++) {
            System.out.println((i + 1) + ". " + consultas.get(i));
        }

        int opcaoConsulta = leitor.lerInt("Digite o número da consulta a ser atualizada");

        if (opcaoConsulta < 1 || opcaoConsulta > consultas.size()) {
            System.err.println("Opção inválida. Tente novamente.");
            return;
        }

        Consulta consultaSelecionada = consultas.get(opcaoConsulta - 1);

        String novoStatus = leitor.lerString("Digite o novo status da consulta " + Arrays.toString(Consulta.Status.values()));

        if (!novoStatus.isBlank()) {
            Consulta.Status status = Consulta.procurarStatusPorNome(novoStatus);

            if (status == null) {
                System.err.println("Status inválido. Tente novamente.");
                return;
            }

            consultaSelecionada.setStatus(status);

            if (status == Consulta.Status.FINALIZADA) {
                consultaSelecionada.setDataFechamento(LocalDateTime.now());
            }
        }

        boolean desejaAtualizarData = leitor.lerBoolean("Deseja atualizar a data da consulta?");
        if (desejaAtualizarData) {
            LocalDateTime novaData = leitor.lerData("Digite a nova data da consulta (formato: AAAA-MM-DD HH:MM)");
            consultaSelecionada.setData(novaData);
        }

        boolean desejaAtualizarVeterinario = leitor.lerBoolean("Deseja atualizar o veterinário da consulta?");
        if (desejaAtualizarVeterinario) {
            String novoCpfVeterinario = leitor.lerCPF("Digite o CPF do novo veterinário");
            Veterinario novoVeterinario = gerenciadorVeterinarios.buscarPorCpf(novoCpfVeterinario);

            if (novoVeterinario == null) {
                System.err.println("Veterinário não encontrado. Tente novamente.");
                return;
            }

            consultaSelecionada.setVeterinario(novoVeterinario);
        }

        boolean desejaAtualizarPet = leitor.lerBoolean("Deseja atualizar o pet da consulta?");
        if (desejaAtualizarPet) {
            String cpfDoCliente = leitor.lerCPF("Digite o CPF do cliente");
            List<Pet> petsDoCliente = gerenciadorPets.listarPorTutor(cpfDoCliente);

            if (petsDoCliente.isEmpty()) {
                System.err.println("Nenhum pet encontrado para o cliente com CPF: " + cpfDoCliente);
                return;
            }

            Pet petSelecionado;

            if (petsDoCliente.size() == 1) {
                petSelecionado = petsDoCliente.getFirst();
            } else {
                System.out.println("Selecione o pet:");

                for (int i = 0; i < petsDoCliente.size(); i++) {
                    System.out.println((i + 1) + ". " + petsDoCliente.get(i).getNome());
                }

                int opcaoPet = leitor.lerInt("Digite o número do pet");

                if (opcaoPet > petsDoCliente.size() || opcaoPet < 1) {
                    System.err.println("Opção inválida. Tente novamente.");
                    return;
                }

                petSelecionado = petsDoCliente.get(opcaoPet - 1);
            }

            consultaSelecionada.setPet(petSelecionado);
        }

        gerenciadorConsultas.atualizar(consultaSelecionada);
        System.out.println("Consulta atualizada com sucesso!");
    }
}
