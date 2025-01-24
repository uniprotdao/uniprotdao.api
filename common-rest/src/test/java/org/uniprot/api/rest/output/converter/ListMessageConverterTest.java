package org.uniprot.api.rest.output.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * @author lgonzales
 * @since 2020-04-03
 */
class ListMessageConverterTest {

    @Test
    void canWriteEntity() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ListMessageConverter messageConverter = new ListMessageConverter();
        messageConverter.writeEntity("entity", outputStream);

        String result = outputStream.toString("UTF-8");
        assertNotNull(result);
        assertEquals("entity\n", result);
    }
}
