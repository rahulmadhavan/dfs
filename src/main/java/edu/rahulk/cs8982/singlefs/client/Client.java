package edu.rahulk.cs8982.singlefs.client;

import edu.rahulk.cs8982.singlefs.api.ProviderAPI;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderResponse;
import edu.rahulk.cs8982.singlefs.task.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class Client {
    static Logger logger = LoggerFactory.getLogger(Client.class);

    private Client() {}

    public static void main(String[] args) {
        CommandLineHelper commandLineHelper = null;
        try {
            commandLineHelper = new CommandLineHelper();
            commandLineHelper.parseArgs(args);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}


//            //Provider provider = stub.getProviderDetails("1649244054");
//            ProviderResponse providerResponse = stub.getProvidersByProcedureCode("99205");
////            List<Provider> providerList = stub.getProvidersByZipCode("11559");