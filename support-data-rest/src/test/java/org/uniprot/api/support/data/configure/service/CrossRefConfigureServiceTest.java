package org.uniprot.api.support.data.configure.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.api.support.data.configure.service.UtilServiceTest.CONTEXT_PATH;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.api.support.data.configure.response.AdvancedSearchTerm;
import org.uniprot.api.support.data.configure.response.UniProtReturnField;

/**
 * @author lgonzales
 * @since 18/03/2021
 */
class CrossRefConfigureServiceTest {

    @Test
    void getResultFields() {
        CrossRefConfigureService service = new CrossRefConfigureService();
        List<UniProtReturnField> resultGroups = service.getResultFields();

        assertNotNull(resultGroups);
        assertEquals(1, resultGroups.size());

        assertEquals(10, resultGroups.get(0).getFields().size());
    }

    @Test
    void getSearchItems() {
        CrossRefConfigureService service = new CrossRefConfigureService();
        List<AdvancedSearchTerm> result = service.getSearchItems(CONTEXT_PATH);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
