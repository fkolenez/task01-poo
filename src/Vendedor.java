public class Vendedor extends Usuario{
    public double extrato;

    public Vendedor(int id, String nome, String email, String telefone) {
        super(id, nome, email, telefone);
        this.extrato = extrato;
    }

    public void setExtrato(double extrato) {
        this.extrato = extrato;
    }

    public double getExtrato() {
        return extrato;
    }
}
