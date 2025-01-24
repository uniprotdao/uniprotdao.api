package org.uniprot.api.rest.output.converter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.uniprot.api.rest.output.UniProtMediaType;
import org.uniprot.api.rest.validation.error.ErrorInfo;

/**
 * This class is responsible to write error message body for http status BAD REQUESTS (400)
 *
 * @author lgonzales
 */
public class ErrorMessageConverter extends AbstractGenericHttpMessageConverter<ErrorInfo> {

    public ErrorMessageConverter() {
        super(
                UniProtMediaType.FF_MEDIA_TYPE,
                UniProtMediaType.FASTA_MEDIA_TYPE,
                UniProtMediaType.GFF_MEDIA_TYPE,
                UniProtMediaType.LIST_MEDIA_TYPE,
                UniProtMediaType.TSV_MEDIA_TYPE,
                UniProtMediaType.OBO_MEDIA_TYPE,
                UniProtMediaType.MARKDOWN_MEDIA_TYPE);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ErrorInfo.class);
    }

    @Override
    protected void writeInternal(
            ErrorInfo errorInfo, Type type, HttpOutputMessage httpOutputMessage)
            throws IOException, HttpMessageNotWritableException {
        if (errorInfo.getMessages() != null) {
            OutputStream outputStream = httpOutputMessage.getBody();
            String header = "Error messages" + System.lineSeparator();
            outputStream.write(header.getBytes(StandardCharsets.UTF_8));
            String responseBody =
                    errorInfo.getMessages().stream()
                            .collect(Collectors.joining(System.lineSeparator()));
            outputStream.write(responseBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
    }

    @Override
    protected ErrorInfo readInternal(
            Class<? extends ErrorInfo> aClass, HttpInputMessage httpInputMessage)
            throws HttpMessageNotReadableException {
        throw new UnsupportedOperationException("");
    }

    @Override
    public ErrorInfo read(Type type, Class<?> aClass, HttpInputMessage httpInputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("");
    }
}
