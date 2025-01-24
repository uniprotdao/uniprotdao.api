package org.uniprot.api.common.exception;

/**
 * @author uniprotdao

 */
public class ForbiddenRequestException extends RuntimeException {
    public ForbiddenRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenRequestException(String message) {
        super(message);
    }
}
