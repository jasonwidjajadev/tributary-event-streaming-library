package tributary.core.clients.consumer;

import java.util.HashMap;
import java.util.Map;

/**
 * The main API for consuming messages of consumer, responsible for managing the
 * consumption of messages from topics, including tracking which partition and
 *  offset the consumer is currently consuming from.
 */
public class Consumer<K, V> {
    private K groupId;
    private V consumerId;
    private Map<String, Long> partitionToOffset = new HashMap<>();

    public Consumer(K groupId, V consumerId) {
        this.groupId = groupId;
        this.consumerId = consumerId;
    }

    public synchronized Map<String, Long> getPartitionOffsets() {
        return partitionToOffset;
    }

    public void setConsumerId(V consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return (String) consumerId;
    }

    public K getGroupId() {
        return groupId;
    }

    public void setGroupId(K groupId) {
        this.groupId = groupId;
    }

    // Subscribing to Topics: Consumers subscribe to topics by their names to receive the messages sent to those topics

    public synchronized void commit(String partitionId) {
        long offset = partitionToOffset.get(partitionId);
        partitionToOffset.put(partitionId, offset + 1);
    }

    // Repositions the consumer's offset for the specified partition to the given offset.
    // playback <consumer> <partition> <offset>
}
