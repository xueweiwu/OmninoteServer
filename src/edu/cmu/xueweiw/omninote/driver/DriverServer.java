/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.driver;

import edu.cmu.xueweiw.omninote.db.DatabaseManager;
import edu.cmu.xueweiw.omninote.server.OmniNoteServer;

/**
 * 
 *
 */
public class DriverServer {

	public static void main(String[] args) {
		DatabaseManager databaseManager = DatabaseManager.getInstance();
		databaseManager.open();
		
		OmniNoteServer server = new OmniNoteServer();
		server.start();
	}

}
