package ActionHouseADM;

import Item.Item;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Auctioneer {
    @WebMethod boolean inserir(int id, String nome, int quantidade, double valor, String decricao);
    @WebMethod boolean alterar(String campo, String condicao, String novoValor) throws SQLException;
    @WebMethod boolean excluir(int id);
    @WebMethod Item listar_item(int id) throws SQLException;
    @WebMethod Object[] listar_itens() throws SQLException;
    @WebMethod void excluirBancoDeDados() throws SQLException;
    @WebMethod String compras_realizadas() throws SQLException;
}
