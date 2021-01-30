package Socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Buffers.Buffer;
import Message.*;
import Util.Utilitario;

import static Message.TipoCliente.*;

class ThreadCliente extends Thread {
    Socket socketCliente;
    InputStream entrada;
    Mensagem msg;
    Buffer buffer;

    public ThreadCliente(Socket socketCliente, Buffer buffer) throws IOException {
        this.socketCliente = socketCliente;
        this.entrada = socketCliente.getInputStream();
        this.buffer = buffer;
    }

    public void mostrarMsgCliente() {
        System.out.println("---------------------------\n" +
                "Cliente: " + this.msg.getId() + " conectado!" + "\n" +
                "Tipo de cliente: " + this.msg.getTipoCliente() + "\n" +
                "Tipo de operacao: " + this.msg.getTipoOperacao() + "\n" +
                "Dados recebidos: " + this.msg.getDados() + "\n" +
                "---------------------------\n");
    }

    public void mostrarBufferAlterado(String tipoCliente){
        System.out.println("********************************************************");
        mostrarMsgCliente();
        System.out.printf("[%s] >> Buffer Alterado! \n%s\n", tipoCliente, this.buffer.toString());
        System.out.println("********************************************************");
    }

    public void msgEspera(String tipoCliente, int id, String tipoEspera){
        System.out.printf("[%s] id: %d esta esperando o buffer %s...\n", tipoCliente, id, tipoEspera);
    }

    public void enviarMensagemCliente(Mensagem msg) throws IOException {
        OutputStream saida = socketCliente.getOutputStream();
        saida.write(msg.toString().getBytes());
        saida.flush();
    }

    public void consumidor() throws IOException, InterruptedException {

        // Mensagem de erro
        if (this.buffer.buffer_vazio()) {
            this.enviarMensagemCliente(new Mensagem(0, BUFFER, TipoOperacao.RETIRAR_ERRO, -1));

            this.msgEspera("CONSUMIDOR", this.msg.getId(), "encher");
        }

        int produto = -1;

        produto = this.buffer.remover_produto();

        // Mensagem de OK
        this.enviarMensagemCliente(new Mensagem(0, BUFFER, TipoOperacao.RETIRAR_OK, produto));

        this.mostrarBufferAlterado("CONSUMIDOR");

    }

    public void produtor() throws InterruptedException, IOException {
        // Mensagem de erro
        if (this.buffer.buffer_cheio()) {
            this.enviarMensagemCliente(new Mensagem(0, BUFFER, TipoOperacao.ARMAZENAR_ERRO, -1));

            this.msgEspera("PRODUTOR", this.msg.getId(), "esvaziar");
        }

        this.buffer.adicionar_produto(this.msg.getDados());

        // Mensagem de OK
        this.enviarMensagemCliente(new Mensagem(0, BUFFER, TipoOperacao.ARMAZENAR_OK, -1));
        this.mostrarBufferAlterado("PRODUTOR");

    }


    @Override
    public void run() {
        super.run();
        while (true) {
            byte[] dadosRecebidos = new byte[100];

            try {
                entrada.read(dadosRecebidos);
            } catch (IOException e) {
                try {
                    socketCliente.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Cliente desconectado!");
                break;
            }
            this.msg = Utilitario.montarMensagem(new String(dadosRecebidos));
            if (msg != null) {
                switch (msg.getTipoCliente()) {
                    case CONSUMIDOR:
                        try {
                            consumidor();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case BUFFER:

                        break;
                    case PRODUTOR:
                        try{
                            produtor();
                        } catch (InterruptedException | IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        }

    }
}

public class ServidorTCP {
    private static final int PORTA = 7777;

    public static void main(String[] args) throws IOException {
        System.out.println("Aluno: Lucas Macedo da Silva\nDisciplina: Sistemas distribuidos Turma: A01" +
                "\n---Problema do Produtor Consumidor com Socket---\nVersao 3.1\n[SERVIDOR TCP]\n\n");
        ServerSocket ss = new ServerSocket(PORTA);            // Cria o socket

        System.out.println("Esperando na porta " + PORTA + " ...");

        Buffer buffer = new Buffer(5);

        while (true) {
            // Fica esperando na porta
            Socket conexaoCliente = ss.accept();
            System.out.println("Conexao estabelecida");
            ThreadCliente threadCliente = new ThreadCliente(conexaoCliente, buffer);
            threadCliente.start();
        }


    }
}
