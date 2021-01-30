package Buffers;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

	public Buffer(int tamanho) {
		this.indice = 0;
		this.tamanho = tamanho;
	}
	
	private volatile int tamanho = 5;
	public  int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tAMANHO_TOTAL) {
		tamanho = tAMANHO_TOTAL;
	}

	private volatile int indice;
	
	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	private volatile List<Integer> buffer = new ArrayList<Integer>();
	
	@Override
	public synchronized String toString() {
		return "Buffer - > " + this.buffer;
	}

	public Boolean buffer_cheio() {
		return this.indice == tamanho;
	}
	
	public Boolean buffer_vazio() {
		return this.indice == 0;
	}
	

	
	public synchronized void adicionar_produto(int produto) throws InterruptedException {
	    while (buffer_cheio()){
	    	wait();
        }
		this.buffer.add(produto);
		this.indice++;
		notifyAll();
	}
	
	public synchronized int remover_produto() throws InterruptedException {
		while (indice == 0) {
           this.wait();
        }
		int produto = this.buffer.get(0);
		this.buffer.remove(0);
		this.indice--;
        notifyAll();
		return produto;
	}

}
