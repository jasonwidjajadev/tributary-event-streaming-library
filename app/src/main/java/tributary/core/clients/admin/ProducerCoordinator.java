package tributary.core.clients.admin;

import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import java.io.IOException;

import tributary.core.clients.producer.Producer;
import tributary.core.clients.producer.ProducerRecord;
import tributary.core.common.Partition;
import tributary.core.common.Topic;

public class ProducerCoordinator<T, K, V> {
    // private static final String RESET = "\u001B[0m";
    // private static final String MAGENTA = "\033[0;35m";
    private static final int DELAY = 0;
    private Map<String, Producer<T>> producers = new HashMap<>();

    public boolean addProducer(String producerId, Producer<T> producer, String allocation) {
        if (producers.get(producerId) != null) {
            throw new IllegalArgumentException("Error: choose another name, producerId already exist!");
        }
        producers.put(producerId, producer);
        return true;
    }

    // =========================================================================
    /**
     * 7. - Produces a new event from the given producer to the given topic.
     *    - How you represent the event is up to you. We recommend using a JSON
     *      structure to represent the different arg of an event and the
     *      event parameter to this command is a filename to a JSON
     *      file with the event content inside.
     *    - partition is an optional parameter used only if the
     *      producer publishes events to a manually specified partition
     *
     *      Output: The event id, the id of the partition it is currently in.
     *      Usage: produce event <producer> <topic> <event> <partition>
     *
     *      Manual Producer,
     */
    public boolean produceEvent(String producerId, Topic<T, K, V> topic, String topicId, String event) {
        Producer<T> producer = producers.get(producerId);
        if (producer == null) {
            throw new IllegalArgumentException("Producer ID not found!");
        }

        if (!producer.getTypeName().equals(topic.getTypeName())) {
            System.err.println("Error: producer can't produce to this topic, type mismatch");
            return false;
        }
        try {
            JSONObject eventNode = new JSONObject(FileLoader.loadResourceFile(event));
            JSONObject headers = eventNode.getJSONObject("headers");
            Long timestamp = headers.getLong("timestamp");
            String eventId = headers.getString("id");
            String payloadType = headers.getString("payloadType");

            String key = eventNode.getString("key");
            String value = eventNode.getJSONObject("value").toString();
            if (!producer.getTypeName().toLowerCase().equals(payloadType.toLowerCase())) {
                System.err.println("Error: payload type mismatch, producer and event key");
                return false;
            }

            if (!topic.getTypeName().toLowerCase().equals(payloadType.toLowerCase())) {
                System.err.println("Error: payload type mismatch, topic and event key");
                return false;
            }

            String allocation = producer.getAllocation();
            if (allocation.equals("manual")) {
                System.err.println("Error: producer is manual producer, must provide partitionId");
                return false;
            }
            Partition<K, V> partition = topic.getRandomPartition();
            if (partition == null) {
                throw new IllegalArgumentException("Partition not found!");
            }
            ProducerRecord<String, String> record = new ProducerRecord<>(topicId, partition.getPartitionId(), timestamp,
                    eventId, key, value);
            partition.addRecord(record);

            System.out.println("📩 " + "Event created in topic:   " + topicId);
            Thread.sleep(DELAY);
            System.out.println("📩 " + "Created in partition:     " + partition.getPartitionId());
            Thread.sleep(DELAY);
            System.out.println("📩 " + "Created with id:          " + eventId);
            System.out.println("");
            return true;
        } catch (NoSuchFileException e) {
            System.err.println("Error: JSON file not found at path: " + event);
            return false;
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean produceEvent(String producerId, Topic<T, K, V> topic, String topicId, String event,
            String partitionId) {
        Producer<T> producer = producers.get(producerId);
        if (producer == null) {
            throw new IllegalArgumentException("Producer ID not found!");
        }
        if (!producer.getTypeName().equals(topic.getTypeName())) {
            System.err.println("Error: producer can't produce to this topic, type mismatch");
            return false;
        }
        try {
            JSONObject eventNode = new JSONObject(FileLoader.loadResourceFile(event));
            JSONObject headers = eventNode.getJSONObject("headers");
            Long timestamp = headers.getLong("timestamp");
            String eventId = headers.getString("id");
            String payloadType = headers.getString("payloadType");
            String key = eventNode.getString("key");
            String value = eventNode.getJSONObject("value").toString();

            if (!producer.getTypeName().equals(payloadType.toLowerCase())) {
                System.err.println("Error: payload type mismatch, producer and event key");
                return false;
            }
            if (!topic.getTypeName().equals(payloadType.toLowerCase())) {
                System.err.println("Error: payload type mismatch, topic and event key");
                return false;
            }
            String allocation = producer.getAllocation();
            if (allocation.equals("random")) {
                System.err.println("Error: producer is Random producer, remove partitionId");
                return false;
            }
            Partition<K, V> partition = topic.getPartition(partitionId);
            if (partition == null) {
                throw new IllegalArgumentException("Partition not found!");
            }
            ProducerRecord<String, String> record = new ProducerRecord<>(topicId, partition.getPartitionId(), timestamp,
                    eventId, key, value);
            partition.addRecord(record);

            System.out.println("📩 " + "Event created in topic:   " + topicId);
            Thread.sleep(DELAY);
            System.out.println("📩 " + "Created in partition:     " + partitionId);
            Thread.sleep(DELAY);
            System.out.println("📩 " + "Created with id:          " + eventId);
            System.out.println("");
            return true;
        } catch (NoSuchFileException e) {
            System.err.println("Error: JSON file not found at path: " + event);
            return false;
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================================
    //Function assumes all lists are of the same size
    public void produceEventsParallel(Map<String, Topic<T, K, V>> topics, List<String> producerIds,
            List<String> topicId, List<String> event) {
        for (int i = 0; i < producerIds.size(); ++i) {
            Topic<T, K, V> currTopic = topics.get(topicId.get(i));
            produceEventThreaded(producerIds.get(i), currTopic, topicId.get(i), event.get(i));
        }
    }

    private void produceEventThreaded(String producerId, Topic<T, K, V> topic, String topicId, String event) {
        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                produceEvent(producerId, topic, topicId, event);
                return;
            }
        });

        producerThread.run();
    }

}
