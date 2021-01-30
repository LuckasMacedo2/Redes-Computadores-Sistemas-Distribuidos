package Message;


public class Mensagem {
	
	private TipoCliente tipoCliente;
	private TipoOperacao tipoOperacao;
	private int dados;
	
	public Mensagem(int id, TipoCliente tipoCliente, TipoOperacao tipoOperacao, int dados) {
		super();
		this.tipoCliente = tipoCliente;
		this.tipoOperacao = tipoOperacao;
		this.dados = dados;
		this.id = id;
	}

	public Mensagem( int id, TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
		this.id = id;
		this.dados = -1;
		this.tipoOperacao = null;
	}

	private int id;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public int getDados() {
		return dados;
	}

	public void setDados(int dados) {
		this.dados = dados;
	}

	
	@Override
	public String toString() {
		return id + ";" + tipoCliente.ordinal() + ";" + tipoOperacao.ordinal() + ";" + dados;
	}

}
