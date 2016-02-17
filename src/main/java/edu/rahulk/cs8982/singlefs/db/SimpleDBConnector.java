package edu.rahulk.cs8982.singlefs.db;

import edu.rahulk.cs8982.singlefs.db.impl.FileDBAPIImpl;
import edu.rahulk.cs8982.singlefs.db.impl.PrimaryIndexImpl;
import edu.rahulk.cs8982.singlefs.db.impl.SecondaryIndexImpl;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;
import edu.rahulk.cs8982.singlefs.models.Provider;
import edu.rahulk.cs8982.singlefs.models.ProviderIndexerImpl;
import edu.rahulk.cs8982.singlefs.models.ProviderSerializerImpl;

import java.io.FileNotFoundException;

/**
 * Created by rahulk on 2/11/16.
 */
public class SimpleDBConnector<T> {

    private Indexer<T> indexer;
    private Serializer<T> serializer;

    public SimpleDBConnector(Indexer<T> indexer, Serializer<T> serializer) {
        this.indexer = indexer;
        this.serializer = serializer;
    }

    private String getTableFileName(String name) {
        return String.format("data/%s.csv", name);
    }

    private String getPrimaryIndexFileName(String name) {
        return String.format("data/primary_%s.csv", name);
    }

    private String getSecondaryIndexFileName(String name, String index) {
        return String.format("data/secondary_%s_%s.csv", name, index);
    }


    public SimpleDBConnection<T> createConnection(String tableName) throws FileNotFoundException {
        FileDBAPI fileDBAPI = new FileDBAPIImpl(getTableFileName(tableName));
        PrimaryIndex primaryIndex = new PrimaryIndexImpl(getPrimaryIndexFileName(tableName));
        SimpleDBConnection.Builder<T> builder = new SimpleDBConnection.Builder<>();

        builder.withPersistentStoreAPI(fileDBAPI).
                withPrimaryIndex(primaryIndex).
                withIndexer(indexer).
                withSerializer(serializer);

        for (String index : indexer.getFieldsToIndex()) {
            SecondaryIndex secondaryIndex = new SecondaryIndexImpl(getSecondaryIndexFileName(tableName, index));
            builder.withSecondaryIndex(index, secondaryIndex);
        }


        SimpleDBConnection<T> simpleDBConnection = builder.build();
        return simpleDBConnection;
    }

}
