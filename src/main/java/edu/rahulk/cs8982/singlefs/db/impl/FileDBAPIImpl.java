package edu.rahulk.cs8982.singlefs.db.impl;

import edu.rahulk.cs8982.singlefs.db.FileDBAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by rahulk on 2/10/16.
 */
public class FileDBAPIImpl implements FileDBAPI {

    Logger logger = LoggerFactory.getLogger(FileDBAPIImpl.class);

    private RandomAccessFile fileDB;
    private String fileName;


    public FileDBAPIImpl(String fileNameWithPath) throws FileNotFoundException {
        fileName = fileNameWithPath;
        fileDB = new RandomAccessFile(new File(fileNameWithPath), "rw");
    }

    @Override
    public String read(Long offset) {
        String data = null;
        try {
            fileDB.seek(0);
            fileDB.seek(offset);
            data = fileDB.readLine();
        } catch (IOException e) {
            logger.error(String.format("File DB read Failed. File : Offset %d Name %s", offset, fileName),e);
        }
        return data;
    }

    @Override
    public Long write(String data) {
        Long offset = -1L;
        try {
            fileDB.seek(0);
            offset = fileDB.length();
            fileDB.seek(offset);
            fileDB.writeBytes(data+"\n");
        } catch (IOException e) {
            logger.error(String.format("File DB read Failed. File : Offset %d Name %s", offset, fileName),e);
        }
        return offset;
    }

    @Override
    public void connect() {

    }

    @Override
    public void close() {
        if (fileDB != null) {
            try {
                fileDB.close();
            } catch (IOException e) {
                logger.error("File DB close failed : file close failed",e);
            }
        }
    }
}
