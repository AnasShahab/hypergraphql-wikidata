![HyperGraphQL](docs/HyperGraphQL.png)  HyperGraphQL
======

HyperGraphQL is a [GraphQL](http://graphql.org) interface for querying and serving [linked data](https://www.w3.org/standards/semanticweb/data) on the Web. It is designed to support federated querying and exposing data from multiple linked data services using GraphQL query language and schemas. The basic response format is [JSON-LD](https://json-ld.org), which extends the standard JSON with the [JSON-LD context](https://json-ld.org/spec/latest/json-ld-api-best-practices/#dfn-json-ld-context) enabling semantic disambiguation of the contained data.

### HypergraphQL:
##### Clone the forked [project](https://github.com/AnasShahab/hypergraphql-wikidata) into a local directory. In the root of the project execute the following:
```sh
gradle clean build shadowJar
```
This will build two JAR files in build/libs: hypergraphql-3.0.1.jar and hypergraphql-3.0.1-exe.jar

#### Example
##### We can find all the cats that have a name, sex, birth date, death date and birth place
###### schema.graphql
    type __Context {
        Cat:    _@href(iri: "http://www.wikidata.org/entity/Q146")
        sex:    _@href(iri: "http://www.wikidata.org/prop/direct/P21")
        name:   _@href(iri: "http://www.wikidata.org/prop/direct/P735")
        label:  _@href(iri: "http://www.w3.org/2000/01/rdf-schema#label")
        birthDate:  _@href(iri: "http://www.wikidata.org/prop/direct/P569")
        LivingThings:    _@href(iri: "http://www.wikidata.org/entity/Q16334298")
        deathDate:  _@href(iri: "http://www.wikidata.org/prop/direct/P570")
        birthPlace:  _@href(iri: "http://www.wikidata.org/prop/direct/P19")
        SexItem:    _@href(iri: "http://www.wikidata.org/entity/Q16334298")
        NameItem:    _@href(iri: "http://www.wikidata.org/entity/Q101352")
        BirthPlaceItem:    _@href(iri: "http://www.wikidata.org/entity/Q6256")
    }

    type Cat @service(id:"wikidata-sparql") {
        name: [NameItem] @service(id:"wikidata-sparql")
        sex: [SexItem] @service(id:"wikidata-sparql")
        label: [String] @service(id:"wikidata-sparql")
        birthDate: String @service(id:"wikidata-sparql")
        deathDate: String @service(id:"wikidata-sparql")
        birthPlace: [BirthPlaceItem] @service(id:"wikidata-sparql")
    }
    
    type SexItem @service(id:"wikidata-sparql"){
        label: [String] @service(id:"wikidata-sparql")
    }
    
    type NameItem @service(id:"wikidata-sparql"){
        label: [String] @service(id:"wikidata-sparql")
    }
    
    type BirthPlaceItem @service(id:"wikidata-sparql"){
        label: [String] @service(id:"wikidata-sparql")
    }

##### Provide the configuration file
###### config.json
    {
        "name": "wikidata-hgql",
        "schema": "schema/schema.graphql",
        "server": {
            "port": 8081,
            "graphql": "/graphql",
            "graphiql": "/graphiql"
        },
        "services": [
            {
                "id": "wikidata-sparql",
                "type": "SPARQLEndpointService",
                "url": "https://query.wikidata.org/sparql",
                "graph": "",
                "user": "",
                "password": ""
            }
        ]
    }


##### Run the executable JAR
```sh
java -jar build/libs/hypergraphql-3.0.1-exe.jar --config wikidata_example/config.json
```
The HyperGraphQL server starts at: http://localhost:8081/graphql
The GraphiQL UI is initiated at: http://localhost:8080/graphiql
      
