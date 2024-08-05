package tributary.core.clients.consumer;

import tributary.core.common.Partition;
import java.util.List;
import java.util.ArrayList;

/**
 * The main API for consuming messages of consumer, responsible for managing the
 * consumption of messages from topics, including tracking which partition and
 *  offset the consumer is currently consuming from.
 * @param <K>
 * @param <V>
 */
public class Consumer<K, V> {
    private String groupId;
    private String consumerId;

    private List<Partition<K, V>> assignedPartitions = new ArrayList<>();

    public Consumer(String groupId, String consumerId) {
        this.groupId = groupId;
        this.consumerId = consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    // =========================================================================
    public void assignPartition(Partition<K, V> partition) {
        if (assignedPartitions != null) {
            assignedPartitions.clear();
        }

        assignedPartitions.add(partition);
    }

    public List<Partition<K, V>> getAssignedPartitions() {
        return assignedPartitions;
    }
    // =========================================================================
    // Subscribing to Topics: Consumers subscribe to topics by their names to receive the messages sent to those topics

    // Repositions the consumer's offset for the specified partition to the given offset.
    // playback <consumer> <partition> <offset>
}
