package edu.rahulk.cs8982.singlefs.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rahulk on 2/16/16.
 */
public class ProviderResponse implements Serializable{
    List<Provider> providers;
    Long serverExecutionTime;

    public ProviderResponse(List<Provider> providers, long time) {
        this.providers = providers;
        this.serverExecutionTime = time;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public Long getServerExecutionTime() {
        return serverExecutionTime;
    }
}
