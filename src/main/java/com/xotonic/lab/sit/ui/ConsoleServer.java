package com.xotonic.lab.sit.ui;

import com.xotonic.lab.sit.network.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConsoleServer {

    private static Logger log = LogManager.getLogger(Server.class);

    public static void main(String... args)
    {

        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(8888);

        while (true) {
                log.debug("Listening for connection");
                socket = serverSocket.accept();

            // new threa for a client
            new Server(socket).start();
        }
        } catch (IOException e) {
            log.error(e);
        }
    }

}
