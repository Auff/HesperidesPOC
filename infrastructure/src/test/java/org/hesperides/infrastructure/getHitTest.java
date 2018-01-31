package org.hesperides.infrastructure;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.node.Node;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class getHitTest {

    @Autowired
    EmbeddedElasticsearchServer embeddedElasticsearchServer;

    Node els;

    @BeforeClass
    public void StartServer()throws Exception{
        this.els = this.embeddedElasticsearchServer.getObject();
        CreateIndexRequest indexRequest =new CreateIndexRequest("hesperides");
        els.client().admin().indices().create(indexRequest).actionGet();
    }

    @Test
    public void JeSaisPasCeQueJeFais(){
        assert
    }


    @AfterClass
    public void CleanServer()throws Exception{

    }

}
