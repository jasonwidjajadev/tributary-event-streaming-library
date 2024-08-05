package tributary.core.clients.producer;

/**
 * The main API that applications use to send record to topics.
 */

public class Producer<T> {
    private String id;
    private Class<T> type;
    private String allocation;

    /**
     * Constructs a new Producer with the given ID and type.
     *
     * @param id   the unique ID for the producer
     * @param type the type of events the producer will produce
     */
    public Producer(String id, Class<T> type) {
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
    public Producer(String id, Class<T> type, String allocation) {
        this.id = id;
        this.type = type;
        this.allocation = allocation;
    }

    public String getAllocation() {
        return allocation;
    }

    public String getTypeName() {
        return type.getSimpleName().toLowerCase();
    }

    public Class<T> getType() {
        return type;
    }

    public String getName() {
        return type.getSimpleName();
    }

    @Override
    public String toString() {
        return "Producer: [id ='" + id + "', type ='" + type + "', allocation ='" + allocation + "']";
    }

}
