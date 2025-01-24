package org.uniprot.api.support.data.configure.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.api.support.data.configure.service.UtilServiceTest.CONTEXT_PATH;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.api.support.data.configure.response.AdvancedSearchTerm;
import org.uniprot.api.support.data.configure.response.UniProtReturnField;

/**
 * @author lgonzales
 * @since 27/05/2020
 */
class ProteomeConfigureServiceTest {

    @Test
    void getResultFields() {
        ProteomeConfigureService service = new ProteomeConfigureService();
        List<UniProtReturnField> resultGroups = service.getResultFields();

        assertNotNull(resultGroups);
        assertEquals(2, resultGroups.size());

        assertEquals(6, resultGroups.get(0).getFields().size());
        assertEquals(5, resultGroups.get(1).getFields().size());
    }

    @Test
    void getSearchItems() {
        ProteomeConfigureService service = new ProteomeConfigureService();
        List<AdvancedSearchTerm> result = service.getSearchItems(CONTEXT_PATH);
        assertNotNull(result);
        assertEquals(8, result.size());
    }
}
