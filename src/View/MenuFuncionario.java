package View;

import Model.*;

import java.util.InputMismatchException;

public class MenuFuncionario {
    private MenuGeral menuGeral;

    public MenuFuncionario(MenuGeral menuGeral) {
        this.menuGeral = menuGeral;
    }

    public void exibir() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU DO FUNCIONÁRIO ---");
            System.out.println("1. Cadastrar Novo Entregador e Veículo");
            System.out.println("2. Listar Entregadores na Frota");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = menuGeral.getScanner().nextInt();
                menuGeral.getScanner().nextLine();

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
                menuGeral.getScanner().nextLine();
            }
        }
    }

    private void fluxoCadastrarFuncionario() {
        System.out.println("\n[Cadastro de Funcionário]");
        String nome = menuGeral.lerTextoObrigatorio("Nome do Funcionário: ");
        String cpf = menuGeral.lerCpfValido("CPF: ");

        System.out.println("Selecione o Tipo de Veículo dele:");
        System.out.println("1 - Moto (Até 50kg)");
        System.out.println("2 - Carro (Até 150kg)");
        System.out.println("3 - Caminhão (Cargas Pesadas)");
        System.out.print("Opção: ");
        int op = menuGeral.getScanner().nextInt();
        menuGeral.getScanner().nextLine();

        VeiculoFactory factory;
        if (op == 2) {
            factory = new CarroFactory();
        } else if (op == 3) {
            factory = new CaminhaoFactory();
        } else {
            factory = new MotoFactory();
        }

        String placa = menuGeral.lerPlacaValida("Placa: ");
        String cor = menuGeral.lerTextoObrigatorio("Cor: ");

        TipoVeiculo veiculo = factory.criarVeiculo(placa, cor);
        Funcionario f = new Funcionario(nome, cpf, veiculo);

        menuGeral.getFuncionariosCadastrados().add(f);
        System.out.println("Funcionário e veículo cadastrados na frota com sucesso!");
    }

    private void listarFrota() {
        System.out.println("\n--- ENTREGADORES CADASTRADOS ---");
        if (menuGeral.getFuncionariosCadastrados().isEmpty()) {
            System.out.println("Nenhum entregador na frota.");
            return;
        }
        for (Funcionario f : menuGeral.getFuncionariosCadastrados()) {
            System.out.println(f.toString());
        }
    }
}
