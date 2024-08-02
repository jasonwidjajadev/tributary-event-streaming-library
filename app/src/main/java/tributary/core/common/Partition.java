package tributary.core.common;

public class Partition {
    private String partitionId;

    public Partition(String partitionId) {
        this.partitionId = partitionId;
    }

    public String getId() {
        return partitionId;
    }

    // @Override
    // public String toString() {
    //     return partitionId;
    // }
}
