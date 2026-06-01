import java.util.ArrayList;
import java.util.List;

public class Comprador extends Usuario {
    private double saldo;
    public Comprador(String nome, String email, String telefone, double saldo) {
        super(nome, email, telefone, "Comprador");
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String getTipo() {
        return "Comprador";
    }
}