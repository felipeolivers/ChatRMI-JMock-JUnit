package ita.br.rmi.chat.client;

import ita.br.rmi.chat.common.INotify;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotifyImpl extends UnicastRemoteObject implements INotify {

	/**
	 *  Version 1.0
	 */
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JTextArea textArea;   
    private String name;   
    
    // Constructor
    public NotifyImpl(javax.swing.JTextArea ta) throws RemoteException { textArea = ta; }

	@Override
	public String exitMessage(String name) throws RemoteException {
		String message = "";
		try {   
			message = name + " saiu da sala.";
			textArea.append(message + "\n");
			return message;
		} catch(Exception e) {      
			return message;   
		}   
	}

	@Override
	public String joinMessage(String name) throws RemoteException {
		String message = "";
		try {   
			message = name + " entrou na sala.";
			textArea.append(message + "\n"); 
			return message;
		} catch(Exception e) {
			textArea.append("A mensagem de " + name + " não foi entregue.\n");
			return message; 
		}   
	}

	@Override
	public String sendMessage(String name, String message) throws RemoteException {
		String messageReturn = "";
		try {   
			messageReturn = name + " diz: " + message;
			textArea.append(messageReturn + "\n");
			return messageReturn;
		} catch(Exception e) {      
			return messageReturn;   
		}   
	}

	// Getters and Setters
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getUserName(String name) throws RemoteException {
		return name;
	}

	@Override
	public void setUserName(String name) throws RemoteException {
		this.name = name;
	}

}
