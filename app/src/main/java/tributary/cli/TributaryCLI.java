package tributary.cli;

import java.util.List;
import java.util.Scanner;
import tributary.api.API;
import tributary.api.APIFactory;

public class TributaryCLI {
    private static API api = APIFactory.createAdminClient();
    private static final int DELAY = 0;
    // private static final int DELAY = 3000;

    private static final String RESET = "\u001B[0m";
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String MAGENTA = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String WHITE = "\033[0;37m";

    // Bold Colors
    private static final String BOLD_BLACK = "\033[1;30m";
    private static final String BOLD_RED = "\033[1;31m";
    private static final String BOLD_GREEN = "\033[1;32m";
    private static final String BOLD_YELLOW = "\033[1;33m";
    private static final String BOLD_BLUE = "\033[1;34m";
    private static final String BOLD_MAGENTA = "\033[1;35m";
    private static final String BOLD_CYAN = "\033[1;36m";
    private static final String BOLD_WHITE = "\033[1;37m";

    // Background Colors
    private static final String BACKGROUND_BLACK = "\033[40m";
    private static final String BACKGROUND_RED = "\033[41m";
    private static final String BACKGROUND_GREEN = "\033[42m";
    private static final String BACKGROUND_YELLOW = "\033[43m";
    private static final String BACKGROUND_BLUE = "\033[44m";
    private static final String BACKGROUND_MAGENTA = "\033[45m";
    private static final String BACKGROUND_CYAN = "\033[46m";
    private static final String BACKGROUND_WHITE = "\033[47m";

