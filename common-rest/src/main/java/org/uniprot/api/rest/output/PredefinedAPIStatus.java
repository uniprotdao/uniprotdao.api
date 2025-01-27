package org.uniprot.api.rest.output;

import lombok.Getter;

/**
 * @author uniprotdao

 */
@Getter
public enum PredefinedAPIStatus {
    FACET_WARNING(20, "Filters are not supported for mapping results with more than %d IDs"),
    ENRICHMENT_WARNING(
            21,
            "UniProt data enrichment is not supported for mapping results with more than %d \"mapped to\" IDs"),
    LIMIT_EXCEED_ERROR(
            40,
            "Id Mapping API is not supported for mapping results with more than %d \"mapped to\" IDs"),

    LEADING_WILDCARD_IGNORED(
            41,
            "Leading wildcard (*, ?) was removed for this search. Please check the help page for more information on using wildcards on queries."),
    SERVER_ERROR(50, "Internal server error");

    private final String message;
    private final int code;

    PredefinedAPIStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getErrorMessage(int limit) {
        return String.format(message, limit);
    }
}
