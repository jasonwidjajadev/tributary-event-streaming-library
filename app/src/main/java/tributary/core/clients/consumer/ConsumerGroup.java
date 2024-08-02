package tributary.core.clients.consumer;

import java.util.HashMap;
import java.util.Map;

public class ConsumerGroup {
    private String id;
    private String topicId;
    private String rebalancing;
    private Map<String, Consumer> consumers;
    //TODO max - consumer group subscribes to one topic
    //TODO max - consumer can subscribe to multiple partitions at the same time

    public ConsumerGroup(String id, String topicId, String rebalancing) {
        this.id = id;
        this.topicId = topicId;
        this.rebalancing = rebalancing;
        this.consumers = new HashMap<>();
    }

    public boolean createConsumer(String consumerId) {
        if (consumers.containsKey(consumerId)) {
            throw new IllegalArgumentException("consumerId already exist on this group");
        }
        consumers.put(consumerId, new Consumer(consumerId));
        return true;
    }

    public void deleteConsumer(String consumerId) {
        consumers.remove(consumerId);
    }

    /**
     * 11. - Shows all consumers in the consumer group, and which partitions each
     *       consumer is receiving events from.
     *       Usage: show consumer group <group>
     */
    //TODO maxxximussss, not yet implemented, create a table, nicely formatted, etc...
    public void showConsumerGroup() {
        System.out.println("");
        System.out.println("> Consumer group: " + id);
        System.out.println("");
        System.out.printf("%-10s%10s%n", "Consumers", "| Partition", "| Offset");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.printf("%-10s%10s%n", "consumer1", "| 1", "| 0");
        System.out.printf("%-10s%10s%n", "consumer1", "| 2", "| 4");
        System.out.printf("%-10s%10s%n", "consumer2", "| 2", "| 4");
    }
}
