import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

public class JSONFileUtil {
    //Creates file in directory
    public static void createIntegerIndex(int i, String dir, String baseFileName) {
        JSONObject jsonObject = new JSONObject();

        //Create nested header
        JSONObject header = new JSONObject();
        header.put("timestamp", "1659275200000");
        String id = "event_" + i;
        header.put("id", id);
        header.put("payloadType", "Integer");

        jsonObject.put("headers", header);

        jsonObject.put("key", Integer.toString(i));

        //nested value
        JSONObject value = new JSONObject();
        value.put("info", id);

        jsonObject.put("value", value);

        //Export the file
        try (FileWriter file = new FileWriter(dir + "/" + baseFileName + "_" + i + ".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonObject.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createStringIndex(int i, String dir, String baseFileName) {
        JSONObject jsonObject = new JSONObject();

        //Create nested header
        JSONObject header = new JSONObject();
        header.put("timestamp", "1659275200000");
        String id = "event_" + i;
        header.put("id", id);
        header.put("payloadType", "String");

        jsonObject.put("headers", header);

        jsonObject.put("key", Integer.toString(i));

        //nested value
        JSONObject value = new JSONObject();
        value.put("info", id);

        jsonObject.put("value", value);

        //Export the file
        try (FileWriter file = new FileWriter(dir + "/" + baseFileName + "_" + i + ".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(jsonObject.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Taken from https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string
    private static String removeLastChar(String str) {
        return removeChars(str, 1);
    }

    private static String removeChars(String str, int numberOfCharactersToRemove) {
        if (str != null && !str.trim().isEmpty()) {
            return str.substring(0, str.length() - numberOfCharactersToRemove);
        }
        return "";
    }

    public static String generateCommand() {
        String command = "parallel produce ";

        String[] partitions = {"A_P0", "A_P1", "A_P2"};
        String[] producers = {"producer_1", "producer_2"};
        String topicName = "topic_A";

        for (int i = 0; i < 50; ++i) {
            String name = String.format("xParallelism_Integer_%d.json", i);

            String producer = producers[i % 2];

            String commandAppend = String.format("(%s, %s, %s), ", producer, topicName, name);

            command += commandAppend;
        }

        command = removeChars(command, 2);

        return command;
    }

    public static void main(String[] args) {
        System.out.println("JSON File Creator Active");

        //Change to resources directory in machine
        String directory =
        "/Users/ner0/Documents/Git/yeeun_personal/comp_2511/programs/assignment-iii/app/src/main/resources";

        // //Create
        // for (int i = 0; i < 50; ++i) {
        //     createIntegerIndex(i, directory, "xParallelism_Integer");
        // }

        // for (int i = 0; i < 50; ++i) {
        //     createStringIndex(i, directory, "xParallelism_String");
        // }

        System.out.println(generateCommand());
    }
}
