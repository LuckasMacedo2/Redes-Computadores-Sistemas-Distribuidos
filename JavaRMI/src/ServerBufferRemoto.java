import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerBufferRemoto{

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(7777);

        BufferRMI bufferRMI = new BufferRMI();

        System.out.println("Aluno: Lucas Macedo da Silva\nDisciplina: Sistemas distribuidos Turma: A01" +
                "\n---Problema do Produtor Consumidor com RMI---\nVersao 1.3\n\n");

        BufferRemoto stub = (BufferRemoto) UnicastRemoteObject.exportObject(bufferRMI, 0);
        Naming.rebind("rmi://localhost:7777/BufferRemoto", stub);

        System.out.println("Registro em execucao...");

    }
}
