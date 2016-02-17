package edu.rahulk.cs8982.singlefs.db;

import edu.rahulk.cs8982.singlefs.db.impl.SimpleDBDataAPIImpl;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;
import edu.rahulk.cs8982.singlefs.db.utils.serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahulk on 2/9/16.
 */
public class SimpleDBConnection<T> {

    private Serializer<T> serializer;
    private Indexer<T> indexer;
    private PrimaryIndex primaryIndex;
    private Map<String,SecondaryIndex> secondaryIndexMap;
    private FileDBAPI persistentStoreAPI;
    private SimpleDBDataAPI<T> simpleDBDataAPI;

    private SimpleDBConnection(){
        secondaryIndexMap = new HashMap<>();
    }

    public Serializer<T> getSerializer() {
        return serializer;
    }

    public PrimaryIndex getPrimaryIndex() {
        return primaryIndex;
    }

    public SecondaryIndex getSecondaryIndex(String index) {
        return secondaryIndexMap.get(index);
    }

    public FileDBAPI getPersistentStoreAPI() {
        return persistentStoreAPI;
    }

    public Indexer<T> getIndexer() {
        return indexer;
    }

    public static class Builder<E> {
        private SimpleDBConnection<E> simpleDBConnection;

        public Builder() {
            this.simpleDBConnection = new SimpleDBConnection<>();
        }

        public Builder withSerializer(Serializer<E> serializer) {
            this.simpleDBConnection.serializer = serializer;
            return this;
        }

        public Builder withPrimaryIndex(PrimaryIndex primaryIndex) {
            this.simpleDBConnection.primaryIndex = primaryIndex;
            return this;
        }

        public Builder withSecondaryIndex(String index, SecondaryIndex secondaryIndex) {
            this.simpleDBConnection.secondaryIndexMap.put(index, secondaryIndex);
            return this;
        }

        public Builder withPersistentStoreAPI(FileDBAPI persistentStoreAPI) {
            this.simpleDBConnection.persistentStoreAPI = persistentStoreAPI;
            return this;
        }

        public Builder withIndexer(Indexer<E> indexer) {
            this.simpleDBConnection.indexer = indexer;
            return this;
        }

        public SimpleDBConnection<E> build(){
            return simpleDBConnection;
        }

    }

    public SimpleDBDataAPI<T> getSimpleDBQueryAPI() {
        if (simpleDBDataAPI == null) {
            simpleDBDataAPI = new SimpleDBDataAPIImpl<>(this);
        }
        return simpleDBDataAPI;
    }

}
