package tributary.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import tributary.api.API;
import tributary.api.APIFactory;
import tributary.core.clients.producer.Producer;
import tributary.core.common.Topic;

public class TributaryCLI<T, K, V> {
    private API<T, K, V> api = APIFactory.createAdminClient();
    private int counter = 0;
    private static final int DELAY = 0;
    private static final String RESET = "\u001B[0m";
    // private static final String BLACK = "\033[0;30m";
    // private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    // private static final String YELLOW = "\033[0;33m";
    // private static final String BLUE = "\033[0;34m";
    private static final String MAGENTA = "\033[0;35m";
    // private static final String CYAN = "\033[0;36m";
    // private static final String WHITE = "\033[0;37m";

    // private static final String BOLD_BLACK = "\033[1;30m";
    private static final String BOLD_RED = "\033[1;31m";
    // private static final String BOLD_GREEN = "\033[1;32m";
    // private static final String BOLD_YELLOW = "\033[1;33m";
    // private static final String BOLD_BLUE = "\033[1;34m";
    // private static final String BOLD_MAGENTA = "\033[1;35m";
    // private static final String BOLD_CYAN = "\033[1;36m";
    // private static final String BOLD_WHITE = "\033[1;37m";

    public static void main(String[] args) {
        TributaryCLI<String, String, String> cli = new TributaryCLI<>();
        cli.run();
    }

