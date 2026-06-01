import java.util.ArrayList;
import java.util.List;

public class Produto {
    private String nome;
    private double preco;
    private int estoque;
    private int idVendedor;

    private static List<Produto> listaDeProdutos = new ArrayList<>();

    public Produto(String nome, double preco, int estoque, int idVendedor){
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.idVendedor = idVendedor;
    }

    public static void cadastrar(Produto produto){
        listaDeProdutos.add(produto);
    }

    public static boolean excluir(String nomeBusca){
        for (Produto produto : listaDeProdutos){
            if(produto.getNome().equalsIgnoreCase(nomeBusca)){
                listaDeProdutos.remove(produto);
                return true;
            }
        }
        return false;
    }


    public static boolean editar(String nomeBusca, String novoNome, double novoPreco, int novoEstoque){
        for (Produto produto : listaDeProdutos){
            if(produto.getNome().equalsIgnoreCase(nomeBusca)){
                produto.setNome(novoNome);
                produto.setPreco(novoPreco);
                produto.setEstoque(novoEstoque);
                return true;
            }
        }
        return false;
    }

    public static List<Produto> getListaDeProdutos(){
        return listaDeProdutos;
    }

    @Override
    public String toString(){
        return "Produto: " + nome + "Preço: R$ " + preco;
    }

    public double getPreco(){
        return preco;
    }

    public String getNome(){
        return nome;
    }

    public int getEstoque(){
        return estoque;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setPreco(double preco){
        if(preco >= 0){
            this.preco = preco;
        }else{
            System.out.println("--- O preço não pode ser negativo. ---");
        }
    }

    public void setEstoque(int estoque){
        if (estoque >=0){
            this.estoque = estoque;
        }
    }
}