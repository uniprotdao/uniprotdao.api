package org.uniprot.api.uniparc.request;

import org.springdoc.api.annotations.ParameterObject;
import org.uniprot.api.rest.request.IdsDownloadRequest;
import org.uniprot.api.rest.validation.ValidPostByIdsRequest;
import org.uniprot.store.config.UniProtDataType;

import lombok.Data;

/**
 * @author uniprotdao

 */
@Data
@ValidPostByIdsRequest(accessions = "upis", uniProtDataType = UniProtDataType.UNIPARC)
@ParameterObject
public class UniParcIdsPostRequest extends IdsDownloadRequest {
    private String upis;
    private String fields;

    private String format;

    public String getCommaSeparatedIds() {
        return this.upis;
    }
}
