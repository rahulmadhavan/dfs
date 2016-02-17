package edu.rahulk.cs8982.singlefs.db.impl;

import edu.rahulk.cs8982.singlefs.db.PrimaryIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahulk on 2/10/16.
 */
public class PrimaryIndexImpl implements PrimaryIndex {

    Logger logger = LoggerFactory.getLogger(PrimaryIndexImpl.class);

    Long indexCount;
    private RandomAccessFile primaryIndexDB;
    private String primaryIndexFileName;

    private Map<String, IndexData> indexMap;


    public static class IndexData {
        String id;
        Boolean deleted;
        Long offset;
        private static final String indexLine = "%s, %d, %d\n";

        public IndexData(String id, Long offset){
            this.id = id;
            this.offset = offset;
            this.deleted = false;
        }

        public IndexData(String id, Long offset, Boolean deleted){
            this.id = id;
            this.offset = offset;
            this.deleted = deleted;
        }

        public void delete() {
            deleted = true;
        }

        public int isDeleted() {
            return deleted ? 1 : 0;
        }

        @Override
        public String toString() {
            return String.format(indexLine, id, isDeleted(), offset);
        }

        public static PrimaryIndexImpl.IndexData fromString(String data) {
            String[] indexDataArray = data.split(", ");
            return new IndexData(indexDataArray[0],
                    Long.valueOf(indexDataArray[2]),
                    Integer.valueOf(indexDataArray[1]) == 1);
        }
    }

    public PrimaryIndexImpl(String fileNameWithPath) throws FileNotFoundException {
        primaryIndexFileName = fileNameWithPath;
        primaryIndexDB = new RandomAccessFile(new File(fileNameWithPath), "rw");
        initializeIndexMap();
    }

    @Override
    public String write(Long dataOffset) {
        IndexData indexData = new IndexData(indexCount.toString(), dataOffset);
        try {
            primaryIndexDB.seek(0);
            Long offset = primaryIndexDB.length();
            primaryIndexDB.seek(offset);
            primaryIndexDB.writeBytes(indexData.toString());
            indexMap.put(indexData.id, indexData);
            indexCount = indexCount + 1;
        } catch (IOException e) {
            logger.error(String.format(
                    "Primary Index DB write Failed. File : indexCount %d Name %s",
                    indexCount, primaryIndexFileName),e);
        }
        return String.valueOf(indexCount - 1);
    }

    @Override
    public Long read(String id) {
        Long dataOffset = null;
        IndexData indexData = indexMap.get(id);
        if (indexData != null){
            dataOffset = indexData.offset;
        }
        return dataOffset;
    }

    @Override
    public void connect() {

    }

    @Override
    public void close() {
        if (primaryIndexDB != null) {
            try {
                primaryIndexDB.close();
            } catch (IOException e) {
                logger.error("Primary Index DB close failed : file close failed",e);
            }
        }
    }

    private void initializeIndexMap() {
        indexMap = new HashMap<>();
        logger.info("loading primary Index");
        try {
            primaryIndexDB.seek(0);
            String data = primaryIndexDB.readLine();
            String prevData = null;

            while (data != null) {
                prevData = data;
                IndexData indexData = IndexData.fromString(prevData);
                indexMap.put(indexData.id, indexData);
                data = primaryIndexDB.readLine();
            }

            if (prevData == null) {
                indexCount = 0L;
            } else {
                IndexData indexData = IndexData.fromString(prevData);
                indexCount = Long.valueOf(indexData.id);
            }

            primaryIndexDB.readLine();
            indexCount = indexCount + 1;
        } catch (IOException e) {
            logger.error(String.format(
                    "Primary Index DB init Failed. File : indexCount %d Name %s",
                    indexCount, primaryIndexFileName),e);
        }
        logger.info("finished loading primary Index");

    }
}
