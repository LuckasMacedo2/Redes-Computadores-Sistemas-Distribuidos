
public class Dashboard extends Thread{
	// Atributos
	private Trabalhador trabalhadores[];	// Vetor de trabalhadores
	private Buffer buffer;
	private Consumidor consumidores[];
	
	// Construtor
	public Dashboard(Trabalhador[] trabalhadores, Consumidor[] consumidores, Buffer buffer) {
		super();
		this.trabalhadores = trabalhadores;
		this.consumidores = consumidores;
		this.buffer = buffer;
	};
	
	public void print() {
		System.out.println("________________________________________________________________");
		System.out.println("Trabalhadores");
		for (Trabalhador trabalhador: this.trabalhadores) {
			if (trabalhador.getStatus() == "Esperando")
				System.out.println(trabalhador.getNome() + " -> " + trabalhador.getStatus() +
					", Produziu: " + trabalhador.getProduto() + ", Demorou " + trabalhador.getTempo() + " ms");
			else
				System.out.println(trabalhador.getNome() + " -> " + trabalhador.getStatus() +
						", Produzindo: " + trabalhador.getProduto() + ", Demorará " + trabalhador.getTempo() + " ms");
				
		}
		
		System.out.println("\n" + this.buffer.toString()+"\n");
		
		System.out.println("Consumidores");
		for (Consumidor consumidor: this.consumidores) {
			if (consumidor.getStatus() == "Esperando")
				
				System.out.println(consumidor.getNome() + " -> " + consumidor.getStatus() +
					", Consumiu: " + consumidor.getProduto() + ", Domorou " + consumidor.getTempo() + " ms");
			else
				System.out.println(consumidor.getNome() + " -> " + consumidor.getStatus() +
					", Consumindo: " + consumidor.getProduto() + ", Demorará " + consumidor.getTempo() + " ms");
				
		}
		System.out.println("________________________________________________________________");
		
		try {
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		// Gera o dashboard
		while(true) {
			print();
			if(buffer.getIndice() > buffer.getTAMANHO_TOTAL()) {
				System.out.println("Deu ruim");
				break;
			}
		}
	}
}
