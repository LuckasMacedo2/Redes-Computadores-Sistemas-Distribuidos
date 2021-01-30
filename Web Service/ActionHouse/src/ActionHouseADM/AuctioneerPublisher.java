package ActionHouseADM;

import javax.xml.ws.Endpoint;
import java.sql.SQLException;

public class AuctioneerPublisher {

    public static void main(String[] args) throws SQLException {
        Endpoint.publish("http://127.0.0.1:9999/ActionHouseADM", new ActioneerImpl());
        System.out.println("[Modo ADM] -> Em execução ...");
    }
}