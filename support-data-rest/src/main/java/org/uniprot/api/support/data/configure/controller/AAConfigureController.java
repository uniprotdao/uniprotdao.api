package org.uniprot.api.support.data.configure.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.uniprot.api.rest.openapi.OpenAPIConstants.*;
import static org.uniprot.api.rest.openapi.OpenAPIConstants.TAG_CONFIG;
import static org.uniprot.api.rest.openapi.OpenAPIConstants.TAG_CONFIG_DESC;
import static org.uniprot.api.support.data.configure.response.AdvancedSearchTerm.PATH_PREFIX_FOR_AUTOCOMPLETE_SEARCH_FIELDS;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uniprot.api.support.data.configure.response.AdvancedSearchTerm;
import org.uniprot.api.support.data.configure.response.UniProtReturnField;
import org.uniprot.api.support.data.configure.service.AAConfigureService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author uniprotdao

 */
@Tag(name = TAG_CONFIG, description = TAG_CONFIG_DESC)
@RestController
@RequestMapping("/configure")
public class AAConfigureController {
    private final AAConfigureService service;

    public AAConfigureController(AAConfigureService service) {
        this.service = service;
    }

    @Operation(
            hidden = true,
            summary = CONFIG_UNIRULE_FIELDS_OPERATION,
            responses = {
                @ApiResponse(
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(
                                                                    implementation =
                                                                            UniProtReturnField
                                                                                    .class)))
                        })
            })
    @GetMapping("/unirule/result-fields")
    public List<UniProtReturnField> getUniRuleResultFields() {
        return service.getUniRuleResultFields();
    }

    @Operation(
            hidden = true,
            summary = CONFIG_UNIRULE_SEARCH_OPERATION,
            responses = {
                @ApiResponse(
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(
                                                                    implementation =
                                                                            AdvancedSearchTerm
                                                                                    .class)))
                        })
            })
    @GetMapping("/unirule/search-fields")
    public List<AdvancedSearchTerm> getUniRuleSearchFields() {
        return service.getUniRuleSearchItems(PATH_PREFIX_FOR_AUTOCOMPLETE_SEARCH_FIELDS);
    }

    @Operation(
            hidden = true,
            summary = CONFIG_ARBA_SEARCH_OPERATION,
            responses = {
                @ApiResponse(
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(
                                                                    implementation =
                                                                            UniProtReturnField
                                                                                    .class)))
                        })
            })
    @GetMapping("/arba/result-fields")
    public List<UniProtReturnField> getArbaResultFields() {
        return service.getArbaResultFields();
    }

    @Operation(
            hidden = true,
            summary = CONFIG_ARBA_FIELDS_OPERATION,
            responses = {
                @ApiResponse(
                        content = {
                            @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array =
                                            @ArraySchema(
                                                    schema =
                                                            @Schema(
                                                                    implementation =
                                                                            AdvancedSearchTerm
                                                                                    .class)))
                        })
            })
    @GetMapping("/arba/search-fields")
    public List<AdvancedSearchTerm> getArbaSearchFields() {
        return service.getArbaSearchItems(PATH_PREFIX_FOR_AUTOCOMPLETE_SEARCH_FIELDS);
    }
}
