public class Main {
    public static void main(String[] args) {

        Usuario u1 = new Usuario(1, "Flavio", "flavio@email.com", "9999");
        Usuario u2 = new Usuario(2, "Joao", "joao@email.com", "8888");

        Usuario.cadastrarUsuario(u1);
        Usuario.cadastrarUsuario(u2);

        Usuario.listarUsuarios();
    }
}