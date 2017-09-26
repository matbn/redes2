package cliente;

import java.io.Serializable;

import javax.swing.JOptionPane;

import comunicacao.Comunicacao;


public class Client extends Thread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick = null;
	private Comunicacao com;
	private ClientWindow cw;
	private int status = 0;
	private ClientInfo ci;
	public Client() {
		while (nick == null || nick.equals("") || nick.charAt(0)==' ') 
		nick = JOptionPane.showInputDialog("Insira seu nick");
		ci = new ClientInfo(nick, status);
		com = new Comunicacao();
		com.conectar("localhost", 3030);
		com.enviarMensagem("/s "+"\""+ ci.getNick()+"\""+ci.getStatus());
		cw = new ClientWindow(this);
		this.start();
	}
	
	public Client(String nick) {
		this.nick = nick;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public Comunicacao getCom() {
		return com;
	}
	public void setCom(Comunicacao com) {
		this.com = com;
	}
	public ClientWindow getCw() {
		return cw;
	}
	public void setCw(ClientWindow cw) {
		this.cw = cw;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void changeStatus() {
		status++;
		if(status>=3) {
			status = 0;
		}
		ci.setStatus(status);
		enviarMensagem("/s \""+nick+"\""+status);
	}
	public void enviarMensagem(String text) {
		com.enviarMensagem(text);
	}
	
	public void run() {
		while(true) {
			String msg = (String)com.receberMensagem();
			if(msg.charAt(0)=='/') {
				if(msg.charAt(1)=='s') {
					String[] msgp = msg.split("\"");
					status = Integer.parseInt(msgp[2]);
					cw.atualizarClientInfoStatus(nick, status);
				}else if(msg.charAt(1)=='c') {
					cw.adicionarCliente(msg);
				}
			}
			else{
				cw.atualizarChat(msg);
			}
		}
	}
	public ClientInfo getClientInfo() {
		return ci;
	}
}
