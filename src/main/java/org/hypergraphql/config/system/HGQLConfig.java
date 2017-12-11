package org.hypergraphql.config.system;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.language.TypeDefinition;
import graphql.schema.*;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.apache.log4j.Logger;

import org.hypergraphql.datafetching.services.Service;
import org.hypergraphql.config.schema.FieldConfig;
import org.hypergraphql.config.schema.QueryFieldConfig;
import org.hypergraphql.config.schema.TypeConfig;
import org.hypergraphql.datamodel.HGQLSchema;
import org.hypergraphql.datamodel.HGQLSchemaWiring;

import static graphql.Scalars.*;

/**
 * Created by szymon on 05/09/2017.
 */

public class HGQLConfig {

    private String name;
    private String serviceFile;
    private String schemaFile;
    private GraphqlConfig graphqlConfig;
    private List<ServiceConfig> serviceConfigs;
    private TypeDefinitionRegistry registry;

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceFile(String serviceFile) {
        this.serviceFile = serviceFile;
    }

    public String getSchemaFile() {
        return schemaFile;
    }

    public void setSchemaFile(String schemaFile) {
        this.schemaFile = schemaFile;
    }

    public void setGraphqlConfig(GraphqlConfig graphqlConfig) {
        this.graphqlConfig = graphqlConfig;
    }

    public void setServiceConfigs(List<ServiceConfig> serviceConfigs) {
        this.serviceConfigs = serviceConfigs;
    }

    public void setRegistry(TypeDefinitionRegistry registry) {
        this.registry = registry;
    }

    public GraphQLSchema getSchema() {
        return schema;
    }

    public void setSchema(GraphQLSchema schema) {
        this.schema = schema;
    }

    public HGQLSchema getHgqlSchema() {
        return hgqlSchema;
    }

    public void setHgqlSchema(HGQLSchema hgqlSchema) {
        this.hgqlSchema = hgqlSchema;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        HGQLConfig.logger = logger;
    }

    private GraphQLSchema schema;
    private HGQLSchema hgqlSchema;

    private static Logger logger = Logger.getLogger(HGQLConfig.class);

    @JsonCreator
    private  HGQLConfig(
            @JsonProperty("name") String name,
            @JsonProperty("serviceFile") String serviceFile,
            @JsonProperty("schemaFile") String schemaFile,
            @JsonProperty("graphql") GraphqlConfig graphql
    ) {
        this.name = name;
        this.serviceFile = serviceFile;
        this.schemaFile = schemaFile;
        this.graphqlConfig = graphql;
    }



    public HGQLConfig(String propertyFilepath) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            HGQLConfig config = mapper.readValue(new File(propertyFilepath), HGQLConfig.class);

            if (config != null) {

                try {

                    this.serviceConfigs = mapper.readValue(new File(config.serviceFile), new TypeReference<List<ServiceConfig>>(){});

                } catch (IOException e) {
                    logger.error(e);
                }
            }




            SchemaParser schemaParser = new SchemaParser();
            this.registry = schemaParser.parse(new File(config.schemaFile));

            this.name = config.name;
            this.schemaFile = config.schemaFile;
            this.serviceFile = config.serviceFile;
            this.graphqlConfig = config.graphqlConfig;
            HGQLSchemaWiring wiring = new HGQLSchemaWiring(this.registry,this.name,this.serviceConfigs);
            this.schema = wiring.getSchema();
            this.hgqlSchema = wiring.getHgqlSchema();

        } catch (IOException e) {
            logger.error(e);
        }


    }


    public List<ServiceConfig> getServiceConfigs() {
        return serviceConfigs;
    }

    public GraphqlConfig getGraphqlConfig() {
        return graphqlConfig;
    }

    public String getName() {
        return name;
    }

    public String getServiceFile() {
        return serviceFile;
    }

    public TypeDefinitionRegistry getRegistry() {
        return registry;
    }

}


