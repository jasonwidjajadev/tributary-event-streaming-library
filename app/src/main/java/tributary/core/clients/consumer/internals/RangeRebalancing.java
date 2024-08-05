package tributary.core.clients.consumer.internals;

import java.util.List;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;

public class RangeRebalancing<V> implements RebalancingStrategy<V> {
    @Override
    public void distributePartitions(List<Consumer> consumerList, List<Partition<String, V>> partitions) {
        int numPartitions = partitions.size();
        int numConsumers = consumerList.size();

        for (Consumer<?, ?> cons : consumerList) {
            cons.clearPartition();
        }

        int partitionsPerConsumer = numPartitions / numConsumers;
        int leftOverPartitions = numPartitions % numConsumers;

        int index = 0;
        for (int i = 0; i < numConsumers; i++) {
            int numPartitionsForThisConsumer = partitionsPerConsumer + (i < leftOverPartitions ? 1 : 0);

            Consumer consumer = consumerList.get(i);

            //Set each patition for consumer
            for (int nPartition = 0; nPartition < numPartitionsForThisConsumer; nPartition++) {
                consumer.addPartition(partitions.get(index + nPartition));
            }

            index += numPartitionsForThisConsumer;
        }
    }
}
