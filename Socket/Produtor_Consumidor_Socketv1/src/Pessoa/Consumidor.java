package Pessoa;
import Buffers.Buffer;

public class Consumidor extends Pessoa{
	
	public Consumidor(String nome, Buffer buffer) {
		super(nome, buffer);
	}
	
	private void consumir() throws InterruptedException {
		this.produto = this.buffer.remover_produto();
		if  (this.produto != -1) {
			this.status = "Consumindo";
		}else {
			this.status = "Esperando";
		}
	}
	
	private void espera_buffer_encher() throws InterruptedException {
		this.status = "Esperando Buffer ter itens";
		while(this.buffer.buffer_vazio()){
			Thread.sleep(this.gerar_valor_aleatorio());
		}
	}
	
	public void exec() throws InterruptedException {
		// TODO Auto-generated method stub
		
		while (true) {
			if(buffer.buffer_vazio())
				try {
					espera_buffer_encher();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			consumir();
			try {
				esperar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
