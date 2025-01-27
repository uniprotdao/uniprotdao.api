package org.uniprot.api.async.download.messaging.config.uniprotkb;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.uniprot.api.async.download.messaging.config.common.QueueConsumerConfigUtils;
import org.uniprot.api.async.download.messaging.consumer.uniprotkb.UniProtKBMessageConsumer;
import org.uniprot.api.async.download.model.request.DownloadRequestToArrayConverter;
import org.uniprot.api.async.download.model.request.uniprotkb.UniProtKBDownloadRequest;
import org.uniprot.api.rest.request.HashGenerator;

/**
 * Initialisation code for Rabbit MQ to be used by both producer and consumer
 *
 * @author uniprotdao

 */
@Configuration
public class UniProtKBRabbitMQConfig {

    @Bean
    public Exchange uniProtKBDownloadExchange(
            UniProtKBAsyncDownloadQueueConfigProperties asyncDownloadQConfigProps) {
        return ExchangeBuilder.directExchange(asyncDownloadQConfigProps.getExchangeName())
                .durable(asyncDownloadQConfigProps.isDurable())
                .build();
    }

    /**
     * Relationship among downloadQueue(DQ), retryQueue(RQ) and undeliveredQueue(UQ) : Producer
     * writes message to the exchange and the consumer receives the message from DQ. If the consumer
     * processes the message successfully then the message is removed from DQ. Else the message is
     * sent to dead letter queue(RQ) of DQ. RQ has a. no consumer b. ttl of n millis c. DLQ is DQ.
     * So after passing n millis, the message of RQ is sent to its DLQ (DQ). The consumer picks the
     * message from DQ. If the max retry of the message is not reached, the message is reprocessed.
     * Else the message is sent to UQ.
     */
    @Bean
    public Queue uniProtKBDownloadQueue(
            UniProtKBAsyncDownloadQueueConfigProperties asyncDownloadQConfigProps) {
        return QueueBuilder.durable(asyncDownloadQConfigProps.getQueueName())
                .deadLetterExchange(asyncDownloadQConfigProps.getExchangeName())
                .deadLetterRoutingKey(asyncDownloadQConfigProps.getRetryQueueName())
                .ttl(asyncDownloadQConfigProps.getTtlInMillis())
                .quorum()
                .build();
    }

    @Bean
    Queue uniProtKBRetryQueue(
            UniProtKBAsyncDownloadQueueConfigProperties asyncDownloadQConfigProps) {
        return QueueBuilder.durable(asyncDownloadQConfigProps.getRetryQueueName())
                .ttl(asyncDownloadQConfigProps.getRetryDelayInMillis())
                .deadLetterExchange(asyncDownloadQConfigProps.getExchangeName())
                .deadLetterRoutingKey(asyncDownloadQConfigProps.getRoutingKey())
                .quorum()
                .build();
    }

    // queue where failed messages after maximum retries will end up
    @Bean
    Queue uniProtKBUndeliveredQueue(
            UniProtKBAsyncDownloadQueueConfigProperties asyncDownloadQConfigProps) {
        return QueueBuilder.durable(asyncDownloadQConfigProps.getRejectedQueueName())
                .quorum()
                .build();
    }

    @Bean
    public Binding uniProtKBDownloadBinding(
            Queue uniProtKBDownloadQueue,
            Exchange uniProtKBDownloadExchange,
            UniProtKBAsyncDownloadQueueConfigProperties asyncDownloadQConfigProps) {
        return BindingBuilder.bind(uniProtKBDownloadQueue)
                .to((DirectExchange) uniProtKBDownloadExchange)
                .with(asyncDownloadQConfigProps.getRoutingKey());
    }

    @Bean
    Binding uniProtKBRetryBinding(Queue uniProtKBRetryQueue, Exchange uniProtKBDownloadExchange) {
        return BindingBuilder.bind(uniProtKBRetryQueue)
                .to((DirectExchange) uniProtKBDownloadExchange)
                .with(uniProtKBRetryQueue.getName());
    }

    @Bean
    Binding uniProtKBUndeliveredBinding(
            Queue uniProtKBUndeliveredQueue, Exchange uniProtKBDownloadExchange) {
        return BindingBuilder.bind(uniProtKBUndeliveredQueue)
                .to((DirectExchange) uniProtKBDownloadExchange)
                .with(uniProtKBUndeliveredQueue.getName());
    }

    @Bean
    public MessageListenerContainer uniProtKBMessageListenerContainer(
            ConnectionFactory connectionFactory,
            UniProtKBMessageConsumer uniProtKBMessageConsumer,
            UniProtKBAsyncDownloadQueueConfigProperties configProps) {
        return QueueConsumerConfigUtils.getSimpleMessageListenerContainer(
                connectionFactory, uniProtKBMessageConsumer, configProps);
    }

    @Bean
    public HashGenerator<UniProtKBDownloadRequest> uniProtKBDownloadHashGenerator(
            @Value("${async.download.uniprotkb.hash.salt}") String hashSalt) {
        return new HashGenerator<>(new DownloadRequestToArrayConverter<>(), hashSalt);
    }
}
