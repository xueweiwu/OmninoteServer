/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import edu.cmu.xueweiw.omninote.db.DatabaseManager;
import net.rangesoftware.mengw.omninote.model.Model;
import net.rangesoftware.mengw.omninote.model.Note;
import net.rangesoftware.mengw.omninote.model.NoteLocation;
import net.rangesoftware.mengw.omninote.model.User;

public class DefaultSocketServer extends Thread implements SocketClientConstants, SocketClientInterface {

	private Socket socket;
	private DatabaseManager databaseManager;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;

	public DefaultSocketServer(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		databaseManager = DatabaseManager.getInstance();
	}

	public boolean openConnection() {
		return true;
	}

	public void handleSession() {
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());

			int cmd = 0;

			while (true) {
				try {
					cmd = objectInputStream.readInt();
				} catch (EOFException e) {
					break;
				}

				if (cmd == SIGN_IN) {
					System.out.println("Sign in...");

					String userString = objectInputStream.readUTF();
					System.out.println(userString);
					String[] emailAndPwd = userString.split(",");
					List<Model> userList = databaseManager.findByFieldName(User.class, "email", emailAndPwd[0]);
					if (userList.isEmpty()) {
						System.out.println("Sign in Fail!");
						objectOutputStream.writeObject("fail");

					} else {
						System.out.println("Sign in success!");
						objectOutputStream.writeObject("success");
					}

				} else if (cmd == SIGN_UP) {
					System.out.println("Sign up...");
					try {
						User user = (User) objectInputStream.readObject();
						databaseManager.save(user);
						System.out.println("Sign up success!");
						objectOutputStream.writeObject("success");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (cmd == INSERT_NOTE) {
					System.out.println("Insert note...");
					try {
						Note note = (Note) objectInputStream.readObject();
						databaseManager.save(note);
						System.out.println("Insert note success!");
						objectOutputStream.writeObject(note);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (cmd == GET_NEARBY) {
					System.out.println("Get nearby...");
					try {
						NoteLocation location = (NoteLocation) objectInputStream.readObject();
						int radius_km = 1000;
						List<Model> userList = databaseManager.findNoteByRange(location.getLongitude(),
								location.getLatitude(), radius_km);
						if (userList.isEmpty()) {

						} else {
							// see whether it's match
						}
						System.out.println("upload success!");
						objectOutputStream.writeObject("success");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				objectOutputStream.flush();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void closeSession() {
		try {
			objectInputStream.close();
			objectOutputStream.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		if (openConnection()) {
			handleSession();
			// closeSession();
		}
	}
}
