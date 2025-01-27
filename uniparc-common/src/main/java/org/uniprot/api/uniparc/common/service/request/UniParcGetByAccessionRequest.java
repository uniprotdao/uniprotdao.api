package org.uniprot.api.uniparc.common.service.request;

import static org.uniprot.api.rest.openapi.OpenAPIConstants.*;
import static org.uniprot.api.rest.openapi.OpenAPIConstants.FIELDS_UNIPARC_DESCRIPTION;
import static org.uniprot.api.rest.openapi.OpenAPIConstants.FIELDS_UNIPARC_EXAMPLE;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springdoc.api.annotations.ParameterObject;
import org.uniprot.api.rest.validation.ValidReturnFields;
import org.uniprot.store.config.UniProtDataType;
import org.uniprot.store.search.field.validator.FieldRegexConstants;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author uniprotdao

 */
@Data
@EqualsAndHashCode(callSuper = true)
@ParameterObject
public class UniParcGetByAccessionRequest extends UniParcGetByIdRequest {

    @Pattern(
            regexp = FieldRegexConstants.UNIPROTKB_ACCESSION_REGEX,
            flags = {Pattern.Flag.CASE_INSENSITIVE},
            message = "{search.invalid.accession.value}")
    @NotNull(message = "{search.required}")
    @Parameter(description = ACCESSION_UNIPARC_DESCRIPTION, example = ACCESSION_UNIPARC_EXAMPLE)
    private String accession;

    @Parameter(description = FIELDS_UNIPARC_DESCRIPTION, example = FIELDS_UNIPARC_EXAMPLE)
    @ValidReturnFields(uniProtDataType = UniProtDataType.UNIPARC)
    private String fields;
}
