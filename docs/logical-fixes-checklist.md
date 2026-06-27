# Checklist de Correções de Lógica

Use este checklist para marcar as correções conforme forem implementadas.

## Pedido e Estoque

- [x] Atualizar o estoque ao finalizar um pedido.
  - Arquivo: `src/Main.java`
  - Linha de referência: `623`
  - Local atual:
    ```java
    comprador.setSaldo(comprador.getSaldo() - pedido.getPrecoTotal());
    ```
  - Sugestão:
    ```java
    for (ItemPedido item : pedido.getItens()) {
        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() - item.getQuantidade());
    }
    ```

- [x] Impedir que o comprador adicione ao mesmo pedido uma quantidade total maior que o estoque.
  - Arquivo: `src/Main.java`
  - Linha de referência: `593`
  - Local atual:
    ```java
    if(quantidade > produto.getEstoque()) {
    ```
  - Observação: a validação atual considera apenas a quantidade digitada naquele momento. Ela precisa considerar também quantas unidades do mesmo produto já foram adicionadas ao pedido.

## Produtos

- [xx] Remover produto também da lista do vendedor.
  - Arquivo: `src/Main.java`
  - Linha de referência: `380`
  - Local atual:
    ```java
    boolean removido = Produto.excluir(nomeProduto);
    ```
  - Sugestão:
    ```java
    vendedor.getListaDeProdutosVendedor().removeIf(
        produto -> produto.getNome().equalsIgnoreCase(nomeProduto)
    );
    ```

- [ ] Validar preço e estoque no construtor de `Produto`.
  - Arquivo: `src/Produto.java`
  - Linha de referência: `12`
  - Local atual:
    ```java
    public Produto(String nome, double preco, int estoque, int idVendedor){
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.idVendedor = idVendedor;
    }
    ```
  - Sugestão:
    ```java
    public Produto(String nome, double preco, int estoque, int idVendedor){
        this.nome = nome;
        setPreco(preco);
        setEstoque(estoque);
        this.idVendedor = idVendedor;
    }
    ```

## Pedido e Pagamento

- [ ] Atualizar o status do pedido depois de finalizar.
  - Arquivo: `src/Pedido.java`
  - Linha de referência: `64`
  - Adicionar perto de:
    ```java
    public String getStatus() {
        return status;
    }
    ```
  - Sugestão:
    ```java
    public void setStatus(String status) {
        this.status = status;
    }
    ```
  - Depois aplicar em `src/Main.java`, linha de referência `625`, antes de cadastrar o pedido:
    ```java
    pedido.setStatus("Finalizado");
    Pedido.cadastrar(pedido);
    ```

- [ ] Atualizar o extrato do vendedor ao finalizar uma venda.
  - Arquivo: `src/Main.java`
  - Linha de referência: `623`
  - Local recomendado: depois de descontar o saldo do comprador e no mesmo fluxo onde o estoque for atualizado.
  - Observação: use `produto.getIdVendedor()` para encontrar o vendedor com `Usuario.getById(...)` e atualizar o `extrato`.

## Comparações e Entradas

- [ ] Corrigir comparação de `String` usando `.equalsIgnoreCase`.
  - Arquivo: `src/Main.java`
  - Linha de referência: `582`
  - Trocar:
    ```java
    if(produto.getNome() == "edinei") {
    ```
  - Por:
    ```java
    if(produto.getNome().equalsIgnoreCase("edinei")) {
    ```

- [ ] Validar entradas numéricas do menu.
  - Arquivo: `src/Main.java`
  - Linha de referência: `116`
  - Local atual:
    ```java
    return Integer.parseInt(input.nextLine());
    ```
  - Observação: tratar `NumberFormatException` para evitar que o sistema quebre quando o usuário digitar texto.

- [ ] Revisar entradas com `nextDouble()` e `nextInt()`.
  - Arquivo: `src/Main.java`
  - Linhas de referência:
    - `280`: `input.nextDouble()`
    - `289`: `input.nextDouble()`
    - `397`: `input.nextDouble()`
    - `400`: `input.nextInt()`
  - Observação: essas leituras também podem quebrar se o usuário digitar texto ou usar formato inválido.
