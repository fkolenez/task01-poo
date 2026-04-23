import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int COUNT = 0;

        while (COUNT < 2) {
            System.out.println("\nCadastro do usuário " + (COUNT + 1));
            System.out.println("1 - Comprador");
            System.out.println("2 - Vendedor");

            int type = input.nextInt();
            input.nextLine();

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
                    COUNT++;
                    break;

                case 2:
                    System.out.print("Extrato do vendedor: ");
                    double extrato = input.nextDouble();
                    input.nextLine();

                    Usuario vendedor = new Vendedor(nome, email, telefone, extrato);
                    Usuario.cadastrarUsuario(vendedor);
                    COUNT++;
                    break;

                default:
                    System.out.println("Tipo inválido. Tente novamente.");
                    break;
            }
        }

        System.out.println("\n=== Lista de usuários ===");

        Usuario.listarUsuarios();

    }
}