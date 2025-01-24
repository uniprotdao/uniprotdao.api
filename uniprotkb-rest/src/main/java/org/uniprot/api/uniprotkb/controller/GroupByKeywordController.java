package org.uniprot.api.uniprotkb.controller;

import static org.uniprot.api.rest.openapi.OpenAPIConstants.*;
import static org.uniprot.api.uniprotkb.controller.GroupByKeywordController.GROUP_BY_KEYWORD_RESOURCE;

import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.uniprot.api.uniprotkb.common.service.groupby.GroupByKeywordService;
import org.uniprot.api.uniprotkb.common.service.groupby.model.GroupByResult;
import org.uniprot.store.search.field.validator.FieldRegexConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping(value = GROUP_BY_KEYWORD_RESOURCE)
@RestController
@Validated
public class GroupByKeywordController extends GroupByController {
    static final String GROUP_BY_KEYWORD_RESOURCE = GROUPS + "/keyword";
    private final GroupByKeywordService uniProtKBGroupByKeywordService;

    @Autowired
    public GroupByKeywordController(GroupByKeywordService uniProtKBGroupByKeywordService) {
        this.uniProtKBGroupByKeywordService = uniProtKBGroupByKeywordService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            hidden = true,
            summary = GROUP_KEYWORD_OPERATION,
            description = GROUP_KEYWORD_OPERATION_DESC)
    @ApiResponse(
            content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GroupByResult.class)))
    public ResponseEntity<GroupByResult> getGroups(
            @Parameter(description = QUERY_UNIPROTKB_KEYWORD_DESCRIPTION)
                    @RequestParam(value = "query")
                    String query,
            @Parameter(description = GROUP_PARENT_DESCRIPTION)
                    @Pattern(
                            regexp = FieldRegexConstants.KEYWORD_ID_REGEX,
                            flags = {Pattern.Flag.CASE_INSENSITIVE},
                            message = "{groupby.keyword.invalid.id}")
                    @RequestParam(value = "parent", required = false)
                    String parent) {
        return new ResponseEntity<>(
                uniProtKBGroupByKeywordService.getGroupByResult(query, parent), HttpStatus.OK);
    }
}
