package ita.br.rmi.chat.server;

import ita.br.rmi.chat.common.IMessage;
import ita.br.rmi.chat.common.INotify;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;

public class ChatServer implements IMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Globals
	private ChatServerList serverList = new ChatServerList();
	
	public ChatServer() throws RemoteException { }

	public boolean join(INotify notify, String name) throws RemoteException {
		boolean ret = false;
		try {
	        // Add User
	    	serverList.add(notify);
	        serverList.incCounter();   
	        for (Iterator<?> i = serverList.getCollection().iterator(); i.hasNext();) {   
	        	INotify client = (INotify)i.next();
	        	if(client.getUserName(name) != name) {
		        	ret = client.joinMessage(name) != "";
		        	if(!ret)
		        		break;
	        	}
	        }   
	        serverList.decCounter();
	        return ret;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean leave(INotify notify, String name) throws RemoteException {
		boolean ret = false;
		try {
	    	serverList.remove(notify);   
	        serverList.incCounter();   
	        for (Iterator<?> i = serverList.getCollection().iterator(); i.hasNext();) {   
	        	INotify client = (INotify)i.next();   
	        	if(client.getUserName(name) != name) {
		            ret = client.exitMessage(name) != "";
		            if(!ret)
		            	break;
	        	}
	        }   
	        serverList.decCounter();   
	        return ret;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean talk(String name, String message) throws RemoteException {
		boolean ret = false;
		try {
			serverList.incCounter();   
	        for (Iterator<?> i = serverList.getCollection().iterator(); i.hasNext();) {   
	        	INotify client = (INotify)i.next();   
	        	if(client.getUserName(name) != name) {
		        	ret = client.sendMessage(name, message) != ""; 
		            if(!ret)
		            	break;
	        	}
	        }   
	        serverList.decCounter();
	        
	        return ret;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void main(String[] args) throws IOException {   
		try {
			System.setProperty("java.rmi.server.codebase", "file:C:\\Java\\Workspace\\ChatMock\\chat-common\\bin\\");
			Runtime.getRuntime().exec("rmiregistry");
			
			IMessage server = new ChatServer();
					
			// Stubs in runtime
			IMessage stub = (IMessage)UnicastRemoteObject.exportObject(server, 0);
			
			Registry r = LocateRegistry.getRegistry();

			r.rebind("itachat", stub);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
