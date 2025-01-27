package org.uniprot.api.aa.repository;

import org.apache.solr.client.solrj.SolrClient;
import org.springframework.stereotype.Repository;
import org.uniprot.api.common.repository.search.SolrQueryRepository;
import org.uniprot.api.common.repository.search.SolrRequestConverter;
import org.uniprot.store.search.SolrCollection;
import org.uniprot.store.search.document.arba.ArbaDocument;

/**
 * @author uniprotdao

 */
@Repository
public class ArbaQueryRepository extends SolrQueryRepository<ArbaDocument> {
    public ArbaQueryRepository(
            SolrClient solrClient,
            ArbaFacetConfig facetConfig,
            SolrRequestConverter requestConverter) {
        super(solrClient, SolrCollection.arba, ArbaDocument.class, facetConfig, requestConverter);
    }
}
