package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Consumer
{
    private Properties properties = new Properties();
    public Consumer()
    {
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"test-group-1");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,30000);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String,String>(properties);
        kafkaConsumer.subscribe(Collections.singletonList("firstTopic"));
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
            System.out.println("-------------------------------");
            ConsumerRecords<String,String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(1000)); // s'il ecrit 20 msg dans une seconde alors on prend tous les 20 msg  , par contre si on mit 500 alors on va prendre que 10msg car on va s'interesser a la moitie
            consumerRecords.forEach(msg->{
                System.out.println("Key : "+msg.key() + " Value : "+msg.value()+" Offset : "+msg.offset());
            });
        },1000,1000, TimeUnit.MILLISECONDS);
    }
}
