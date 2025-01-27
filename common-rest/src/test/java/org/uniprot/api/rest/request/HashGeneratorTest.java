package org.uniprot.api.rest.request;

import java.util.function.Function;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * @author uniprotdao

 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HashGeneratorTest {
    private static final String SALT_STR = "TEST_SALT";
    private HashGenerator generator;

    @BeforeAll
    void testSetup() {
        generator = new HashGenerator<>(new ToArrayConverter(), SALT_STR);
    }

    @Test
    void testTwoHashesOfTwoRequests() {
        String req1 = "this is a request";
        String req1Hash = generator.generateHash(req1);
        Assertions.assertNotNull(req1Hash);
        String req2 = "this is a request";
        String req2Hash = generator.generateHash(req2);
        Assertions.assertNotNull(req2Hash);
        Assertions.assertEquals(req1Hash, req2Hash);
    }

    @Test
    void testTwoHashesOfTwoRequestsWithIdsInDifferentOrder() {
        String req1 = "4,2,1,3";
        String req1Hash = generator.generateHash(req1);
        Assertions.assertNotNull(req1Hash);
        String req2 = "1,2,3,4";
        String req2Hash = generator.generateHash(req2);
        Assertions.assertNotNull(req2Hash);
        Assertions.assertNotEquals(req1Hash, req2Hash);
    }

    @Test
    void testTwoHashesOfTwoRequestsWithIdsOfSameCharacters() {
        String req1 = "4,2,1,3";
        String req1Hash = generator.generateHash(req1);
        Assertions.assertNotNull(req1Hash);
        // create another request object with same fields values
        String req2 = "4213";
        String req2Hash = generator.generateHash(req2);
        Assertions.assertNotNull(req2Hash);
        Assertions.assertNotEquals(req1Hash, req2Hash);
    }

    private static class ToArrayConverter implements Function<String, char[]> {
        @Override
        public char[] apply(String request) {
            return request.toCharArray();
        }
    }
}
