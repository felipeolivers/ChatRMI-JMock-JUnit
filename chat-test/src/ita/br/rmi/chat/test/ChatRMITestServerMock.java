package ita.br.rmi.chat.test;

import static org.junit.Assert.assertTrue;
import ita.br.rmi.chat.common.IMessage;
import ita.br.rmi.chat.common.INotify;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChatRMITestServerMock {
	
	static Mockery context = new Mockery();
	
	// IMessage
	static IMessage chatServer;
	static IMessage stubServer;
	static IMessage server = context.mock(IMessage.class);
	
	// INotify
	static INotify stubNotify;
	static INotify notify = context.mock(INotify.class);
	
	static Registry r;
	
	@BeforeClass
	public static void beforeClass() throws AlreadyBoundException, IOException, NotBoundException {
		// setProperty CodeBase and Policy
		System.setProperty("java.rmi.server.codebase", "file:C:\\Java\\Workspace\\ChatMock\\chat-common\\bin\\");
		System.setProperty("java.security.policy", "file:C:\\Java\\Workspace\\ChatMock\\chat-test\\security.policy");
		// Start RMIRegistry
		Runtime.getRuntime().exec("rmiregistry");
		
		// Stubs
		stubServer = (IMessage)UnicastRemoteObject.exportObject(server, 0);
		stubNotify = (INotify)UnicastRemoteObject.exportObject(notify, 0);
		
		r = LocateRegistry.getRegistry();
		r.rebind("itachat", stubServer);	
		
		// Lookup Object
		// -->
		System.setSecurityManager (new RMISecurityManager());
		Remote remoteObject = Naming.lookup("itachat");
		chatServer = (IMessage)remoteObject ;
		
	}  
    
    @Test
	public void testServerJoinMessage() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	one(server).join(with(any(INotify.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
               
        // Assert
        assertTrue(chatServer.join(stubNotify, "Felipe"));
        context.assertIsSatisfied();
	}
    
    @Test
	public void testServerTalk() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	allowing(server).talk(with(any(String.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Assert
        assertTrue(chatServer.talk("Felipe", "Oi"));
        assertTrue(chatServer.talk("Felipe", "Tudo Bem"));
        context.assertIsSatisfied();
	}
    
    @Test
	public void testServerLeave() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	one(server).leave(with(any(INotify.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Assert
        assertTrue(chatServer.leave(stubNotify, "Felipe"));
        context.assertIsSatisfied();
	}
    
    @AfterClass
    public static void afterClass() throws AccessException, RemoteException, NotBoundException {
    	// Unbind Object
    	// -->
    	r.unbind("itachat");
    }
    
}
