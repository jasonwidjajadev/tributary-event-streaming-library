package tributary.core.clients.consumer;

/**
 * ConsumerRecord: Represents a record/data that has been fetched from topic.
 */
public class ConsumerRecord<K, V> {
    private K key;
    private V value;

    /**
     * Constructs a new ConsumerRecord with the given key and value.
     *
     * @param key   the key of the record
     * @param value the value of the record
     */
    public ConsumerRecord(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConsumerRecord [key = '" + key + "', value = '" + value + "']";
    }
}
