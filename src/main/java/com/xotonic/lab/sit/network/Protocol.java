package com.xotonic.lab.sit.network;


import com.xotonic.lab.sit.vehicle.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

class Protocol {

    private static Logger  log = LogManager.getLogger(Protocol.class);
    
    final static  int UNKNOWN = -1;

    final static  int CLOSE_CONNECTION = 0;

    final static  int CLIENTS_REQUEST = 8;
    final static  int CLIENTS_RESPONSE = 9;

    final static  int SWAP_REQUEST = 10;
    final static  int SWAP_RESPONSE = 11;

    static  class QueryData
    {
         int code;
         Object data;
    }

    static void CloseConnection(Socket s) throws IOException
    {
        s.getOutputStream().write(CLOSE_CONNECTION);
        s.getOutputStream().flush();
        s.close();
        log.debug("Connection closed");
    }

    static void clientsListRequest(Socket s) throws IOException
    {
        OutputStream os = s.getOutputStream();
        os.write(CLIENTS_REQUEST);
        os.flush();
    }

    static void clientsListResponse(Socket s, List<String> clients) throws IOException {
        OutputStream os = s.getOutputStream();
        os.write(CLIENTS_RESPONSE);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(clients);
        os.flush();
    }

    static void swapObjectsRequest(Socket s, String clientName) throws IOException {
        OutputStream os = s.getOutputStream();
        os.write(SWAP_REQUEST);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(clientName);
        os.flush();
    }

    static void swapObjectsResponse(Socket s, Collection<Vehicle> vehicles) throws IOException {
        OutputStream os = s.getOutputStream();
        os.write(SWAP_RESPONSE);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(vehicles);
        os.flush();
    }


    static QueryData ListenServer(Socket s) throws IOException, ClassNotFoundException
    {
        InputStream is = s.getInputStream();

        log.debug("CLIENT: InputStream size = " + is.available());
        int code = is.read();
        QueryData qd = new QueryData();
        qd.code = code;
        switch (code)
        {
            case(SWAP_REQUEST): {
                ObjectInputStream ois = new ObjectInputStream(is);
                qd.data = ois.readObject();
            }
                break;
            case(SWAP_RESPONSE):
            {
                ObjectInputStream ois = new ObjectInputStream(is);
                qd.data = ois.readObject();
            }
            break;
            case(CLIENTS_RESPONSE):
            {
                ObjectInputStream ois = new ObjectInputStream(is);
                qd.data = ois.readObject();
            }
            break;
            default: { qd.code = UNKNOWN; qd.data = null; } break;
        }
        return qd;
    }

    static QueryData ListenClient(Socket s) throws IOException, ClassNotFoundException
    {
        InputStream is = s.getInputStream();
        int code = is.read();
        QueryData q = new QueryData();

        q.code = code;

        switch(code) {
            case(SWAP_RESPONSE):{
                ObjectInputStream ois = new ObjectInputStream(is);
                q.data = ois.readObject();
            } break;
            case(SWAP_REQUEST) : {
                ObjectInputStream ois = new ObjectInputStream(is);
                q.data = ois.readObject();
            } break;
            case(CLOSE_CONNECTION):
            case(CLIENTS_REQUEST): break;
            default: { q.code = UNKNOWN; q.data = null; }
        }
        return q;
    }
}