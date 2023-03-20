package com.kafka.producer.java.com.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class CustomProducerCallback {
    public static void main(String[] args) {
        //0. 配置
        Properties properties = new Properties();
        //连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "public:9092");
        //指定对应的key和value序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        //1. 创建 kafka 生产者的配置对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        //2.send
        for (int i = 0; i < 3; i++) {
            kafkaProducer.send(new ProducerRecord<>("firstTopic", "this is " + i + "th value"), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {
                        System.out.println("topic: " + recordMetadata.topic() + " partition: " + recordMetadata.partition());
                    }
                }
            });
        }

        //3.close
        kafkaProducer.close();
    }
}
