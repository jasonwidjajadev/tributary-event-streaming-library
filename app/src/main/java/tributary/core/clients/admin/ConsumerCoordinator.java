
package tributary.core.clients.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tributary.core.clients.consumer.Consumer;
import tributary.core.clients.consumer.ConsumerGroup;

public class ConsumerCoordinator {
    private static final String RESET = "\u001B[0m";
    private static final String MAGENTA = "\033[0;35m";

    // Get the consumer group: String groupId -> Class consumerGroup
    private Map<String, ConsumerGroup> consumerGroups = new HashMap<>();
    // Get the consumer class: String groupId -> Class consumer
    private Map<String, Map<String, Consumer<?, ?>>> groupToConsumersMap = new HashMap<>();
    // Get the groups consumer is in: String consumerId -> List(GroupsId)
    private Map<String, List<String>> consumerToGroupsMap = new HashMap<>();

    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing) {
        if (consumerGroups.containsKey(groupId)) {
            throw new IllegalArgumentException("Consumer group already exists, choose another name!");
        }
        consumerGroups.put(groupId, new ConsumerGroup(groupId, topicId, rebalancing));

        // Create a new hashmap to hold all consumer
        groupToConsumersMap.put(groupId, new HashMap<>());
        return true;
    }

    /**
     * Creates a new consumer with the given group and ID.
     *
     * @param groupId the group ID for the consumer
     * @param consumerId the unique ID for the consumer within the group
     * @return true if the consumer is created successfully
     * @throws IllegalArgumentException if the consumer ID already exists in the group
     */
    public boolean createConsumer(String groupId, String consumerId) {
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);
        if (consumerGroup == null) {
            throw new IllegalArgumentException("Error: consumer group doesn't exist!");
        }

        Map<String, Consumer<?, ?>> consumers = groupToConsumersMap.get(groupId);
        if (consumers.containsKey(consumerId)) {
            throw new IllegalArgumentException("Error: choose another name, consumerId already exists in this group!");
        }

        Consumer<String, String> consumer = new Consumer<>(groupId, consumerId);
        consumers.put(consumerId, consumer);
        consumerGroup.addConsumer(consumer);

        // Track the groups for this consumer
        List<String> groups = consumerToGroupsMap.get(consumerId);
        if (groups == null) {
            groups = new ArrayList<>();
            consumerToGroupsMap.put(consumerId, groups);
        }
        groups.add(groupId);
        return true;
    }

    /**
     * Deletes the consumer with the given id from all groups.
     *
     * Output: A list of groups that the consumer was previously in.
     * Usage: delete consumer <consumerId>
     */
    public List<String> deleteConsumer(String consumerId) {
        List<String> groups = consumerToGroupsMap.get(consumerId);
        if (groups == null) {
            throw new IllegalArgumentException("Error: consumer doesn't exist in any group");
        }

        List<String> deletedGroups = new ArrayList<>(groups);
        for (String groupId : groups) {
            ConsumerGroup consumerGroup = consumerGroups.get(groupId);
            if (consumerGroup != null) {
                Map<String, Consumer<?, ?>> consumers = groupToConsumersMap.get(groupId);
                if (consumers != null) {
                    consumers.remove(consumerId);
                }
                consumerGroup.removeConsumer(consumerId);
            }
        }

        consumerToGroupsMap.remove(consumerId);
        return deletedGroups;
    }

    /**
     * Shows all consumers in the consumer group, and which partitions each
     * consumer is receiving events from.
     * Usage: show consumer group <group>
     */
    public void showConsumerGroup(String groupId) {
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);
        if (consumerGroup == null) {
            throw new IllegalArgumentException("Error: consumer group doesn't exist, nothing to show!");
        }

        System.out.println("");
        System.out.println("Consumer group: " + groupId);
        System.out.println("");
        System.out.printf("%-10s%15s%10s%n", "Consumers", "Partition", "Offset");
        System.out.println("-----------------------------------------------");

        for (Consumer<?, ?> consumer : consumerGroup.getConsumers()) {
            for (Entry<String, Long> partitionInfo : consumer.getPartitionOffsets().entrySet()) {
                System.out.printf(MAGENTA + "+ " + RESET + "%-10s%15d%10d%n", consumer.getConsumerId(),
                        partitionInfo.getKey(), partitionInfo.getValue());
            }
        }
    }
}
