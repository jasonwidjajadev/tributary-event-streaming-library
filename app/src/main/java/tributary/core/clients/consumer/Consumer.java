package tributary.core.clients.consumer;

import java.util.HashMap;
import java.util.Map;

// import java.util.Collection;

// import java.util.List;

/**
 * The main API for consuming messages of consumer, responsible for managing the
 * consumption of messages from topics, including tracking which partition and
 *  offset the consumer is currently consuming from.
 */
public class Consumer {
    private String id;
    private Map<String, Integer> partitionToOffset = new HashMap<>();

    public Consumer(String consumerId) {
        this.id = consumerId;
    }

    // Subscribing to Topics: Consumers subscribe to topics by their names to receive the messages sent to those topics

    // subscribe(Collection<String> topics):

    // Subscribes the consumer to the specified list of topics.
    // assign(Collection<TopicPartition> partitions):

    //TODO need to first subscribe
    //TODO need to synchronize the consuming process

    public synchronized void commit(String partitionId) {
        int offset = partitionToOffset.get(partitionId);
        // increment offset
        //TODO error check to make sure its not index out of bound
        partitionToOffset.put(partitionId, offset + 1);
    }

    public synchronized Map<String, Integer> getPartitionToOffset() {
        return partitionToOffset;
    }

    // Repositions the consumer's offset for the specified partition to the given offset.
    // playback <consumer> <partition> <offset>

}
