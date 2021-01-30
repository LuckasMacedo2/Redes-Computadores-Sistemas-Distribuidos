import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class BufferRMI implements BufferRemoto {

    private volatile List<Integer> buffer = new ArrayList<Integer>();
    private static final int TAM = 5;

    public void msgInserido(int item) throws UnknownHostException {
        System.out.println("----------- Inserir -----------------------");
        System.out.printf("Produtor %s \n", InetAddress.getLocalHost().toString());
        System.out.printf("Inseriu [%d] no buffer\n", item);
        System.out.println("Buffer: " + buffer.toString());
        System.out.println("-------------------------------------------");
    }

    public void msgRetirado(int item) throws UnknownHostException {
        System.out.println("----------- Retirar -----------------------");
        System.out.printf("Consumidor %s \n", InetAddress.getLocalHost().toString());
        System.out.printf("Retirou [%d] do buffer\n", item);
        System.out.println("Buffer: " + buffer.toString());
        System.out.println("-------------------------------------------");
    }

    @Override
    public synchronized void inserir(int item) throws InterruptedException, UnknownHostException {
        while (this.cheio())
            wait();
        buffer.add(item);
        msgInserido(item);
        notifyAll();
    }

    @Override
    public synchronized int remover() throws InterruptedException,  UnknownHostException {
        while (this.vazio())
            wait();
        int item;
        item = buffer.remove(0);
        msgRetirado(item);
        notifyAll();
        return item;
    }

    @Override
    public boolean cheio() {
        if (buffer.size() == TAM)
            return true;
        return false;
    }

    @Override
    public boolean vazio() {
        if (buffer.size() == 0)
            return true;
        return false;
    }
}
