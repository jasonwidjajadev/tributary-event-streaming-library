package tributary.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tributary.api.API;
import tributary.core.clients.admin.ConsumerCoordinator;
import tributary.core.clients.admin.ProducerCoordinator;
import tributary.core.clients.producer.Producer;
import tributary.core.common.Topic;

public class AdminClient<T, K, V> implements API<T, K, V> {
    private Map<String, Topic<T, K, V>> topics = new HashMap<>();
    private ProducerCoordinator<T, K, V> producerCoordinator = new ProducerCoordinator<>();
    private ConsumerCoordinator<T, K, V> consumerCoordinator = new ConsumerCoordinator<>();

    @Override
    public boolean addTopic(String topicId, Topic<T, K, V> topic) {
        if (topics.get(topicId) != null) {
            throw new IllegalArgumentException("Error: choose another name, topicId already exist!");
        }
        this.topics.put(topicId, topic);
        return true;
    }

    public boolean createPartition(String topicId, String partitionId) {
        Topic<T, K, V> topic = topics.get(topicId);
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
        Topic<T, K, V> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Error: TopicId not found!");
        }
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

    @Override
    public boolean addProducer(String producerId, Producer<T> producer, String allocation) {
        return producerCoordinator.addProducer(producerId, producer, allocation.toLowerCase());
    }

    @Override
    public boolean produceEvent(String producerId, String topicId, String event) {
        Topic<T, K, V> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Error: TopicId not found!");
        }
        return producerCoordinator.produceEvent(producerId, topic, topicId, event);
    }

    @Override
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId) {
        Topic<T, K, V> topic = topics.get(topicId);
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
    @Override
    public void showTopic(String topicId) {
        Topic<T, K, V> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Nothing to show, topic not found!");
        }
        topic.showTopic();
    }

    @Override
    public void showConsumerGroup(String groupId) {
        consumerCoordinator.showConsumerGroup(groupId);
    }

    // =========================================================================
    @Override
    public void produceEventsParallel(List<String> producerIds, List<String> topicId, List<String> event) {
        producerCoordinator.produceEventsParallel(topics, producerIds, topicId, event);
    }

    @Override
    public void consumeEventsParallel(List<String> consumerId, List<String> partitionId) {
        consumerCoordinator.consumeEventsParallel(consumerId, partitionId);
    }

    // =========================================================================
    @Override
    public boolean setRebalancingStrategy(String strategy, String groupId) {
        return consumerCoordinator.setGroupRebalancingStrategy(strategy, groupId);
    }

    @Override
    public boolean playback(String consumerId, String partitionId, int offset) {
        return consumerCoordinator.playback(consumerId, partitionId, offset);
    }
}
