package ita.br.rmi.chat.client;

import ita.br.rmi.chat.common.IMessage;
import ita.br.rmi.chat.common.INotify;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class ChatClient extends javax.swing.JFrame implements IMessage {

    /**
	 * VERSION 1.0 
	 */
	private static final long serialVersionUID = 1L;
	
	
	// Variables declaration - do not modify
    private javax.swing.JButton jButtonEntrar;
    private javax.swing.JLabel jLabelITA;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JMenuBar jMenuBarITAChat;
    private javax.swing.JMenu jMenuSair;
    private javax.swing.JPanel jPanelMessage;
    private javax.swing.JPanel jPanelUsuario;
    private javax.swing.JScrollPane jScrollPaneChatMessages;
    private javax.swing.JScrollPane jScrollPaneMessage;
    private javax.swing.JTextArea jTextChatMessages;
    private javax.swing.JTextField jTextFieldNickName;
    private javax.swing.JTextArea jTextMessage;
    // End of variables declaration
    
    private static String name = null;
    
    // Globals Chat
    INotify displayChat;   
    IMessage chatServer; 
	
    // Constructor
    public ChatClient() {
    	
        initComponents();
        
        try {   
    		System.setProperty("java.rmi.server.codebase", "file:C:\\Java\\Workspace\\ChatMock\\chat-common\\bin\\");
    		System.setProperty("java.security.policy", "file:C:\\Java\\Workspace\\ChatMock\\chat-client\\security.policy");
        	
            Remote remoteObject = Naming.lookup("itachat");   
   
            if (remoteObject instanceof IMessage) {
                chatServer = (IMessage)remoteObject;
            	displayChat = new NotifyImpl(jTextChatMessages);

            } else {   
                System.out.println("Falha ao conectar no ITAChat.");   
               System.exit(0);   
            }   
        }   
        catch(Exception e){      
            e.printStackTrace();
        };
        
        // Leave
        this.addWindowListener(new WindowAdapter() {   
            public void windowClosing(WindowEvent e) {   
              try {   
                  if (name != null) {   
                      chatServer.leave(displayChat, name);   
                  }   
              }   
              catch (Exception ex) {   
                  jTextChatMessages.append("Falha ao sair.");   
              }   
              System.exit(0);   
            }   
          });
        
    }
    
    private void initComponents() {

    	jLabelITA = new javax.swing.JLabel();
        jPanelUsuario = new javax.swing.JPanel();
        jButtonEntrar = new javax.swing.JButton();
        jTextFieldNickName = new javax.swing.JTextField();
        jLabelNome = new javax.swing.JLabel();
        jPanelMessage = new javax.swing.JPanel();
        jScrollPaneChatMessages = new javax.swing.JScrollPane();
        jTextChatMessages = new javax.swing.JTextArea();
        jScrollPaneMessage = new javax.swing.JScrollPane();
        jTextMessage = new javax.swing.JTextArea();
        jMenuBarITAChat = new javax.swing.JMenuBar();
        jMenuSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelITA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelITA.setText("INSTITUTO TECNOLÓGICO DE AERONÁUTICA");

        jPanelUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder("Entrar na Sala"));

        jButtonEntrar.setText("Entrar");
        jButtonEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	try {
					jButtonEntrarMouseClicked(evt);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
            }
        });

        jLabelNome.setText("Nick Name:");

        javax.swing.GroupLayout jPanelUsuarioLayout = new javax.swing.GroupLayout(jPanelUsuario);
        jPanelUsuario.setLayout(jPanelUsuarioLayout);
        jPanelUsuarioLayout.setHorizontalGroup(
            jPanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuarioLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabelNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldNickName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonEntrar)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanelUsuarioLayout.setVerticalGroup(
            jPanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEntrar)
                    .addComponent(jTextFieldNickName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNome))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelMessage.setBorder(javax.swing.BorderFactory.createTitledBorder("Mensagens"));

        jTextChatMessages.setColumns(20);
        jTextChatMessages.setRows(5);
        jTextChatMessages.setEnabled(false);
        jScrollPaneChatMessages.setViewportView(jTextChatMessages);

        jTextMessage.setColumns(20);
        jTextMessage.setRows(5);
        jTextMessage.setEnabled(false);
        jTextMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextMessageKeyPressed(evt);
            }
        });
        jScrollPaneMessage.setViewportView(jTextMessage);

        javax.swing.GroupLayout jPanelMessageLayout = new javax.swing.GroupLayout(jPanelMessage);
        jPanelMessage.setLayout(jPanelMessageLayout);
        jPanelMessageLayout.setHorizontalGroup(
            jPanelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMessageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPaneMessage, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                    .addComponent(jScrollPaneChatMessages, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelMessageLayout.setVerticalGroup(
            jPanelMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMessageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneChatMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuSair.setText("Sair");
        jMenuSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuSairMouseClicked(evt);
            }
        });
        jMenuBarITAChat.add(jMenuSair);

        setJMenuBar(jMenuBarITAChat);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabelITA))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelITA)
                .addGap(18, 18, 18)
                .addComponent(jPanelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanelMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    // Leave
    private void jMenuSairMouseClicked(java.awt.event.MouseEvent evt) {
    	try {   
            if (name != null) {   
                chatServer.leave(displayChat, name);   
            }   
        } catch (Exception ex) {   
            jTextChatMessages.append("Falha ao sair.");   
        }   
        System.exit(0);
    }
	
    // Join
    @SuppressWarnings("deprecation")
	private void jButtonEntrarMouseClicked(java.awt.event.MouseEvent evt) throws RemoteException {
        if(jTextFieldNickName.getText() == null ? "" != null : !jTextFieldNickName.getText().equals("")) {
            jTextFieldNickName.enable(false);
            jTextMessage.enable(true);
            jButtonEntrar.setVisible(false);
     	   name = jTextFieldNickName.getText();   
           chatServer.join(displayChat, name);
        }
    }

    // Enter Event - Send Message
    private void jTextMessageKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyChar() == '\n'){
        	try {
        		chatServer.talk(name, jTextMessage.getText());
        		this.jTextMessage.setText("");
        	} catch (Exception ie) {   
                jTextChatMessages.append("Falha ao enviar mensagem.");   
            }
        }
    }
    
    // Run Chat Client
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }

	@Override
	public boolean join(INotify notify, String name) throws RemoteException {
		displayChat.joinMessage(name);
		return chatServer.join(notify, name);
		
	}

	@Override
	public boolean leave(INotify notify, String name) throws RemoteException {
		displayChat.exitMessage(name);
		return chatServer.leave(notify, name);
	}

	@Override
	public boolean talk(String name, String message) throws RemoteException {
		displayChat.sendMessage(name, message);
		return chatServer.talk(name, message);
	}
	
	public void setNotify(INotify notify) {
		displayChat = notify;
	}
    
}