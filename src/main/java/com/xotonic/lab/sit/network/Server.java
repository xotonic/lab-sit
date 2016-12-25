package com.xotonic.lab.sit.network;


import com.xotonic.lab.sit.vehicle.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Server extends Thread {
    private static Logger log = LogManager.getLogger(Server.class);

    private final Socket s;
    private final String clientName;
    private static Map<String, Server> clientsMap = new HashMap<>();
    private static Map<String, String> swapMap = new HashMap<>();
    private static int serverCount = 0;

    public Server( Socket s)
    {
        this.s = s;
        clientName = "Client No " + serverCount++;
        clientsMap.put(clientName, this);
        log.debug("{} connected", clientName);
        broadcastClientsList();
    }
    @Override
    public void run()
    {
        log.debug("Begin run");
        while (true)
            try {
                if (!s.isClosed())
                {
                    Protocol.QueryData q = Protocol.ListenClient(s);

                    switch (q.code)
                    {
                        case (Protocol.CLIENTS_REQUEST):
                        {
                            log.debug("Got CLIENTS_REQUEST");
                            sendClientsList();
                        } break;
                        case (Protocol.SWAP_RESPONSE):
                        {
                            log.debug("Got SWAP_RESPONSE. Forwading data");
                            Collection<Vehicle> v = (Collection<Vehicle>)q.data;
                            String requester = swapMap.get(clientName);
                            log.debug("Requester={}", requester);
                            Server otherServer = clientsMap.get(requester);
                            Protocol.swapObjectsResponse(otherServer.s, v);
                            swapMap.remove(clientName);
                        } break;
                        case (Protocol.SWAP_REQUEST):
                        {
                            log.debug("Got SWAP_REQUEST. Forwading data");
                            String destination = (String)q.data;
                            log.debug("Destination={}", destination);
                            swapMap.put(destination, clientName);
                            Server otherServer = clientsMap.get(destination);
                            assert otherServer.clientName.equals(destination);
                            Protocol.swapObjectsRequest(otherServer.s, destination);

                        } break;
                        case(Protocol.CLOSE_CONNECTION):
                        {
                            log.debug("Got CLOSE_CONNECTION");
                            Protocol.CloseConnection(s);
                        } break;
                        default: log.debug("SERVER: unknown query code"); break;
                    }
                } else {
                    log.warn("Closing server socket");
                    break;
                }
            }
            catch (Exception e) {
                log.error(e);
                break;
            }

        log.debug("Stop running");
    }

    private void sendClientsList()
    {
        try {
            Protocol.clientsListResponse(s,clientsMap
                            .keySet()
                            .stream()
                            .filter(name -> !name.equals(clientName))
                            .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastClientsList()
    {
        log.debug("Broadcasting client list");
        clientsMap.values().forEach(Server::sendClientsList);
    }

}