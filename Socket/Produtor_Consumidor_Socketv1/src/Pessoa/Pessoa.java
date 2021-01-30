package Pessoa;

import Buffers.Buffer;
import java.util.Random;

public class Pessoa extends Thread{
	protected String nome;			// O nome do trabalhador
	protected int tempo;		// O tempo que o trabalhador demorara para produzir o produto
	protected int produto;			// O produto 
	protected String status;			// Se o trabalhador esta esperando ou trabalhando
	protected Buffer buffer;			// Buffer para armazenar os itens
	
	public Pessoa(String nome, Buffer buffer) {
		super();
		this.nome = nome;
		this.buffer = buffer;
		this.tempo = 0;
		this.produto = -1;
		this.status = "Esperando";
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public int getProduto() {
		return produto;
	}
	public void setProduto(int produto) {
		this.produto = produto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Buffer getBuffer() {
		return buffer;
	}
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	protected int gerar_valor_aleatorio() {
		/*
		 * Gera um valor aleatorio entre 0 e 5000
		 */
		Random random = new Random();
		return random.nextInt(5000);
	}
	
	protected void esperar() throws InterruptedException {
		this.tempo = gerar_valor_aleatorio();
		Thread.sleep(this.tempo);
	}
	
	
}
