public class Comprador extends Usuario{
    public double saldo;

    public Comprador(int id, String nome, String email, String telefone) {
        super(id, nome, email, telefone);
        this.saldo = saldo;
    }
}
