package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//le groupe id n'est pas obligatoire par contre le client id c'est obligatoire
public class Producer {
    private Properties properties = new Properties();
    private int counter = 0;
    private static final String TOPIC_NAME = "firstTopic";

    public Producer() throws JsonProcessingException {
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"client-producer-1");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        KafkaProducer<String,String> kafkaProducer = new KafkaProducer<String, String>(properties);


        Random random = new Random();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{

            // Exemple de message JSON à produire
            String jsonString = "{\"nom\":\"John Doe\",\"age\":30}";

            // Conversion du message JSON en objet Java
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(jsonString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            // Récupération des valeurs des champs "nom" et "age"
            String nom = jsonNode.get("nom").asText();
            int age = jsonNode.get("age").asInt();

            // Création du message à envoyer dans Kafka
            String message = String.format("Nom: %s, Age: %d", nom, age);
            String key = String.valueOf(++counter);
            String value = String.valueOf(random.nextDouble()*9999);
            kafkaProducer.send(new ProducerRecord<String,String>("firstTopic",key,message),((recordMetadata, e) ->
            {
            System.out.println("the msg is : "+message + " Partition is : "+recordMetadata.partition() + "offset is : "+recordMetadata.offset());
            }));
        },1000,1000, TimeUnit.MILLISECONDS);



    }
}
