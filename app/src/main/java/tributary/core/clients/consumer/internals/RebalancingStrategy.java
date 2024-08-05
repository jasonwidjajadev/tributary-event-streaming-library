package tributary.core.clients.consumer.internals;

import tributary.core.clients.consumer.Consumer;
import tributary.core.common.Partition;
import java.util.List;

public interface RebalancingStrategy<T, K, V> {
    void assignPartitions(List<Consumer<K, V>> consumers, List<Partition<K, V>> partitions);
}
