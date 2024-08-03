package tributary.core.common;

import java.util.ArrayList;
import java.util.List;

import tributary.core.clients.producer.ProducerRecord;

public class Partition<K, V> {
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
    private String partitionId;
    private List<ProducerRecord<K, V>> records;

    public Partition(String partitionId) {
        this.partitionId = partitionId;
        this.records = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void addRecord(ProducerRecord<String, String> record) {
        records.add((ProducerRecord<K, V>) record);
    }

    public List<ProducerRecord<K, V>> getRecords() {
        return records;
    }

    public String getPartitionId() {
        return partitionId;
    }

    public synchronized void printRecords() {
        int index = 0;
        for (ProducerRecord<K, V> r : records) {
            // System.out.printf("%-10s%15s%17s%n", partitionId, "| " + index, "| " + r.getEventId());
            System.out.printf(BLUE + "" + RESET + partitionId + "             " + index + "             "
                    + r.getEventId() + "\n");
            index = index + 1;
        }
        System.out.println("");
        System.out.println("");
    }

    @Override
    public String toString() {
        return "Partition: [" + "partitionId = " + partitionId + ", records = " + records + ']';
    }
}
