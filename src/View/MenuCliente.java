package View;

import Controller.*;
import Model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class MenuCliente {
    private MenuGeral menuGeral;

    public MenuCliente(MenuGeral menuGeral) {
        this.menuGeral = menuGeral;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU DO CLIENTE ---");
            System.out.println("1. Solicitar Nova Entrega (Fazer Pedido)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = menuGeral.getScanner().nextInt();
                menuGeral.getScanner().nextLine();

                if (opcao == 1) {
                    fluxoSolicitarEntrega();
                }
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                menuGeral.getScanner().nextLine();
            }
        }
    }

    private void fluxoSolicitarEntrega() {
        System.out.println("\n[Nova Solicitação de Entrega]");

        String nomeCliente = menuGeral.lerTextoObrigatorio("Seu Nome: ");
        String cpfCliente = menuGeral.lerCpfValido("Seu CPF: ");
        String rua = menuGeral.lerTextoObrigatorio("Rua de entrega: ");
        String numero = menuGeral.lerTextoObrigatorio("Número: ");
        String bairro = menuGeral.lerTextoObrigatorio("Bairro: ");
        String cep = menuGeral.lerCepValido("CEP: ");

        Local endereco = new Local(rua, numero, bairro, cep);
        Cliente cliente = new Cliente(nomeCliente, cpfCliente, endereco);

        String nomeEnc = menuGeral.lerTextoObrigatorio("O que está enviando? (ex: Celular): ");
        String descEnc = menuGeral.lerTextoObrigatorio("Descrição do produto: ");
        String tamanhoEnc = menuGeral.lerTextoObrigatorio("Tamanho (P/M/G): ");

        double pesoEnc = 0;
        boolean pesoValido = false;
        Encomenda encomenda = null;

        while (!pesoValido) {
            try {
                System.out.print("Peso da encomenda (em Kg): ");
                pesoEnc = menuGeral.getScanner().nextDouble();
                menuGeral.getScanner().nextLine();

                encomenda = new Encomenda(nomeEnc, descEnc, pesoEnc, tamanhoEnc, cliente);
                pesoValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido para o peso.");
                menuGeral.getScanner().nextLine();
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
                escolhaFunc = menuGeral.getScanner().nextInt() - 1;
                menuGeral.getScanner().nextLine();
                if (escolhaFunc < 0 || escolhaFunc >= filtrados.size()) {
                    System.out.println("Escolha inválida");
                } else {
                    escolhaValida = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Digite um número válido para o entregador.");
                menuGeral.getScanner().nextLine();
            }
        }

        Funcionario funcionarioEscolhido = filtrados.get(escolhaFunc);

        System.out.println("\n[Modalidade de Frete]");
        System.out.println("1 - Frete Padrão");
        System.out.println("2 - Frete Expresso");
        System.out.print("Escolha: ");
        int opcaoFrete = menuGeral.getScanner().nextInt();
        menuGeral.getScanner().nextLine();

        LogisticaFactory factory = (opcaoFrete == 2) ? new LogisticaExpressaFactory() : new LogisticaPadraoFactory();
        ModalidadeFrete frete = factory.criarFrete();
        PrazoEntrega prazo = factory.criarPrazo();

        Entrega novaEntrega = new Entrega(funcionarioEscolhido, encomenda, frete, prazo);
        menuGeral.getController().adicionarEntrega(novaEntrega);

        System.out.println("\nPedido de entrega realizado com sucesso!");
        System.out.println("Entregador Designado: " + funcionarioEscolhido.getNome() + " operando um(a) " + funcionarioEscolhido.getVeiculo().getTipo());
        System.out.println("Valor do Frete: R$ " + String.format("%.2f", novaEntrega.calcularTotalFrete()));
    }

    private List<Funcionario> filtrarFuncionariosPorPeso(double peso) {
        List<Funcionario> motos = new ArrayList<>();
        List<Funcionario> carros = new ArrayList<>();
        List<Funcionario> caminhoes = new ArrayList<>();
        for (Funcionario f : menuGeral.getFuncionariosCadastrados()) {
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
}
