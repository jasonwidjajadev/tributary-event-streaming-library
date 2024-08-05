package tributary.core.clients.consumer.internals;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;
import java.util.List;

public class RangeRebalancing<T, K, V> implements RebalancingStrategy<T, K, V> {
    @Override
    public void assignPartitions(List<Consumer<K, V>> consumers, List<Partition<K, V>> partitions) {
        int numPartitions = partitions.size();
        int numConsumers = consumers.size();
        int partitionsPerConsumer = numPartitions / numConsumers;
        int extraPartitions = numPartitions % numConsumers;

        int startIndex = 0;
        for (int i = 0; i < numConsumers; i++) {

            int numPartitionsForThisConsumer = partitionsPerConsumer + (i < extraPartitions ? 1 : 0);
            Consumer<K, V> consumer = consumers.get(i);
            for (int j = 0; j < numPartitionsForThisConsumer; j++) {
                consumer.assignPartition(partitions.get(startIndex + j));
            }
            startIndex += numPartitionsForThisConsumer;
        }
    }
}
