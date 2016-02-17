package edu.rahulk.cs8982.singlefs.db.impl;

import edu.rahulk.cs8982.singlefs.db.PrimaryIndex;
import edu.rahulk.cs8982.singlefs.db.SimpleDBConnection;
import edu.rahulk.cs8982.singlefs.db.SimpleDBDataAPI;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rahulk on 2/9/16.
 */
public class SimpleDBDataAPIImpl<T> implements SimpleDBDataAPI<T> {

    SimpleDBConnection<T> simpleDBConnection;

    public SimpleDBDataAPIImpl(SimpleDBConnection<T> simpleDBConnection){
        this.simpleDBConnection = simpleDBConnection;
    }

    @Override
    public List<T> read(String column, String value) {
        List<String> idList = this.simpleDBConnection.getSecondaryIndex(column).read(value);
        List<T> result = new ArrayList<>();
        for(String id : idList) {
            Long offset = this.simpleDBConnection.getPrimaryIndex().read(id);
            String data = this.simpleDBConnection.getPersistentStoreAPI().read(offset);
            result.add(this.simpleDBConnection.getSerializer().deserialize(data));
        }
        return result;
    }

    @Override
    public void write(T t) {

        Long offset = simpleDBConnection.getPersistentStoreAPI().write(
                simpleDBConnection.getSerializer().serialize(t));
        String id = simpleDBConnection.getPrimaryIndex().write(offset);
        Map<String,String> objectMap = simpleDBConnection.getIndexer().getIndexFields(t);
        for(String index : objectMap.keySet()) {
            simpleDBConnection
                    .getSecondaryIndex(index)
                    .write(objectMap.get(index), id);
        }
    }

}
