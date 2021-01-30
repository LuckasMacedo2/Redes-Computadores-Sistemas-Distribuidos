package Item;

public class Item {
    int id;
    String nome;
    int quantidade;
    double valor;
    String descricao;

    public Item()
    {
        this.id = -1;
        this.nome = "";
        this.quantidade = 0;
        this.valor = 0;
        this.descricao = "";
    }

    public Item(int id, String nome, int quantidade, double valor, String descricao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.descricao = descricao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
