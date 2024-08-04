package tributary.core;

import java.util.HashMap;
import java.util.List;
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

    public boolean createTopic(String topicId, String type) {
        if (topics.containsKey(topicId)) {
            throw new IllegalArgumentException("Error: ID already exists!");
        }
        if (type.equalsIgnoreCase("string")) {
            topics.put(topicId, new Topic<String>(topicId, "String"));
        } else if (type.equalsIgnoreCase("integer")) {
            topics.put(topicId, new Topic<Integer>(topicId, counter));
        } else {
            throw new IllegalArgumentException(
                    "Unrecognized type: " + type + ". Allowed types are 'String' and 'Integer'.");
        }
        incrementCounter();
        return true;
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
            throw new IllegalArgumentException("Error: PartitionId already exists!");
        }
    }

    // =========================================================================
    @Override
    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing) {
        Topic<?> topic = topics.get(topicId);
        return consumerCoordinator.createConsumerGroup(groupId, topicId, rebalancing.toLowerCase(), topic);
    }

    @Override
    public boolean createConsumer(String groupId, String consumerId) {
        return consumerCoordinator.createConsumer(groupId, consumerId);
    }

    @Override
    public List<String> deleteConsumer(String consumerId) {
        return consumerCoordinator.deleteConsumer(consumerId);
    }

    // =========================================================================
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

    @Override
    public boolean createProducer(String producerId, String type, String allocation) {
        Object instance;
        try {
            instance = createInstance(type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error: Unable to create instance of class " + type, e);
        }
        return producerCoordinator.createProducer(producerId, instance, allocation.toLowerCase());
    }

    @Override
    public boolean produceEvent(String producerId, String topicId, String event) {
        Topic<?> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Error: TopicId not found!");
        }
        return producerCoordinator.produceEvent(producerId, topic, topicId, event);
    }

    @Override
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId) {
        Topic<?> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Error: TopicId not found!");
        }
        return producerCoordinator.produceEvent(producerId, topic, topicId, event, partitionId);
    }

    // =========================================================================

    @Override
    public boolean consumeSingleEvent(String consumerId, String partitionId) {
        return consumerCoordinator.consumeSingleEvent(consumerId, partitionId);
    }

    @Override
    public boolean consumeMultipleEvents(String consumerId, String partitionId, int numOfEvents) {
        return consumerCoordinator.consumeMultipleEvents(consumerId, partitionId, numOfEvents);
    }

    // =========================================================================
    //TODO not yet fully implemented
    @Override
    public void showTopic(String topicId) {
        Topic<?> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Nothing to show, topic not found!");
        }
        topic.showTopic();
    }

    @Override
    public void showConsumerGroup(String groupId) {
        consumerCoordinator.showConsumerGroup(groupId);
    }

    //TODO parallel produce (<producer>, <topic>, <event>)

    //TODO parallel consume (<consumer>, <partition>

    @Override
    public boolean setRebalancingStrategy(String strategy, String groupId) {
        return consumerCoordinator.setGroupRebalancingStrategy(strategy, groupId);
    }

    @Override
    public boolean playback(String consumerId, String partitionId, int offset) {
        return consumerCoordinator.playback(consumerId, partitionId, offset);
    }
}
