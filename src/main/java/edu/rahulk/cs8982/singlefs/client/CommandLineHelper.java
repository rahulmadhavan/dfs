package edu.rahulk.cs8982.singlefs.client;

import edu.rahulk.cs8982.singlefs.api.ProviderAPI;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderResponse;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Created by rahulk on 2/16/16.
 */
public class CommandLineHelper {

    static Logger logger = LoggerFactory.getLogger(CommandLineHelper.class);

    private ProviderAPI providerAPI;

    public CommandLineHelper() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(null);
        providerAPI = (ProviderAPI) registry.lookup("ProviderAPI");
    }

    public Options getOptions() {
        Options options = new Options();
        options.addOption(Option.builder("gpd")
                .longOpt("get-provider-details")
                .desc("returns data associated with that provider id (names, address, city, zip, state, gender)")
                .hasArg()
                .argName("npi")
                .build());

        options.addOption(Option.builder("gpz")
                .longOpt("get-providers-by-zip-code")
                .desc("returns provider id, name, address, city for all providers in the requested zip code")
                .hasArg()
                .argName("nppes_provider_zip")
                .build());

        options.addOption(Option.builder("gppc")
                .longOpt("get-providers-by-procedure-code")
                .desc("provider id and number of procedures (line_srvc_cnt) for all providers" +
                        " who perform the specified procedure.")
                .hasArg()
                .argName("nppes_provider_zip")
                .build());

        return options;
    }

    public void parseArgs(String[] args) throws RemoteException {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(getOptions(), args);
            if (commandLine.hasOption("gpd")) {
                String optionValue = commandLine.getOptionValue("gpd");
                long startTime = System.currentTimeMillis();
                ProviderResponse providerResponse = providerAPI.getProviderDetails(optionValue.trim());
                long endTime = System.currentTimeMillis();
                printResponse1(providerResponse, endTime - startTime);

            } else if(commandLine.hasOption("gpz")) {
                String optionValue = commandLine.getOptionValue("gpz");
                long startTime = System.currentTimeMillis();
                ProviderResponse providerResponse = providerAPI.getProvidersByZipCode(optionValue.trim());
                long endTime = System.currentTimeMillis();
                printResponse2(providerResponse, endTime - startTime);
            } else if(commandLine.hasOption("gppc")) {
                String optionValue = commandLine.getOptionValue("gppc");
                long startTime = System.currentTimeMillis();
                ProviderResponse providerResponse = providerAPI.getProvidersByProcedureCode(optionValue.trim());
                long endTime = System.currentTimeMillis();
                printResponse3(providerResponse, endTime - startTime);
            } else {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("test", getOptions(), true);
            }
        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("test", getOptions(), true);
            logger.error("command line parse exception", e);
        }

    }

    void printResponse1(ProviderResponse providerResponse, long responseTime) {
        int count = 0;
        System.out.println("id, name, street1, street2, city, zip, state, gender\n");
        for(Provider provider : providerResponse.getProviders()) {
            System.out.println(String.format("%s, %s, %s, %s, %s, %s, %s, %s",
                    provider.getProviderId(),
                    provider.getName(),
                    provider.getStreet1(),
                    provider.getStreet2(),
                    provider.getCity(),
                    provider.getZipCode(),
                    provider.getState(),
                    provider.getGender()));
            count++;
        }

        System.out.println("count : " + count);
        System.out.println("Server request process time :" + providerResponse.getServerExecutionTime()+ "ms");
        System.out.println("Total request process time :" + responseTime+ "ms");
    }

    void printResponse2(ProviderResponse providerResponse, long responseTime) {
        int count = 0;
        System.out.println("id, name, street1, street2, city\n");
        for(Provider provider : providerResponse.getProviders()) {
            System.out.println(String.format("%s, %s, %s, %s, %s",
                    provider.getProviderId(),
                    provider.getName(),
                    provider.getStreet1(),
                    provider.getStreet2(),
                    provider.getCity()));
            count++;
        }
        System.out.println("count : " + count);
        System.out.println("Server request process time : " + providerResponse.getServerExecutionTime() + "ms");
        System.out.println("Total request process time : " + responseTime+ "ms");
    }

    void printResponse3(ProviderResponse providerResponse, long responseTime) {
        int count = 0;
        System.out.println("id, line_srvc_cnt\n");
        for(Provider provider : providerResponse.getProviders()) {
            System.out.println(String.format("%s, %s",
                    provider.getProviderId(),
                    provider.getNosOfProcedures()));
            count++;
        }
        System.out.println("count : " + count);
        System.out.println("Server request process time : " + providerResponse.getServerExecutionTime() + "ms");
        System.out.println("Total request process time : " + responseTime + "ms");
    }



}
