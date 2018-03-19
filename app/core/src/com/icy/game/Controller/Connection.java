package com.icy.game.Controller;

import io.socket.client.Socket;
import io.socket.client.IO;

/**
 * Created by havard on 13.03.18.
 */

public class Connection {

    private Socket socket;

    public Connection() {
        try {
            socket = IO.socket("http://localhost:8080");
        } catch (Exception e) {
            System.out.println("Failed to connect..Error: " + e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
