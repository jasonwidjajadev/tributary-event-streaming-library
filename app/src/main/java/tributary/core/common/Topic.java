package tributary.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles topic creation, configuration, and deletion
 */
public class Topic<T, K, V> {
    private static final String RESET = "\u001B[0m";
    private static final String MAGENTA = "\033[0;35m";
    private static final int DELAY = 0;
    private String id;
    // private T type;
    private Class<T> type;
    private List<Partition<K, V>> partitions;

    public Topic(String id, Class<T> type) {
        this.id = id;
        this.type = type;
        this.partitions = new ArrayList<>();
    }

    public boolean createPartition(String partitionId) {
        for (Partition<K, V> p : partitions) {
            if (p.getPartitionId().equals(partitionId)) {
                return false;
            }
        }
        partitions.add(new Partition<>(partitionId));
        return true;
    }

    public List<Partition<K, V>> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Partition<K, V>> partitions) {
        this.partitions = partitions;
    }

    public Class<T> getType() {
        return type;
    }

    public String getTypeName() {
        return type.getSimpleName().toLowerCase();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Partition<K, V> getPartition(String partitionId) {
        for (Partition<K, V> p : partitions) {
            if (p.getPartitionId().equals(partitionId)) {
                return p;
            }
        }
        return null;
    }

    public Partition<K, V> getRandomPartition() {
        if (partitions.isEmpty()) {
            return null;
        }
        Random rndm = new Random();
        int rndmIndx = rndm.nextInt(partitions.size());
        return partitions.get(rndmIndx);
    }

    /**
     * Output: Prints a visual display of the given topic, including all
     * partitions and all of the events currently in each partition.
     * Usage: show topic <topic>
     */
    public void showTopic() {
        try {
            System.out.println(MAGENTA + "+ " + RESET + "Topic created with id:     " + id);
            Thread.sleep(DELAY);
            System.out.println(MAGENTA + "+ " + RESET + "Topic type:                " + this.getTypeName());
            System.out.println("");
            for (Partition<K, V> p : partitions) {
                System.out.println("Partition      | Index       | Events");
                System.out.println("- - - - - - -  + - - - - - - + - - - - - ");
                p.printRecords();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
