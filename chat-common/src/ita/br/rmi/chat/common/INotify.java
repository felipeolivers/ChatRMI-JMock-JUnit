package ita.br.rmi.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface INotify extends Remote {
	public String joinMessage(String name) throws RemoteException;   
    public String sendMessage(String name, String message) throws RemoteException;   
    public String exitMessage(String name) throws RemoteException;
    public void setUserName(String name) throws RemoteException;
    public String getUserName(String name) throws RemoteException;
}