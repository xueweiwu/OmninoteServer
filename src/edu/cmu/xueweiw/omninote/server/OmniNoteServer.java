/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 *
 */
public class OmniNoteServer {
	private final int PORT = 4444;

	private ServerSocket serverSocket;
	private Thread serverThread;
	private boolean running;

	public OmniNoteServer() {
		// TODO Auto-generated constructor stub
		running = false;
	}

	public boolean start() {
		running = true;
		serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					serverSocket = new ServerSocket(PORT);
					System.out.println("Server start!");
					while (running) {
						System.out.println("Wait for the client!");
						Socket socket = serverSocket.accept();
						DefaultSocketServer defaultSocketServer = new DefaultSocketServer(socket);
						defaultSocketServer.start();
					}
				} catch (IOException e) {
					System.err.println("Could not listen on port: " + PORT + ".");
					System.exit(1);
				}

			}
		});

		serverThread.start();
		return true;
	}

	public boolean stop() {
		running = false;
		return true;
	}

}
