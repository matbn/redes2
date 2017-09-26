package cliente;

import java.io.Serializable;

public class ClientInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nick;
	private int status;
	public ClientInfo(String nick, int status) {
		super();
		this.nick = nick;
		this.status = status;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
