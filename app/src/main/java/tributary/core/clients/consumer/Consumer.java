package tributary.core.clients.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tributary.core.clients.producer.ProducerRecord;
import tributary.core.common.Partition;

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
    private Map<String, Long> partitionToOffset = new HashMap<>();
    private List<Partition<K, V>> partitions = new ArrayList<>();
    private ConsumerRecord<K, V> readRecord;

    // =========================================================================

    public Consumer(String groupId, String consumerId) {
        this.groupId = groupId;
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    // =========================================================================
    public synchronized Map<String, Long> getPartitionOffsets() {
        return partitionToOffset;
    }

    // =========================================================================
    public void setPartitions(Partition<K, V> newPartition) {
        partitions.clear();
        partitions.add(newPartition);
    }

    public void clearPartition() {
        partitions.clear();
    }

    public void addPartition(Partition<K, V> newPartition) {
        partitions.add(newPartition);
    }

    public List<String> getPartitionIds() {
        List<String> temp = new ArrayList<>();
        for (Partition<K, V> partition : partitions) {
            temp.add(partition.getPartitionId());
        }
        return temp;
    }

    //Assumes partition always exists
    private Partition<K, V> getPartition(String partitionId) {
        for (Partition<K, V> partition : partitions) {
            if (partition.getPartitionId().equals(partitionId)) {
                return partition;
            }
        }
        return null;
    }

    //Pre condition that partition always exists
    public synchronized void consumeFromPartition(String partitionId, int index) {
        Partition<K, V> partition = getPartition(partitionId);

        if (index >= partition.getPartitionSize()) {
            throw new IllegalArgumentException("All messages available read already in partition");
        }

        ProducerRecord<K, V> partitionReturn = (ProducerRecord<K, V>) partition.getRecord(index);
        readRecord = new ConsumerRecord<K, V>(partitionReturn.getKey(), partitionReturn.getValue());

        System.out.printf("%-10s%15s%15s%10s%10s%s%n", "Consumer: ",
        consumerId, "Partition: ", partitionId, "Record: ", readRecord);
        // System.out.printf("Consumer: %s Consumed: %s From Partition %s\n", consumerId, readRecord, partitionId);
    }

    public boolean playback(String partitionId, int offset) {
        Partition<K, V> partition = getPartition(partitionId);

        if (partition.getPartitionSize() <= offset) {
            throw new IllegalArgumentException("Out of Partition Bounds Error :(");
        }

        return true;
    }
}
