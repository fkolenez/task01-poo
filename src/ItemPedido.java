public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double PrecoUnitario;

    public ItemPedido(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
        this.PrecoUnitario = produto.getPreco();
    }

    public double calcularTotal(){
        return this.quantidade * this.PrecoUnitario;
    }

    public int getQuantidade(){
        return quantidade;
    }

    public Produto getProduto(){
        return produto;
    }

    public double getPrecoUnitario(){
        return PrecoUnitario;
    }
}