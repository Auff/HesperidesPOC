package org.hesperides.infrastructure.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchClient {
    @Autowired
    ElasticsearchConfiguration elasticsearchConfiguration;

    public RestClient getRestClient() {
        HttpHost httpHost = new HttpHost(this.elasticsearchConfiguration.getHost(), this.elasticsearchConfiguration.getPort());
        return RestClient.builder(httpHost).build(); //TODO .setFailureListener()
    }
}
