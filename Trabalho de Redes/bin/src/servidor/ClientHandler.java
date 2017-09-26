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
					//não ta entrando nesse if
					System.out.println("b");
					if(msg.charAt(0) == '/') {
						if(msg.charAt(1)== 'w') {
							enviarPM(msg);
						}else if(msg.charAt(1)=='s') {
							atualizarStatus(msg);
						}else if(msg.charAt(1)=='c') {
							adicionarCliente(msg);
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
		private void enviarPM(String msg) {
			String [] msgp = msg.split("\"");

			for(int i=0; i < clist.size(); i++) {
				if(clist.get(i).getNick().equals(msgp[1])) {
					coms.get(i).enviarMensagem("[PM]"+msgp[2]);
					break;
				}
			}
//			sw.atualizarClist(clist);
//			for(Comunicacao comm : coms) {
//				comm.enviarMensagem(msg);
//			}
		}
		
		
		
		public void atualizarClist(ArrayList<ClientInfo> clist) {
			this.clist=clist;
				for(ClientInfo ci : clist) {
					for(Comunicacao comm : coms) {
						comm.enviarMensagem("/c "+"\""+ ci.getNick()+"\""+ci.getStatus());
					}
				}
			}
		public void atualizarComs(ArrayList<Comunicacao> coms) {
			this.coms=coms;
		}
	
}
