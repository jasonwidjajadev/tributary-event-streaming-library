package tributary.core.clients.consumer.internals;

import java.util.List;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;

public interface RebalancingStrategy<V> {
    void distributePartitions(List<Consumer> consumerList, List<Partition<String, V>> partitions);
}
