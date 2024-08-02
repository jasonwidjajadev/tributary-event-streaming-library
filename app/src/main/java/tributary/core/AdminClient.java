package tributary.core;

import java.util.HashMap;
import java.util.Map;

import tributary.api.API;
import tributary.core.clients.admin.Broker;
import tributary.core.clients.admin.ConsumerCoordinator;
import tributary.core.clients.admin.ProducerCoordinator;
import tributary.core.common.Topic;

public class AdminClient implements API {
    private int counter = 0;
    private Map<String, Topic<?>> topics = new HashMap<>();

    private Broker broker = new Broker();
    private ProducerCoordinator producerCoordinator = new ProducerCoordinator();
    private ConsumerCoordinator consumerCoordinator = new ConsumerCoordinator();

    public boolean createTopic(String id, String type) {
        Object instance = createInstance(type);
        try {
            instance = createInstance(type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: Unable to create instance of class " + type, e);
        }

        if (topics.containsKey(id)) {
            throw new IllegalArgumentException("Error: ID already exists!");
        }

        topics.put(id, new Topic<>(id, instance));
        incrementCounter();
        return true;
    }

    private Object createInstance(String type) {
        switch (type.toLowerCase()) {
        case "string":
            return "String" + counter;
        case "integer":
            return counter;
        default:
            throw new IllegalArgumentException(
                    "Unrecognized type: " + type + ". Allowed types are 'String' and 'Integer'.");
        }
    }

    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter++;
    }

    public boolean createPartition(String topicId, String partitionId) {
        Topic<?> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Error: TopicId not found!");
        }
        if (topic.createPartition(partitionId)) {
            return true;
        } else {
            throw new IllegalArgumentException("Error: PartitionId already exist!");
        }
    }

    // =========================================================================
    @Override
    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing) {
        return consumerCoordinator.createConsumerGroup(groupId, topicId, rebalancing);
    }

    @Override
    public boolean createConsumer(String groupId, String consumerId) {
        return consumerCoordinator.createConsumer(groupId, consumerId);
    }

    @Override
    public String deleteConsumer(String consumerId) {
        return consumerCoordinator.deleteConsumer(consumerId);
    }

    // =========================================================================
    @Override
    public boolean createProducer(String producerId, String type, String allocation) {
        return producerCoordinator.createProducer(producerId, type, allocation.toUpperCase());
    }

    @Override
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId) {
        return producerCoordinator.produceEvent(producerId, topicId, event, partitionId);
    }

    // =========================================================================
    @Override
    //TODO max -> not yet implemented
    public void showTopic(String topicId) {
        Topic<?> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Nothing to show, topic not found!");
        }
        topic.showTopic(topicId);
    }

    //TODO max -> not yet implemented
    @Override
    public void showConsumerGroup(String groupId) {
        consumerCoordinator.showConsumerGroup(groupId);
    }
}
/*
tributary
├── api
│   ├── API.java
│   └── APIFactory.java
├── cli
│   └── TributaryCLI.java
└── core
    ├── AdminClient.java
    ├── clients
    │   ├── admin
    │   │   ├── Broker.java
    │   │   ├── ProducerCoordinator.java
    │   │   └── ConsumerCoordinator.java
    │   ├── consumer
    │   │   ├── Consumer.java
    │   │   ├── ConsumerGroups.java
    │   │   ├── ConsumerRecord.java
    │   │   └── internals
    │   │       ├── RangeRebalancing.java
    │   │       ├── RebalancingStrategy.java
    │   │       └── RoundRobinRebalancing.java
    │   └── producer
    │       ├── Producer.java
    │       └── ProducerRecord.java
    └── common
        ├── Headers.java
        ├── Partition.java
        └── Topic.java
*/
