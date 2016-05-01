/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.driver;

import edu.cmu.xueweiw.omninote.server.OmniNoteServer;

/**
 * 
 *
 */
public class DriverServer {

	public static void main(String[] args) {
		OmniNoteServer server = new OmniNoteServer();
		server.start();
	}

}
