package com.github.monosoul.fortuneteller.test.aspect;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import javax.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Profile("unit")
@Component
public class RequestContextHolderConfigurer implements BeanFactoryPostProcessor {

    private static final int LIMIT = 10;

    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        val requestAttributes = mock(ServletRequestAttributes.class);
        val request = mock(HttpServletRequest.class);

        doReturn(randomAlphanumeric(LIMIT)).when(request).getRemoteAddr();
        doReturn(request).when(requestAttributes).getRequest();

        RequestContextHolder.setRequestAttributes(requestAttributes);
    }
}
