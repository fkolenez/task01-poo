import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        jokes.edinei();

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

        input.close();
    }

    public static void menu() {
        printBox(
                "ctrl alt del+ito 🤫",
                "1 - Área do administrador",
                "2 - Área do vendedor",
                "3 - Área do cliente",
                "4 - Piadinhas",
                "0 - Sair"
        );
    }

    private static void header(String areaName) {
        printBox(areaName);
    }

    private static void printBox(String title, String... lines) {
        int width = visibleLength(title);

        for (String line : lines) {
            width = Math.max(width, visibleLength(line));
        }

        String border = "+" + repeat("-", width + 2) + "+";

        System.out.println();
        System.out.println(border);
        printBoxLine(title, width);
        System.out.println(border);

        for (String line : lines) {
            printBoxLine(line, width);
        }

        if (lines.length > 0) {
            System.out.println(border);
        }
    }

    private static void printBoxLine(String text, int width) {
        System.out.println("| " + text + repeat(" ", width - visibleLength(text)) + " |");
    }

    private static String repeat(String value, int times) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < times; i++) {
            result.append(value);
        }

        return result.toString();
    }

    private static int visibleLength(String text) {
        int length = 0;

        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            length += charWidth(codePoint);
            i += Character.charCount(codePoint);
        }

        return length;
    }

    private static int charWidth(int codePoint) {
        if (Character.getType(codePoint) == Character.OTHER_SYMBOL) {
            return 2;
        }

        return 1;
    }

    public static int option() {
        return option("Escolha uma opção: ");
    }

    public static int option(String mensagem) {
        System.out.print(mensagem);
        return Integer.parseInt(input.nextLine());
    }

    public static void piadinhasMenu() {
        boolean back = false;

        while (!back) {
            printBox(
                    "Piadinhas",
                    "1 - Roll a dice",
                    "2 - Flip a coin",
                    "3 - Edinei",
                    "0 - Voltar"
            );

            int option = option();

            switch (option) {
                case 1:
                    printBox("Roll a dice", "Resultado: " + jokes.rollDice());
                    break;
                case 2:
                    header("Flip a coin");
                    jokes.flipCoin();
                    break;
                case 3:
                    header("Edinei");
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
        return !Produto.getListaDeProdutos().isEmpty();
    }

    private static void listaVazia() {
        System.out.println("Lista vazia");
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
            printBox(
                    "Área do administrador 🎫",
                    "1 - Cadastrar novo usuário",
                    "2 - Lista todos os usuários",
                    "3 - Banir o betinha desfuncional",
                    "0 - Sair"
            );
            int option = option();

            switch (option) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
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
    private static void listarUsuarios() {
        if (!hasUsuarios()) {
            listaVazia();
            return;
        }

        Usuario.listarUsuarios();
    }

    private static void removerUsuario(){
        if (!hasUsuarios()) {
            listaVazia();
            return;
        }

        listarUsuarios();

        int id = option("Digite o id do usuário que vai ser removido: ");
        boolean removido = Usuario.removerUsuario(id);

        if(removido) {
            System.out.println("Usuário removido com sucesso! ");
        } else {
            System.out.println("Usuário não encontrado! ");
        }
    }

    private static void cadastrarUsuario() {
        printBox(
                "Cadastro de usuário",
                "1 - Comprador",
                "2 - Vendedor"
        );

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
            if (!hasVendedores()) {
                listaVazia();
                return;
            }

            header("Área do vendedor 💳");
            Usuario.listarVendedores();

            int idVendedor = option("Selecione o vendedor: ");

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

            printBox(
                    "Menu do vendedor: "+vendedor.getNome(),
                    "1 - Cadastrar produtos",
                    "2 - Listar produtos",
                    "3 - Relatório de pedidos em aberto",
                    "4 - Remover produto",
                    "0 - Sair"
            );

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
        if (vendedor.getListaDeProdutosVendedor().isEmpty()) {
            listaVazia();
            return;
        }

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
        List<Produto> produtos = vendedor.getListaDeProdutosVendedor();

        if (produtos.isEmpty()) {
            listaVazia();
            return;
        }

        header("Produtos do vendedor: " + vendedor.getNome());

        for (Produto produto : produtos) {
            printBox(
                    produto.getNome(),
                    "Preço do produto: R$ " + produto.getPreco(),
                    "Quantidade em estoque: " + produto.getEstoque()
            );
        }
    }

    public static void relatorioPedidosEmAberto(Vendedor vendedor) {
        if (Pedido.getListaDePedidos().isEmpty()) {
            listaVazia();
            return;
        }

        boolean encontrouPedido = false;
        header("Pedidos em aberto do vendedor: " + vendedor.getNome());

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
            if (!hasCompradores()) {
                listaVazia();
                return;
            }

            header("Área do comprador 🛒");
            Usuario.listarCompradores();

            int idComprador = option("Selecione o comprador: ");

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

            printBox(
                    "Menu do comprador: "+comprador.getNome(),
                    "1 - Visualizar produtos",
                    "2 - Realizar pedido",
                    "0 - Sair"
            );

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
        List<Produto> produtos = Produto.getListaDeProdutos();

        if (produtos.isEmpty()) {
            listaVazia();
            return;
        }

        header("Produtos disponíveis");

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);

            printBox(
                    (i + 1) + " - " + produto.getNome(),
                    "Preço: R$ " + produto.getPreco(),
                    "Estoque: " + produto.getEstoque()
            );
        }
    }

    public static void realizarPedido(Comprador comprador) {
        // id do capeta ai é dinamico e pega a data atual dinamicamente conforme o dia tb
        Pedido pedido = new Pedido(LocalDate.now().toString(), "Em aberto");

        boolean adicionandoItens = true;

        while (adicionandoItens) {
            if (!hasProdutosDisponiveis()) {
                listaVazia();
                return;
            }

            listarProdutosDisponiveis();

            int indiceProduto = option("Escolha o número do produto: ") - 1;

            List<Produto> produtos = Produto.getListaDeProdutos();

            if (indiceProduto < 0 || indiceProduto >= produtos.size()) {
                System.out.println("Produto inválido!");
                return;
            }

            Produto produto = produtos.get(indiceProduto);

            if(produto.getNome() == "edinei") {
                jokes.edinei();
            }

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

            printBox(
                    "Deseja adicionar outro produto?",
                    "1 - Sim",
                    "0 - Finalizar pedido"
            );

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
