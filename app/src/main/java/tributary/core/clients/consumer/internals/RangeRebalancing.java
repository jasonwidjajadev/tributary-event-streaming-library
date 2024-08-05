package tributary.core.clients.consumer.internals;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;
import java.util.List;

public class RangeRebalancing<T, K, V> implements RebalancingStrategy<T, K, V> {
    @Override
    public void distributePartitions(List<Consumer<K, V>> consumerList, List<Partition<K, V>> partitions) {
        int numPartitions = partitions.size();
        int numConsumers = consumerList.size();

        for (Consumer<K, V> cons : consumerList) {
            cons.clearPartition();
        }

        int partitionsPerConsumer = numPartitions / numConsumers;
        int leftOverPartitions = numPartitions % numConsumers;

        int index = 0;
        for (int i = 0; i < numConsumers; i++) {
            int numPartitionsForThisConsumer = partitionsPerConsumer + (i < leftOverPartitions ? 1 : 0);

            Consumer<K, V> consumer = consumerList.get(i);
            //Set each patition for consumer
            for (int nPartition = 0; nPartition < numPartitionsForThisConsumer; nPartition++) {
                consumer.addPartition(partitions.get(index + nPartition));
            }
            index += numPartitionsForThisConsumer;
        }
    }
}
