package View;

import Controller.*;
import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MenuConsole {
    private CorreioController controller;
    private Scanner scanner;

    public MenuConsole(){
        this.controller = new CorreioController();
        this.scanner = new Scanner(System.in);
    }

    private List<Funcionario> funcionariosCadastrados = new ArrayList<>();
    private List<Cliente> clientesCadastrados = new ArrayList<>();

    public void iniciar() {
        popularDadosIniciais();

        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n========== SISTEMA DE LOGÍSTICA MULTI-MENU ==========");
                System.out.println("1. Menu do Cliente ");
                System.out.println("2. Menu do Funcionário");
                System.out.println("3. Menu Administrativo");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        menuCliente();
                        break;
                    case 2:
                        menuFuncionario();
                        break;
                    case 3:
                        menuAdm();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite um número inteiro!");
                scanner.nextLine();
            }
        }
    }
    // =========================================================
    // SUB-MENU 1: CLIENTE
    // =========================================================
    private void menuCliente() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU DO CLIENTE ---");
            System.out.println("1. Solicitar Nova Entrega (Fazer Pedido)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) {
                    fluxoSolicitarEntrega();
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                scanner.nextLine();
            }
        }
    }
    // =========================================================
    // SUB-MENU 2: FUNCIONÁRIO
    // =========================================================
    private void menuFuncionario() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU DO FUNCIONÁRIO ---");
            System.out.println("1. Cadastrar Novo Entregador e Veículo");
            System.out.println("2. Listar Entregadores na Frota");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        fluxoCadastrarFuncionario();
                        break;
                    case 2:
                        listarFrota();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                scanner.nextLine();
            }
        }
    }
    // =========================================================
    // SUB-MENU 2: ADMINISTRADOR
    // =========================================================
    private void menuAdm() {
        System.out.println("\n--- SISTEMA ADMINISTRATIVO - TODAS AS ENTREGAS ---");
        List<Entrega> lista = controller.listarTodasEntregas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma entrega solicitada no sistema até agora.");
            return;
        }
        for (Entrega e : lista) {
            System.out.println(e.toString());
            System.out.println("--------------------------------------------------");
        }
    }
    // =========================================================
    // LÓGICA DE SOLICITAÇÃO DE ENTREGA
    // =========================================================
    private void fluxoSolicitarEntrega() {
        System.out.println("\n[Nova Solicitação de Entrega]");

        String nomeCliente = lerTextoObrigatorio("Seu Nome: ");
        String cpfCliente = lerCpfValido("Seu CPF: ");
        String rua = lerTextoObrigatorio("Rua de entrega: ");
        String numero = lerTextoObrigatorio("Número: ");
        String bairro = lerTextoObrigatorio("Bairro: ");
        String cep = lerCepValido("CEP: ");

        Local endereco = new Local(rua, numero, bairro, cep);
        Cliente cliente = new Cliente(nomeCliente, cpfCliente, endereco);

        String nomeEnc = lerTextoObrigatorio("O que está enviando? (ex: Celular): ");
        String descEnc = lerTextoObrigatorio("Descrição do produto: ");
        String tamanhoEnc = lerTextoObrigatorio("Tamanho (P/M/G): ");

        double pesoEnc = 0;
        boolean pesoValido = false;
        Encomenda encomenda = null;

        while (!pesoValido) {
            try {
                System.out.print("Peso da encomenda (em Kg): ");
                pesoEnc = scanner.nextDouble();
                scanner.nextLine();

                encomenda = new Encomenda(nomeEnc, descEnc, pesoEnc, tamanhoEnc, cliente);
                pesoValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido para o peso.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        List<Funcionario> filtrados = filtrarFuncionariosPorPeso(pesoEnc);

        if (filtrados.isEmpty()) {
            System.out.println("\nDesculpe! Não temos nenhum veículo disponível na frota cadastrado para suportar o peso de " + pesoEnc + "kg no momento.");
            return;
        }

        System.out.println("\nSelecione um dos entregadores disponíveis para a sua carga:");
        for (int i = 0; i < filtrados.size(); i++) {
            System.out.println((i + 1) + " - " + filtrados.get(i).getNome() + " [Veículo: " + filtrados.get(i).getVeiculo().getTipo() + "]");
        }

        boolean escolhaValida = false;
        int escolhaFunc = -1;

        while(!escolhaValida) {
            try {
                System.out.print("Escolha o entregador pelo número: ");
                escolhaFunc = scanner.nextInt() - 1;
                scanner.nextLine();
                if (escolhaFunc < 0 || escolhaFunc >= filtrados.size()) {
                    System.out.println("Escolha inválida");
                } else {
                    escolhaValida = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Digite um número válido para o entregador.");
                scanner.nextLine();
            }
        }

        Funcionario funcionarioEscolhido = filtrados.get(escolhaFunc);

        System.out.println("\n[Modalidade de Frete]");
        System.out.println("1 - Frete Padrão");
        System.out.println("2 - Frete Expresso");
        System.out.print("Escolha: ");
        int opcaoFrete = scanner.nextInt();
        scanner.nextLine();

        ModalidadeFrete frete = (opcaoFrete == 2) ? new FreteExpresso() : new FretePadrao();

        Entrega novaEntrega = new Entrega(funcionarioEscolhido, encomenda, frete);
        controller.adicionarEntrega(novaEntrega);

        System.out.println("\nPedido de entrega realizado com sucesso!");
        System.out.println("Entregador Designado: " + funcionarioEscolhido.getNome() + " operando um(a) " + funcionarioEscolhido.getVeiculo().getTipo());
        System.out.println("Valor do Frete: R$ " + String.format("%.2f", novaEntrega.calcularTotalFrete()));
    }

    private List<Funcionario> filtrarFuncionariosPorPeso(double peso) {
        List<Funcionario> motos = new ArrayList<>();
        List<Funcionario> carros = new ArrayList<>();
        List<Funcionario> caminhoes = new ArrayList<>();
        for (Funcionario f : funcionariosCadastrados) {
            String tipo = f.getVeiculo().getTipo();
            if (tipo.equalsIgnoreCase("Moto")) motos.add(f);
            else if (tipo.equalsIgnoreCase("Carro")) carros.add(f);
            else if (tipo.equalsIgnoreCase("Caminhão")) caminhoes.add(f);
        }

        if (peso < 50.0) {
            if (!motos.isEmpty()) return motos;
            if (!carros.isEmpty()) return carros;
            return caminhoes;
        }
        else if (peso >= 50.0 && peso < 150.0) {
            if (!carros.isEmpty()) return carros;
            return caminhoes;
        }
        else {
            return caminhoes;
        }
    }
    // =========================================================
    // CADASTROS COMPLEMENTARES
    // =========================================================
    private void fluxoCadastrarFuncionario() {
        System.out.println("\n[Cadastro de Funcionário]");
        String nome = lerTextoObrigatorio("Nome do Funcionário: ");
        String cpf = lerCpfValido("CPF: ");

        System.out.println("Selecione o Tipo de Veículo dele:");
        System.out.println("1 - Moto (Até 50kg)");
        System.out.println("2 - Carro (Até 150kg)");
        System.out.println("3 - Caminhão (Cargas Pesadas)");
        System.out.print("Opção: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        String tipo = "Moto";
        if (op == 2) tipo = "Carro";
        if (op == 3) tipo = "Caminhão";

        String placa = lerPlacaValida("Placa: ");
        String cor = lerTextoObrigatorio("Cor: ");

        TipoVeiculo veiculo = new TipoVeiculo(tipo, pesoDoTipo(tipo), placa, cor);
        Funcionario f = new Funcionario(nome, cpf, veiculo);

        funcionariosCadastrados.add(f);
        System.out.println("Funcionário e veículo cadastrados na frota com sucesso!");
    }

    private double pesoDoTipo(String tipo) {
        if(tipo.equals("Moto")) return 50.0;
        if(tipo.equals("Carro")) return 150.0;
        return 2000.0;
    }

    private void listarFrota() {
        System.out.println("\n--- ENTREGADORES CADASTRADOS ---");
        if (funcionariosCadastrados.isEmpty()) {
            System.out.println("Nenhum entregador na frota.");
            return;
        }
        for (Funcionario f : funcionariosCadastrados) {
            System.out.println(f.toString());
        }
    }


    private void popularDadosIniciais() {
        funcionariosCadastrados.add(new Funcionario("Luiz Motoboy", "11111111111", new TipoVeiculo("Moto", 50.0, "MOT1111", "Preta")));
        funcionariosCadastrados.add(new Funcionario("Otavio Carro", "22222222222", new TipoVeiculo("Carro", 150.0, "CAR2222", "Branco")));
        funcionariosCadastrados.add(new Funcionario("Lucas Caminhão", "33333333333", new TipoVeiculo("Caminhão", 2000.0, "CAM3333", "Azul")));
    }

    private String lerTextoObrigatorio(String mensagem) {
        String texto = "";
        while (texto.trim().isEmpty()) {
            System.out.print(mensagem);
            texto = scanner.nextLine();

            if (texto.trim().isEmpty()) {
                System.out.println("Erro: Este campo é obrigatório e não pode ficar vazio!");
            }
        }
        return texto;
    }
    private String lerCpfValido(String mensagem) {
        String cpf = "";
        boolean valido = false;

        while (!valido) {
            System.out.print(mensagem);
            cpf = scanner.nextLine();

            String cpfLimpo = cpf.replaceAll("[^0-9]", "");
            if (cpfLimpo.length() == 11) {
                valido = true;
            } else {
                System.out.println("Erro: O CPF deve ter 11 dígitos. Tente novamente.");
            }
        }
        return cpf;
    }

    private String lerCepValido(String mensagem) {
        String cep = "";
        boolean valido = false;
        while (!valido) {
            System.out.print(mensagem);
            cep = scanner.nextLine();
            String cepLimpo = cep.replaceAll("[^0-9]", "");
            if (cepLimpo.length() == 8) {
                valido = true;
                cep = cepLimpo;
            } else {
                System.out.println("Erro: O CEP deve ter exatamente 8 dígitos.");
            }
        }
        return cep;
    }

    private String lerPlacaValida(String mensagem) {
        String placa = "";
        boolean valido = false;
        while (!valido) {
            System.out.print(mensagem);
            placa = scanner.nextLine();
            String placaLimpa = placa.replaceAll("\\s+", "");
            if (placaLimpa.length() == 7) {
                valido = true;
                placa = placaLimpa.toUpperCase();
            } else {
                System.out.println("Erro: A placa deve ter exatamente 7 caracteres (ex: ABC1234).");
            }
        }
        return placa;
    }
}
