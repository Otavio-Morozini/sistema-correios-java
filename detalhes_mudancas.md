# Relatório de Refatoração: Modularização e Padrões de Projeto

Este documento detalha as alterações feitas no sistema de correios em Java, divididas em duas etapas principais: a **modularização dos menus de console** e a **aplicação de padrões de projeto criacionais (Factory Method e Abstract Factory)**.

---

## Etapa 1: Divisão do Menu Monolítico (`MenuConsole`)

Originalmente, o sistema contava com uma classe central chamada `MenuConsole.java` que acumulava todas as responsabilidades de entrada/saída do usuário para os fluxos de Cliente, Funcionário e Administrador.

### O que mudou:
1. **Remoção de `MenuConsole.java`**: A classe monolítica foi excluída.
2. **Criação da camada modular de View**:
   - [MenuGeral.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/View/MenuGeral.java): Atua como o coordenador geral do console. Inicializa o `Scanner` e o `CorreioController` compartilhados, e abriga os métodos utilitários de validação de leitura (ex: `lerCpfValido`, `lerCepValido`, `lerPlacaValida`, `lerTextoObrigatorio`). Também exibe o menu principal.
   - [MenuCliente.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/View/MenuCliente.java): Gerencia especificamente as interações do cliente, como preenchimento de encomenda e solicitação de frete.
   - [MenuFuncionario.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/View/MenuFuncionario.java): Contém as interações do funcionário, abrangendo cadastro de novos entregadores e listagem da frota.
   - [MenuAdministrativo.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/View/MenuAdministrativo.java): Focado no painel de administração para visualização de todas as entregas registradas no sistema.
3. **Atualização do Ponto de Entrada**:
   - [Main.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Main.java): Alterado para importar e iniciar a classe `MenuGeral` no lugar da antiga `MenuConsole`.

---

## Etapa 2: Implementação de Padrões de Projeto Criacionais

Para tornar o código mais flexível, diminuir o acoplamento e aplicar polimorfismo, adicionamos os seguintes padrões GoF:

### 1. Padrão Factory Method (Método Fábrica)
* **Objetivo**: Delegar a instanciação de objetos de veículos (`TipoVeiculo`) para subclasses especializadas, isolando a regra de capacidade de carga (peso limite) das telas de console.
* **Componentes adicionados**:
   - [VeiculoFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/VeiculoFactory.java): Interface criadora que define a assinatura do Factory Method:
     ```java
     TipoVeiculo criarVeiculo(String placa, String cor);
     ```
   - [MotoFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/MotoFactory.java): Fábrica concreta que retorna uma Moto com peso máximo padrão de `50.0` kg.
   - [CarroFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/CarroFactory.java): Fábrica concreta que retorna um Carro com peso máximo padrão de `150.0` kg.
   - [CaminhaoFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/CaminhaoFactory.java): Fábrica concreta que retorna um Caminhão com peso máximo padrão de `2000.0` kg.
* **Refatoração realizada**: O método auxiliar `pesoDoTipo(String tipo)` da interface visual foi excluído. Agora, o cadastro no `MenuFuncionario` usa o respectivo criador concreto para instanciar o veículo de forma limpa.

### 2. Padrão Abstract Factory (Fábrica Abstrata)
* **Objetivo**: Fornecer uma interface para criar famílias de objetos relacionados (neste caso, a modalidade de preço de frete e o prazo estimado de entrega) sem especificar suas classes concretas.
* **Componentes adicionados**:
   - **Família de Produtos de Prazo de Entrega**:
     - [PrazoEntrega.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/PrazoEntrega.java): Nova interface definindo o contrato de prazos de frete.
     - [PrazoNormal.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/PrazoNormal.java): Retorna prazo de 5 dias úteis (usado no frete comum).
     - [PrazoPrioritario.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/PrazoPrioritario.java): Retorna prazo de 1 dia útil (usado no frete expresso).
   - **Família de Fábricas de Logística**:
     - [LogisticaFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/LogisticaFactory.java): Interface do Abstract Factory declarando os métodos criadores das famílias:
       ```java
       ModalidadeFrete criarFrete();
       PrazoEntrega criarPrazo();
       ```
     - [LogisticaPadraoFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/LogisticaPadraoFactory.java): Retorna a combinação de `FretePadrao` e `PrazoNormal`.
     - [LogisticaExpressaFactory.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/LogisticaExpressaFactory.java): Retorna a combinação de `FreteExpresso` e `PrazoPrioritario`.
* **Refatoração realizada**:
   - [Entrega.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/Model/Entrega.java): Atualizado para conter o campo `PrazoEntrega`, inicializado via construtor. A representação textual (`toString()`) foi modificada para exibir o prazo de entrega calculado.
   - [MenuCliente.java](file:///c:/Users/Otavio/Desktop/sistema-correios-java-main/src/View/MenuCliente.java): Agora utiliza as implementações de `LogisticaFactory` para instanciar a modalidade e o prazo compatíveis em tempo de execução de forma atômica e coerente.

---

## Benefícios da Refatoração

1. **Separação de Conceitos (Single Responsibility)**: As classes do console agora cuidam estritamente do seu próprio domínio de menu.
2. **Substituição Fácil (Polimorfismo)**: Novos tipos de frete, prazos e veículos podem ser inseridos de forma transparente, apenas adicionando novas classes de fábrica, sem quebrar o código existente.
3. **Redução de Acoplamento**: A visualização não contém mais lógica de negócio, limites de carga específicos ou regras cruzadas de precificação direta em condicionais (`if/else`).
