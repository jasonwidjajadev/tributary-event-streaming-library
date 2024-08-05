package tributary.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles topic creation, configuration, and deletion
 */
public class Topic<V> {
    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String MAGENTA = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String WHITE = "\033[0;37m";
    private static final int DELAY = 0;
    private String id;

    //For the purpose of the assignment type is: Integer or String
    private V type;
    private List<Partition<String, V>> partitions;

    public Topic(String id, V type) {
        this.id = id;
        this.type = type;
        this.partitions = new ArrayList<>();
    }

    public boolean createPartition(String partitionId) {
        for (Partition<String, V> p : partitions) {
            if (p.getPartitionId().equals(partitionId)) {
                return false;
            }
        }
        partitions.add(new Partition<>(partitionId));
        return true;
    }

    public List<Partition<String, V>> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Partition<String, V>> partitions) {
        this.partitions = partitions;
    }

    public V getType() {
        return type;
    }

    public List<String> getAllPartitionId() {
        List<String> partitionIdList = new ArrayList<String>();
        partitions.forEach(p -> {
            partitionIdList.add(p.getPartitionId());
        });

        return partitionIdList;
    }

    public String getTypeName() {
        return type.getClass().getSimpleName().toLowerCase();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Partition<String, V> getPartition(String partitionId) {
        for (Partition<String, V> p : partitions) {
            if (p.getPartitionId().equals(partitionId)) {
                return p;
            }
        }
        return null;
    }

    public Partition<String, V> getRandomPartition() {
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

            // int index = 1;
            for (Partition<String, V> p : partitions) {
                // System.out.printf("%-10s%20s%15s%n", "Partition", "| Index", "| Events");
                // System.out.println("");
                System.out.println("Partition      | Index       | Events");
                System.out.println("- - - - - - -  + - - - - - - + - - - - - ");
                p.printRecords();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
