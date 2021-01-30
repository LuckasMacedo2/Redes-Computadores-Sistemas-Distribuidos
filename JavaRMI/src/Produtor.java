import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Produtor{

    Registry registry;
    BufferRemoto stub;
    public Produtor() throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry("localhost", 7777);
        stub  = (BufferRemoto) registry.lookup("BufferRemoto");
    }


    public void msg(int tempoEspera, int produto){
        System.out.println("-------------------------");
        System.out.printf("Estou produzindo [%d] e vou demorar %d ms\n", produto, tempoEspera);
        System.out.println("-------------------------");
    }

    public void msgBufferCheio(){
        System.out.println("Buffer cheio, esperando ...");
    }

    public void msgInserido(int produto, int tempoEspera){
        System.out.println("-------------------------");
        System.out.printf("Produzi [%d] e demorei %d ms\n", produto, tempoEspera);
        System.out.println("-------------------------");
    }

    public void produzir(){
        try {
            while (true) {
                // Gera o tempo de espera
                int tempo = (new Random()).nextInt(5000);
                // Gera o produto
                int produto = (new Random()).nextInt(100);

                // Mensagem de status
                msg(tempo, produto);

                // Verifica se o buffer ta cheio
                if (this.stub.cheio())
                    msgBufferCheio();      // Informa que o buffer ta cheio

                this.stub.inserir(produto);// Inseri o produto ou espera se o buffer ta cheio
                Thread.sleep(tempo);       // Poe a thread para dormir

                msgInserido(produto, tempo);// Informa que inseriu


            }
        } catch (InterruptedException | RemoteException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Aluno: Lucas Macedo da Silva\nDisciplina: Sistemas distribuidos Turma: A01" +
                "\n---Problema do Produtor Consumidor com RMI---\nVersao 1.3\n\n");
        Produtor produtor = new Produtor();
        produtor.produzir();
    }
}
