package edu.rahulk.cs8982.singlefs.db.utils.serializer;

import java.util.List;
import java.util.Map;

/**
 * Created by rahulk on 2/10/16.
 */
public interface Indexer<T> {

    List<String> getFieldsToIndex();

    Map<String, String> getIndexFields(T t);

}
