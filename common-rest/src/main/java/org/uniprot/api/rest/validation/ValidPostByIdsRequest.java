package org.uniprot.api.rest.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.uniprot.core.util.Utils;
import org.uniprot.store.config.UniProtDataType;
import org.uniprot.store.config.returnfield.config.ReturnFieldConfig;
import org.uniprot.store.config.returnfield.factory.ReturnFieldConfigFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author uniprotdao

 */
@Constraint(validatedBy = ValidPostByIdsRequest.PostByIdsRequestValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPostByIdsRequest {
    String message() default "invalid ids download request";

    String accessions() default "accessions";

    String fields() default "fields";

    UniProtDataType uniProtDataType();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Slf4j
    class PostByIdsRequestValidator extends CommonIdsRequestValidator
            implements ConstraintValidator<ValidPostByIdsRequest, Object> {

        @Value("${ids.max.download.length}")
        private String maxDownloadLength;

        private UniProtDataType uniProtDataType;
        private String accessions;
        private String fields;
        private ReturnFieldConfig returnFieldConfig;

        @Autowired private HttpServletRequest request;

        @Override
        public void initialize(ValidPostByIdsRequest constraintAnnotation) {
            this.uniProtDataType = constraintAnnotation.uniProtDataType();
            this.accessions = constraintAnnotation.accessions();
            this.fields = constraintAnnotation.fields();
            this.returnFieldConfig = ReturnFieldConfigFactory.getReturnFieldConfig(uniProtDataType);
            SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {
            boolean isValid;
            try {
                String idList = BeanUtils.getProperty(value, this.accessions);
                String fieldsValue = BeanUtils.getProperty(value, this.fields);

                boolean isValidReturnFields =
                        isValidReturnFields(fieldsValue, this.returnFieldConfig, context);

                boolean isIdListValid =
                        validateIdsAndPopulateErrorMessage(
                                idList, getMaxDownloadLength(), getDataType(), context);
                isValid = isValidReturnFields && isIdListValid;

                if (!isValid && context != null) {
                    context.disableDefaultConstraintViolation();
                }
            } catch (Exception e) {
                log.warn("Error during validation {}", e.getMessage());
                isValid = false;
            }

            return isValid;
        }

        @Override
        boolean validateIdsAndPopulateErrorMessage(
                String commaSeparatedIds,
                int length,
                UniProtDataType dataType,
                ConstraintValidatorContext context) {
            boolean isValid = true;

            if (Utils.nullOrEmpty(commaSeparatedIds)) {
                isValid = false;
                buildRequiredFieldMessage(
                        context, "'" + this.accessions + "' is a required parameter");
            }
            return isValid
                    && super.validateIdsAndPopulateErrorMessage(
                            commaSeparatedIds, length, dataType, context);
        }

        void buildRequiredFieldMessage(ConstraintValidatorContext context, String message) {
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        int getMaxDownloadLength() {
            return Integer.parseInt(maxDownloadLength);
        }

        UniProtDataType getDataType() {
            return this.uniProtDataType;
        }

        @Override
        HttpServletRequest getHttpServletRequest() {
            return this.request;
        }
    }
}
