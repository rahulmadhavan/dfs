package edu.rahulk.cs8982.singlefs.db.utils.serializer;

import java.util.Map;

/**
 * Created by rahulk on 2/9/16.
 */
public interface Serializer<T> {

    String serialize(T t);

    T deserialize(String stringObject);

}
