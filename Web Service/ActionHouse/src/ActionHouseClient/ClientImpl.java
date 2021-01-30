package ActionHouseClient;

import GerenciamentoBancoDeDados.GerenciadorBancoDeDadosCompras;
import GerenciamentoBancoDeDados.GerenciadorBancoDeDadosItem;
import Item.Item;

import javax.jws.WebService;
import java.sql.SQLException;


import Item.Compras;

@WebService(endpointInterface = "ActionHouseClient.Client")
public class ClientImpl implements Client {
    GerenciadorBancoDeDadosItem gbd;
    GerenciadorBancoDeDadosCompras gbdc;
    Item item;

    ClientImpl() throws SQLException {
        gbd = new GerenciadorBancoDeDadosItem();
        gbdc = new GerenciadorBancoDeDadosCompras();
        item = new Item();
       // gbdc.criarTabela();
    }

    @Override
    public boolean alterar(String campo, String condicao, String novoValor) throws SQLException {
        return gbd.update(campo, condicao, novoValor);
    }

    @Override
    public Item listar_item(int id) throws SQLException {
        Item item = new Item();
        try {
            item = gbd.selectItem(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Object[] listar_itens() throws SQLException {
        return gbd.selectItens().toArray();
    }

    @Override
    public boolean item_em_estoque(int id, int quantidade) throws SQLException {
        this.item = gbd.selectItem(id);
        if (item.getQuantidade() - quantidade < 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean comprar(int id, int quantidade) throws SQLException {

        Compras compras = new Compras(item.getValor(), item.getId(), quantidade,
                item.getValor() * quantidade);
        System.out.println(compras.toString());
        gbdc.insertCompra(compras);
        return this.alterar("quantidade", "id = "+id, "quantidade - " + quantidade);
    }

}
