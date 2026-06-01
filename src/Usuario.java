import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public static final List<Usuario> usuarios = new ArrayList<>();

    private static int NEXT_ID = 1;
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo;

    public Usuario(String nome, String email, String telefone, String tipo) {
        this.id = NEXT_ID++;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.tipo = tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) { this.tipo = tipo; }

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
        return this.tipo;
    }

    /* Static pq eu não quero instanciar um objeto usuario para cadastrar */
    public static void cadastrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public static Usuario getById(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }

        return null;
    }


    public static void listarUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println("Id: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Email: " + usuario.getEmail());
            System.out.println("Telefone: " + usuario.getTelefone());
            System.out.println("Tipo de usuário: " + usuario.getTipo());
            System.out.println("--------------------");
        }
    }

    public static void listarVendedores() {
        System.out.println("Listando vendedores: ");

        for (Usuario usuario : usuarios) {
            if(usuario instanceof Vendedor) {
                System.out.println("\n--------------------");
                System.out.println("Id: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("--------------------\n");
            }
        }
    }

    public static void listarCompradores() {
        System.out.println("Listando compradores: ");

        for (Usuario usuario : usuarios) {
            if(usuario instanceof Comprador) {
                System.out.println("\n--------------------");
                System.out.println("Id: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Email: " + usuario.getEmail());
                System.out.println("Telefone: " + usuario.getTelefone());
                System.out.println("Tipo de usuário: " + usuario.getTipo());
                System.out.println("--------------------\n");
            }
        }
    }
}
