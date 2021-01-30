package ActionHouseClient;

import Item.Item;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.SQLException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Client {
    @WebMethod public boolean alterar(String campo, String condicao, String novoValor) throws SQLException;
    @WebMethod public Item listar_item(int id) throws SQLException;
    @WebMethod public Object[] listar_itens() throws SQLException;
    @WebMethod public boolean item_em_estoque(int id, int quantidade) throws SQLException;
    @WebMethod public boolean comprar(int id, int quantidade) throws SQLException;
}
