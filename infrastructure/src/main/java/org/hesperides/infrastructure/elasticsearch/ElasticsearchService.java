package org.hesperides.infrastructure.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.client.RestClient;
import org.hesperides.infrastructure.elasticsearch.response.ResponseHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//Dépendances à vérifier
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.asm.TypeReference;
import com.fasterxml.jackson.core.type.TypeReference;



import java.io.IOException;
import java.util.Collections;

@Slf4j
@Service
public class ElasticsearchService {
    @Autowired
    ElasticsearchConfiguration elasticsearchConfiguration;

    @Autowired
    ElasticsearchClient elasticsearchClient;

    public ResponseHits getResponseHits(final String method, final String url, final String requestBody, final TypeReference typeReference) {
        ResponseHits responseHits = null;
        RestClient restClient = this.elasticsearchClient.getRestClient();
        String endPoint = "/" + this.elasticsearchConfiguration.getIndex() + url;
        try {
            HttpEntity entity = new NStringEntity(requestBody, ContentType.APPLICATION_JSON);
            Response response = restClient.performRequest(method,endPoint, Collections.emptyMap(),entity);
            responseHits = new ObjectMapper().readValue(response.getEntity().getContent(),typeReference);
        } catch (ResponseException e){
            if (hesperidesIndexIsNotPresent(e)){
                log.warn("Hesperides index was not found, index creation");

                createHesperidesIndex();
                return getResponseHits(method,url,requestBody,typeReference);
            }
            throw new RuntimeException(e);

        }catch (IOException e){
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return responseHits;

    }

    private boolean hesperidesIndexIsNotPresent(ResponseException e) {
        // si on est sur du 404, alors tente de créer l'index.
        return e.getResponse().getStatusLine().getStatusCode() == 404 && entityContainsIndexMissingException(e.getResponse().getEntity());
    }

    private boolean entityContainsIndexMissingException(HttpEntity entity) {
        try {
            String e = EntityUtils.toString(entity);
            return e != null && e.contains("IndexMissingException[[hesperides] missing]");
        } catch (IOException e) {
            return false;
        }
    }
    private void createHesperidesIndex() {
        try {
            elasticsearchClient.getRestClient().performRequest("PUT", "/" + this.elasticsearchConfiguration.getIndex());
        } catch (IOException e) {
            throw new RuntimeException("could not create hesperides index: " + e.getMessage(), e);
        }
    }

}
