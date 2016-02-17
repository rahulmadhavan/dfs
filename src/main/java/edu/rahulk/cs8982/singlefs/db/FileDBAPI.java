package edu.rahulk.cs8982.singlefs.db;

/**
 * Created by rahulk on 2/9/16.
 */
public interface FileDBAPI {

    String read(Long offset);

    Long write(String data);

    void connect();

    void close();

}
