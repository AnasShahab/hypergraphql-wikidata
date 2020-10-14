package org.hypergraphql.datafetching.services;

import org.hypergraphql.config.system.ServiceConfig;

public abstract class SPARQLService extends Service {

    private String graph;

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public String getGraph() {
        return graph;
    }

    public void setParameters(final ServiceConfig serviceConfig) {

        setId(serviceConfig.getId());
        if (serviceConfig.getGraph() == null) {
            this.graph = "";
        } else {
            this.graph = serviceConfig.getGraph();
        }
    }
}
