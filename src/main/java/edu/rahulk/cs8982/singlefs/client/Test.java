package edu.rahulk.cs8982.singlefs.client;

import edu.rahulk.cs8982.singlefs.db.*;
import edu.rahulk.cs8982.singlefs.db.impl.FileDBAPIImpl;
import edu.rahulk.cs8982.singlefs.db.impl.PrimaryIndexImpl;
import edu.rahulk.cs8982.singlefs.db.impl.SecondaryIndexImpl;
import edu.rahulk.cs8982.singlefs.db.impl.SimpleDBDataAPIImpl;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderIndexerImpl;
import edu.rahulk.cs8982.singlefs.models.ProviderSerializerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.rmi.Remote;
import java.util.List;

/**
 * Created by rahulk on 2/9/16.
 */
public class Test {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(Test.class);
        logger.info("Hello world.");

        try {
//            FileDBAPI fileDBAPI = new FileDBAPIImpl("test.csv");
//            PrimaryIndex primaryIndex = new PrimaryIndexImpl("primary.csv");
//            SecondaryIndex secondaryIndex = new SecondaryIndexImpl("secondary_pid.csv");
//            SecondaryIndex secondaryIndex2 = new SecondaryIndexImpl("secondary_zip.csv");
//            Indexer<Provider> indexer = new ProviderIndexerImpl();
//            Serializer<Provider> serializer = new ProviderSerializerImpl();
//
//            SimpleDBConnection.Builder<Provider> builder = new SimpleDBConnection.Builder<>();
//
//            SimpleDBConnection<Provider> simpleDBConnection = builder.
//                    withPersistentStoreAPI(fileDBAPI).
//                    withPrimaryIndex(primaryIndex).
//                    withSecondaryIndex("providerId", secondaryIndex).
//                    withSecondaryIndex("zipCode", secondaryIndex2).
//                    withIndexer(indexer).
//                    withSerializer(serializer).
//                    build();

            Indexer<Provider> indexer = new ProviderIndexerImpl();
            Serializer<Provider> serializer = new ProviderSerializerImpl();
            SimpleDBConnector simpleDBConnector = new SimpleDBConnector<>(indexer, serializer);
            SimpleDBConnection<Provider> simpleDBConnection = simpleDBConnector.createConnection("provider");

//            Provider provider1 = new Provider("test1", "test12", "test13");
//            Provider provider2 = new Provider("test2", "test22", "test33");
//            Provider provider3 = new Provider("test3", "test32", "test33");

            SimpleDBDataAPI<Provider> simpleDBDataAPI = new SimpleDBDataAPIImpl<>(simpleDBConnection);
//            simpleDBDataAPI.write(provider1);
//            simpleDBDataAPI.write(provider2);
//            simpleDBDataAPI.write(provider3);

            List<Provider> result = simpleDBDataAPI.read("providerId","1992994701");
            for (Provider provider : result) {
                System.out.println(provider);
            }



//            Long offset1 = fileDBAPI.write("test1, test12, test13\n");
//            String Id1 = primaryIndex.write(offset1);
//
//            Long offset2 = fileDBAPI.write("test2, test22, test23\n");
//            String Id2 = primaryIndex.write(offset2);
//
//            Long offset3 = fileDBAPI.write("test3, test32, test33\n");
//            String Id3 = primaryIndex.write(offset3);
//
//            offset1 = primaryIndex.read(Id1);
//            offset2 = primaryIndex.read(Id2);
//            offset3 = primaryIndex.read(Id3);
//
//            secondaryIndex.write("text1",Id1);
//            secondaryIndex.write("text2",Id2);
//            secondaryIndex.write("text3",Id3);
//
//
//            System.out.println(fileDBAPI.read(offset1));
//            System.out.println(fileDBAPI.read(offset2));
//            System.out.println(fileDBAPI.read(offset3));
//
//
//            System.out.println(secondaryIndex.read("text1"));
//            System.out.println(secondaryIndex.read("text2"));
//            System.out.println(secondaryIndex.read("text3"));




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
