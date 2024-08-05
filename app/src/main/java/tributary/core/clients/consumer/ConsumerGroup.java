package tributary.core.clients.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tributary.core.clients.consumer.internals.RangeRebalancing;
import tributary.core.clients.consumer.internals.RebalancingStrategy;
import tributary.core.clients.consumer.internals.RoundRobinRebalancing;
import tributary.core.common.Topic;

public class ConsumerGroup<T, K, V> {
    private String groupId;
    private String topicId;
    private String rebalancing;
    private List<Consumer<K, V>> consumers = new ArrayList<>();
    private Topic<T, K, V> topic;
    private RebalancingStrategy<T, K, V> balanceStrategy;
    private HashMap<String, Integer> readIndex = new HashMap<String, Integer>();

    public ConsumerGroup(String groupId, String topicId, String rebalancing, Topic<T, K, V> topic) {
        this.groupId = groupId;
        this.topicId = topicId;
        this.rebalancing = rebalancing;
        this.topic = topic;

        if ("roundrobin".equals(rebalancing)) {
            balanceStrategy = new RoundRobinRebalancing<>();
        } else if ("range".equals(rebalancing)) {
            balanceStrategy = new RangeRebalancing<>();
        }

        //Set each as read index zero
        List<String> partitionIdList = topic.getAllPartitionId();
        partitionIdList.forEach(id -> {
            readIndex.put(id, 0);
        });
    }

    // =========================================================================
    //Note: Balance strategy is always implicitly called since to run it
    //you need a consumer which has to be added. Without the addition
    //of a consumer you have nothing to balance. Therefore it is ok.
    public void addConsumer(Consumer<K, V> consumer) {
        consumers.add(consumer);
        balanceStrategy.distributePartitions(consumers, topic.getPartitions());
    }

    public void removeConsumer(String consumerId) {
        consumers.removeIf(consumer -> consumer.getConsumerId().equals(consumerId));
        balanceStrategy.distributePartitions(consumers, topic.getPartitions());
    }

    // =========================================================================
    //Must be synchronized to ensure correctness of hashmap
    public void singleEventConsume(String consumerId, String partitionId) {
        for (Consumer<K, V> cons : consumers) {
            if (cons.getConsumerId().equals(consumerId)) {
                //Important as two consumers can have same name but not same partition
                if (readIndex.get(partitionId) == null) {
                    break;
                }

                cons.consumeFromPartition(partitionId, readIndex.get(partitionId));

                int currIndex = readIndex.get(partitionId);
                readIndex.put(partitionId, currIndex + 1);
                break;
            }
        }
    }

    // =========================================================================
    //Assumes partition ID exists
    public int getPartitionReadIndex(String partitionId) {
        return readIndex.get(partitionId);
    }

    public List<Consumer<K, V>> getConsumers() {
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

    // =========================================================================
    public void setRebalancingStrategy(String rebalancing, boolean message) {
        if ("roundrobin".equals(rebalancing)) {
            balanceStrategy = new RoundRobinRebalancing<>();
        } else if ("range".equals(rebalancing)) {
            balanceStrategy = new RangeRebalancing<>();
        }

        if (message) {
            System.out.println("Set new strategy " + rebalancing + " for " + groupId);
        }

        balanceStrategy.distributePartitions(consumers, topic.getPartitions());
    }

    // =========================================================================
    public void playback(String consumerId, String partitionId, int offset) {
        //Keep track of offset
        int originalPosition = readIndex.get(partitionId);
        int replayCount = originalPosition - offset;

        for (Consumer<K, V> cons : consumers) {
            if (cons.getConsumerId().equals(consumerId)) {
                if (cons.playback(partitionId, offset)) {
                    readIndex.put(partitionId, offset);
                }
            }
            break;
        }
        System.out.println("Currently at position " + offset);
        System.out.println("Replaying back to position " + originalPosition);
        for (int i = 0; i < replayCount; ++i) {
            singleEventConsume(consumerId, partitionId);
        }
    }
}
