package tributary.core.clients.consumer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerGroup {
    private String groupId;
    private String topicId;
    private String rebalancing;
    private List<Consumer<?, ?>> consumers = new ArrayList<>();

    public ConsumerGroup(String groupId, String topicId, String rebalancing) {
        this.groupId = groupId;
        this.topicId = topicId;
        this.rebalancing = rebalancing;
    }

    public void addConsumer(Consumer<?, ?> consumer) {
        consumers.add(consumer);
    }

    public void removeConsumer(String consumerId) {
        consumers.removeIf(consumer -> consumer.getConsumerId().equals(consumerId));
    }

    public List<Consumer<?, ?>> getConsumers() {
        return consumers;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getTopicId() {
        return topicId;
    }

    public String getRebalancing() {
        return rebalancing;
    }
}
