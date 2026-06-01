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
        System.out.println("\nEscolha uma opção: ");
        return Integer.parseInt(input.nextLine());
    }

    /**
     * Metodos do menu admistrador
     * */
    public static void admMenu() {
        boolean back = false;

        while(!back){
            header("Área do administrador 🎫");

            System.out.println("1 - Cadastrar novo usuário");
            System.out.println("2 - Lista todos os usuários");
            System.out.println("0 - Sair");
            int option = option();

            switch (option) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    Usuario.listarUsuarios();
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
            System.out.println("0 - Sair");

            int option = option();

            switch (option) {
                case 1:
                    cadastrarProduto(vendedor);
                    break;
                case 2:
                    listarProdutosVendedor(vendedor);
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
//                    realizarPedido(comprador);
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
}
