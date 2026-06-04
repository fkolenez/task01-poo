import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            menu();
            int option = option();

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
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida, selecione outra!");
            }
        }

        input.close();
    }

    public static void menu() {
        header("ctrl alt del+ito 🤫");

        System.out.println("1 - Área do administrador");
        System.out.println("2 - Área do vendedor");
        System.out.println("3 - Área do cliente");
        System.out.println("0 - Sair");
    }

    private static void header(String AREA_NAME) {
        System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println(AREA_NAME);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=");
    }

    public static int option() {
        return option("Escolha uma opção: ");
    }

    public static int option(String mensagem) {
        System.out.println(mensagem);
        return Integer.parseInt(input.nextLine());
    }
    /**
     * Metodos do menu admistrador
     * */
    public static void admMenu() {
        boolean back = false;

        System.out.println("Digite a senha de administrador: ");
        String senha = input.nextLine();

        if(!senha.equals("jota")) {
            System.out.println("Senha incorreta!");
            back = true;
        }

        while(!back){
            header("Área do administrador 🎫");

            System.out.println("1 - Cadastrar novo usuário");
            System.out.println("2 - Lista todos os usuários");
            System.out.println("3 - Banir o betinha desfuncional");
            System.out.println("0 - Sair");
            int option = option();

            switch (option) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    Usuario.listarUsuarios();
                    break;
                case 3:
                    removerUsuario();
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
    private static void removerUsuario(){
        Usuario.listarUsuarios();

        int id = option("Digite o id do usuário que vai ser removido: ");
        boolean removido = Usuario.removerUsuario(id);

        if(removido) {
            System.out.println("Usuário removido com sucesso! ");
        } else {
            System.out.println("Usuário não encontrado! ");
        }
    }

    private static void cadastrarUsuario() {
        System.out.println("Selecione o tipo de usuário a ser cadastrado: ");
        System.out.println("1 - Comprador");
        System.out.println("2 - Vendedor");

        int type = option();

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Telefone: ");
        String telefone = input.nextLine();

        switch (type) {
            case 1:
                System.out.print("Saldo do comprador: ");
                double saldo = input.nextDouble();
                input.nextLine();

                Usuario comprador = new Comprador(nome, email, telefone, saldo);
                Usuario.cadastrarUsuario(comprador);
                break;

            case 2:
                System.out.print("Extrato do vendedor: ");
                double extrato = input.nextDouble();
                input.nextLine();

                Usuario vendedor = new Vendedor(nome, email, telefone, extrato);
                Usuario.cadastrarUsuario(vendedor);
                break;

            default:
                System.out.println("Tipo inválido. Tente novamente.");
                break;
        }
    }

    /* *
     * Menu do vendedor
     * */
    public static void vendedorMenu() {
        boolean back = false;

        while(!back){
            header("Área do vendedor 💳");
            Usuario.listarVendedores();

            System.out.println("Selecione o vendedor: ");
            int idVendedor = Integer.parseInt(input.nextLine());

            Usuario usuario = Usuario.getById(idVendedor);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            if (!(usuario instanceof Vendedor)) {
                System.out.println("Usuário selecionado não pode executar ações nesse menu!");
                return;
            }

            Vendedor vendedor = (Vendedor) usuario;

            header("Menu do vendedor: "+vendedor.getNome());

            System.out.println("1 - Cadastrar produtos");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Relatório de pedidos em aberto");
            System.out.println("4 - Remover produto");
            System.out.println("0 - Sair");

            int option = option();

            switch (option) {
                case 1:
                    cadastrarProduto(vendedor);
                    break;
                case 2:
                    listarProdutosVendedor(vendedor);
                    break;
                case 3:
                    relatorioPedidosEmAberto(vendedor);
                    break;
                case 4:
                    removerProduto(vendedor);
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
        listarProdutosVendedor(vendedor);

        System.out.println("Digite o nome do produto a ser excluido: ");
        String nomeProduto = input.nextLine();

        boolean removido = Produto.excluir(nomeProduto);

        if(removido) {
            System.out.println("Produto removido com sucesso! ");
        } else {
            System.out.println("Produto não encontrado! ");
        }

    }

    public static void cadastrarProduto(Vendedor vendedor) {
        header("Cadastrando um novo produto! ");

        System.out.print("Digite o nome do produto: ");
        String nome = input.nextLine();

        System.out.print("Digite o preco do produto: ");
        double preco = input.nextDouble();

        System.out.print("Digite a quantidade em estoque: ");
        int estoque = input.nextInt();
        input.nextLine();

        Produto produto = new Produto(nome, preco, estoque, vendedor.getId());

        Produto.cadastrar(produto);
        vendedor.cadastrarNovoProduto(produto);

        System.out.println("Produto "+produto.getNome()+" cadastrado com sucessexo");
    }

    public static void listarProdutosVendedor(Vendedor vendedor) {
        header("Produtos do vendedor: " + vendedor.getNome());

        List<Produto> produtos = vendedor.getListaDeProdutosVendedor();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println("\n- - - "+produto.getNome()+" - - -");
            System.out.println("Preço do produto: R$ " + produto.getPreco());
            System.out.println("Quantidade em estoque: " + produto.getEstoque());
            System.out.println("----------------------");
        }
    }

    public static void relatorioPedidosEmAberto(Vendedor vendedor) {
        header("Pedidos em aberto do vendedor: " + vendedor.getNome());

        boolean encontrouPedido = false;

        for (Pedido pedido : Pedido.getListaDePedidos()) {
            if (!pedido.getStatus().equalsIgnoreCase("Em aberto")) {
                continue;
            }

            boolean mostrouCabecalhoPedido = false;
            double totalDoVendedorNoPedido = 0.0;

            for (ItemPedido item : pedido.getItens()) {
                Produto produto = item.getProduto();

                if (produto.getIdVendedor() != vendedor.getId()) {
                    continue;
                }

                if (!mostrouCabecalhoPedido) {
                    System.out.println("\nPedido #" + pedido.getId());
                    System.out.println("Data: " + pedido.getData());
                    System.out.println("Status: " + pedido.getStatus());
                    mostrouCabecalhoPedido = true;
                    encontrouPedido = true;
                }

                double totalItem = item.calcularTotal();
                totalDoVendedorNoPedido += totalItem;

                System.out.println("- Produto: " + produto.getNome());
                System.out.println("  Quantidade: " + item.getQuantidade());
                System.out.println("  Preço unitário: R$ " + item.getPrecoUnitario());
                System.out.println("  Total do item: R$ " + totalItem);
            }

            if (mostrouCabecalhoPedido) {
                System.out.println("Total do vendedor neste pedido: R$ " + totalDoVendedorNoPedido);
                System.out.println("----------------------");
            }
        }

        if (!encontrouPedido) {
            System.out.println("Nenhum pedido em aberto encontrado para este vendedor.");
        }
    }

    public static void compradorMenu() {
        boolean back = false;

        while(!back){
            header("Área do comprador 🛒");
            Usuario.listarCompradores();

            System.out.println("Selecione o comprador: ");
            int idComprador = Integer.parseInt(input.nextLine());

            Usuario usuario = Usuario.getById(idComprador);

            if (usuario == null) {
                System.out.println("Usuário não encontrado!");
                return;
            }

            if (!(usuario instanceof Comprador)) {
                System.out.println("Usuário selecionado não pode executar ações nesse menu!");
                return;
            }

            Comprador comprador = (Comprador) usuario;

            header("Menu do comprador: "+comprador.getNome());

            System.out.println("1 - Visualizar produtos");
            System.out.println("2 - Realizar pedido");
            System.out.println("0 - Sair");

            int option = option();

            switch (option) {
                case 1:
                    listarProdutosDisponiveis();
                    break;
                case 2:
                    realizarPedido(comprador);
                    break;
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
        header("Produtos disponíveis");

        List<Produto> produtos = Produto.getListaDeProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);

            System.out.println((i + 1) + " - " + produto.getNome());
            System.out.println("Preço: R$ " + produto.getPreco());
            System.out.println("Estoque: " + produto.getEstoque());
            System.out.println("----------------------");
        }
    }

    public static void realizarPedido(Comprador comprador) {
        // id do capeta ai é dinamico e pega a data atual dinamicamente conforme o dia tb
        Pedido pedido = new Pedido(LocalDate.now().toString(), "Em aberto");

        boolean adicionandoItens = true;

        while (adicionandoItens) {
            listarProdutosDisponiveis();

            System.out.println("Escolha o número do produto: ");
            int indiceProduto = option() - 1;

            List<Produto> produtos = Produto.getListaDeProdutos();

            if (indiceProduto < 0 || indiceProduto >= produtos.size()) {
                System.out.println("Produto inválido!");
                return;
            }

            Produto produto = produtos.get(indiceProduto);

            int quantidade = option("Digite a quantidade desejada: ");

            if(quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                return;
            }

            if(quantidade > produto.getEstoque()) {
                System.out.println("Estoque insuficiente!");
                return;
            }

            ItemPedido item = new ItemPedido(produto, quantidade);
            pedido.adicionarItem(item);

            System.out.println("Deseja adicionar outro produto?");
            System.out.println("1 - Sim");
            System.out.println("0 - Finalizar pedido");

            int opcao = option();

            // encerra o loop de adicionar novos produtos
            if (opcao == 0) {
                adicionandoItens = false;
            }
        }

        // valida o preço
        if(pedido.getPrecoTotal() > comprador.getSaldo()) {
            System.out.println("Saldo insuficiente para finalizar o pedido.");
            System.out.println("Total: R$ " + pedido.getPrecoTotal());
            System.out.println("Saldo: R$ " + comprador.getSaldo());
            return;
        }

        // atualiza o valor de saldo do comprador
        comprador.setSaldo(comprador.getSaldo() - pedido.getPrecoTotal());
        Pedido.cadastrar(pedido);

        System.out.println("Pedido realizado com sucesso!");
        System.out.println("Total do pedido: R$ " + pedido.getPrecoTotal());
        System.out.println("Saldo restante: R$ " + comprador.getSaldo());
    }
}
