package tributary.api;

import java.util.List;

/**
 * A Java API of an event-driven system.
 */
public interface API {
    /**
     * Creates a new topic in the system.
     *
     * @param topicId the topic’s identifier
     * @param type the type of event that goes through the topic. Can be Integer or String.
     * @return true if the topic is created successfully, false otherwise
     */
    public boolean createTopic(String topicId, String type);

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
     * Creates a new producer in the system.
     *
     * @param producerId the producer’s identifier
     * @param type the type of events the producer will produce
     * @param allocation the allocation method for partition selection (Random or Manual)
     * @return true if the producer is created successfully, false otherwise
     */
    public boolean createProducer(String producerId, String type, String allocation);

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
     * Shows all the consumer in a consumer group. Additionally also shows the allocated
     * partitions.
     * @param groupId
     */
    public void showConsumerGroup(String groupId);

    /**
     * Consumes a number of events from the allocated partition. Precondition:
     * The partition given as an input will always exist
     *
     * @param consumerId consumer's identifier
     * @param partitionId partition identifier
     * @return true if consumed event, false otherwise
     */
    public boolean consumeSingleEvent(String consumerId, String partitionId);

    /**
     * Consumes a number of events from the allocated partition. Precondition:
     * The partition given as an input will always exist
     *
     * @param consumerId consumer's identifier
     * @param partitionId partition identifier
     * @param numOfEvents number of events to consume from the partition
     * @return true if consumed event, false otherwise
     */
    public boolean consumeMultipleEvents(String consumerId, String partitionId, int numOfEvents);

    /**
     * Displays information about the specified topic.
     *
     * @param topicId the topic’s identifier
     */
    public void showTopic(String topicId);

    //TODO parallel produce (<producer>, <topic>, <event>)

    //TODO parallel consume (<consumer>, <partition>

    /**
     * Sets a new rebalancing strategy for the given consumer group. Automatically
     * rebalances the rest of the partitions in one go
     * @param strategy
     * @param groupId
     * @return
     */
    public boolean setRebalancingStrategy(String strategy, String groupId);

    /**
     * Sets the partition of a consumer into a given offset. Checks if offset is
     * valid. Assumes that the partitionId is always valid
     * @param consumerId
     * @param partitionId
     * @param offset
     * @return
     */
    public boolean playback(String consumerId, String partitionId, int offset);
}
