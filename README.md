# Sistema de Gestão de Clínica Veterinária

Este projeto é um sistema simples para gerenciamento de uma clínica veterinária, desenvolvido em Java. Ele permite o cadastro e controle de produtos, vendas, consultas, pets, veterinários e prontuários.

## Funcionalidades

- Cadastro e atualização de produtos (alimentos, medicamentos, acessórios, serviços, etc.)
- Registro de vendas com itens vendidos e controle de estoque
- Agendamento e finalização de consultas veterinárias
- Geração de prontuários para pets atendidos

## Estrutura do Projeto

- `entidades`: Contém as classes que representam os principais objetos do sistema, como Cliente, Pet, Veterinário, Consulta, Prontuário, Produto, Venda e Funcionário.
- `servicos`: Implementa o leitor de _input_.
- `validadores`: Contém a classe responsável pela validação dos dados de entrada.

## Requisitos

- Java 8 ou superior
- IDE recomendada: IntelliJ IDEA

## Como Executar

1. Clone o repositório.
2. Importe o projeto em sua IDE.
3. Compile e execute a aplicação conforme as instruções do seu ambiente.

## Observações

- O formato de datas segue o padrão: `dd MMM yyyy - HH:mm` (ex: 18 may 2005 - 00:00).