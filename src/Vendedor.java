public class Vendedor extends Usuario {
    private double extrato;

    public Vendedor(String nome, String email, String telefone, double extrato) {
        super(nome, email, telefone);
        this.extrato = extrato;
    }

    public double getExtrato() {
        return extrato;
    }
    public void setExtrato(double extrato) {
        this.extrato = extrato;
    }

    @Override
    public String getTipo() {
        return "Vendedor";
    }
}