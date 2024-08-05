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
 */
public class Consumer<K, V> {
    private K groupId;
    private V consumerId;
    private Map<String, Long> partitionToOffset = new HashMap<>();
    private List<Partition<String, ?>> partitions = new ArrayList<Partition<String, ?>>();
    private ConsumerRecord<K, V> readRecord;

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

    public void setPartitions(Partition<String, ?> newPartition) {
        partitions.clear();
        partitions.add(newPartition);
    }

    public void clearPartition() {
        partitions.clear();
    }

    public void addPartition(Partition<String, ?> newPartition) {
        partitions.add(newPartition);
    }

    public List<String> getPartitionIds() {
        List<String> temp = new ArrayList<>();
        for (Partition<String, ?> partition : partitions) {
            temp.add(partition.getPartitionId());
        }

        return temp;
    }

    //Assumes partition always exists
    private Partition<String, ?> getPartition(String partitionId) {
        for (Partition<String, ?> partition : partitions) {
            if (partition.getPartitionId().equals(partitionId)) {
                return partition;
            }
        }

        return null;
    }

    //Pre condition that partition always exists
    public synchronized void consumeFromPartition(String partitionId, int index) {
        Partition<String, ?> partition = getPartition(partitionId);

        if (index >= partition.getPartitionSize()) {
            throw new IllegalArgumentException("All messages available read already in partition");
        }

        ProducerRecord<K, V> partitionReturn = (ProducerRecord<K, V>) partition.getRecord(index);

        readRecord = new ConsumerRecord<K, V>(partitionReturn.getKey(), partitionReturn.getValue());

        System.out.printf("Consumer: %s Consumed: %s From Partition %s\n", consumerId, readRecord, partitionId);
    }

    public boolean playback(String partitionId, int offset) {
        Partition<String, ?> partition = getPartition(partitionId);

        if (partition.getPartitionSize() <= offset) {
            throw new IllegalArgumentException("Out of Partition Bounds Error :(");
        }

        return true;
    }
}
