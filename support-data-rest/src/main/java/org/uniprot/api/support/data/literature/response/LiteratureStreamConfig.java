package org.uniprot.api.support.data.literature.response;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.uniprot.api.common.repository.stream.document.DefaultDocumentIdStream;
import org.uniprot.api.rest.converter.SolrDocumentRdfIdConverter;
import org.uniprot.api.rest.respository.RepositoryConfig;
import org.uniprot.api.support.data.literature.repository.LiteratureRepository;
import org.uniprot.store.search.document.literature.LiteratureDocument;

import lombok.extern.slf4j.Slf4j;

/**
 * @author uniprotdao

 */
@Configuration
@Import(RepositoryConfig.class)
@Slf4j
public class LiteratureStreamConfig {
    @Bean
    public DefaultDocumentIdStream<LiteratureDocument> literatureDocumentIdStream(
            LiteratureRepository repository) {
        return DefaultDocumentIdStream.<LiteratureDocument>builder()
                .repository(repository)
                .documentToId(document -> new SolrDocumentRdfIdConverter().apply(document))
                .build();
    }
}
