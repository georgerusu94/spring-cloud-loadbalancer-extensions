package com.example.spring.cloud.loadbalancer.extensions.propagator.feign;

import com.example.spring.cloud.loadbalancer.extensions.context.ExecutionContext;
import com.example.spring.cloud.loadbalancer.extensions.propagator.AbstractExecutionContextCopy;
import com.example.spring.cloud.loadbalancer.extensions.propagator.Filter;
import com.example.spring.cloud.loadbalancer.extensions.propagator.PatternFilter;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Feign request interceptor that copies current {@link ExecutionContext} entries to the feign headers pre-filtering
 * the header names using the provided {@link #urlFilter}.
 */
@Slf4j
public class PreservesHttpHeadersFeignInterceptor extends AbstractExecutionContextCopy<RequestTemplate>
        implements RequestInterceptor {

    private final PatternFilter urlFilter;

    /**
     * constructor.
     *
     * @param urlFilter          The url filter.
     * @param filter             The context entry key filter.
     * @param extraStaticEntries The extra static entries to copy.
     */
    public PreservesHttpHeadersFeignInterceptor(@NotNull PatternFilter urlFilter,
                                                @NotNull Filter<String> filter,
                                                @NotNull Map<String, String> extraStaticEntries) {
        super(filter, RequestTemplate::header, extraStaticEntries);
        this.urlFilter = urlFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(RequestTemplate template) {
        String url = template.url();
        if (urlFilter.accept(url)) {
            Set<Entry<String, String>> propagatedAttributes = copy(template);
            log.trace("Propagated outbound headers {} for url [{}].", propagatedAttributes, url);
        } else {
            log.trace("Propagation disabled for url [{}]", url);
        }
    }
}
