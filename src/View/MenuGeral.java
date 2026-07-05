package View;

import Controller.*;
import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class MenuGeral {
    private CorreioController controller;
    private Scanner scanner;
    private List<Funcionario> funcionariosCadastrados = new ArrayList<>();
    private List<Cliente> clientesCadastrados = new ArrayList<>();

    public MenuGeral() {
        this.controller = new CorreioController();
        this.scanner = new Scanner(System.in);
    }

    public CorreioController getController() {
        return controller;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public List<Funcionario> getFuncionariosCadastrados() {
        return funcionariosCadastrados;
    }

    public List<Cliente> getClientesCadastrados() {
        return clientesCadastrados;
    }

    public void iniciar() {
        popularDadosIniciais();

        MenuCliente menuCliente = new MenuCliente(this);
        MenuFuncionario menuFuncionario = new MenuFuncionario(this);
        MenuAdministrativo menuAdministrativo = new MenuAdministrativo(this);

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
                        menuCliente.exibir();
                        break;
                    case 2:
                        menuFuncionario.exibir();
                        break;
                    case 3:
                        menuAdministrativo.exibir();
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

    private void popularDadosIniciais() {
        funcionariosCadastrados.add(new Funcionario("Luiz Motoboy", "11111111111", new TipoVeiculo("Moto", 50.0, "MOT1111", "Preta")));
        funcionariosCadastrados.add(new Funcionario("Otavio Carro", "22222222222", new TipoVeiculo("Carro", 150.0, "CAR2222", "Branco")));
        funcionariosCadastrados.add(new Funcionario("Lucas Caminhão", "33333333333", new TipoVeiculo("Caminhão", 2000.0, "CAM3333", "Azul")));
    }

    public String lerTextoObrigatorio(String mensagem) {
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

    public String lerCpfValido(String mensagem) {
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

    public String lerCepValido(String mensagem) {
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

    public String lerPlacaValida(String mensagem) {
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
