package tributary.core.clients.consumer.internals;

import java.util.List;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;

public class RoundRobinRebalancing<V> implements RebalancingStrategy<V> {
    @Override
    public void distributePartitions(List<Consumer> consumerList, List<Partition<String, V>> partitions) {
        //Clear all partition first before moving forward
        for (Consumer<?, ?> cons : consumerList) {
            cons.clearPartition();
        }

        int numConsumers = consumerList.size();

        int index = 0;
        for (Partition<String, ?> partition : partitions) {
            Consumer assignedConsumer = consumerList.get(index);
            assignedConsumer.addPartition(partition);

            index = (index + 1) % numConsumers;
        }
    }
}
