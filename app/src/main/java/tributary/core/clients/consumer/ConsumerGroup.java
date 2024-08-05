package tributary.core.clients.consumer;

import java.util.ArrayList;
import java.util.List;
// import java.util.HashMap;
// import java.util.Map;

public class ConsumerGroup<K, V> {
    private String groupId;
    private String topicId;
    private String rebalancing;
    private List<Consumer<K, V>> consumers = new ArrayList<>();
    // private Map<String, Long> partitionsToOffset = new HashMap<>();

    public ConsumerGroup(String groupId, String topicId, String rebalancing) {
        this.groupId = groupId;
        this.topicId = topicId;
        this.rebalancing = rebalancing;
    }

    public void addConsumer(Consumer<K, V> consumer) {
        consumers.add(consumer);
    }

    public void removeConsumer(String consumerId) {
        consumers.removeIf(consumer -> consumer.getConsumerId().equals(consumerId));
    }

    public List<Consumer<K, V>> getConsumers() {
        return consumers;
    }

    public int getNumConsumers() {
        return consumers.size();
    }

    // =========================================================================
    public String getGroupId() {
        return groupId;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getRebalancing() {
        return rebalancing;
    }

    // =========================================================================
    // public void partitionConsumer(Consumer<String, String> consumer) {
    //     if (rebalancing.equals("range")) {
    //         // rebalancingStrategy = new RoundRobinRebalancing();
    //     } else if (rebalancing.equals("round_robin")) {
    //         // rebalancingStrategy = new RangeRebalancing();
    //     }
    // }

    // =========================================================================
    // public synchronized Map<String, Long> getPartitionOffsets() {
    //     return partitionsToOffset;
    // }

    // public void updatePartitionOffset(String partition, long offset) {
    //     partitionsToOffset.put(partition, offset);
    // }

    // // Method to get the offset for a partition
    // public long getPartitionOffset(String partition) {
    //     return partitionsToOffset.getOrDefault(partition, 0L);
    // }

    // public synchronized void commit(String partitionId) {
    //     long offset = partitionToOffset.get(partitionId);
    //     partitionToOffset.put(partitionId, offset + 1);
    // }

}
