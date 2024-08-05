package tributary.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tributary.api.API;
import tributary.core.clients.admin.Broker;
import tributary.core.clients.admin.ConsumerCoordinator;
import tributary.core.clients.admin.ProducerCoordinator;
import tributary.core.clients.producer.Producer;
import tributary.core.common.Topic;

public class AdminClient<T, K, V> implements API<T, K, V> {
    private int counter = 0;
    private Broker broker = new Broker();
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

    // // =========================================================================
    @Override
    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing) {
        return consumerCoordinator.createConsumerGroup(groupId, topicId, rebalancing.toLowerCase());
    }

    @Override
    public boolean createConsumer(String groupId, String consumerId) {
        return consumerCoordinator.createConsumer(groupId, consumerId, topics);
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

    // // =========================================================================

    // //TODO consume event <consumer> <partition>

    // //TODO consume events <consumer> <partition> <number of events>

    // // =========================================================================
    // //TODO not yet fully implemented
    @Override
    public void showTopic(String topicId) {
        Topic<T, K, V> topic = topics.get(topicId);
        if (topic == null) {
            throw new IllegalArgumentException("Nothing to show, topic not found!");
        }
        topic.showTopic();
    }

    // //TODO not yet fully implemented
    @Override
    public void showConsumerGroup(String groupId) {
        consumerCoordinator.showConsumerGroup(groupId);
    }

    // //TODO parallel produce (<producer>, <topic>, <event>)

    // //TODO parallel consume (<consumer>, <partition>

    // //TODO set consumer group rebalancing <group> <rebalancing>

    // //TODO playback <consumer> <partition> <offset>

}
