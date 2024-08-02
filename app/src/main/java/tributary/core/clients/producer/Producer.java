package tributary.core.clients.producer;

/**
 * The main API that applications use to send record  to topics.
 */

public class Producer<T> {
    private String id;
    private T type;
    private String allocation;

    public Producer(String id, T type) {
        this.id = id;
        this.type = type;
        this.allocation = null;
    }

    public Producer(String id, T type, String allocation) {
        this.id = id;
        this.type = type;
        this.allocation = allocation;
    }

    // public void send(ProducerRecord record) {
    //     //TODO
    // }

    @Override
    public String toString() {
        return "Producer{id='" + id + "', type='" + type + "', allocation='" + allocation + "'}";
    }
}

/**
 * 6. - Creates a new producer which produces events of the given type.
 *    - allocation is either Random or Manual, determining which method of
 *      partition selection is used for publishing events.
 *
 *      Output: A message confirming the producer’s creation.
 *      Usage: create producer <id> <type> <allocation>
 */
