package edu.rahulk.cs8982.singlefs.api;

import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by rahulk on 2/9/16.
 */
public interface ProviderAPI extends Remote {

    void putProviders(List<Provider> providers) throws RemoteException;

    void putProvider(Provider provider) throws RemoteException;

    ProviderResponse getProviderDetails(String providerId) throws RemoteException;

    ProviderResponse getProvidersByZipCode(String zipcode) throws RemoteException;

    ProviderResponse getProvidersByProcedureCode(String procedureCode) throws RemoteException;

}
