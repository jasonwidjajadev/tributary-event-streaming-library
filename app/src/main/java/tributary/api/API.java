package tributary.api;

import tributary.core.clients.producer.Producer;
import tributary.core.common.Topic;

import java.util.List;

/**
 * A Java API of an event-driven system.
 */
public interface API<T, K, V> {
    /**
     * Creates a new topic in the system.
     *
     * @param topicId the topic’s identifier
     * @param type the type of event that goes through the topic. Can be Integer or String.
     * @return true if the topic is created successfully, false otherwise
     */
    public boolean addTopic(String topicId, Topic<T, K, V> topic);

    /**
     * Creates a new partition in the specified topic.
     *
     * @param topicId the topic’s identifier
     * @param partitionId the partition’s identifier
     * @return true if the partition is created successfully, false otherwise
     */
    public boolean createPartition(String topicId, String partitionId);

    /**
     * Creates a new consumer group in the system.
     *
     * @param groupId the group’s identifier
     * @param topicId the topic’s identifier the group will consume from
     * @param rebalancing the rebalancing strategy for the group
     * @return true if the consumer group is created successfully, false otherwise
     */
    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing);

    /**
     * Creates a new consumer in the specified group.
     *
     * @param groupId the group’s identifier
     * @param consumerId the consumer’s identifier
     * @return true if the consumer is created successfully, false otherwise
     */
    public boolean createConsumer(String groupId, String consumerId);

    /**
     * Deletes the specified consumer from all groups it belongs to.
     *
     * @param consumerId the consumer’s identifier
     * @return a list of group IDs that the consumer was previously in
     */
    public List<String> deleteConsumer(String consumerId);

    /**
     * @param producerId
     * @param producer
     * @param allocation
     * @return
     */
    public boolean addProducer(String producerId, Producer<T> producer, String allocation);

    /**
     * Produces an event to the specified topic.
     *
     * @param producerId the producer’s identifier
     * @param topicId the topic’s identifier
     * @param event the event to be produced
     * @return true if the event is produced successfully, false otherwise
     */
    public boolean produceEvent(String producerId, String topicId, String event);

    /**
     * Produces an event to the specified topic and partition.
     *
     * @param producerId the producer’s identifier
     * @param topicId the topic’s identifier
     * @param event the event to be produced
     * @param partitionId the partition’s identifier
     * @return true if the event is produced successfully, false otherwise
     */
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId);

    /**
     * @param groupId
     */
    public void showConsumerGroup(String groupId);

    //TODO consume event <consumer> <partition>

    //TODO consume events <consumer> <partition> <number of events>

    /**
     * Displays information about the specified topic.
     *
     * @param topicId the topic’s identifier
     */
    public void showTopic(String topicId);

    /**
     * Displays information about the specified consumer group.
     *
     * @param groupId the group’s identifier
     */

    //TODO parallel produce (<producer>, <topic>, <event>)

    //TODO parallel consume (<consumer>, <partition>

    //TODO set consumer group rebalancing <group> <rebalancing>

    //TODO playback <consumer> <partition> <offset>
}
