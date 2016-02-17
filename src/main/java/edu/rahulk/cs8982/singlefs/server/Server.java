package edu.rahulk.cs8982.singlefs.server;

import edu.rahulk.cs8982.singlefs.api.ProviderAPI;
import edu.rahulk.cs8982.singlefs.api.impl.ProviderAPIImpl;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnection;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnector;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderIndexerImpl;
import edu.rahulk.cs8982.singlefs.models.ProviderSerializerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends ProviderAPIImpl {

    static Logger logger = LoggerFactory.getLogger(Server.class);

    public Server() {

        System.out.println("initializing server");
        Indexer<Provider> indexer = new ProviderIndexerImpl();
        Serializer<Provider> serializer = new ProviderSerializerImpl();
        SimpleDBConnector simpleDBConnector = new SimpleDBConnector<>(indexer, serializer);
        SimpleDBConnection<Provider> simpleDBConnection = null;
        try {
            System.out.println("creating connection");
            simpleDBConnection = simpleDBConnector.createConnection("provider");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.setDBConnection(simpleDBConnection);
        System.out.println("completed server initialization");
    }

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            logger.info("about to configure");
            ProviderAPI stub = (ProviderAPI) UnicastRemoteObject.exportObject(obj, 0);
            logger.info("almost configured");
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("ProviderAPI", stub);
            logger.info("ready");

        } catch (Exception e) {
            logger.error("Server exception ", e);
        }
    }
}