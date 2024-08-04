
package tributary.core.clients.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tributary.core.clients.consumer.Consumer;
import tributary.core.clients.consumer.ConsumerGroup;
import tributary.core.common.Topic;

public class ConsumerCoordinator {
    private static final String RESET = "\u001B[0m";
    private static final String MAGENTA = "\033[0;35m";

    // Get the consumer group: String groupId -> Class consumerGroup
    private Map<String, ConsumerGroup> consumerGroups = new HashMap<>();
    // Get the consumer class: String groupId -> Class consumer
    private Map<String, Map<String, Consumer<?, ?>>> groupToConsumersMap = new HashMap<>();
    // Get the groups consumer is in: String consumerId -> List(GroupsId)
    private Map<String, List<String>> consumerToGroupsMap = new HashMap<>();

    public boolean createConsumerGroup(String groupId, String topicId, String rebalancing, Topic<?> topic) {
        if (consumerGroups.containsKey(groupId)) {
            throw new IllegalArgumentException("Consumer group already exists, choose another name!");
        }
        consumerGroups.put(groupId, new ConsumerGroup(groupId, topicId, rebalancing, topic));

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
            List<String> partitionId = consumer.getPartitionIds();
            System.out.println(consumer.getConsumerId());
            partitionId.forEach(p -> {
                System.out.printf("%-5s%15s%10s%n", "", p, consumerGroup.getPartitionReadIndex(p));
            });
        }
    }

    /**
     * Consumes one event from a given partitionId
     * Usage: consume event <consumer> <partition>
     */
    public boolean consumeSingleEvent(String consumerId, String partitionId) {
        List<String> groups = consumerToGroupsMap.get(consumerId);
        if (groups == null) {
            throw new IllegalArgumentException("Error: consumer doesn't exist in any group");
        }

        groups.forEach(g -> {
            consumerGroups.get(g).singleEventConsume(consumerId, partitionId);
        });

        return true;
    }

    /**
     * Consume single event multiple time
     * Usage: consume event <consumer> <partition> <n partitions>
     */
    public boolean consumeMultipleEvents(String consumerId, String partitionId, int numOfEvents) {
        for (int i = 0; i < numOfEvents; ++i) {
            consumeSingleEvent(consumerId, partitionId);
        }

        return true;
    }

    /**
     * Sets the group strategy
     * Usage: set consumer group rebalancing <group> <rebalancing>
     */
    public boolean setGroupRebalancingStrategy(String strategy, String groupId) {
        ConsumerGroup consumerGroup = consumerGroups.get(groupId);

        consumerGroup.setRebalancingStrategy(strategy.toLowerCase(), true);

        return true;
    }

    /**
     * Sets the offset tof a given read position for a given partition in
     * a consumer. Assumes consumer exists
     */
    public boolean playback(String consumerId, String partitionId, int offset) {
        List<String> groupsWithConsumers = consumerToGroupsMap.get(consumerId);

        groupsWithConsumers.forEach(g -> {
            consumerGroups.get(g).playback(consumerId, partitionId, offset);
        });

        return true;
    }
}
