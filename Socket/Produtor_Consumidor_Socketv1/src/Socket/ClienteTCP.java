package Socket;

import Message.Mensagem;
import Message.TipoCliente;
import Message.TipoOperacao;
import Util.Utilitario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


class ClienteTCPThread {

	public static void mostrarResposta(Mensagem msg){
		System.out.println("---------------------------\n" +
		"Recebido a resposta de: "  + msg.getId() + "\n" +
		"Tipo: " + msg.getTipoCliente() + "\n" +
		"Resposta: " + msg.getTipoOperacao() + "\n" +
		"Dados recebidos: " + msg.getDados() + "\n" +
		"---------------------------\n");
	}

    protected static int gerar_valor_aleatorio() {
        /*
         * Gera um valor aleatorio entre 0 e 5000
         */
        Random random = new Random();
        return random.nextInt(5000);
    }

	public static void consumidor() throws IOException, InterruptedException {
	    int id = gerar_valor_aleatorio();

	    Mensagem msg = new Mensagem(id, TipoCliente.CONSUMIDOR, TipoOperacao.RETIRAR_BUFFER, -1);
        Mensagem msgResposta = new Mensagem(id, TipoCliente.CONSUMIDOR);

        System.out.println("Conectando ao Servidor ...");
        Socket conexaoServidor = new Socket("127.0.0.1", 7777);

        // Envia a pergunta se pode retirar
        System.out.println("Conectado");
        OutputStream saida = conexaoServidor.getOutputStream();

        byte[] dadosEnviados = msg.toString().getBytes();

        System.out.println("[CONSUMIDOR]");
        System.out.println("Meu id e: " + id);

        while (true) {
            // Envia a mensagem
            saida.write(dadosEnviados);
            saida.flush();

            // Espera a respota do servidor e espera
            byte[] dadosRecebidos = new byte[100];
            //do {
            // Espera
            InputStream entrada = conexaoServidor.getInputStream();
            entrada.read(dadosRecebidos);
            msgResposta = Utilitario.montarMensagem(new String(dadosRecebidos));
            mostrarResposta(msgResposta);

            if (msgResposta.getTipoOperacao() == TipoOperacao.RETIRAR_ERRO) {
                System.out.println("Esperando Buffer encher...");
            } else {
                if (msgResposta.getTipoOperacao() == TipoOperacao.RETIRAR_OK) {
                    int tempo = gerar_valor_aleatorio();
                    System.out.printf("Consegui! estou consumindo: %s e vou demorar: %d ms\n", msgResposta.getDados(), tempo);
                    Thread.sleep(tempo);
                }
            }
            // }while (msgResposta.getTipoOperacao() == TipoOperacao.RETIRAR_ERRO);
        }
    }

    public static void produtor() throws IOException, InterruptedException {

	    int id =  gerar_valor_aleatorio();
	    Mensagem msg = new Mensagem(id, TipoCliente.PRODUTOR, TipoOperacao.ARMAZENAR_BUFFER, -1);   // Mensagem de envio
        Mensagem msgResposta;

        System.out.println("Conectando ao servidor...");

        // Cria o socket
        Socket conexaoServidor = new Socket("127.0.0.1", 7777);

        System.out.println("Conectado ao servidor");

        OutputStream saida = conexaoServidor.getOutputStream();
        System.out.println("[PRODUTOR]");
        System.out.println("Meu id e: " + id);

        int produto = 0;
        int tempo = 0;
        byte[] dadosEnviados;

        while (true){
            // Produz
            produto = gerar_valor_aleatorio();
            tempo = gerar_valor_aleatorio();
            System.out.printf("Estou produzindo: %d e vou demorar %d ms\n", produto, tempo);
            Thread.sleep(tempo);

            // Envia pergunta se pode armazenar
            msg.setDados(produto);
            dadosEnviados = msg.toString().getBytes();
            saida.write(dadosEnviados);
            saida.flush();

            // Recebe a resposta e espera ...
            do {
                // Recebe a resposta se pode ou nao armazenar
                byte[] dadosRecebidos = new byte[100];
                InputStream entrada = conexaoServidor.getInputStream();
                entrada.read(dadosRecebidos);
                msgResposta = Utilitario.montarMensagem(new String(dadosRecebidos));

                mostrarResposta(msgResposta);
                if (msgResposta.getTipoOperacao() == TipoOperacao.ARMAZENAR_ERRO) {
                    System.out.println("Esperando Buffer esvaziar...");
                }else{
                    System.out.println("Armazenado com sucesso");
                    //break;
                }
            }while (msgResposta.getTipoOperacao() == TipoOperacao.ARMAZENAR_ERRO);
        }

    }

	public static void main(String[] args) throws Exception {

        System.out.println("Aluno: Lucas Macedo da Silva\nDisciplina: Sistemas distribuidos Turma: A01" +
                "\n---Problema do Produtor Consumidor com Socket---\nVersao 3.1\n[CLIENTE TCP]\n\n");

	    int tipo_cliente;
	    System.out.println("Digite: \n[1] Para Consumidor \n[2] Para produtor \n[TIPO]: ");
	    Scanner ler = new Scanner(System.in);
	    tipo_cliente = ler.nextInt();

	    System.out.println(tipo_cliente);

	    switch (tipo_cliente){
            case 1:
                consumidor();
                break;
            case 2:
                produtor();
                break;
        }
	}


}
