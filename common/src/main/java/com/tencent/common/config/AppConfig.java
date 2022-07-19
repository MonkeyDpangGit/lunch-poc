package com.tencent.common.config;

import org.springframework.context.annotation.Bean;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: AppConfig
 */
@Configuration
public class AppConfig {

    @Bean("projectValidator")
    public Validator getValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }
}
