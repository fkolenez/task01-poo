# Pendências do Projeto

Documento gerado em 27/06/2026 para consolidar o que ainda falta no código e conferir o estado real do `logical-fixes-checklist.md`.

## Estado geral

- O projeto compila com `javac src/*.java -d out`.
- O fluxo de pedido já cria pedido, adiciona itens e cadastra o pedido como `AGUARDANDO_PAGAMENTO`.
- O pagamento do pedido foi separado: o comprador precisa escolher a opção de pagar para descontar saldo, reter fundos via `Escrow` e baixar estoque.
- O status do pedido agora usa `Pedido.StatusPedido` em vez de `String`.
- O fluxo de escrow ainda está incompleto: o dinheiro é retido, mas ainda não existe conclusão da venda nem cancelamento com devolução.

## Fluxo atual de pedido

1. `Main.realizarPedido(...)` cria um `Pedido` com a data atual e comprador.
2. `Pedido` inicia com status `AGUARDANDO_PAGAMENTO`.
3. O comprador escolhe produtos e quantidades.
4. O sistema valida se a quantidade informada é positiva e se a soma daquele produto dentro do pedido não passa do estoque atual.
5. O pedido é cadastrado em `Pedido.listaDePedidos`.
6. O resumo do pedido é exibido com status `AGUARDANDO_PAGAMENTO`.
7. O comprador escolhe `Pagar pedido`.
8. O sistema valida saldo e estoque novamente.
9. `Escrow.reterFundos(...)` muda o status do pedido para `VALOR_RETIDO`.
10. O saldo do comprador é descontado.
11. O estoque dos produtos é reduzido.

## Pendências principais

### 1. Completar o fluxo de escrow

Hoje existe apenas:

```java
AGUARDANDO_PAGAMENTO -> VALOR_RETIDO
```

Ainda falta implementar:

```java
VALOR_RETIDO -> CONCLUIDO
VALOR_RETIDO -> CANCELADO
```

Sugestões:

- Criar método `liberarFundos(Pedido pedido)` em `Escrow`.
- Criar método `cancelarPedido(Pedido pedido)` em `Escrow`.
- Definir em qual menu o vendedor ou administrador vai concluir/cancelar pedidos.

### 2. Atualizar extrato do vendedor

O vendedor possui `getExtrato()` e `setExtrato(...)`, mas o extrato ainda não é atualizado.

Quando um pedido for concluído, o sistema deve:

- percorrer os itens do pedido;
- encontrar o vendedor pelo `produto.getIdVendedor()`;
- somar `item.calcularTotal()` no extrato desse vendedor.

Importante: com escrow, o ideal é atualizar o extrato apenas quando o pedido virar `CONCLUIDO`, não no momento em que o dinheiro fica `VALOR_RETIDO`.

### 3. Cancelamento precisa devolver saldo e estoque

Quando um pedido em `VALOR_RETIDO` for cancelado, o sistema deve:

- mudar status para `CANCELADO`;
- devolver o valor ao saldo do comprador;
- devolver os itens ao estoque;
- não atualizar o extrato do vendedor.

O pedido já guarda o comprador, então o próximo passo é implementar a devolução.

### 4. Conclusão precisa pagar vendedor

Quando um pedido em `VALOR_RETIDO` for concluído, o sistema deve:

- mudar status para `CONCLUIDO`;
- atualizar o extrato de cada vendedor envolvido;
- manter o estoque já reduzido;
- não descontar saldo do comprador novamente.

### 5. Ajustar relatório de pedidos

O método `relatorioPedidosEmAberto(...)` foi ajustado para mostrar apenas pedidos com status `VALOR_RETIDO`.

Ainda vale melhorar o nome do método, porque ele agora representa pedidos pagos que aguardam conclusão.

Opções:

- Renomear para `relatorioPedidosPagos`.
- Separar relatórios por status se o projeto precisar.

### 6. Corrigir bug no menu do administrador

No `switch` do menu administrador, o `case 3` chama `removerUsuario()` mas não tem `break`.

Resultado: depois de remover usuário, o fluxo cai no `case 0` e sai do menu administrador.

Precisa ficar assim:

```java
case 3:
    removerUsuario();
    break;
```

### 7. Melhorar mensagens e nomes

Algumas mensagens e nomes ainda estão informais ou com erros de digitação.

Exemplos:

- `"sucessexo"`
- `"Banir o betinha desfuncional"`
- comentários com termos informais

Isso não quebra a lógica, mas prejudica a entrega do projeto.

## Conferência do logical-fixes-checklist.md

Esta seção compara o arquivo `docs/logical-fixes-checklist.md` com o estado real do código.

### Aplicados

- Atualizar o estoque ao finalizar um pedido.
  - Estado: aplicado.
  - Evidência: `Main.pagarPedido(...)` percorre `pedido.getItens()` e chama `produto.setEstoque(...)`.

- Impedir que o comprador adicione ao mesmo pedido uma quantidade total maior que o estoque.
  - Estado: aplicado.
  - Evidência: `Main.realizarPedido(...)` usa `quantidadeDoProdutoNoPedido(...)` antes de adicionar novo item.

- Remover produto também da lista do vendedor.
  - Estado: aplicado.
  - Evidência: `removerProduto(...)` chama `vendedor.getListaDeProdutosVendedor().removeIf(...)`.
  - Observação: no checklist está marcado como `[xx]`, mas o formato correto de Markdown seria `[x]`.

- Corrigir comparação de `String` usando `.equalsIgnoreCase`.
  - Estado: aplicado.
  - Evidência: a comparação com `"edinei"` usa `produto.getNome().equalsIgnoreCase("edinei")`.

- Validar entradas numéricas do menu.
  - Estado: aplicado.
  - Evidência: `option(...)` trata `NumberFormatException`.

- Revisar entradas com `nextDouble()` e `nextInt()`.
  - Estado: aplicado em `Main.java`.
  - Evidência: `Main.java` usa `option(...)` e `decimalOption(...)`.
  - Observação: ainda existem `nextInt(...)` em `jokes.java`, mas eles vêm de `Random`, não de entrada do usuário.

### Parcialmente aplicados

- Atualizar o status do pedido depois de finalizar.
  - Estado: parcialmente aplicado/adaptado.
  - O checklist sugeria `String` com `"Finalizado"`.
  - O código atual foi melhorado para usar `enum StatusPedido`.
  - Ao finalizar a compra, o pedido muda para `VALOR_RETIDO` via `Escrow.reterFundos(...)`.
  - Ainda falta um fluxo posterior para mudar para `CONCLUIDO`.

### Não aplicados

- Validar preço e estoque no construtor de `Produto`.
  - Estado: não aplicado.
  - Motivo: o construtor ainda usa atribuição direta:

```java
this.preco = preco;
this.estoque = estoque;
```

- Atualizar o extrato do vendedor ao finalizar uma venda.
  - Estado: não aplicado.
  - Motivo: não existe chamada que some o valor vendido no extrato do vendedor.
  - Observação: com escrow, essa atualização deve acontecer ao concluir o pedido, não necessariamente no momento de retenção dos fundos.

## Ordem sugerida para continuar

1. Ajustar construtor de `Produto` para usar `setPreco(...)` e `setEstoque(...)`.
2. Implementar `Escrow.cancelarPedido(...)`.
3. Implementar `Escrow.liberarFundos(...)`.
4. Atualizar extrato do vendedor quando o pedido virar `CONCLUIDO`.
5. Criar opção de menu para concluir/cancelar pedidos.
6. Renomear/ajustar relatório de pedidos para os novos status.
8. Limpar mensagens e comentários informais antes da entrega.
