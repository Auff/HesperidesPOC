package org.hesperides.infrastructure.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("elasticsearch")
public class ElasticsearchConfiguration {
    private String host;
    private String index;
    private Integer port;

}
