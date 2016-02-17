package edu.rahulk.cs8982.singlefs.db.impl;

import edu.rahulk.cs8982.singlefs.db.SecondaryIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahulk on 2/10/16.
 */
public class SecondaryIndexImpl implements SecondaryIndex {

    Logger logger = LoggerFactory.getLogger(SecondaryIndexImpl.class);

    private RandomAccessFile secondaryIndexDB;
    private String secondaryIndexFileName;
    private Map<String, Map<String,IndexData>> indexMap;


    public static class IndexData {
        String id;
        String data;
        private static final String indexLine = "%s, %s\n";

        public IndexData(String id, String data){
            this.id = id;
            this.data = data;
        }

        @Override
        public String toString() {
            return String.format(indexLine, id, data);
        }

        public static SecondaryIndexImpl.IndexData fromString(String data) {
            String[] indexDataArray = data.split(", ");
            return new IndexData(indexDataArray[0], indexDataArray[1]);
        }
    }

    public SecondaryIndexImpl(String fileNameWithPath) throws FileNotFoundException {
        secondaryIndexFileName = fileNameWithPath;
        secondaryIndexDB = new RandomAccessFile(new File(fileNameWithPath), "rw");
        initializeIndexMap();
    }

    @Override
    public void write(String data, String id) {
        IndexData indexData = new IndexData(id, data);
        try {
            secondaryIndexDB.seek(0);
            Long offset = secondaryIndexDB.length();
            secondaryIndexDB.seek(offset);
            secondaryIndexDB.writeBytes(indexData.toString());
            Map<String,IndexData> idMap;
            if(indexMap.containsKey(indexData.data)) {
                idMap = indexMap.get(indexData.data);
                idMap.put(indexData.id, indexData);
            } else {
                idMap = new HashMap<>();
                idMap.put(indexData.id, indexData);
                indexMap.put(indexData.data, idMap);
            }
        } catch (IOException e) {
            logger.error(String.format(
                    "Secondary Index DB init Failed. Name %s", secondaryIndexFileName),e);
        }
    }

    @Override
    public List<String> read(String data) {
        Map<String,IndexData> idMap = indexMap.get(data);
        List<String> result = new ArrayList<>();
        if(idMap != null)
            result.addAll(idMap.keySet());
        return result;
    }

    private void initializeIndexMap() {
        indexMap = new HashMap<>();
        logger.info(String.format("loading secondary Index %s",secondaryIndexFileName));
        try {
            secondaryIndexDB.seek(0);
            String data = secondaryIndexDB.readLine();

            while (data != null) {
                IndexData indexData = IndexData.fromString(data);
                Map<String,IndexData> idMap;
                if(indexMap.containsKey(indexData.data)) {
                    idMap = indexMap.get(indexData.data);
                    idMap.put(indexData.id, indexData);
                } else {
                    idMap = new HashMap<>();
                    idMap.put(indexData.id, indexData);
                    indexMap.put(indexData.data, idMap);
                }
                data = secondaryIndexDB.readLine();
            }
        } catch (IOException e) {
            logger.error(String.format(
                    "Secondary Index DB init Failed. Name %s", secondaryIndexFileName),e);

        }
        logger.info(String.format("finished loading secondary Index %s",secondaryIndexFileName));

    }
}
