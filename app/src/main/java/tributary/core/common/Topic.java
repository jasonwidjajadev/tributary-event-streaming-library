package tributary.core.common;

import java.util.List;
import java.util.ArrayList;

/**
 * Handles topic creation, configuration, and deletion
 */
public class Topic<T> {
    private String id;
    private T type;
    private List<Partition> partitions;

    public Topic(String id, T type) {
        this.id = id;
        this.type = type;
        this.partitions = new ArrayList<>();
    }

    public String getKey() {
        return id;
    }

    public void setKey(String id) {
        this.id = id;
    }

    public T getValue() {
        return type;
    }

    public void setValue(T type) {
        this.type = type;
    }

    public boolean createPartition(String partitionId) {
        for (Partition p : partitions) {
            if (p.getId().equals(partitionId)) {
                return false;
            }
        }
        partitions.add(new Partition(partitionId));
        return true;

    }

    /**
     * 10. - Output: Prints a visual display of the given topic, including all
     *       partitions and all of the events currently in each partition.
     *       Usage: show topic <topic>
     */
    //TODO max -> this is not yet fully implemented
    public void showTopic(String topicId) {
        System.out.println("");
        System.out.println("Topic = " + id + ", type = " + id.getClass().getName());
        System.out.println("");
        // System.out.printf("%-10s%6s%n", "Partition", "partitionId");
        // System.out.println("Partition" + "    " + "Id");
        System.out.printf("%-10s%10s%n", "Index", "Partition ID");
        // System.out.println("---------------------------------");
        int index = 1;
        for (Partition p : partitions) {
            // System.out.println(index + "            " + p);
            System.out.printf("%-10d%10s%n", index, p.getId());
            index++;
        }
    }
}
