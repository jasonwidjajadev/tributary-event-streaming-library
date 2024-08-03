package tributary.core.clients.producer;

/**
 * The main API that applications use to send record to topics.
 */

public class Producer<K, V> {
    private K id;
    private V type;
    private String allocation;

    /**
     * Constructs a new Producer with the given ID and type.
     *
     * @param id   the unique ID for the producer
     * @param type the type of events the producer will produce
     */
    public Producer(K id, V type) {
        this.id = id;
        this.type = type;
        this.allocation = null;
    }

    /**
     *
     * @param id         the unique ID for the producer
     * @param type       the type of events the producer will produce
     * @param allocation the allocation method for partition selection (Random or Manual)
     */
    public Producer(K id, V type, String allocation) {
        this.id = id;
        this.type = type;
        this.allocation = allocation;
    }

    public String getAllocation() {
        return allocation;
    }

    public V getType() {
        return type;
    }

    public void setType(V type) {
        this.type = type;
    }

    public String getTypeName() {
        return type.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String toString() {
        return "Producer: [id ='" + id + "', type ='" + type + "', allocation ='" + allocation + "']";
    }

}
