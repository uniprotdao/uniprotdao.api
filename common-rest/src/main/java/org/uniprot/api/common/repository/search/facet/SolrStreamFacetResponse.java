package org.uniprot.api.common.repository.search.facet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class to keep the response returned by List of facet value search and facet functions.
 *
 * @author uniprotdao

 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolrStreamFacetResponse {
    private List<Facet> facets;
    private List<String> ids;
}
