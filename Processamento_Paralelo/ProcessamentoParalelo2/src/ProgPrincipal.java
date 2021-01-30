/*
 * Aluno: Lucas Macedo da Silva
 * Turma: A01 
 * Professor: Rafael Leal
 * Disciplina: Sistemas Distribuidos
 * 
 * Aplicação Produtor consumidor com Threads feito em Java
 * 
 * 						Versao: 1.2
 * 
 * Feito no IDE Eclipse
 */
public class ProgPrincipal {
	

	public static Trabalhador [] gerar_trabalhadores(int n, Buffer buffer) {
		Trabalhador[] trabalhadores = new Trabalhador[n];
		
		for (int i = 0; i < n; i++) {
			trabalhadores[i] = new Trabalhador("Trabalhador" + i, buffer);
		}
		
		return trabalhadores;
	}
	
	public static Consumidor [] gerar_consumidores(int n, Buffer buffer) {
		Consumidor[] consumidores = new Consumidor[n];
		
		for (int i = 0; i < n; i++) {
			consumidores[i] = new Consumidor("Consumidor" + i, buffer);
		}
		
		return consumidores;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int n_trab = 6;
		int n_con = 5;
		
		Buffer buffer = new Buffer();
		Trabalhador[] trabalhadores = new Trabalhador[n_trab];
		Consumidor[] consumidores = new Consumidor[n_con];
		
		trabalhadores = gerar_trabalhadores(n_trab, buffer);
		consumidores = gerar_consumidores(n_con, buffer);

		for (Trabalhador trabalhador: trabalhadores)
			trabalhador.start();
		
		for (Consumidor consumidor: consumidores)
			consumidor.start();
		
		Dashboard dashboard = new Dashboard(trabalhadores, consumidores, buffer);
		dashboard.run();
		
	}

}
