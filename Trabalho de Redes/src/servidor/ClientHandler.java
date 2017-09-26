package servidor;

import java.util.ArrayList;

import cliente.ClientInfo;
import comunicacao.Comunicacao;

class ClientHandler extends Thread {
	ArrayList<Comunicacao> coms;
	ArrayList<ClientInfo> clist;
	Comunicacao com;
	ServerWindow sw;
	public ClientHandler(ArrayList<Comunicacao> coms, ArrayList<ClientInfo> clist, ServerWindow sw) {
		this.clist=clist;
		this.coms = coms;
		this.sw=sw;
	}
	
	@Override
	public void run() {
		
		while(true) {
			if(coms.size()>0) {
			String msg = sw.com.receberMensagem();
				if(msg != null) {
					String[] m = msg.split(":");
					
					if(m[1].charAt(1) == '/') {
						if(m[1].charAt(2)== 'w') {
							enviarPM(m[1],m[0]);
						}else if(m[1].charAt(2)=='s') {
							atualizarStatus(m[1]);
						}else if(m[1].charAt(2)=='c') {
							adicionarCliente(m[1]);
						}
					}
					else{
						enviarTodos(msg);
					}
				}
			}else {
				try {
					this.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
			
	}
		private void adicionarCliente(String msg) {
			String[] msgp = msg.split("\"");
			ClientInfo aux = new ClientInfo(msgp[1],Integer.parseInt(msgp[2]));
			clist.add(aux);
			sw.atualizarClist(clist);
			sw.atualizarTabelaUsuarios();
			enviarTodos(msg);
	}

		private void atualizarStatus(String msg) {
			String [] msgp = msg.split("\"");

			for(int i=0; i < clist.size(); i++) {
				if(clist.get(i).getNick().equals(msgp[1])) {
					clist.get(i).setStatus(Integer.parseInt(msgp[2]));
					break;
				}
			}
		
	}

		public void enviarTodos(String msg) {
			for(Comunicacao comm : coms) {
				comm.enviarMensagem(msg);
			}
			
		}
		private void enviarPM(String msg, String remetente) {
			String [] msgp = msg.split("\"");

			for(int i=0; i < clist.size(); i++) {
				if(clist.get(i).getNick().equals(msgp[1])) {
					coms.get(i).enviarMensagem("[FROM] "+remetente+": "+msgp[2]);
					for(int j=0; j < clist.size(); j++)
						if(clist.get(j).getNick().equals(remetente)) {
							coms.get(j+1).enviarMensagem("[TO] "+msgp[1]+": "+msgp[2]);
							break;
						}
					break;
				}
			}

		}
		
		
		
		public void atualizarClist(ArrayList<ClientInfo> clist) {
			this.clist=clist;
			for(Comunicacao comm : coms) {
				for(ClientInfo ci : clist) {
						comm.enviarMensagem("/c "+"\""+ ci.getNick()+"\""+ci.getStatus());
					}
				}
			}
		public void atualizarComs(ArrayList<Comunicacao> coms) {
			this.coms=coms;
		}
	
}
