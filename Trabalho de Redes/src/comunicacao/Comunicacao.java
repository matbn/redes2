package comunicacao;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import cliente.ClientInfo;

public class Comunicacao {
	private InetAddress IPAddress;
	private Integer porta;
	private Socket connectionSocket;
	private Socket clienteSocket;
	private ServerSocket serverSocket;
	private ObjectInputStream entrada;
	private ObjectOutputStream saida;
	public Comunicacao conectar(String ip, Integer porta) {
		try {
			return conectar(InetAddress.getByName(ip), porta);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return this;
	}
	public Comunicacao conectar(InetAddress ip, Integer porta) {
		try {
			this.IPAddress = ip;
			this.porta = porta;
			this.connectionSocket = new Socket(this.IPAddress ,this.porta);
			this.connectionSocket.setKeepAlive(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public Comunicacao iniciarServidor(Integer porta) {
		try {
			this.serverSocket = new ServerSocket(porta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	public Comunicacao conectarCliente() {
		Comunicacao cliente = null;
		try {
			cliente = new Comunicacao();
			this.clienteSocket = this.serverSocket.accept();
			this.clienteSocket.setKeepAlive(true);
			this.IPAddress = clienteSocket.getInetAddress();
			this.porta = clienteSocket.getPort();
			cliente.setConnectionSocket(this.clienteSocket);
			cliente.setIPAddress(this.IPAddress);
			cliente.setPorta(this.porta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cliente;
	}
	
	public boolean enviarMensagem(Object mensagem) {
		boolean enviado = true;
		try {
			if(this.connectionSocket != null && ! this.connectionSocket.isClosed()) {
				this.saida = new ObjectOutputStream(this.connectionSocket.getOutputStream());
				this.saida.writeObject(mensagem);
			} else if(this.clienteSocket != null && ! this.clienteSocket.isClosed()) {
				this.saida = new ObjectOutputStream(this.clienteSocket.getOutputStream());
				this.saida.writeObject(mensagem);
			} 
		} catch (IOException e) {
			enviado = false;
			e.printStackTrace();
		}
		return enviado;
	}

	
	public String receberMensagem() {
		Object retorno = null;
		try {
			if(this.connectionSocket != null){
				this.entrada = new ObjectInputStream(this.connectionSocket.getInputStream());
			} else if(this.clienteSocket != null){
				this.entrada = new ObjectInputStream(this.clienteSocket.getInputStream());
			} 
			if(this.entrada!=null)
			retorno = this.entrada.readObject();
//			if (retorno==null){
//				connectionSocket.close();
//			}
		} catch (IOException e) {
			e.printStackTrace();
			fecharConexao();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return (String) retorno;
	}

	public boolean fecharConexao() {
		boolean fechar = true;
		try {
			this.entrada.close();
			this.saida.close();
			if(this.clienteSocket != null){
				this.clienteSocket.close();
			}
			if(this.connectionSocket != null){
				this.connectionSocket.close();
			}
		} catch (IOException e) {
			fechar = false;
			e.printStackTrace();
		}
		
		return fechar;
	}
	public InetAddress getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(InetAddress iPAddress) {
		IPAddress = iPAddress;
	}
	public Integer getPorta() {
		return porta;
	}
	public void setPorta(Integer porta) {
		this.porta = porta;
	}
	public Socket getConnectionSocket() {
		return connectionSocket;
	}
	public void setConnectionSocket(Socket connectionSocket) {
		this.connectionSocket = connectionSocket;
	}
	public Socket getClienteSocket() {
		return clienteSocket;
	}
	public void setClienteSocket(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public ObjectInputStream getEntrada() {
		return entrada;
	}
	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}
	public ObjectOutputStream getSaida() {
		return saida;
	}
	public void setSaida(ObjectOutputStream saida) {
		this.saida = saida;
	}
}
