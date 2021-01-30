package Pessoa;
import Buffers.Buffer;

public class Trabalhador extends Pessoa{
	
	
	// Construtor
	public Trabalhador(String nome, Buffer buffer) {
		super(nome, buffer);
		this.nome = nome;
		this.tempo = 0;
		this.produto = gerar_valor_aleatorio();
		this.buffer = buffer;
		this.status = "Esperando";
	}
	
	
	public void produzir() {
		// Produz o produto
		this.produto = gerar_valor_aleatorio();
	}
	
	
	public void espera_buffer_esvaziar() {
		// Espera o buffer esvaziar
		this.status = "Esperando Buffer esvaziar";
		while (buffer.buffer_cheio()) {
			try {
				Thread.sleep(this.gerar_valor_aleatorio());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.status = "Trabalhando";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			produzir();
			try {
				esperar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.status = "Trabalhando";
			if (buffer.buffer_cheio()) {
				espera_buffer_esvaziar();
			}
			try {
				buffer.adicionar_produto(this.produto);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
