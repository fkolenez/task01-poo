import java.util.Scanner;

public class ConsoleInput {
    private static final Scanner input = new Scanner(System.in);

    public static int option() {
        return option("Escolha uma opção: ");
    }

    public static int option(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Entrada inválida! Digite um número inteiro.");
            }
        }
    }

    public static double decimalOption(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(input.nextLine());
            } catch (NumberFormatException exception) {
                System.out.println("Entrada inválida! Digite um número válido.");
            }
        }
    }

    public static String textOption(String mensagem) {
        System.out.print(mensagem);
        return input.nextLine();
    }

    public static void close() {
        input.close();
    }
}
