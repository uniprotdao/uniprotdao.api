package org.uniprot.api.rest.validation;

import static org.mockito.Mockito.mock;

import java.util.stream.Stream;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.uniprot.store.config.idmapping.IdMappingFieldConfig;

/**
 * Created 26/02/2021
 *
 * @author uniprotdao
 */
class ValidIdTypeTest {
    private static ValidIdType.ValidIdTypeValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new ValidIdType.ValidIdTypeValidator();
        validator.initialize(null);
    }

    @Test
    void testInvalidValue() {
        ConstraintValidatorContextImpl context = mock(ConstraintValidatorContextImpl.class);
        String value = "invalid";
        Assertions.assertFalse(validator.isValid(value, context));
    }

    @ParameterizedTest
    @MethodSource("validDbNamesFromClient")
    void checkValidClientSpecifiedDb(String value) {
        Assertions.assertTrue(validator.isValid(value, null));
    }

    private static Stream<Arguments> validDbNamesFromClient() {
        return IdMappingFieldConfig.getAllIdMappingTypes().stream()
                .map(detail -> Arguments.of(detail.getName()));
    }
}
