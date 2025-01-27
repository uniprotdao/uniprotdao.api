package org.uniprot.api.idmapping.common.response.converter.uniref;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.uniprot.api.common.concurrency.Gatekeeper;
import org.uniprot.api.idmapping.common.response.converter.EntryPairValueMapper;
import org.uniprot.api.idmapping.common.response.model.UniRefEntryPair;
import org.uniprot.api.rest.output.converter.*;
import org.uniprot.core.json.parser.uniref.UniRefEntryLightJsonConfig;
import org.uniprot.core.parser.tsv.uniref.UniRefEntryLightValueMapper;
import org.uniprot.store.config.returnfield.config.ReturnFieldConfig;

/**
 * @author uniprotdao

 *     <p>Converters related to UniRefEntryPair
 */
public class UniRefMessageConverterConfig {

    public static int appendUniRefConverters(
            int currentIndex,
            List<HttpMessageConverter<?>> converters,
            ReturnFieldConfig returnFieldConfig,
            Gatekeeper downloadGatekeeper) {
        JsonMessageConverter<UniRefEntryPair> jsonMessageConverter =
                new JsonMessageConverter<>(
                        UniRefEntryLightJsonConfig.getInstance().getSimpleObjectMapper(),
                        UniRefEntryPair.class,
                        returnFieldConfig,
                        downloadGatekeeper);
        converters.add(currentIndex++, jsonMessageConverter);
        converters.add(currentIndex++, new UniRefEntryFastaMessageConverter(downloadGatekeeper));
        converters.add(currentIndex++, new ListMessageConverter(downloadGatekeeper));
        converters.add(currentIndex++, new RdfMessageConverter(downloadGatekeeper));
        converters.add(currentIndex++, new TurtleMessageConverter(downloadGatekeeper));
        converters.add(currentIndex++, new NTriplesMessageConverter(downloadGatekeeper));
        converters.add(
                currentIndex++,
                new TsvMessageConverter<>(
                        UniRefEntryPair.class,
                        returnFieldConfig,
                        new EntryPairValueMapper<>(new UniRefEntryLightValueMapper()),
                        downloadGatekeeper));
        converters.add(
                currentIndex++,
                new XlsMessageConverter<>(
                        UniRefEntryPair.class,
                        returnFieldConfig,
                        new EntryPairValueMapper<>(new UniRefEntryLightValueMapper()),
                        downloadGatekeeper));
        return currentIndex;
    }

    private UniRefMessageConverterConfig() {}
}
