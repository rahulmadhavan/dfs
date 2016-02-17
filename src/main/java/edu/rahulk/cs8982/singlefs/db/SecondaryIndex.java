package edu.rahulk.cs8982.singlefs.db;

import java.util.List;

/**
 * Created by rahulk on 2/9/16.
 */
public interface SecondaryIndex {

    void write(String data, String id);

    List<String> read(String data);

}
