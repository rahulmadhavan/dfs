package edu.rahulk.cs8982.singlefs.db;

/**
 * Created by rahulk on 2/9/16.
 */
public interface PrimaryIndex {

    String write(Long dataOffset);

    Long read(String id);

    void connect();

    void close();

}
