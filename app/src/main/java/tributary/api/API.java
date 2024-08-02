package tributary.api;

/**
 * a Java API of an event-driven system.
 */
public interface API {
    /**
     * @param id
     * @param type
     * @return
     */
    public boolean createTopic(String topicId, String type);

    /**
     * @param topic
     * @param id
     * @return
     */
    public boolean createPartition(String topicId, String partitionId);

    // =========================================================================

    /**
     * @param groupId
     * @param topicId
     * @param rebalancing
     * @return
     */
    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing);

    /**
     * @param groupId
     * @param consumerId
     * @return
     */
    public boolean createConsumer(String groupId, String consumerId);

    /**
     * @param consumerId
     * @return
     */
    public String deleteConsumer(String consumerId);

    /**
     * @param producerId
     * @param type
     * @param allocation
     * @return
     */
    public boolean createProducer(String producerId, String type, String allocation);

    /**
     * @param producerId
     * @param topicId
     * @param event
     * @param partitionId
     * @return
     */
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId);

    // =========================================================================
    /**
     * @param topic
     */
    public void showTopic(String topidId);

    /**
     * @param groupId
     */
    public void showConsumerGroup(String groupId);

    // =========================================================================
}
