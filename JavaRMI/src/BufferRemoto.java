import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BufferRemoto  extends Remote {

    void inserir (int item) throws RemoteException, InterruptedException, UnknownHostException;
    int remover () throws RemoteException, InterruptedException, UnknownHostException;

    boolean cheio() throws RemoteException;
    boolean vazio() throws RemoteException;

}
