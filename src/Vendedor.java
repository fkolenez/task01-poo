import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private double extrato;
    private List<Produto> listaDeProdutosVendedor = new ArrayList<>();

    public Vendedor(String nome, String email, String telefone, double extrato) {
        super(nome, email, telefone, "Vendedor");
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

    public void cadastrarNovoProduto(Produto produto) {
        listaDeProdutosVendedor.add(produto);
    }

    public List<Produto> getListaDeProdutosVendedor() {
        return listaDeProdutosVendedor;
    }
}