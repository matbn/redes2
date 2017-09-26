package servidor;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import cliente.ClientInfo;
import comunicacao.Comunicacao;

public class ServerWindow extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table = new JTable();
	private JButton btnRemover;
	ArrayList<ClientInfo> clist = new ArrayList<ClientInfo>();
	ArrayList<Comunicacao> ips = new ArrayList<Comunicacao>();
	Comunicacao com = new Comunicacao();
	ClientHandler ch = new ClientHandler(ips,clist,this);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			ServerWindow frame = new ServerWindow();
			frame.setVisible(true);
			frame.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Carrega a tabela com as informações: nome e ip do usuario
	 * @return
	 */
	public void atualizarTabelaUsuarios(){
		Object [][] dados = new Object[clist.size()][ips.size()];
		for(int i= 0; i< clist.size(); i++){
			Object[] dado= new Object[2];
			dado[0]=clist.get(i).getNick();
			dado[1]=ips.get(i).getIPAddress();
			dados[i]=dado;
		}
		String[] colunas = {"Nick","IP"};
		table = new JTable(dados,colunas);
		table.revalidate();
		table.repaint();
	}
	
	/**
	 * Create the frame.
	 */
	public ServerWindow() {
		com = new Comunicacao();
		com.iniciarServidor(3030);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblUsuriosLogados = new JLabel("Usu\u00E1rios logados");
		contentPane.add(lblUsuriosLogados, BorderLayout.NORTH);
				
		contentPane.add(table, BorderLayout.WEST);
		
		btnRemover = new JButton("Remover");
		contentPane.add(btnRemover, BorderLayout.EAST);
	}

	@Override
	public void run() {
		ch.start();
		while(true) {
			Comunicacao clientecom = com.conectarCliente();
			if(clientecom != null) {		
				ips.add(clientecom);
				ch.atualizarComs(ips);
			}
		}
	}
	
	public void atualizarClist(ArrayList<ClientInfo> clist) {
		this.clist=clist;
	}
	
}
