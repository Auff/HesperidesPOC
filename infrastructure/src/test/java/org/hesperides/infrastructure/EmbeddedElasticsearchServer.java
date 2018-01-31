package org.hesperides.infrastructure;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Factory bean that creates an embedded ElasticSearch node
 */
@Component
public class EmbeddedElasticsearchServer implements FactoryBean<Node> {

    private Node node;

    @Override
    public Node getObject() throws Exception {
        return getNode();
    }

    @Override
    public Class getObjectType() {
        return Node.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private Node getNode() {

        ImmutableSettings.Builder settingsBuilder =
                ImmutableSettings.settingsBuilder();

        settingsBuilder.put("path.data", "target/elasticsearch-data");
        settingsBuilder.put("http.enabled", false);

        Settings settings = settingsBuilder.build();

        node = NodeBuilder.nodeBuilder()
                .settings(settings)
                .data(true).local(true).node();
        return node;
    }

    public Client getClient() {
        return node.client();
    }

    public void shutdown() {
        node.close();
//        deleteDataDirectory();
    }

}