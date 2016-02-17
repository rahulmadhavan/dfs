package edu.rahulk.cs8982.singlefs.models;

import edu.rahulk.cs8982.singlefs.db.utils.serializer.Indexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rahulk on 2/11/16.
 */
public class ProviderIndexerImpl implements Indexer<Provider> {

    List<String> indices = new ArrayList<>();

    public ProviderIndexerImpl() {
        indices.add("providerId");
        indices.add("zipCode");
        indices.add("procedureCode");
    }

    @Override
    public List<String> getFieldsToIndex() {
        return indices;
    }

    @Override
    public Map<String, String> getIndexFields(Provider provider) {
        Map<String, String> map = new HashMap<>();
        map.put("providerId",provider.getProviderId());
        map.put("zipCode",provider.getZipCode());
        map.put("procedureCode",provider.getProcedureCode());
        return map;
    }
}
