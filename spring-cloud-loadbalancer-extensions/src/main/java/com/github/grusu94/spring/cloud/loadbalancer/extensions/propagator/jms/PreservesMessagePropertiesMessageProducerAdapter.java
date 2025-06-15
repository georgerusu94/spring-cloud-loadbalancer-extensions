package com.github.grusu94.spring.cloud.loadbalancer.extensions.propagator.jms;

import com.github.grusu94.spring.cloud.loadbalancer.extensions.context.ExecutionContext;
import com.github.grusu94.spring.cloud.loadbalancer.extensions.propagator.AbstractExecutionContextCopy;
import com.github.grusu94.spring.cloud.loadbalancer.extensions.propagator.Filter;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import jakarta.jms.*;
import java.util.Map;
import java.util.Set;

/**
 * Message producer adapter that copies the current {@link ExecutionContext} to the message
 * propagationProperties pre-filtering entry keys using the provided.
 */
@Slf4j
public class PreservesMessagePropertiesMessageProducerAdapter extends AbstractExecutionContextCopy<Message>
        implements MessageProducer {
    /**
     * The context entry key filter.
     */
    private final MessageProducer delegate;

    /**
     * constructor.
     *
     * @param delegate           the delegate message producer.
     * @param keysToPropagate    the context entry key filter.
     * @param extraStaticEntries The extra static entries to copy.
     * @param encoder            the message property encoder.
     */
    public PreservesMessagePropertiesMessageProducerAdapter(@NotNull MessageProducer delegate,
                                                            @NotNull Filter<String> keysToPropagate,
                                                            @NotNull Map<String, String> extraStaticEntries,
                                                            @NotNull MessagePropertyEncoder encoder) {
        super(keysToPropagate, (message, key, value) -> message.setStringProperty(encoder.encode(key), value), extraStaticEntries);
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeliveryDelay(long deliveryDelay) throws JMSException {
        delegate.setDeliveryDelay(deliveryDelay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getDeliveryDelay() throws JMSException {
        return delegate.getDeliveryDelay();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisableMessageID(boolean value) throws JMSException {
        delegate.setDisableMessageID(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getDisableMessageID() throws JMSException {
        return delegate.getDisableMessageID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDisableMessageTimestamp(boolean value) throws JMSException {
        delegate.setDisableMessageTimestamp(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getDisableMessageTimestamp() throws JMSException {
        return delegate.getDisableMessageTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDeliveryMode(int deliveryMode) throws JMSException {
        delegate.setDeliveryMode(deliveryMode);
    }

    @Override
    public int getDeliveryMode() throws JMSException {
        return delegate.getDeliveryMode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriority(int defaultPriority) throws JMSException {
        delegate.setPriority(defaultPriority);
    }

    @Override
    public int getPriority() throws JMSException {
        return delegate.getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeToLive(long timeToLive) throws JMSException {
        delegate.setTimeToLive(timeToLive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getTimeToLive() throws JMSException {
        return delegate.getTimeToLive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Destination getDestination() throws JMSException {
        return delegate.getDestination();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws JMSException {
        delegate.close();
    }

    /**
     * Trace copied entries.
     *
     * @param copiedEntries the copied entries.
     */
    private void trace(Set<Map.Entry<String, String>> copiedEntries) {
        log.trace("Context entries copied to message propagationProperties {}", copiedEntries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message) throws JMSException {
        trace(copy(message));
        delegate.send(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        trace(copy(message));
        delegate.send(message, deliveryMode, priority, timeToLive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Destination destination, Message message) throws JMSException {
        trace(copy(message));
        delegate.send(destination, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
        trace(copy(message));
        delegate.send(destination, message, deliveryMode, priority, timeToLive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message, CompletionListener completionListener) throws JMSException {
        trace(copy(message));
        delegate.send(message, completionListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Destination destination, Message message, CompletionListener completionListener) throws JMSException {
        trace(copy(message));
        delegate.send(destination, message, completionListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
        trace(copy(message));
        delegate.send(message, deliveryMode, priority, timeToLive, completionListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive, CompletionListener completionListener) throws JMSException {
        trace(copy(message));
        delegate.send(destination, message, deliveryMode, priority, timeToLive, completionListener);
    }
}
