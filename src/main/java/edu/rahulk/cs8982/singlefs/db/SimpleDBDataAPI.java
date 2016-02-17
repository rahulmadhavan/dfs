package edu.rahulk.cs8982.singlefs.db;

import java.util.List;

/**
 * Created by rahulk on 2/9/16.
 */
public interface SimpleDBDataAPI<T> {

    List<T> read(String column, String value);

    void write(T t);

}
