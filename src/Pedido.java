import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static int NEXT_ID = 1;
    private static List<Pedido> listaDePedidos = new ArrayList<>();

    private int id;
    private String data;
    private StatusPedido status;
    private double precoTotal;
    private Comprador comprador;
    private List<ItemPedido> itens;

    public Pedido(String data, Comprador comprador) {
        this.id = NEXT_ID++;
        this.data = data;
        this.comprador = comprador;
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        this.precoTotal = 0.0;
        this.itens = new ArrayList<>();
    }

    public enum StatusPedido {
        AGUARDANDO_PAGAMENTO,
        VALOR_RETIDO,
        CONCLUIDO,
        CANCELADO
    }

    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            System.out.println("Item não pode ser nulo!");
            return;
        }

        this.itens.add(item);
        calcularTotal();
    }

    public static void cadastrar(Pedido pedido) {
        listaDePedidos.add(pedido);
    }

    public static List<Pedido> getListaDePedidos() {
        return listaDePedidos;
    }

    private void calcularTotal() {
        double totalTemporario = 0.0;

        for(ItemPedido item : itens) {
            totalTemporario += item.calcularTotal();
        }

        this.precoTotal = totalTemporario;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Comprador getComprador() {
        return comprador;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }
}
