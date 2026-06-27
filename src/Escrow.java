public class Escrow {
    public boolean reterFundos(Pedido pedido) {
        if (pedido.getStatus() == Pedido.StatusPedido.AGUARDANDO_PAGAMENTO) {
            pedido.setStatus(Pedido.StatusPedido.VALOR_RETIDO);

            for (ItemPedido item : pedido.getItens()) {
                item.setStatus(ItemPedido.StatusItemPedido.VALOR_RETIDO);
            }

            return true;
        }

        return false;
    }

    public boolean liberarFundos(Pedido pedido) {
        if (pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO) {
            for (ItemPedido item : pedido.getItens()) {
                item.setStatus(ItemPedido.StatusItemPedido.CONCLUIDO);
            }

            pedido.setStatus(Pedido.StatusPedido.CONCLUIDO);
            return true;
        }

        return false;
    }

    public boolean liberarFundos(Pedido pedido, Vendedor vendedor) {
        if (pedido.getStatus() != Pedido.StatusPedido.VALOR_RETIDO) {
            return false;
        }

        boolean liberouItemDoVendedor = false;

        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();

            if (produto.getIdVendedor() == vendedor.getId()
                    && item.getStatus() == ItemPedido.StatusItemPedido.VALOR_RETIDO) {
                item.setStatus(ItemPedido.StatusItemPedido.CONCLUIDO);
                liberouItemDoVendedor = true;
            }
        }

        if (!liberouItemDoVendedor) {
            return false;
        }

        if (todosItensConcluidos(pedido)) {
            pedido.setStatus(Pedido.StatusPedido.CONCLUIDO);
        }

        return true;
    }

    public boolean cancelarPedido(Pedido pedido) {
        if (pedido.getStatus() == Pedido.StatusPedido.AGUARDANDO_PAGAMENTO
                || (pedido.getStatus() == Pedido.StatusPedido.VALOR_RETIDO && !possuiItemConcluido(pedido))) {
            pedido.setStatus(Pedido.StatusPedido.CANCELADO);

            for (ItemPedido item : pedido.getItens()) {
                item.setStatus(ItemPedido.StatusItemPedido.CANCELADO);
            }

            return true;
        }

        return false;
    }

    private boolean todosItensConcluidos(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            if (item.getStatus() != ItemPedido.StatusItemPedido.CONCLUIDO) {
                return false;
            }
        }

        return true;
    }

    private boolean possuiItemConcluido(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            if (item.getStatus() == ItemPedido.StatusItemPedido.CONCLUIDO) {
                return true;
            }
        }

        return false;
    }
}
