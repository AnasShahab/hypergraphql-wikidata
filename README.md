![HyperGraphQL](docs/HyperGraphQL.png)  HyperGraphQL
======

HyperGraphQL is a [GraphQL](http://graphql.org) interface for querying and serving [linked data](https://www.w3.org/standards/semanticweb/data) on the Web. It is designed to support federated querying and exposing data from multiple linked data services using GraphQL query language and schemas. The basic response format is [JSON-LD](https://json-ld.org), which extends the standard JSON with the [JSON-LD context](https://json-ld.org/spec/latest/json-ld-api-best-practices/#dfn-json-ld-context) enabling semantic disambiguation of the contained data.

### HypergraphQL:
##### Clone the forked [project](https://github.com/AnasShahab/hypergraphql-wikidata) into a local directory. In the root of the project execute the following:
```sh
gradle clean build shadowJar
```
This will build two JAR files in build/libs: hypergraphql-3.0.1.jar and hypergraphql-3.0.1-exe.jar

##### Run the executable JAR
```sh
java -jar build/libs/hypergraphql-3.0.1-exe.jar --config wikidata_example/config.json
```
The HyperGraphQL server starts at: http://localhost:8081/graphql
The GraphiQL UI is initiated at: http://localhost:8080/graphiql

      
