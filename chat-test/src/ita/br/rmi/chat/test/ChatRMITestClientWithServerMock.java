package ita.br.rmi.chat.test;

import static org.junit.Assert.assertTrue;
import ita.br.rmi.chat.client.ChatClient;
import ita.br.rmi.chat.common.IMessage;
import ita.br.rmi.chat.common.INotify;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class ChatRMITestClientWithServerMock {

	static Mockery context = new Mockery();
	
	// IMessage Server
	static IMessage stubServer;
	static IMessage server = context.mock(IMessage.class);

	// IMessage Client
	static ChatClient chatClient1;
	static ChatClient chatClient2;
	
	// INotify
	static INotify stubNotifyClient;
	static INotify notifyClient = context.mock(INotify.class);
	
	static Registry r;
	
    
	@BeforeClass
	public static void beforeClass() throws AlreadyBoundException, IOException, NotBoundException {
		// Load Server RMI
		// -->
		// setProperty CodeBase
		System.setProperty("java.rmi.server.codebase", "file:C:\\Java\\Workspace\\ChatMock\\chat-common\\bin\\");
		System.setProperty("java.security.policy", "file:C:\\Java\\Workspace\\ChatMock\\chat-test\\security.policy");
		// Start RMIRegistry
		Runtime.getRuntime().exec("rmiregistry");
		
		// Stubs
		stubServer = (IMessage)UnicastRemoteObject.exportObject(server, 0);
		stubNotifyClient = (INotify)UnicastRemoteObject.exportObject(notifyClient, 0);
        
		r = LocateRegistry.getRegistry();
		r.rebind("itachat", stubServer);
		
		// Lookup Object
		// -->
		chatClient1 = new ChatClient();
		chatClient2 = new ChatClient();
	}  
    
    @Test
	public void testClientJoinInServerMock() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	one(notifyClient).joinMessage(with(any(String.class)));
        	one(server).join(with(any(INotify.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Update ChatClient with Notify Mock
        chatClient1.setNotify(notifyClient);
        
        // Assert
        assertTrue(chatClient1.join(stubNotifyClient, "Felipe"));
        context.assertIsSatisfied();
	}
    
    @Test
	public void testClientTalkInServerMock() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	allowing(notifyClient).sendMessage(with(any(String.class)), with(any(String.class)));
        	will(returnValue("Felipe diz: Oi"));
        	one(server).talk(with(any(String.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Update ChatClient with Notify Mock
        chatClient1.setNotify(notifyClient);
        
        // Assert
        assertTrue(chatClient1.talk("Felipe", "Oi"));
        Assert.assertEquals("Felipe diz: Oi", notifyClient.sendMessage("Felipe", "Oi"));
        context.assertIsSatisfied();
	}
    
    @Test
	public void testClientLeaveInServerMock() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	one(notifyClient).exitMessage(with(any(String.class)));
        	one(server).leave(with(any(INotify.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Update ChatClient with Notify Mock
        chatClient1.setNotify(notifyClient);
        
        // Assert
        assertTrue(chatClient1.leave(notifyClient, "Felipe"));
        context.assertIsSatisfied();
	}
    
    @Test
	public void testClientLeaveInServerMockAndPrintMessage() throws RemoteException {
    	// Expectation
        context.checking(new Expectations() {{
        	one(notifyClient).exitMessage(with(any(String.class)));
        	one(server).leave(with(any(INotify.class)), with(any(String.class)));
        	will(returnValue(true));
        }});
        
        // Update ChatClient with Notify Mock
        chatClient1.setNotify(notifyClient);
        
        // Assert
        assertTrue(chatClient1.leave(notifyClient, "Felipe"));
        context.assertIsSatisfied();
	}
    
    @AfterClass
    public static void afterClass() throws AccessException, RemoteException, NotBoundException, MalformedURLException {
    	// Unbind Object
    	// -->
    	r.unbind("itachat");
    }
    
}
