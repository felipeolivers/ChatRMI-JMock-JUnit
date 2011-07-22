package ita.br.rmi.chat.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMessage extends Remote  {
	public boolean join(INotify notify, String name) throws RemoteException;
	public boolean talk(String name, String message) throws RemoteException;
	public boolean leave(INotify notify, String name) throws RemoteException;
}
