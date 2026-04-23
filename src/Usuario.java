import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private static int NEXT_ID = 1;

    private int id;
    private String nome;
    private String email;
    private String telefone;

    private static List<Usuario> usuarios = new ArrayList<>();

    public Usuario(String nome, String email, String telefone) {
        this.id = NEXT_ID++;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getTipo() {
        return "Usuário";
    }

    /* Static pq eu não quer instanciar um objeto usuario para cadastrar */
    public static void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static void listarUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("Usuário: " + usuario.getNome());
            System.out.println("Id: " + usuario.getId());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println(
                    "Tipo de usuário: " + (usuario instanceof Vendedor ? "Vendedor" : "Comprador")
            );
            System.out.println("--------------------");
        }
    }
}
