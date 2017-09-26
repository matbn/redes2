package cliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;


public class ClientWindow {

	private JFrame frame;
	private JTextField textField;
	private Client c;
	private JTextArea chat;
	private JPanel listaUsuarios;
	ArrayList<ClientInfo> cilist = new ArrayList<ClientInfo>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
		frame.setVisible(true);
	}
	public ClientWindow(Client c) {
		this.c= c;
		initialize();
		frame.setVisible(true);
		cilist.add(c.getClientInfo());
		atualizarListaUsuarios();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//c = new Client();
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane().getLayout();
		borderLayout.setVgap(1);
		borderLayout.setHgap(1);
		frame.setBounds(100, 100, 600, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(51, 255, 204));
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel nick = new JLabel(c.getNick());
		panel.add(nick);
		
		JLabel status = new JLabel("");
		status.setFont(new Font("Symbol", Font.PLAIN, 11));
		status.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				c.changeStatus();
				setStatusIcon(c.getClientInfo(),status);
				atualizarListaUsuarios();
			}
		});
		status.setIcon(new ImageIcon("images/circuloverde.png"));
		panel.add(status);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(null);
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER )
					if(!textField.getText().equals("")) {
						c.enviarMensagem(c.getNick()+": "+ textField.getText());
						textField.setText("");
					}
			}
		});
		textField.setToolTipText("Insira sua mensagem");
		textField.setColumns(10);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!textField.getText().equals("")) {
					c.enviarMensagem(c.getNick()+": "+ textField.getText());
					textField.setText("");
				}
			}
		});
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(3)
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
					.addGap(2)
					.addComponent(btnEnviar, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(3))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnEnviar))
		);
		panel_3.setLayout(gl_panel_3);
		
		chat = new JTextArea();
		chat.setText("Bem vindo ao bate-papo!");
		chat.setBackground(new Color(204, 255, 255));
		chat.setRows(20);
		chat.setEditable(false);
		chat.setLineWrap(true);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 255, 255));
		panel_2.setForeground(Color.BLUE);
		
		JScrollPane scrollPane = new JScrollPane(chat);
		scrollPane.setBounds(1, 1, 426, 355);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		panel_2.setLayout(gl_panel_2);
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.add(scrollPane);
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 255, 255));
		frame.getContentPane().add(panel_4, BorderLayout.EAST);
		
		listaUsuarios = new JPanel();
		listaUsuarios.setBackground(Color.CYAN);
		listaUsuarios.setBorder(null);
		
		JLabel lblUsurios = new JLabel("Usu\u00E1rios");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(listaUsuarios, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(54)
					.addComponent(lblUsurios, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(51))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUsurios)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listaUsuarios, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
					.addGap(3))
		);
		listaUsuarios.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		listaUsuarios.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Status");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		listaUsuarios.add(lblNewLabel_1);
		panel_4.setLayout(gl_panel_4);
	}
	public JTextField getTextField() {
		return textField;
	}
	
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	
	public void atualizarChat(String msg) {
		if(chat.getText().equals("Bem vindo ao bate-papo!")) {
			chat.setText(msg);	
		}else {
			chat.setText(chat.getText()+"\n"+msg);
		}
	}
	public void atualizarListaUsuarios() {
		listaUsuarios.removeAll();
		for(ClientInfo client : cilist) {
			listaUsuarios.add(new JLabel(client.getNick()));
			JLabel cstatus = new JLabel("");
			setStatusIcon(client,cstatus);
			listaUsuarios.add(cstatus);
		}
		listaUsuarios.revalidate();
		listaUsuarios.repaint();
	}
	public void setStatusIcon(ClientInfo client, JLabel is) {
		int s = client.getStatus();
		if(s==1) {
			is.setIcon(new ImageIcon("images/circulovermelho.png"));
		}else if(s==2) {
			is.setIcon(new ImageIcon("images/circuloamarelo.png"));
		}else {
			is.setIcon(new ImageIcon("images/circuloverde.png"));
		}
	}
	public void atualizarClientInfoStatus(String nick, int status) {
		for(ClientInfo ci : cilist) {
			if(ci.getNick()==nick) {
				ci.setStatus(status);
				break;
			}
		}
	}
	protected void adicionarCliente(String msg) {
		String[] msgp = msg.split("\"");
		ClientInfo aux = new ClientInfo(msgp[1],Integer.parseInt(msgp[2]));
		cilist.add(aux);
		atualizarListaUsuarios();
	}
	
}
