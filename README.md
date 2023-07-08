# Mensageria Customizada

Está lib tem como objetivo facilitar o processo de implementação de mensageria aos
projetos template da 

Para adicionar mensageria ao seu projeto, basta adicionar a lib:

````
    <dependency>
        <groupId>com.nortetec</groupId>
        <artifactId>custom-messaging</artifactId>
        <version>1.0.0</version>
    </dependency>
````


Depois, deve-se encontrar a classe de configuração da aplicação (ConfiguracaoAplicacao.java) que se encontra no modulo api das aplicações, e adicione os seguintes codigos:

Para Kafka: ``@EnableCustomKafka``
Para RabbitMq: ``@EnableCustomRabbitMQ``


## Configuração

Para que as aplicações possam consumir e produzir eventos, se faz necessario a criacao
de um arquivo de configuração chamado ```mensageria.properties```.

Com esse arquivo é possivel configurar as seguintes propriedades:

1. Para o utilização do KAFKA
```
mensageria.kafka.host=<HOST>
mensageria.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
mensageria.kafka.consumer.value-deserializer=org.apache.kafka.common.serialization.StringDeserializer

mensageria.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
mensageria.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
```

2. Para utilização do RABBITMQ
```
mensageria.rabbitmq.host=<HOST>
mensageria.rabbitmq.port=<PORTA>
mensageria.rabbitmq.usuario=<USUARIO_APLICACAO>
mensageria.rabbitmq.senha=<SENHA>
mensageria.rabbitmq.projetoTag=<NOME_PROJETO_MAVEN>
```

## Logs

Para ignorar os logs gerados no weblogic, adicione no arquivo de logs
os comandos abaixo:

```
log4j.logger.org.springframework.amqp.rabbit.listener=off
log4j.logger.org.springframework.kafka=off
log4j.logger.org.apache.kafka.clients=off
```


## RabbitMQ

Para criação de um *consumidor* RabbitMQ basta usar o modelo abaixo:

```
    @RabbitListener(queues = "<NOME-DA-FILA>")
    public void consumeMessage(@Payload Message mensagem) {
        logger.info("Payload recebido: {} ", new String(mensagem.getBody()));
    }
```

Para criação de um *produtor* RabbitMQ basta usar o modelo abaixo:

```
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String order) {
        rabbitTemplate.convertAndSend(<NOME-DA-FILA>, order);
    }
```

## Kafka

Para criação de um *consumidor* Kafka basta usar o modelo abaixo:

```

    @KafkaListener(topics = "<NOME-DO-TOPICO>", groupId = "<NOME-DA-APLICACAO>")
    public void receiveMessage(ConsumerRecord<String, String> payload) {
        logger.info("Tópico: {}", payload.topic());
        logger.info("key: {}", payload.key());
        logger.info("Headers: {}", payload.headers());
        logger.info("Partion: {}", payload.partition());
        logger.info("Order: {}", payload.value());
        logger.info("Aplicacao: {}", getProjeto());
    }
```

Para criação de um *produtor* Kafka basta usar o modelo abaixo:

```
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String evento){
        kafkaTemplate.send("<NOME-DO-TOPICO>", evento);
    }
```