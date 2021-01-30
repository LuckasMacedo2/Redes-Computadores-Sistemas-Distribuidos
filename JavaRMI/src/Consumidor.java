import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Consumidor {
    Registry registry;
    BufferRemoto stub;

    public Consumidor() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry("localhost", 7777);
        stub = (BufferRemoto) registry.lookup("BufferRemoto");
    }

    public void msg(int tempoEspera, int produto){
        System.out.println("-------------------------");
        System.out.printf("Estou consumindo [%d] e vou demorar %d ms\n", produto, tempoEspera);
        System.out.println("-------------------------");
    }

    public void msgBufferVazio(){
        System.out.println("Buffer vazio, esperando ...");
    }

    public void msgRemovido(int produto, int tempoEspera){
        System.out.println("-------------------------");
        System.out.printf("Consumi [%d] e demorei %d ms\n", produto, tempoEspera);
        System.out.println("-------------------------");
    }

    public void consumir(){

        try {
            while (true) {
                // Gera o temmpo de espera
                int tempo = (new Random()).nextInt(5000);

                if (stub.vazio())               // Verifica se o buffer esta vazio
                    msgBufferVazio();           // Mensagem de que o buffer esta vazio

                int produto = stub.remover();   // Espera se buffer vazio senao removi

                msg(tempo, produto);            // Informa que o produto foi Removido
                Thread.sleep(tempo);            // Espera para consumir
                msgRemovido(tempo, produto);    // Informa que o produto foi consumido
            }
        }catch (InterruptedException | RemoteException | UnknownHostException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Aluno: Lucas Macedo da Silva\nDisciplina: Sistemas distribuidos Turma: A01" +
                "\n---Problema do Produtor Consumidor com RMI---\nVersao 1.3\n\n");
        Consumidor consumidor = new Consumidor();
        consumidor.consumir();
    }
}
