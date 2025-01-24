package org.uniprot.api.common.repository.stream.document;

import java.util.stream.Stream;

import org.uniprot.api.common.repository.search.SolrRequest;

/**
 * @author uniprotdao

 */
public interface DocumentIdStream {
    Stream<String> fetchIds(SolrRequest solrRequest);
}
