package tributary.core.clients.consumer.internals;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;
import java.util.List;

public class RoundRobinRebalancing<T, K, V> implements RebalancingStrategy<T, K, V> {
    @Override
    public void assignPartitions(List<Consumer<K, V>> consumers, List<Partition<K, V>> partitions) {
        int numConsumers = consumers.size();
        int consumerIndex = 0;
        for (Partition<K, V> partition : partitions) {
            Consumer<K, V> assignedConsumer = consumers.get(consumerIndex);
            assignedConsumer.assignPartition(partition);
            consumerIndex = (consumerIndex + 1) % numConsumers;
        }
    }
}
