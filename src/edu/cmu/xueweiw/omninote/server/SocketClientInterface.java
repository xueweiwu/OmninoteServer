/**
 * @author Xuewei Wu
 */
package edu.cmu.xueweiw.omninote.server;

/**
 * 
 *
 */
public interface SocketClientInterface {

    boolean openConnection();
    void handleSession();
    void closeSession();
}

