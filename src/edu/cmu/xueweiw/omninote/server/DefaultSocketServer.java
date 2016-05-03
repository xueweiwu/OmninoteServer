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
						objectOutputStream.writeObject(FAIL + ": email " + emailAndPwd[0] + " cannot be found");
					} else {
						User user = (User) userList.get(0);
						if (!user.getPassword().equals(emailAndPwd[1])) {
							objectOutputStream.writeObject(FAIL + ": password error!");
						} else {
							System.out.println("Sign in success! User ID:" + user.getId().toString());
							objectOutputStream.writeObject(user.getId().toString());
						}
					}

				} else if (cmd == SIGN_UP) {
					System.out.println("Sign up...");
					String userString = objectInputStream.readUTF();
					String[] fields = userString.split(",");
					String userName = fields[2];
					String email = fields[0];
					String pwd = fields[1];
					User user = new User(userName, email, pwd);
					List<Model> userList = databaseManager.findByFieldName(User.class, "email", email);
					List<Model> userListSameUsername = databaseManager.findByFieldName(User.class, "username",
							userName);
					if (!userList.isEmpty()) {
						System.out.println("Sign up Fail! Email " + email + "has been used!");
						objectOutputStream.writeObject(FAIL);
					} else if (!userListSameUsername.isEmpty()) {
						System.out.println("Sign up Fail! User Name " + userName + "has been used!");
						objectOutputStream.writeObject(FAIL);
					} else {
						boolean isSuccessful = databaseManager.save(user);
						if (isSuccessful) {
							System.out.println("Sign up success! User ID:" + user.getId().toString());
							objectOutputStream.writeObject(user.getId().toString());
						} else {
							System.out.println("Sign up Fail!");
							objectOutputStream.writeObject(FAIL);
						}
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
