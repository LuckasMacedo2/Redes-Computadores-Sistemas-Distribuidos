package Item;

public class Compras {
    double valorItem;
    int idItem;
    int qtdItem;
    double total;

    public Compras(double valorCompra, int idItem, int qtdItem, double total) {
        this.valorItem = valorCompra;
        this.idItem = idItem;
        this.qtdItem = qtdItem;
        this.total = total;
    }

    public Compras(){
        this.valorItem = -1;
        this.idItem = 0;
        this.qtdItem = 0;
        this.total = 0;
    }

    public double getValorItem() {
        return valorItem;
    }

    public int getIdItem() {
        return idItem;
    }

    public int getQtdItem() {
        return qtdItem;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setValorItem(double valorItem) {
        this.valorItem = valorItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public void setQtdItem(int qtdItem) {
        this.qtdItem = qtdItem;
    }

    public int getItdItem() {
        return qtdItem;
    }

    public void setNomeItem(int qtdItem) {
        this.qtdItem = qtdItem;
    }

    @Override
    public String toString() {
        return "Valor de cada item R$ " + valorItem +
                "\nID do item comprado: " + idItem +
                "\nQuantidade de itens comprados: " + qtdItem +
                "\nTotal da compra R$ " + total +
                "\n";
    }
}
