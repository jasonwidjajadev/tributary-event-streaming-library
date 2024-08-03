package tributary.core.clients.producer;

/**
 * Represents a record/message that will be sent to a cluster/topic.
 * Messages are key-value pairs. ProducerRecord can optionally include a partition number
 * to specify the exact partition to which the record should be sent.
 * If a partition number is provided, it will be used to determine the partition based on the key
 * (if provided) or using a round-robin approach.
 */

public class ProducerRecord<K, V> {
    private String topicId;
    private String partitionId;
    private Long timestamp;
    private String eventId;
    private K key;
    private V value;

    /**
     * Constructs a new Record with the given key and value.
     *
     * @param topicId     the topic ID where the record will be sent
     * @param partitionId the partition ID where the record will be sent
     * @param timestamp   the timestamp of the record
     * @param eventId     the event ID of the record
     * @param key         key is used for partitioning the data, logical key used for partitioning the data,
     *                    K will be used for the key type
     * @param value       the value of the record, actual data payload, V for the value type in Partition
     */
    public ProducerRecord(String topicId, String partitionId, Long timestamp, String eventId, K key, V value) {
        this.topicId = topicId;
        this.partitionId = partitionId;
        this.timestamp = timestamp;
        this.eventId = eventId;
        this.key = key;
        this.value = value;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getPartitionId() {
        return partitionId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getEventId() {
        return eventId;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ProducerRecord [topicId ='" + topicId + "', partitionId ='" + partitionId + "', timestamp =" + timestamp
                + ", eventId ='" + eventId + "', key ='" + key + "', value ='" + value + "']";
    }
}
