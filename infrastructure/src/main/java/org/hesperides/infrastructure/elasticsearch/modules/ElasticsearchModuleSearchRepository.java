package org.hesperides.infrastructure.elasticsearch.modules;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;
import org.hesperides.infrastructure.elasticsearch.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
//@Profile("!local")
public class ElasticsearchModuleSearchRepository {
    @Autowired
    ElasticsearchService elasticsearchService;
    MustacheFactory mustacheFactory = new DefaultMustacheFactory();
    private static final String MUSTACHE_SEARCH_ALL = "search.module.all.mustache";
}
