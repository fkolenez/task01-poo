public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double PrecoUnitario;
    private StatusItemPedido status;

    public ItemPedido(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
        this.PrecoUnitario = produto.getPreco();
        this.status = StatusItemPedido.AGUARDANDO_PAGAMENTO;
    }

    public enum StatusItemPedido {
        AGUARDANDO_PAGAMENTO,
        VALOR_RETIDO,
        CONCLUIDO,
        CANCELADO
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

    public StatusItemPedido getStatus() {
        return status;
    }

    public void setStatus(StatusItemPedido status) {
        this.status = status;
    }
}