    private void run() {
        System.out.println("");
        System.out.println("=======================================================");
        System.out.println("       ______     _ __          __                   ");
        System.out.println("     /_  __/____(_) /_  __  __/ /_____ ________  __  ");
        System.out.println("      / / / ___/ / __ \\/ / / / __/ __ `/ ___/ / / /  ");
        System.out.println("     / / / /  / / /_/ / /_/ / /_/ /_/ / /  / /_/ /   ");
        System.out.println("    /_/ /_/  /_/_.___/\\__,_/\\__/\\__,_/_/   \\__, /    ");
        System.out.println("                                          /____/       ");
        System.out.println("                                                       ");
        System.out.println("     ⚡ The Premiere Event Streaming Platform! ⚡        ");
        System.out.println("=======================================================");
        System.out.println("");

        System.out.print(GREEN + "Enter a command: " + RESET);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {

            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input.trim()) || "q".equalsIgnoreCase(input.trim())
                    || "quit".equalsIgnoreCase(input.trim())) {
                break;
            }
            processCommand(input);
            System.out.println("");
            System.out.print(GREEN + "Enter a command: " + RESET);
        }
        scanner.close();
        System.out.println("");
        System.out.println("======== ⚡ Thank you for using Tributary! ⚡ =========");
    }

    private void processCommand(String command) {
        String[] arg = command.split(" ");
        if (arg.length > 0) {
            switch (arg[0]) {
            case "create":
                if (arg.length == 4 && "topic".equals(arg[1])) {
                    createTopic(arg[2], arg[3]);
                } else if (arg.length == 4 && "partition".equals(arg[1])) {
                    createPartition(arg[2], arg[3]);
                } else if (arg.length == 6 && "consumer".equals(arg[1]) && "group".equals(arg[2])) {
                    createConsumerGroup(arg[3], arg[4], arg[5]);
                } else if (arg.length == 4 && "consumer".equals(arg[1])) {
                    createConsumer(arg[2], arg[3]);
                } else if (arg.length == 5 && "producer".equals(arg[1])) {
                    createProducer(arg[2], arg[3], arg[4]);
                }
                break;
            case "delete":
                if (arg.length == 3 && "consumer".equals(arg[1])) {
                    deleteConsumer(arg[2]);
                }
                break;
            case "produce":
                if (arg.length == 5 && "event".equals(arg[1])) {
                    //Radom producer
                    produceEvent(arg[2], arg[3], arg[4]);
                } else if (arg.length == 6 && "event".equals(arg[1])) {
                    //Manual producer
                    produceEvent(arg[2], arg[3], arg[4], arg[5]);
                }
                break;
            case "show":
                if (arg.length == 3 && "topic".equals(arg[1])) {
                    showTopic(arg[2]);
                } else if (arg.length == 4 && "consumer".equals(arg[1]) && "group".equals(arg[2])) {
                    showConsumerGroup(arg[3]);
                }
                break;
            case "consume":
                if (arg.length == 4 && "event".equals(arg[1])) {
                    consumeSingleEvent(arg[2], arg[3]);
                } else if (arg.length == 5 && "events".equals(arg[1])) {
                    consumeMultipleEvents(arg[2], arg[3], Integer.parseInt(arg[4]));
                }

                break;
            case "set":
                if (arg.length == 6 && "consumer".equals(arg[1]) && "rebalancing".equals(arg[3])) {
                    consumerGroupRebalance(arg[5], arg[4]);
                }
                break;
            case "playback":
                if (arg.length == 4) {
                    consumerGroupPlayback(arg[1], arg[2], Integer.parseInt(arg[3]));
                }
                break;
            case "parallel":
                if ("produce".equals(arg[1])) {
                    //Create three lists
                    List<String> producerIds = new ArrayList<>();
                    List<String> topicId = new ArrayList<>();
                    List<String> event = new ArrayList<>();

                    int argIndex = 2;
                    while (argIndex < arg.length) {
                        producerIds.add(removeLastChar(arg[argIndex].substring(1)));
                        argIndex++;
                        topicId.add(removeLastChar(arg[argIndex]));
                        argIndex++;
                        event.add(removeChars(arg[argIndex], 2));
                        argIndex++;
                    }

                    //Last bit has to be dropped because assume no comma
                    event.remove(event.size() - 1);
                    event.add(removeLastChar(arg[argIndex - 1]));

                    // Debug code for printing
                    // for (int i = 0; i < producerIds.size(); ++i) {
                    //     System.out.printf("%s %s %s", producerIds.get(i), topicId.get(i), event.get(i));
                    // }

                    produceEventsParallel(producerIds, topicId, event);

                } else if ("consume".equals(arg[1])) {
                    List<String> consumerId = new ArrayList<>();
                    List<String> partitionId = new ArrayList<>();

                    int argIndex = 2;
                    while (argIndex < arg.length) {
                        consumerId.add(removeLastChar(arg[argIndex].substring(1)));
                        argIndex++;
                        partitionId.add(removeChars(arg[argIndex], 2));
                        argIndex++;
                    }

                    partitionId.remove(partitionId.size() - 1);
                    partitionId.add(removeLastChar(arg[argIndex - 1]));

                    // Debug code for printing
                    // for (int i = 0; i < partitionId.size(); ++i) {
                    //     System.out.printf("%s %s ", consumerId.get(i), partitionId.get(i));
                    // }

                    consumeEventsParallel(consumerId, partitionId);
                }

                break;
            default:
                System.out.println("Unknown command: \"" + command + "\"");
            }
        }

    }

    public void incrementCounter() {
        ++this.counter;
    }

    public int getCounter() {
        return counter;
    }

    /**
     * 1. - Creates a new topic in the tributary.
     *    - id is the topic’s identifier.
     *    - type is the type of event that goes through the topic.
     *      While this can be any type in Java, for the purposes of the CLI
     *      it can either be Integer or String.
     *
     *      Output: A message showing the id, type and other relevant
     *      information about the topic confirming its creation.
     *
     *      Usage: create topic <id> <type>
     *      # partitions = 6 ?
     */

    @SuppressWarnings("unchecked")
    private void createTopic(String topicId, String type) {
        try {
            Class<T> classType;
            switch (type.toLowerCase()) {
            case "string" -> classType = (Class<T>) String.class;
            case "integer" -> classType = (Class<T>) Integer.class;
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
            }
            Topic<T, K, V> topic = new Topic<>(topicId, classType);
            if (topic != null && api.addTopic(topicId, topic)) {
                System.out.println(MAGENTA + "+ " + RESET + "Topic created with id:     " + topicId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Topic type:                " + type);
                System.out.println("");
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create topic: \"" + topicId + "\" or Topic ID is duplicate");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. - Creates a new partition in the topic with id topic.
     *    - id is the partition’s identifier.
     *
     *      Output: A message confirming the partition’s creation.
     *      Usage: create partition <topic> <id>
     */
    private void createPartition(String topicId, String partitionId) {
        try {
            if (api.createPartition(topicId, partitionId)) {
                System.out.println(MAGENTA + "+ " + RESET + "Partition created with id  " + partitionId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Created in topic:          " + topicId);
                System.out.println("");
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create partition: \"" + partitionId + "\"");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. - Creates a new consumer group with the given identifier.
     *    - topic is the topic the consumer group is subscribed to.
     *    - rebalancing is the consumer group’s initial rebalancing
     *      method, one of Range or RoundRobin.
     *
     *      Output: A message confirming the consumer group’s creation.
     *      Usage: create consumer group <id> <topic> <rebalancing>
     */
    private void createConsumerGroup(String groupId, String topicId, String rebalancing) {
        try {
            if (api.createConsumerGroup(groupId, topicId, rebalancing)) {
                System.out.println(MAGENTA + "+ " + RESET + "Consumer group created:    " + groupId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Subscribed to topic:       " + topicId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Rebalancing strategy:      " + rebalancing);
                System.out.println("");
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create consumer group: \"" + groupId + "\"");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 4. - Creates a new consumer within a consumer group.
     *
     *      Output: A message confirming the consumer’s creation.
     *      Usage: create consumer <group> <id>
     */
    private void createConsumer(String groupId, String consumerId) {
        try {
            if (api.createConsumer(groupId, consumerId)) {
                System.out.println(MAGENTA + "+ " + RESET + "Consumer created with id:  " + consumerId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Consumer in group:         " + groupId);
                System.out.println("");
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create consumer: \"" + consumerId + "\"");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5. - Deletes the consumer with the given id.
     *
     *      Output: A message confirming the consumer’s deletion, and an output of
     *      the rebalanced consumer group that the consumer was previously in.
     *      Usage: delete consumer <consumer>
     */
    private void deleteConsumer(String consumerId) {
        try {
            List<String> groupsId = api.deleteConsumer(consumerId);
            if (groupsId != null) {
                System.out.println(BOLD_RED + "- " + RESET + "Consumer deleted, id:      " + consumerId);
                Thread.sleep(DELAY);
                for (String g : groupsId) {
                    System.out.println(BOLD_RED + "- " + RESET + "Consumer was from group:   " + g);
                    Thread.sleep(DELAY);
                }
            } else {
                System.err.println("Failed to delete consumer: \"" + consumerId + "\"");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 6. - Creates a new producer which produces events of the given type.
     *    - allocation is either Random or Manual, determining which method of
     *      partition selection is used for publishing events.
     *
     *      Output: A message confirming the producer’s creation.
     *      Usage: create producer <id> <type> <allocation>
     */
    @SuppressWarnings("unchecked")
    private void createProducer(String producerId, String type, String allocation) {
        try {
            Class<T> classType;
            switch (type.toLowerCase()) {
            case "string" -> classType = (Class<T>) String.class;
            case "integer" -> classType = (Class<T>) Integer.class;
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
            }

            Producer<T> producer = new Producer<>(producerId, classType, allocation);
            if (producer != null && api.addProducer(producerId, producer, allocation)) {
                System.out.println(MAGENTA + "+ " + RESET + "Producer created, id:      " + producerId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Producer type:             " + type);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Producer allocation:       " + allocation);
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create producer: \"" + producerId + "\"");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

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
     *      Usage: produce event <producer> <topic> <event>
     */
    private void produceEvent(String producerId, String topicId, String event) {
        try {
            api.produceEvent(producerId, topicId, event);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void produceEvent(String producerId, String topicId, String event, String partitionId) {
        try {
            api.produceEvent(producerId, topicId, event, partitionId);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 8. - The given consumer consumes an event from the given partition.
     *      Precondition: The consumer is already allocated to the given partition.
     *      Output: The id and contents of the event, showing that the consumer has
     *      received the event.
     *      Usage: consume event <consumer> <partition>
     */
    private void consumeSingleEvent(String consumerId, String partitionId) {
        try {
            api.consumeSingleEvent(consumerId, partitionId);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 9. - Consumes multiple events from the given partition.
     *      Output: The id and contents of each event received in order.
     *      Usage: consume events <consumer> <partition> <number of events>
     */
    private void consumeMultipleEvents(String consumerId, String partitionId, int numOfEvents) {
        try {
            api.consumeMultipleEvents(consumerId, partitionId, numOfEvents);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 10. - Output: Prints a visual display of the given topic, including all
     *       partitions and all of the events currently in each partition.
     *       Usage: show topic <topic>
     */
    private void showTopic(String topicId) {
        try {
            api.showTopic(topicId);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 11. - Shows all consumers in the consumer group, and which partitions each
     *       consumer is receiving events from.
     *       Usage: show consumer group <group>
     */
    private void showConsumerGroup(String groupId) {
        try {
            api.showConsumerGroup(groupId);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 12. - Produces a series of events in parallel. This is purely for
     *      demonstrating that your tributary can cope with multiple producers
     *      publishing events simultaneously.
     *      Usage: parallel produce (<producer>, <topic>, <event>), ...
     */
    private void produceEventsParallel(List<String> producerIds, List<String> topicId, List<String> event) {
        try {
            api.produceEventsParallel(producerIds, topicId, event);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 13. - Consumes a series of events in parallel. This is purely for
             demonstrating that your tributary can cope with multiple consumers
             receiving events simultaneously.
     *       Usage: parallel consume (<consumer>, <partition>), ...
     */
    private void consumeEventsParallel(List<String> consumerId, List<String> partitionId) {
        try {
            api.consumeEventsParallel(consumerId, partitionId);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 14. - Sets a new strategy for a given consumer group and rebalances it
     *       Usage: set consumer group rebalancing <group> <rebalancing>
     */
    private void consumerGroupRebalance(String strategy, String groupId) {
        try {
            api.setRebalancingStrategy(strategy, groupId);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 15 - Updates the rebalancing method of consumer group group to
     *      be one of Range or RoundRobin.
     *      Usage: playback <consumer> <partition> <offset>
     */
    private void consumerGroupPlayback(String consumerId, String partitionId, int offset) {
        try {
            api.playback(consumerId, partitionId, offset);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Helper function from
    //https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
    public static String removeLastChar(String str) {
        return removeChars(str, 1);
    }

    public static String removeChars(String str, int numberOfCharactersToRemove) {
        if (str != null && !str.trim().isEmpty()) {
            return str.substring(0, str.length() - numberOfCharactersToRemove);
        }
        return "";
    }
}
