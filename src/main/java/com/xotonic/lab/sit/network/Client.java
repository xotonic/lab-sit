package com.xotonic.lab.sit.network;


import com.xotonic.lab.sit.vehicle.SimpleHabitat;
import com.xotonic.lab.sit.vehicle.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.List;

public class Client extends Thread {
    private static Logger log = LogManager.getLogger(Client.class);
    Socket socket;
    private boolean stoped = false;
    public SimpleHabitat habitat;
    private ServerListener listener;

    private void Stop() { stoped = true;}
    public Client(SimpleHabitat s, String ip, int port, ServerListener listener) throws IOException {
        habitat = s;
        this.listener = listener;

        socket = new Socket(ip, port);
            System.out.println("### CLIENT: started - " + ip + ':' + port);


        setDaemon(true);
        setPriority(NORM_PRIORITY);
    }

    @Override
    public void run()
    {
        while (!stoped)
            try {
                if (!socket.isClosed())
                {
                    Protocol.QueryData q = Protocol.ListenServer(socket);

                    switch(q.code)
                    {
                        case(Protocol.CLIENTS_RESPONSE):
                        {
                            log.debug("Got CLIENTS_RESPONSE");
                            List<String> clients = (List<String>)q.data;
                            listener.clientsListUpdated(clients);
                            clients.forEach(log::debug);

                        } break;
                        case(Protocol.SWAP_RESPONSE):
                        {
                            log.debug("Got SWAP_RESPONSE");
                            Collection<Vehicle> v = (Collection<Vehicle>)q.data;
                            listener.vehiclesUpdated(v);

                        } break;
                        case(Protocol.SWAP_REQUEST):
                        {
                            log.debug("Got SWAP_REQUEST");
                            Protocol.SwapRequestData data = (Protocol.SwapRequestData) q.data;

                            synchronized (habitat.getVehicles()) {
                                Protocol.swapObjectsResponse(socket, habitat.getVehicles());
                                Collection<Vehicle> v = data.vehicles;
                                listener.vehiclesUpdated(v);
                            }
                        } break;
                        case(Protocol.CLOSE_CONNECTION):
                        {
                            log.debug("Got CLOSE_CONNECTION");
                            Protocol.CloseConnection(socket);
                        } break;
                        default: log.debug("CLIENT: unknown command"); break;
                    }
                } else break;
            } catch (Exception e) {
                log.error(e);
                Stop();
            }

        closeConnection();
        log.debug("CLIENT: stop running");
    }

    public void requestClientsList()
    {
        try {
            Protocol.clientsListRequest(socket);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void closeConnection()
    {
        try {
            Protocol.CloseConnection(socket);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void swapObjects(String name) {
        try {
            synchronized (habitat.getVehicles()) {
                Protocol.swapObjectsRequest(socket, name, habitat.getVehicles());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    public interface ServerListener
    {
        void clientsListUpdated(List<String> clients);
        void vehiclesUpdated(Collection<Vehicle> v);
    }
}
