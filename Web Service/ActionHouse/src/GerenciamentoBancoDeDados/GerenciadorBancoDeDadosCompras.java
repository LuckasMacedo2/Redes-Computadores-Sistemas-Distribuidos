package GerenciamentoBancoDeDados;

import Item.Compras;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class GerenciadorBancoDeDadosCompras {
    Connection connection;
    Statement statement;

    public GerenciadorBancoDeDadosCompras() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:bancoCompras.db");
        statement = connection.createStatement();
    }

    public void criarTabela() throws SQLException {

        statement.execute("CREATE TABLE IF NOT EXISTS Compras ( " +
                " valorItem integer," +
                " idItem integer," +
                " qtdItem integer, " +
                " total real);"
        );
    }

    public void insertCompra(Compras compras) throws SQLException {
        String sql = "INSERT INTO Compras (valorItem, idItem, qtdItem, total) VALUES(?,?,?,?)";
        System.out.println(compras.toString());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, compras.getValorItem());
        preparedStatement.setInt(2, compras.getIdItem());
        preparedStatement.setInt(3, compras.getQtdItem());
        preparedStatement.setDouble(4, compras.getTotal());
        preparedStatement.executeUpdate();
    }

    public String selectCompras() throws SQLException {
        ResultSet rs = statement.executeQuery("Select * FROM Compras");
        String s = "";
        while (rs.next()) {
            Compras compras = new Compras(rs.getDouble("valorItem"),
                    rs.getInt("idItem"),
                    rs.getInt("qtdItem"),
                    rs.getDouble("total"));
            s += compras.toString() + "\n";
        }
        return s;
    }

    public void dropTableCompras() throws SQLException {
        statement.execute("Drop Table Compras");
    }
}
