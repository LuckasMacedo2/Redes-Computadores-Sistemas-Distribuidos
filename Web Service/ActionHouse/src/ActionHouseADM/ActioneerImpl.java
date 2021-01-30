package ActionHouseADM;


import GerenciamentoBancoDeDados.GerenciadorBancoDeDadosCompras;
import GerenciamentoBancoDeDados.GerenciadorBancoDeDadosItem;
import Item.Item;


import javax.jws.WebService;
import java.sql.SQLException;
import java.util.ArrayList;


@WebService(endpointInterface = "ActionHouseADM.Auctioneer")
public class ActioneerImpl implements Auctioneer{
    GerenciadorBancoDeDadosItem gbd;
    GerenciadorBancoDeDadosCompras gbdc;
    public ActioneerImpl() throws SQLException {
        gbd = new GerenciadorBancoDeDadosItem();
        gbdc = new GerenciadorBancoDeDadosCompras();
        gbd.criarTabela();
        gbdc.criarTabela();
    }

    @Override
    public boolean inserir(int id, String nome, int quantidade, double valor, String decricao){
        try{
            Item item = new Item(id, nome, quantidade, valor, decricao);
            gbd.insertItem(item);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean excluir(int id) {
        try {
            gbd.deleteItem(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Item listar_item(int id) {
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
    public String compras_realizadas() throws SQLException {
        return gbdc.selectCompras();
    }

    @Override
    public void excluirBancoDeDados() throws SQLException {
        gbd.dropTableItens();
        gbdc.dropTableCompras();
    }

    @Override
    public boolean alterar(String campo, String condicao, String novoValor) throws SQLException {
        return gbd.update(campo, condicao, novoValor);
    }
}
