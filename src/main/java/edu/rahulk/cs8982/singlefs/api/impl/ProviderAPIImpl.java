package edu.rahulk.cs8982.singlefs.api.impl;

import edu.rahulk.cs8982.singlefs.api.ProviderAPI;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnection;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderResponse;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by rahulk on 2/11/16.
 */
public class ProviderAPIImpl implements ProviderAPI {

    SimpleDBConnection<Provider> dbConnection;

    public ProviderAPIImpl() {

    }

    public void setDBConnection(SimpleDBConnection<Provider> dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void putProvider(Provider provider) throws RemoteException {
        dbConnection.getSimpleDBQueryAPI().write(provider);
    }

    @Override
    public void putProviders(List<Provider> providers) throws RemoteException {
        for(Provider provider : providers) {
            dbConnection.getSimpleDBQueryAPI().write(provider);
        }
    }

    @Override
    public ProviderResponse getProviderDetails(String providerId) throws RemoteException {
        long startTime = System.currentTimeMillis();
        List<Provider> providers = dbConnection.getSimpleDBQueryAPI().read("providerId", providerId);
        long endTime = System.currentTimeMillis();
        return new ProviderResponse(providers, endTime - startTime);
    }

    @Override
    public ProviderResponse getProvidersByZipCode(String zipcode) throws RemoteException {
        long startTime = System.currentTimeMillis();
        List<Provider> providers = dbConnection.getSimpleDBQueryAPI().read("zipCode", zipcode);
        long endTime = System.currentTimeMillis();
        return new ProviderResponse(providers, endTime - startTime);
    }

    @Override
    public ProviderResponse getProvidersByProcedureCode(String procedureCode) throws RemoteException {
        long startTime = System.currentTimeMillis();
        List<Provider> providers = dbConnection.getSimpleDBQueryAPI().read("procedureCode", procedureCode);
        long endTime = System.currentTimeMillis();
        return new ProviderResponse(providers, endTime - startTime);
    }



}