    public static void main(String[] args) {

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

    private static void processCommand(String command) {
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
            default:
                System.out.println("Unknown command: \"" + command + "\"");
            }
        }
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
    private static void createTopic(String topidId, String type) {
        try {
            if (api.createTopic(topidId, type)) {
                System.out.println(MAGENTA + "+ " + RESET + "Topic created with id:     " + topidId);
                Thread.sleep(DELAY);
                System.out.println(MAGENTA + "+ " + RESET + "Topic type:                " + type);
                System.out.println("");
                Thread.sleep(DELAY);
            } else {
                System.err.println("Failed to create topic: \"" + topidId + "\"");
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
    private static void createPartition(String topicId, String partitionId) {
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
    private static void createConsumerGroup(String groupId, String topicId, String rebalancing) {
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
    private static void createConsumer(String groupId, String consumerId) {
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
    private static void deleteConsumer(String consumerId) {
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
    private static void createProducer(String producerId, String type, String allocation) {
        try {
            if (api.createProducer(producerId, type, allocation)) {
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

    // if a message has no key, its default partition is round robin allocation
    // messsage with the same key always go in the same parttiion in order

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
    private static void produceEvent(String producerId, String topicId, String event) {
        try {
            api.produceEvent(producerId, topicId, event);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void produceEvent(String producerId, String topicId, String event, String partitionId) {
        try {
            api.produceEvent(producerId, topicId, event, partitionId);
            Thread.sleep(DELAY);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Example
    //TODO -------------- friday finish Max ->> to finish assignement

    /**
     * 8. - The given consumer consumes an event from the given partition.
     *      Precondition: The consumer is already allocated to the given partition.
     *      Output: The id and contents of the event, showing that the consumer has
     *      received the event.
     *      Usage: consume event <consumer> <partition>
     */

    /**
     * 9. - Consumes multiple events from the given partition.
     *      Output: The id and contents of each event received in order.
     *      Usage: consume events <consumer> <partition> <number of events>
     */
    //TODO -------------- saturday finish
    /**
     * 10. - Output: Prints a visual display of the given topic, including all
     *       partitions and all of the events currently in each partition.
     *       Usage: show topic <topic>
     */
    //TODO this is not yet fully implemented, please create format like a table
    private static void showTopic(String topicId) {
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
    //TODO this is not yet fully implemented,
    private static void showConsumerGroup(String groupId) {
        try {
            api.showConsumerGroup(groupId);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * 12. TODO MAX
     *      Usage: parallel produce (<producer>, <topic>, <event>), ...
     */

    /**
     * 13. TODO MAX
     *      Usage: parallel consume (<consumer>, <partition>)
     */

    /**
     * 14. TODO MAX
     *      Usage: set consumer group rebalancing <group> <rebalancing>
     */

    /**
     * 15. TODO MAX
     *      Usage: playback <consumer> <partition> <offset>
     */

    //TODO -------------- sunday finish

    // uml, pair blog
    // Video
    //TODO -------------- Monday finish
}

/*




//** create topic <id> <type>
// create topic weather_update Integer
// create topic e_commerce_order String
create topic topic_A Integer
create topic topic_B String

//** create partition <topic> <id>
// create partition weather_update 0
// create partition weather_update 1
// create partition weather_update 2

// create partition e_commerce_order 0
// create partition e_commerce_order 1
// create partition e_commerce_order 2

create partition topic_A A_P0
create partition topic_A A_P1
create partition topic_A A_P2
create partition topic_A A_P3
create partition topic_A A_P4

create partition topic_B B_P0
create partition topic_B B_P1
create partition topic_B B_P2
create partition topic_B B_P3
create partition topic_B B_P4
create partition topic_B B_P5
create partition topic_B B_P6

//** create consumer group <id> <topic> <rebalancing>
// create consumer group weather_group weather_update range
// create consumer group e_commerce_group e_commerce_order round_robin

create consumer group consumer_group_A topic_A range
create consumer group consumer_group_B topic_B round_robin

//** create consumer <group> <id>
// create consumer weather_group consumer_1_real_time_alert
// create consumer weather_group consumer_2_ai_weather_predictions
// create consumer weather_group consumer_3_stores_weather_data

// create consumer e_commerce_group consumer_1_order_validation
// create consumer e_commerce_group consumer_2_order_fullfillment
// create consumer e_commerce_group consumer_3_order_shipping_service
// create consumer e_commerce_group consumer_4_order_review

create consumer consumer_group_A consumer_I
create consumer consumer_group_A consumer_II
create consumer consumer_group_A consumer_III

create consumer consumer_group_B consumer_I
create consumer consumer_group_B consumer_II
create consumer consumer_group_B consumer_III
create consumer consumer_group_B consumer_IV
create consumer consumer_group_B consumer_V

//** delete consumer <consumer>
// delete consumer consumer_4
delete consumer consumer_V

//** create producer <id> <type> <allocation>
create producer producer_1 Integer manual
create producer producer_2 String random

//** produce event <producer> <topic> <event> <partition>

// produce event producer_1 weather_update /weather/key_sydney_value_sydney1.json partition_0
// produce event producer_1 weather_update /weather/key_sydney_value_paris1.json partition_1
// produce event producer_1 weather_update /weather/key_sydney_value_tokyo1.json partition_2

// produce event producer_1 e_commerce_order /e_commerce/key_order_fulfillment_order_1.json partition_0
// produce event producer_1 e_commerce_order /e_commerce/key_order_shipping_order_1.json partition_1
// produce event producer_1 e_commerce_order /e_commerce/key_order_validation_order_1.json partition_2

create topic topic_A Integer
create partition topic_A A_P0
create producer producer_1 Integer manual


produce event producer_1 topic_A integer_key_0_event_0.json A_P0
produce event producer_1 topic_A integer_key_1_event_1.json A_P1
produce event producer_1 topic_A integer_key_2_event_2.json A_P2
produce event producer_1 topic_A integer_key_3_event_3.json A_P3
produce event producer_1 topic_A integer_key_4_event_4.json A_P4

produce event producer_1 topic_A integer_key_1_event_1.json A_P0
produce event producer_1 topic_A integer_key_2_event_2.json A_P0
produce event producer_1 topic_A integer_key_3_event_3.json A_P0
produce event producer_1 topic_A integer_key_4_event_4.json A_P0

create topic topic_B String
create partition topic_B B_P0
create producer producer_2 String random


produce event producer_2 topic_B string_key_0_event_0.json
produce event producer_2 topic_B string_key_1_event_1.json
produce event producer_2 topic_B string_key_2_event_2.json
produce event producer_2 topic_B string_key_3_event_3.json
produce event producer_2 topic_B string_key_4_event_4.json
produce event producer_2 topic_B string_key_5_event_5.json
produce event producer_2 topic_B string_key_6_event_6.json

//** consume event <consumer> <partition>

//** consume events <consumer> <partition> <number of events>

//** show topic <topic>
show topic topic_A
show topic topic_B

//** show consumer group <group>

//** parallel produce (<producer>, <topic>, <event>), ...

//** parallel consume (<consumer>, <partition>)

//** set consumer group rebalancing <group> <rebalancing>

//** playback <consumer> <partition> <offset>

*/
