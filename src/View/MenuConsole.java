package View;

import Controller.*;
import Model.*;

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

    public void iniciar(){
        int opcao = -1;

        while (opcao != 0) {
            try {
                System.out.println("\n========== SISTEMA DE LOGÍSTICA ==========");
                System.out.println("1. Registrar Nova Entrega");
                System.out.println("2. Listar Entregas Registradas");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        menuRegistrarEntrega();
                        break;
                    case 2:
                        menuListarEntregas();
                        break;
                    case 0:
                        System.out.println("Saindo do sistema... Até logo!");
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número inteiro válido!");
                scanner.nextLine();
            }
        }
    }

    private void menuRegistrarEntrega() {
        System.out.println("\n--- REGISTAR NOVA ENTREGA ---");

        // 1. Dados do Destinatário (Cliente) e o seu Local
        System.out.println("\n[Dados do Destinatário]");
        String nomeCliente = lerTextoObrigatorio("Nome do Cliente: ");
        String cpfCliente = lerCpfValido("CPF do Cliente: ");

        System.out.println("[Endereço de Entrega]");
        String rua = lerTextoObrigatorio("Rua: ");
        String numero = lerTextoObrigatorio("Número: ");
        String bairro = lerTextoObrigatorio("Bairro: ");
        String cep = lerCepValido("CEP: ");

        Local endereco = new Local(rua, numero, bairro, cep);
        Cliente cliente = new Cliente(nomeCliente, cpfCliente, endereco);

        // 2. Dados do Entregador (Funcionário) e o seu Veículo
        System.out.println("\n[Dados do entregador]");
        String nomeFunc = lerTextoObrigatorio("Nome do Funcionário: ");
        String cpfFunc = lerCpfValido("Cpf do Funcionário: ");

        System.out.println("\n[Dados Gerais do Veículo]");
        String placa = lerPlacaValida("Placa (7 caracteres): ");
        String cor = lerTextoObrigatorio("Cor: ");

        // 3. Dados da Encomenda
        System.out.println("\n[Dados da Encomenda]");
        String nomeEnc = lerTextoObrigatorio("Nome/Título (ex: Caixa de Livros): ");
        String descEnc = lerTextoObrigatorio("Descrição: ");
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
                System.out.println("Erro: Por favor, digite um número válido para o peso!");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro de Validação: " + e.getMessage());
            }
        }
        String nomeTipo = "";

        if (pesoEnc < 50.0) {
            nomeTipo = "Moto";
        } else if (pesoEnc >= 50.0 && pesoEnc < 150.0) {
            nomeTipo = "Carro";
        } else {
            nomeTipo = "Caminhão";
        }

        TipoVeiculo veiculo = new TipoVeiculo(nomeTipo, pesoEnc, placa, cor);
        Funcionario funcionario = new Funcionario(nomeFunc, cpfFunc, veiculo);

        // 4. Modalidade de Frete
        System.out.println("\n[Modalidade de Frete]");
        System.out.println("1 - Frete Padrão");
        System.out.println("2 - Frete Expresso");
        System.out.print("Escolha a opção: ");
        int opcaoFrete = scanner.nextInt();
        scanner.nextLine();

        ModalidadeFrete frete = (opcaoFrete == 2) ? new FreteExpresso() : new FretePadrao();

        // 5. Salvar através do Controller
        Entrega novaEntrega = new Entrega(funcionario, encomenda, frete);
        controller.adicionarEntrega(novaEntrega);

        System.out.println("\nEntrega registada com sucesso!");
        System.out.println("Valor calculado do frete: R$ " + novaEntrega.calcularTotalFrete());
    }

    private void menuListarEntregas() {
        System.out.println("\n--- LISTA DE ENTREGAS REGISTADAS ---");

        List<Entrega> lista = controller.listarTodasEntregas();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma entrega foi registada até ao momento.");
            return;
        }
        for (Entrega e : lista) {
            System.out.println(e.toString());
            System.out.println("--------------------------------------------------");
        }
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
