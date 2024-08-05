package tributary.core.clients.consumer.internals;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;
import java.util.List;

public class RoundRobinRebalancing<T, K, V> implements RebalancingStrategy<T, K, V> {
    @Override
    public void distributePartitions(List<Consumer<K, V>> consumerList, List<Partition<K, V>> partitions) {

        //Clear all partition first before moving forward
        for (Consumer<K, V> cons : consumerList) {
            cons.clearPartition();
        }

        int numConsumers = consumerList.size();

        int index = 0;
        for (Partition<K, V> partition : partitions) {
            Consumer<K, V> assignedConsumer = consumerList.get(index);
            assignedConsumer.addPartition(partition);

            index = (index + 1) % numConsumers;
        }
    }
}
