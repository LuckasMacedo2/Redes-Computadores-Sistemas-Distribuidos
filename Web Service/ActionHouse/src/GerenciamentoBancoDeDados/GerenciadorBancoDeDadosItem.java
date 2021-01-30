package GerenciamentoBancoDeDados;

import Item.Item;
import Item.Compras;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GerenciadorBancoDeDadosItem {
    Connection connection;
    Statement statement;

    public GerenciadorBancoDeDadosItem() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:bancoItens.db");
        statement = connection.createStatement();
    }

    public void criarTabela() throws SQLException {

        statement.execute("CREATE TABLE IF NOT EXISTS Produtos ( " +
                " id integer PRIMARY KEY," +
                " nome text NOT NULL," +
                " quantidade integer, " +
                " valor real, " +
                " descricao text);"
        );

        statement.execute("CREATE TABLE IF NOT EXISTS Compras ( " +
                " valorCompra integer," +
                " idItem integer," +
                " qtdItem integer, " +
                " total real);"
        );
    }

    public void insertItem(Item item) throws SQLException {
        String sql = "INSERT INTO Produtos(id, nome, quantidade, valor, descricao) VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, item.getId());
        preparedStatement.setString(2, item.getNome());
        preparedStatement.setInt(3, item.getQuantidade());
        preparedStatement.setDouble(4, item.getValor());
        preparedStatement.setString(5, item.getDescricao());
        preparedStatement.executeUpdate();
    }

    public Item selectItem(int id) throws SQLException {
        ResultSet rs = statement.executeQuery("Select * FROM Produtos Where id == " + id);
        return new Item(rs.getInt("id"), rs.getString("nome"),
                rs.getInt("quantidade"), rs.getDouble("valor"),
                rs.getString("descricao"));
    }

    public List<Item> selectItens() throws SQLException {
        ResultSet rs = statement.executeQuery("Select * FROM Produtos");
        List<Item> listaItem = new ArrayList<Item>();
        while (rs.next()) {
            Item item = new Item(rs.getInt("id"), rs.getString("nome"),
                    rs.getInt("quantidade"), rs.getDouble("valor"),
                    rs.getString("descricao"));
            listaItem.add(item);
        }
        return listaItem;
    }


    public void deleteItem(int id) throws SQLException {
        statement.execute("DELETE FROM Produtos WHERE id = " + id);
    }

    public boolean update(String campo, String condicao, String novoValor){
        try{
            String sql = "UPDATE Produtos SET " + campo + " = " + novoValor + " WHERE " + condicao;
            statement.execute(sql);
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void dropTableItens() throws SQLException {
        statement.execute("Drop Table Produtos");
    }

    public void dropTableCompras() throws SQLException {
        statement.execute("Drop Table Compras");
        connection.close();
    }

    public void insertCompra(Compras compras) throws SQLException {
        String sql = "INSERT INTO Compras (valorCompra, idItem, qtdItem, total) VALUES(?,?,?,?)";
        System.out.println(compras.toString());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, compras.getValorItem());
        preparedStatement.setInt(2, compras.getIdItem());
        preparedStatement.setInt(3, compras.getQtdItem());
        preparedStatement.setDouble(4, compras.getTotal());
        preparedStatement.executeUpdate();
    }
}