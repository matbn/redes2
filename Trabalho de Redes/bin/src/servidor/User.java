package servidor;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class User implements Runnable{

	private String name;
	private Socket socket;
	
	public User(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		
		
		try {
		
			
			InputStream in = socket.getInputStream();
			
			while (true) {
				
				byte[] buffer = new byte[4096];
				int bytesRead = in.read(buffer);
				
				if(bytesRead > 0){
					String msg = new String(buffer);
					System.out.println(msg);
					//serverFile.sendAll(msg);
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
