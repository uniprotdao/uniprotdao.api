package org.uniprot.api.rest.search;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created 01/10/2019
 *
 * @author Edd
 */
class AbstractSolrSortClauseTest {
    private FakeSolrSortClause fakeSolrSortClause;

    @BeforeEach
    void beforeEach() {
        fakeSolrSortClause = new FakeSolrSortClause();
    }

    @Test
    void emptySortClauseProducesDefaultSort() {
        List<SolrQuery.SortClause> defaultSort = fakeSolrSortClause.getSort("");
        assertThat(
                defaultSort,
                contains(
                        SolrQuery.SortClause.desc("score"),
                        defaultSort(),
                        SolrQuery.SortClause.asc(FakeSolrSortClause.ID)));
    }

    @Test
    void singleSortClauseProducesSingleSort() {
        String gene = "gene";
        List<SolrQuery.SortClause> sorts = fakeSolrSortClause.getSort(gene + " asc");

        assertThat(
                sorts,
                contains(
                        SolrQuery.SortClause.asc(field(gene)),
                        SolrQuery.SortClause.asc(FakeSolrSortClause.ID)));
    }

    @Test
    void invalidSortCausesException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> fakeSolrSortClause.getSort("invalid-no-sort-direction"));
    }

    @Test
    void multipleSortClausesWithIDProduceMultipleSorts() {
        String proteinName = "protein_name";
        String gene = "gene";
        List<SolrQuery.SortClause> sorts =
                fakeSolrSortClause.getSort(proteinName + " asc, " + gene + " desc, id desc");

        assertThat(
                sorts,
                contains(
                        SolrQuery.SortClause.asc(field(proteinName)),
                        SolrQuery.SortClause.desc(field(gene)),
                        SolrQuery.SortClause.desc(FakeSolrSortClause.ID)));
    }

    @Test
    void multipleSortClausesProduceMultipleSorts() {
        String gene = "gene";
        String proteinName = "protein_name";
        List<SolrQuery.SortClause> sorts =
                fakeSolrSortClause.getSort(gene + " asc, " + proteinName + " desc");

        assertThat(
                sorts,
                contains(
                        SolrQuery.SortClause.asc(field(gene)),
                        SolrQuery.SortClause.desc(field(proteinName)),
                        SolrQuery.SortClause.asc(FakeSolrSortClause.ID)));
    }

    static String field(String name) {
        return name + "_sort";
    }

    static SolrQuery.SortClause defaultSort() {
        return SolrQuery.SortClause.asc("default");
    }
}
