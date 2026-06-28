import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        carregarDadosTemporarios();
        jokes.edinei();

        boolean running = true;
        while (running) {
            menu();
            int option = ConsoleInput.option();

            switch (option) {
                case 1:
                    admMenu();
                    break;
                case 2:
                    vendedorMenu();
                    break;
                case 3:
                    compradorMenu();
                    break;
                case 4:
                    piadinhasMenu();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida, selecione outra!");
            }
        }

        ConsoleInput.close();
    }

    private static void carregarDadosTemporarios() {
        Vendedor vendedor1 = new Vendedor("Ana Vendas", "ana@market.com", "11999990001", 0.0);
        Vendedor vendedor2 = new Vendedor("Bruno Store", "bruno@market.com", "11999990002", 0.0);
        Comprador comprador = new Comprador("Cliente Teste", "cliente@market.com", "11999990003", 5000.0);

        Produto produto1 = new Produto("Notebook", 2500.0, 5, vendedor1.getId());
        Produto produto2 = new Produto("Mouse gamer", 150.0, 20, vendedor2.getId());

        Usuario.cadastrarUsuario(vendedor1);
        Usuario.cadastrarUsuario(vendedor2);
        Usuario.cadastrarUsuario(comprador);

        Produto.cadastrar(produto1);
        Produto.cadastrar(produto2);

        vendedor1.cadastrarNovoProduto(produto1);
        vendedor2.cadastrarNovoProduto(produto2);
    }

    public static void menu() {
        BoxPrinter.printBox(
                "ctrl alt del+ito 🤫",
                "1 - Área do administrador",
                "2 - Área do vendedor",
                "3 - Área do cliente",
                "4 - Piadinhas",
                "0 - Sair"
        );
    }

    public static void piadinhasMenu() {
        boolean back = false;

        while (!back) {
            BoxPrinter.printBox(
                    "Piadinhas",
                    "1 - Roll a dice",
                    "2 - Flip a coin",
                    "3 - Edinei",
                    "0 - Voltar"
            );

            int option = ConsoleInput.option();

            switch (option) {
                case 1:
                    BoxPrinter.printBox("Roll a dice", "Resultado: " + jokes.rollDice());
                    break;
                case 2:
                    BoxPrinter.header("Flip a coin");
                    jokes.flipCoin();
                    break;
                case 3:
                    BoxPrinter.header("Edinei");
                    jokes.edinei();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static boolean hasUsuarios() {
        return !Usuario.usuarios.isEmpty();
    }

    private static boolean hasVendedores() {
        for (Usuario usuario : Usuario.usuarios) {
            if (usuario instanceof Vendedor) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasCompradores() {
        for (Usuario usuario : Usuario.usuarios) {
            if (usuario instanceof Comprador) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasProdutosDisponiveis() {
        for (Produto produto : Produto.getListaDeProdutos()) {
            if (produto.getEstoque() > 0) {
                return true;
            }
        }

        return false;
    }

    private static void listaVazia() {
        System.out.println("Lista vazia");
    }

    private static void listarVendedores() {
        BoxPrinter.header("Vendedores cadastrados");

        for (Usuario usuario : Usuario.usuarios) {
            if (usuario instanceof Vendedor) {
                BoxPrinter.printBox(
                        "Vendedor #" + usuario.getId(),
                        "Nome: " + usuario.getNome()
                );
            }
        }
    }

    private static void listarCompradores() {
        BoxPrinter.header("Compradores cadastrados");

        for (Usuario usuario : Usuario.usuarios) {
            if (usuario instanceof Comprador) {
                BoxPrinter.printBox(
                        "Comprador #" + usuario.getId(),
                        "Nome: " + usuario.getNome(),
                        "Email: " + usuario.getEmail(),
                        "Telefone: " + usuario.getTelefone(),
                        "Tipo de usuário: " + usuario.getTipo()
                );
            }
        }
    }
    /**
     * Metodos do menu admistrador
     * */
    public static void admMenu() {
        boolean back = false;

        String senha = ConsoleInput.textOption("Digite a senha de administrador: ");

        if(!senha.equals("jota")) {
            System.out.println("Senha incorreta!");
            back = true;
        }

        while(!back){
            BoxPrinter.printBox(
                    "Área do administrador 🎫",
                    "1 - Cadastrar novo usuário",
                    "2 - Lista todos os usuários",
                    "3 - Banir o betinha desfuncional",
                    "0 - Sair"
            );
            int option = ConsoleInput.option();

            switch (option) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    removerUsuario();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    /**
     * Metodos do menu admistrador
     * */
    private static void listarUsuarios() {
        if (!hasUsuarios()) {
            listaVazia();
            return;
        }

        BoxPrinter.header("Usuários cadastrados");

        for (Usuario usuario : Usuario.usuarios) {
            BoxPrinter.printBox(
                    "Usuário #" + usuario.getId(),
                    "Nome: " + usuario.getNome(),
                    "Email: " + usuario.getEmail(),
                    "Telefone: " + usuario.getTelefone(),
                    "Tipo de usuário: " + usuario.getTipo()
            );
        }
    }

    private static void removerUsuario(){
        if (!hasUsuarios()) {
            listaVazia();
            return;
        }

        listarUsuarios();

        int id = ConsoleInput.option("Digite o id do usuário que vai ser removido: ");
        Usuario usuario = Usuario.getById(id);

        if (usuario == null) {
            System.out.println("Usuário não encontrado! ");
            return;
        }

        if (usuarioPossuiPedidoAtivo(usuario)) {
            System.out.println("Usuário possui pedido ativo e não pode ser removido agora.");
            return;
        }

        if (usuario instanceof Vendedor && !((Vendedor) usuario).getListaDeProdutosVendedor().isEmpty()) {
            System.out.println("Vendedor possui produtos cadastrados. Remova os produtos antes de remover o vendedor.");
            return;
        }

        boolean removido = Usuario.removerUsuario(id);

        if(removido) {
            System.out.println("Usuário removido com sucesso! ");
        } else {
            System.out.println("Usuário não encontrado! ");
        }
    }

    private static void cadastrarUsuario() {
        BoxPrinter.printBox(
                "Cadastro de usuário",
                "1 - Comprador",
                "2 - Vendedor"
        );

        int type = ConsoleInput.option();

        if (type != 1 && type != 2) {
            System.out.println("Tipo inválido. Tente novamente.");
            return;
        }

        String nome = ConsoleInput.textOption("Nome: ");
        String email = ConsoleInput.textOption("Email: ");
        String telefone = ConsoleInput.textOption("Telefone: ");

        switch (type) {
            case 1:
                double saldo = ConsoleInput.decimalOption("Saldo do comprador: ");

                if (saldo < 0) {
                    System.out.println("Saldo não pode ser negativo.");
                    return;
                }

                Usuario comprador = new Comprador(nome, email, telefone, saldo);
                Usuario.cadastrarUsuario(comprador);
                break;

            case 2:
                double extrato = ConsoleInput.decimalOption("Extrato do vendedor: ");

                if (extrato < 0) {
                    System.out.println("Extrato não pode ser negativo.");
                    return;
                }

                Usuario vendedor = new Vendedor(nome, email, telefone, extrato);
                Usuario.cadastrarUsuario(vendedor);
                break;
        }
    }

    /* *
     * Menu do vendedor
     * */
    public static void vendedorMenu() {
        boolean back = false;

        while(!back){
            if (!hasVendedores()) {
                listaVazia();
                return;
            }

            BoxPrinter.header("Área do vendedor 💳");
            listarVendedores();

            int idVendedor = ConsoleInput.option("Selecione o vendedor ou 0 para voltar: ");

            if (idVendedor == 0) {
                back = true;
                continue;
            }

            Usuario usuario = Usuario.getById(idVendedor);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                continue;
            }

            if (!(usuario instanceof Vendedor)) {
                System.out.println("Usuário selecionado não pode executar ações nesse menu!");
                continue;
            }

            Vendedor vendedor = (Vendedor) usuario;

            BoxPrinter.printBox(
                    "Menu do vendedor: "+vendedor.getNome(),
                    "1 - Cadastrar produtos",
                    "2 - Listar produtos",
                    "3 - Relatório de pedidos pagos",
                    "4 - Remover produto",
                    "5 - Visualizar extrato",
                    "6 - Concluir pedido pago",
                    "0 - Sair"
            );

            int option = ConsoleInput.option();

            switch (option) {
                case 1:
                    cadastrarProduto(vendedor);
                    break;
                case 2:
                    listarProdutosVendedor(vendedor);
                    break;
                case 3:
                    relatorioPedidosPagos(vendedor);
                    break;
                case 4:
                    removerProduto(vendedor);
                    break;
                case 5:
                    visualizarExtrato(vendedor);
                    break;
                case 6:
                    concluirPedido(vendedor);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    /**
    * Metodos do menu vendedor
    * */
    public static void removerProduto(Vendedor vendedor) {
        if (vendedor.getListaDeProdutosVendedor().isEmpty()) {
            listaVazia();
            return;
        }

        listarProdutosVendedor(vendedor);

        String nomeProduto = ConsoleInput.textOption("Digite o nome do produto a ser excluido: ");
        Produto produto = buscarProdutoDoVendedorPorNome(vendedor, nomeProduto);

        if (produto == null) {
            System.out.println("Produto não encontrado! ");
            return;
        }

        if (produtoPossuiPedidoAtivo(produto)) {
            System.out.println("Este produto possui pedido ativo e não pode ser removido agora.");
            return;
        }

        boolean removidoDoCatalogo = Produto.getListaDeProdutos().remove(produto);
        boolean removidoDoVendedor = vendedor.getListaDeProdutosVendedor().remove(produto);

        if(removidoDoCatalogo && removidoDoVendedor) {
            System.out.println("Produto removido com sucesso! ");
        } else {
            System.out.println("Produto não encontrado! ");
        }

    }

    public static void visualizarExtrato(Vendedor vendedor) {
        BoxPrinter.printBox(
                "Extrato do vendedor",
                "Nome: " + vendedor.getNome(),
                "Extrato: R$ " + vendedor.getExtrato()
        );
    }

    public static void cadastrarProduto(Vendedor vendedor) {
        BoxPrinter.header("Cadastrando um novo produto! ");

        String nome = ConsoleInput.textOption("Digite o nome do produto: ");

        double preco = ConsoleInput.decimalOption("Digite o preco do produto: ");

        if (preco <= 0) {
            System.out.println("Preço deve ser maior que zero.");
            return;
        }

        int estoque = ConsoleInput.option("Digite a quantidade em estoque: ");

        if (estoque <= 0) {
            System.out.println("Estoque deve ser maior que zero.");
            return;
        }

        Produto produto = new Produto(nome, preco, estoque, vendedor.getId());

        Produto.cadastrar(produto);
        vendedor.cadastrarNovoProduto(produto);

        System.out.println("Produto "+produto.getNome()+" cadastrado com sucessexo");
    }

    public static void listarProdutosVendedor(Vendedor vendedor) {
        List<Produto> produtos = vendedor.getListaDeProdutosVendedor();

        if (produtos.isEmpty()) {
            listaVazia();
            return;
        }

        BoxPrinter.header("Produtos do vendedor: " + vendedor.getNome());

        for (Produto produto : produtos) {
            BoxPrinter.printBox(
                    produto.getNome(),
                    "Preço do produto: R$ " + produto.getPreco(),
                    "Quantidade em estoque: " + produto.getEstoque()
            );
        }
    }

    public static void relatorioPedidosPagos(Vendedor vendedor) {
        if (Pedido.getListaDePedidos().isEmpty()) {
            listaVazia();
            return;
        }

        boolean encontrouPedido = false;
        BoxPrinter.header("Pedidos pagos do vendedor: " + vendedor.getNome());

        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (pedido.getStatus() != Pedido.StatusPedido.VALOR_RETIDO) {
                continue;
            }

            List<String> linhasPedido = new ArrayList<>();
            linhasPedido.add("Data: " + pedido.getData());
            linhasPedido.add("Status: " + pedido.getStatus());

            double totalDoVendedorNoPedido = 0.0;

            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();

                if (produto.getIdVendedor() != vendedor.getId()
                        || item.getStatus() != ItemPedido.StatusItemPedido.VALOR_RETIDO) {
                    continue;
                }

                double totalItem = item.calcularTotal();
                totalDoVendedorNoPedido += totalItem;

                linhasPedido.add("Produto: " + produto.getNome());
                linhasPedido.add("Quantidade: " + item.getQuantidade());
                linhasPedido.add("Preço unitário: R$ " + item.getPrecoUnitario());
                linhasPedido.add("Total do item: R$ " + totalItem);
                linhasPedido.add("Status do item: " + item.getStatus());
            }

            if (totalDoVendedorNoPedido > 0) {
                linhasPedido.add("Total do vendedor neste pedido: R$ " + totalDoVendedorNoPedido);
                BoxPrinter.printBox("Pedido #" + pedido.getId(), linhasPedido.toArray(new String[0]));
                encontrouPedido = true;
            }
        }

        if (!encontrouPedido) {
            System.out.println("Nenhum pedido pago encontrado para este vendedor.");
        }
    }

    public static void concluirPedido(Vendedor vendedor) {
        List<Pedido> pedidosPagos = pedidosPendentesDoVendedor(vendedor);

        if (pedidosPagos.isEmpty()) {
            System.out.println("Nenhum pedido pago para concluir.");
            return;
        }

        BoxPrinter.header("Pedidos pagos para concluir");

        for (Pedido pedido : pedidosPagos) {
            double totalPendenteDoVendedor = totalPendenteDoVendedorNoPedido(pedido, vendedor);

            BoxPrinter.printBox(
                    "Pedido #" + pedido.getId(),
                    "Comprador: " + pedido.getComprador().getNome(),
                    "Data: " + pedido.getData(),
                    "Total pendente deste vendedor: R$ " + totalPendenteDoVendedor,
                    "Status: " + pedido.getStatus()
            );
        }

        int idPedido = ConsoleInput.option("Digite o id do pedido que deseja concluir: ");
        Pedido pedido = buscarPedidoPorId(idPedido);

        if (pedido == null || !pedidoPossuiItemPendenteDoVendedor(pedido, vendedor)) {
            System.out.println("Pedido não encontrado para este vendedor.");
            return;
        }

        double valorLiberado = totalPendenteDoVendedorNoPedido(pedido, vendedor);

        Escrow escrow = new Escrow();
        if (!escrow.liberarFundos(pedido, vendedor)) {
            System.out.println("Este pedido não pode ser concluído.");
            return;
        }

        vendedor.setExtrato(vendedor.getExtrato() + valorLiberado);

        BoxPrinter.printBox(
                "Pedido concluído",
                "Pedido #" + pedido.getId(),
                "Valor liberado: R$ " + valorLiberado,
                "Status: " + pedido.getStatus(),
                "Extrato atual: R$ " + vendedor.getExtrato()
        );
    }

    public static void compradorMenu() {
        boolean back = false;

        while(!back){
            if (!hasCompradores()) {
                listaVazia();
                return;
            }

            BoxPrinter.header("Área do comprador 🛒");
            listarCompradores();

            int idComprador = ConsoleInput.option("Selecione o comprador ou 0 para voltar: ");

            if (idComprador == 0) {
                back = true;
                continue;
            }

            Usuario usuario = Usuario.getById(idComprador);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                continue;
            }

            if (!(usuario instanceof Comprador)) {
                System.out.println("Usuário selecionado não pode executar ações nesse menu!");
                continue;
            }

            Comprador comprador = (Comprador) usuario;

            BoxPrinter.printBox(
                    "Menu do comprador: "+comprador.getNome(),
                    "1 - Visualizar produtos",
                    "2 - Realizar pedido",
                    "3 - Pagar pedido",
                    "4 - Visualizar saldo",
                    "5 - Cancelar pedido",
                    "6 - Adicionar saldo",
                    "0 - Sair"
            );

            int option = ConsoleInput.option();

            switch (option) {
                case 1:
                    listarProdutosDisponiveis();
                    break;
                case 2:
                    realizarPedido(comprador);
                    break;
                case 3:
                    pagarPedido(comprador);
                    break;
                case 4:
                    visualizarSaldo(comprador);
                    break;
                case 5:
                    cancelarPedido(comprador);
                    break;
                case 6:
                    adicionarSaldo(comprador);
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    // Metodos menu comprador
    public static void listarProdutosDisponiveis() {
        List<Produto> produtos = produtosDisponiveis();

        if (produtos.isEmpty()) {
            listaVazia();
            return;
        }

        BoxPrinter.header("Produtos disponíveis");

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);

            BoxPrinter.printBox(
                    (i + 1) + " - " + produto.getNome(),
                    "Preço: R$ " + produto.getPreco(),
                    "Estoque: " + produto.getEstoque()
            );
        }
    }

    public static void realizarPedido(Comprador comprador) {
        Pedido pedido = new Pedido(LocalDate.now().toString(), comprador);

        boolean adicionandoItens = true;

        while (adicionandoItens) {
            if (!hasProdutosDisponiveis()) {
                listaVazia();
                return;
            }

            List<Produto> produtos = produtosDisponiveis();
            listarProdutosDisponiveis();

            int indiceProduto = ConsoleInput.option("Escolha o número do produto: ") - 1;

            if (indiceProduto < 0 || indiceProduto >= produtos.size()) {
                System.out.println("Produto inválido!");
                return;
            }

            Produto produto = produtos.get(indiceProduto);

            if (produto.getNome().equalsIgnoreCase("edinei")){
                jokes.edinei();
            }

            int quantidade = ConsoleInput.option("Digite a quantidade desejada: ");

            if(quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                return;
            }

            int quantidadeNoPedido = quantidadeDoProdutoNoPedido(pedido, produto);

            if(quantidade + quantidadeNoPedido > produto.getEstoque()) {
                System.out.println("Estoque insuficiente!");
                return;
            }

            ItemPedido item = new ItemPedido(produto, quantidade);
            pedido.adicionarItem(item);

            BoxPrinter.printBox(
                    "Deseja adicionar outro produto?",
                    "1 - Sim",
                    "0 - Finalizar pedido"
            );

            int opcao = ConsoleInput.option();

            // encerra o loop de adicionar novos produtos
            if (opcao == 0) {
                adicionandoItens = false;
            }
        }

        Pedido.cadastrar(pedido);

        List<String> resumoPedido = new ArrayList<>();
        resumoPedido.add("Pedido #" + pedido.getId());
        resumoPedido.add("Data: " + pedido.getData());
        resumoPedido.add("Status: " + pedido.getStatus());
        resumoPedido.add("Comprador: " + comprador.getNome());
        resumoPedido.add("Itens:");

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            resumoPedido.add("- Produto: " + produto.getNome());
            resumoPedido.add("- Quantidade: " + item.getQuantidade());
            resumoPedido.add("- Preço unitário: R$ " + item.getPrecoUnitario());
            resumoPedido.add("- Total do item: R$ " + item.calcularTotal());
        }

        resumoPedido.add("Total do pedido: R$ " + pedido.getPrecoTotal());
        resumoPedido.add("Saldo atual: R$ " + comprador.getSaldo());

        BoxPrinter.printBox("Pedido criado! Pague para confirmar a compra.", resumoPedido.toArray(new String[0]));
    }

    public static void pagarPedido(Comprador comprador) {
        List<Pedido> pedidosPendentes = pedidosDoCompradorPorStatus(
                comprador,
                Pedido.StatusPedido.AGUARDANDO_PAGAMENTO
        );

        if (pedidosPendentes.isEmpty()) {
            System.out.println("Nenhum pedido aguardando pagamento.");
            return;
        }

        BoxPrinter.header("Pedidos aguardando pagamento");

        for (Pedido pedido : pedidosPendentes) {
            BoxPrinter.printBox(
                    "Pedido #" + pedido.getId(),
                    "Data: " + pedido.getData(),
                    "Total: R$ " + pedido.getPrecoTotal(),
                    "Status: " + pedido.getStatus()
            );
        }

        int idPedido = ConsoleInput.option("Digite o id do pedido que deseja pagar: ");
        Pedido pedido = buscarPedidoDoComprador(idPedido, comprador);

        if (pedido == null) {
            System.out.println("Pedido não encontrado para este comprador.");
            return;
        }

        if (pedido.getStatus() != Pedido.StatusPedido.AGUARDANDO_PAGAMENTO) {
            System.out.println("Este pedido não está aguardando pagamento.");
            return;
        }

        if(pedido.getPrecoTotal() > comprador.getSaldo()) {
            BoxPrinter.printBox(
                    "Saldo insuficiente",
                    "Total: R$ " + pedido.getPrecoTotal(),
                    "Saldo: R$ " + comprador.getSaldo()
            );
            return;
        }

        if (!pedidoPossuiEstoqueDisponivel(pedido)) {
            System.out.println("Estoque insuficiente para pagar este pedido.");
            return;
        }

        Escrow escrow = new Escrow();
        if (!escrow.reterFundos(pedido)) {
            System.out.println("Não foi possível reter os fundos do pedido.");
            return;
        }

        comprador.setSaldo(comprador.getSaldo() - pedido.getPrecoTotal());

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() - item.getQuantidade());
        }

        List<String> resumoPedido = new ArrayList<>();
        resumoPedido.add("Pedido #" + pedido.getId());
        resumoPedido.add("Data: " + pedido.getData());
        resumoPedido.add("Status: " + pedido.getStatus());
        resumoPedido.add("Comprador: " + comprador.getNome());
        resumoPedido.add("Itens:");

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            resumoPedido.add("- Produto: " + produto.getNome());
            resumoPedido.add("- Quantidade: " + item.getQuantidade());
            resumoPedido.add("- Preço unitário: R$ " + item.getPrecoUnitario());
            resumoPedido.add("- Total do item: R$ " + item.calcularTotal());
            resumoPedido.add("- Estoque restante: " + produto.getEstoque());
        }

        resumoPedido.add("Total do pedido: R$ " + pedido.getPrecoTotal());
        resumoPedido.add("Saldo restante: R$ " + comprador.getSaldo());

        BoxPrinter.printBox("Pedido pago com sucesso!", resumoPedido.toArray(new String[0]));
    }

    public static void visualizarSaldo(Comprador comprador) {
        BoxPrinter.printBox(
                "Saldo do comprador",
                "Nome: " + comprador.getNome(),
                "Saldo: R$ " + comprador.getSaldo()
        );
    }

    public static void adicionarSaldo(Comprador comprador) {
        double saldoAdicionado = ConsoleInput.decimalOption("Quanto de saldo deseja adicionar?");
        comprador.setSaldo(saldoAdicionado+comprador.getSaldo());

        BoxPrinter.printBox(
                "Saldo adicionado com sucesso!",
                "Valor adicionado: R$ " + saldoAdicionado,
                "Saldo atual: R$ " + comprador.getSaldo()
        );
    }

    public static void cancelarPedido(Comprador comprador) {
        List<Pedido> pedidosCancelaveis = pedidosCancelaveisDoComprador(comprador);

        if (pedidosCancelaveis.isEmpty()) {
            System.out.println("Nenhum pedido disponível para cancelamento.");
            return;
        }

        BoxPrinter.header("Pedidos disponíveis para cancelamento");

        for (Pedido pedido : pedidosCancelaveis) {
            BoxPrinter.printBox(
                    "Pedido #" + pedido.getId(),
                    "Data: " + pedido.getData(),
                    "Total: R$ " + pedido.getPrecoTotal(),
                    "Status: " + pedido.getStatus()
            );
        }

        int idPedido = ConsoleInput.option("Digite o id do pedido que deseja cancelar: ");
        Pedido pedido = buscarPedidoDoComprador(idPedido, comprador);

        if (pedido == null) {
            System.out.println("Pedido não encontrado para este comprador.");
            return;
        }

        Pedido.StatusPedido statusAntesCancelamento = pedido.getStatus();
        Escrow escrow = new Escrow();

        if (!escrow.cancelarPedido(pedido)) {
            System.out.println("Este pedido não pode ser cancelado.");
            return;
        }

        if (statusAntesCancelamento == Pedido.StatusPedido.VALOR_RETIDO) {
            comprador.setSaldo(comprador.getSaldo() + pedido.getPrecoTotal());
            devolverEstoque(pedido);
        }

        BoxPrinter.printBox(
                "Pedido cancelado",
                "Pedido #" + pedido.getId(),
                "Status: " + pedido.getStatus(),
                "Saldo atual: R$ " + comprador.getSaldo()
        );
    }

    private static int quantidadeDoProdutoNoPedido(Pedido pedido, Produto produtoBusca) {
        int quantidadeTotal = 0;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();

            if (produto == produtoBusca) {
                quantidadeTotal += item.getQuantidade();
            }
        }

        return quantidadeTotal;
    }

    private static boolean pedidoPossuiEstoqueDisponivel(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            int quantidadeTotal = quantidadeDoProdutoNoPedido(pedido, produto);

            if (quantidadeTotal > produto.getEstoque()) {
                return false;
            }
        }

        return true;
    }

    private static List<Produto> produtosDisponiveis() {
        List<Produto> produtosDisponiveis = new ArrayList<>();

        for (Produto produto : Produto.getListaDeProdutos()) {
            if (produto.getEstoque() > 0) {
                produtosDisponiveis.add(produto);
            }
        }

        return produtosDisponiveis;
    }

    private static Produto buscarProdutoDoVendedorPorNome(Vendedor vendedor, String nomeProduto) {
        for (Produto produto : vendedor.getListaDeProdutosVendedor()) {
            if (produto.getNome().equalsIgnoreCase(nomeProduto)) {
                return produto;
            }
        }

        return null;
    }

    private static boolean produtoPossuiPedidoAtivo(Produto produtoBusca) {
        for (Pedido pedido : Pedido.getListaDePedidos()) {
            boolean pedidoAtivo = pedido.getStatus() == Pedido.StatusPedido.AGUARDANDO_PAGAMENTO
                    || pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO;

            if (!pedidoAtivo) {
                continue;
            }

            for (ItemPedido item : pedido.getItens()) {
                if (item.getProduto() == produtoBusca) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean usuarioPossuiPedidoAtivo(Usuario usuarioBusca) {
        for (Pedido pedido : Pedido.getListaDePedidos()) {
            boolean pedidoAtivo = pedido.getStatus() == Pedido.StatusPedido.AGUARDANDO_PAGAMENTO
                    || pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO;

            if (!pedidoAtivo) {
                continue;
            }

            if (pedido.getComprador().getId() == usuarioBusca.getId()) {
                return true;
            }

            if (usuarioBusca instanceof Vendedor) {
                for (ItemPedido item : pedido.getItens()) {
                    if (item.getProduto().getIdVendedor() == usuarioBusca.getId()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static List<Pedido> pedidosDoCompradorPorStatus(Comprador comprador, Pedido.StatusPedido status) {
        List<Pedido> pedidos = new ArrayList<>();

        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (pedido.getComprador().getId() == comprador.getId() && pedido.getStatus() == status) {
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    private static List<Pedido> pedidosCancelaveisDoComprador(Comprador comprador) {
        List<Pedido> pedidos = new ArrayList<>();

        for (Pedido pedido : Pedido.getListaDePedidos()) {
            boolean pertenceAoComprador = pedido.getComprador().getId() == comprador.getId();
            boolean podeCancelar = pedido.getStatus() == Pedido.StatusPedido.AGUARDANDO_PAGAMENTO
                    || (pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO && !pedidoPossuiItemConcluido(pedido));

            if (pertenceAoComprador && podeCancelar) {
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    private static boolean pedidoPossuiItemConcluido(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            if (item.getStatus() == ItemPedido.StatusItemPedido.CONCLUIDO) {
                return true;
            }
        }

        return false;
    }

    private static List<Pedido> pedidosPendentesDoVendedor(Vendedor vendedor) {
        List<Pedido> pedidos = new ArrayList<>();

        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO
                    && pedidoPossuiItemPendenteDoVendedor(pedido, vendedor)) {
                pedidos.add(pedido);
            }
        }

        return pedidos;
    }

    private static boolean pedidoPossuiItemPendenteDoVendedor(Pedido pedido, Vendedor vendedor) {
        for (ItemPedido item : pedido.getItens()) {
            if (item.getProduto().getIdVendedor() == vendedor.getId()
                    && item.getStatus() == ItemPedido.StatusItemPedido.VALOR_RETIDO) {
                return true;
            }
        }

        return false;
    }

    private static double totalPendenteDoVendedorNoPedido(Pedido pedido, Vendedor vendedor) {
        double total = 0.0;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();

            if (produto.getIdVendedor() == vendedor.getId()
                    && item.getStatus() == ItemPedido.StatusItemPedido.VALOR_RETIDO) {
                total += item.calcularTotal();
            }
        }

        return total;
    }

    private static void devolverEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setEstoque(produto.getEstoque() + item.getQuantidade());
        }
    }

    private static Pedido buscarPedidoDoComprador(int idPedido, Comprador comprador) {
        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (pedido.getId() == idPedido && pedido.getComprador().getId() == comprador.getId()) {
                return pedido;
            }
        }

        return null;
    }

    private static Pedido buscarPedidoPorId(int idPedido) {
        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (pedido.getId() == idPedido) {
                return pedido;
            }
        }

        return null;
    }
}
