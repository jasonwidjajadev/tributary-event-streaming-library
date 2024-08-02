package tributary.core.clients.admin;

import java.util.HashMap;
import java.util.Map;

import tributary.core.clients.consumer.ConsumerGroup;

public class ConsumerCoordinator {
    private Map<String, ConsumerGroup> consumerGroups = new HashMap<>();
    private Map<String, String> consumerToGroupMap = new HashMap<>();

    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing) {
        if (consumerGroups.containsKey(groupId)) {
            throw new IllegalArgumentException("Consumer group already exists!");
        }
        consumerGroups.put(groupId, new ConsumerGroup(groupId, topicId, rebalancing));
        return true;
    }

    // A consumer cannot be a member of two different consumer groups at the same time.
    public boolean createConsumer(String groupId, String consumerId) {
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);
        if (consumerGroup == null) {
            throw new IllegalArgumentException("Error: consumer group not found!");
        }
        if (consumerToGroupMap.containsKey(consumerId)) {
            throw new IllegalArgumentException("Consumer ID already exists!");
        }
        consumerGroup.createConsumer(consumerId);
        consumerToGroupMap.put(consumerId, groupId);
        return true;
    }

    /**
     * 5. - Deletes the consumer with the given id.
     *
     *      Output: A message confirming the consumer’s deletion, and an output of
     *      the rebalanced consumer group that the consumer was previously in.
     *      Usage: delete consumer <consumer>
     */
    public String deleteConsumer(String consumerId) {
        String groupId = consumerToGroupMap.get(consumerId);
        if (groupId == null) {
            throw new IllegalArgumentException("Error: consumer doesn't exist");
        }
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);
        consumerToGroupMap.remove(consumerId);
        consumerGroup.deleteConsumer(consumerId);
        return groupId;
    }

    public void showConsumerGroup(String groupId) {
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);
        if (consumerGroup == null) {
            throw new IllegalArgumentException("Error: consumer group not found!");
        }
        consumerGroup.showConsumerGroup();
    }
}
