package org.uniprot.api.idmapping.common.request.uniprotkb;

import static org.uniprot.api.rest.openapi.OpenAPIConstants.*;

import javax.validation.constraints.Pattern;

import org.springdoc.api.annotations.ParameterObject;
import org.uniprot.api.rest.request.StreamRequest;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author uniprotdao

 */
@Data
@EqualsAndHashCode(callSuper = true)
@ParameterObject
public class UniProtKBIdMappingStreamRequest extends UniProtKBIdMappingBasicRequest
        implements StreamRequest {
    @Parameter(description = DOWNLOAD_DESCRIPTION)
    @Pattern(regexp = "^(?:true|false)$", message = "{search.uniprot.invalid.download}")
    private String download;
}
