package com.github.grusu94.spring.cloud.loadbalancer.extensions.support.strict;

import com.github.grusu94.spring.cloud.loadbalancer.extensions.support.StrictMetadataMatcherConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StrictMetadataMatcherConfig.class, properties = {
        "loadbalancer.extensions.rule.strict-metadata-matcher.enabled=false"})
public class StrictMetadataMatcherGloballyDisabledTest {

    @Inject
    ApplicationContext applicationContext;

    @Test()
    public void should_not_be_instantiated() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> applicationContext.getBean(ServiceInstanceListSupplier.class));
    }
}