package tributary.core.clients.admin;

import java.util.HashMap;
import java.util.Map;

import tributary.core.clients.producer.Producer;

public class ProducerCoordinator {
    private Map<String, Producer<?>> producers = new HashMap<>();

    public boolean createProducer(String producerId, String type, String allocation) {
        if (producers.containsKey(producerId)) {
            throw new IllegalArgumentException("Error: choose another name, producerId already exist!");
        }
        producers.put(producerId, new Producer<>(producerId, type, allocation));
        return true;
    }

    /**
     * 7. - Produces a new event from the given producer to the given topic.
     *    - How you represent the event is up to you. We recommend using a JSON
     *      structure to represent the different arg of an event and the
     *      event parameter to this command is a filename to a JSON
     *      file with the event content inside.
     *    - partition is an optional parameter used only if the
     *      producer publishes events to a manually specified partition
     *
     *      Output: The event id, the id of the partition it is currently in.
     *      Usage: produce event <producer> <topic> <event> <partition>
     */
    public boolean produceEvent(String producerId, String topicId, String event, String partitionId) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'produceEvent'");
        // ProducerRecord<String, String> record = new ProducerRecord<>(topicId, event);
        return true;
    }
}
