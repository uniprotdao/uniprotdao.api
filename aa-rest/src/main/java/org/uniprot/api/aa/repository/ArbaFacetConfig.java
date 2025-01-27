package org.uniprot.api.aa.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.uniprot.api.common.repository.search.facet.FacetConfig;
import org.uniprot.api.common.repository.search.facet.FacetProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author uniprotdao

 */
@Component
@Getter
@Setter
@PropertySource("classpath:arba.facet.properties")
@ConfigurationProperties(prefix = "facet")
public class ArbaFacetConfig extends FacetConfig {
    private Map<String, FacetProperty> arba = new HashMap<>();

    @Override
    public Collection<String> getFacetNames() {
        return this.arba.keySet();
    }

    @Override
    public Map<String, FacetProperty> getFacetPropertyMap() {
        return this.arba;
    }
}
