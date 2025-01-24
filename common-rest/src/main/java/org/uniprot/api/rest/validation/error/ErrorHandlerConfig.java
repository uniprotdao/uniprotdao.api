package org.uniprot.api.rest.validation.error;

import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * This class is responsible to configure MessageSource (message.properties), it also make sure that
 * we still keep hibernate validation message.
 *
 * @author lgonzales
 */
@Configuration
public class ErrorHandlerConfig {

    @Primary
    @Bean
    public Validator validatorFactory(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.addBasenames(
                "classpath:org.hibernate.validator.ValidationMessages",
                "classpath:message",
                "classpath:common-message");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }
}
