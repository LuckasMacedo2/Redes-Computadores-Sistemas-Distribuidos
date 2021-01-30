package ActionHouseClient;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class ClientPublisher {

    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://127.0.0.1:7777/ActionHouseClient", new ClientImpl());
        System.out.println("[Modo Cliente] -> Em execução ...");
    }
}