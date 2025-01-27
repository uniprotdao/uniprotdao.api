package org.uniprot.api.aa.service;

import java.io.IOException;
import java.util.function.Function;

import org.springframework.stereotype.Component;
import org.uniprot.core.json.parser.unirule.UniRuleJsonConfig;
import org.uniprot.core.unirule.UniRuleEntry;
import org.uniprot.store.search.document.unirule.UniRuleDocument;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author uniprotdao

 */
@Component
@Slf4j
public class UniRuleEntryConverter implements Function<UniRuleDocument, UniRuleEntry> {
    private final ObjectMapper objectMapper;

    public UniRuleEntryConverter() {
        this.objectMapper = UniRuleJsonConfig.getInstance().getFullObjectMapper();
    }

    @Override
    public UniRuleEntry apply(UniRuleDocument uniRuleDocument) {
        UniRuleEntry entry = null;
        try {
            entry =
                    this.objectMapper.readValue(
                            uniRuleDocument.getUniRuleObj().array(), UniRuleEntry.class);
        } catch (IOException e) {
            log.info("Error converting solr binary to UniRuleEntry: ", e);
        }
        return entry;
    }
}
