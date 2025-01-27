package org.uniprot.api.idmapping.common.request;

import static org.uniprot.api.rest.openapi.OpenAPIConstants.*;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.uniprot.api.rest.validation.ValidCommaSeparatedItemsLength;
import org.uniprot.api.rest.validation.ValidIdType;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

/**
 * Created 16/02/2021
 *
 * @author uniprotdao
 */
@Data
@ValidFromAndTo
public class IdMappingJobRequest implements Serializable {
    private static final long serialVersionUID = 3950807397142678483L;

    @NotNull(message = "{search.required}")
    @Parameter(description = FROM_IDMAPPING_JOB_DESCRIPTION, example = FROM_IDMAPPING_JOB_EXAMPLE)
    @ValidIdType(message = "{idmapping.invalid.from}")
    private String from;

    @NotNull(message = "{search.required}")
    @Parameter(description = TO_IDMAPPING_JOB_DESCRIPTION, example = TO_IDMAPPING_JOB_EXAMPLE)
    @ValidIdType(message = "{idmapping.invalid.to}")
    private String to;

    @NotNull(message = "{search.required}")
    @Parameter(description = IDS_IDMAPPING_JOB_DESCRIPTION, example = IDS_IDMAPPING_JOB_EXAMPLE)
    @ValidCommaSeparatedItemsLength
    private String ids;

    @Parameter(
            description = TAX_ID_IDMAPPING_JOB_DESCRIPTION,
            example = TAX_ID_IDMAPPING_JOB_EXAMPLE)
    private String taxId;
}
