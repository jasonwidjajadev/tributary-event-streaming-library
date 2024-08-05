#!/bin/dash

: <<'END_COMMENT'
Do not delete, this is the command for 5 minute video

To run: ./run.sh

# Replace it with your path in your computer
END_COMMENT

echo ""
cd /Users/ner0/Documents/Git/yeeun_personal/comp_2511/programs/assignment-iii

echo "Compiling TributaryCLI.java ..."

# javac -d app/src/main/java $(find app/src/main/java -name "*.java")
# ./gradlew clean build
./gradlew clean shadowJar

if [ $? -eq 0 ]; then
    echo "Compilation successful. Running TributaryCLI..."
    # ./run_tributary_cli.exp
    # ==========================================================================
    # CLASSPATH=app/build/classes/java/main:$(find app/build/libs -name '*.jar' | tr '\n' ':')

    # java -cp $CLASSPATH tributary.cli.TributaryCLI
    # ==========================================================================
    # Set the classpath to include the build output and dependencies

    # CLASSPATH=$(find app/build/libs -name '*.jar' | tr '\n' ':')app/build/classes/java/main
    # ==========================================================================
    # # Export CLASSPATH to be used in the expect script
    export CLASSPATH=app/build/libs/your-app-0.1.0.jar

    # # Run the Java program using expect
    # ./run_tributary_cli.exp
    # ==========================================================================
    expect <<EOF
        # Set an indefinite timeout for expect commands
        set timeout -1
        set one_second 0
        set two_seconds 0
        set half_second 50

        # Start the TributaryCLI program
        spawn java -cp \$env(CLASSPATH) tributary.cli.TributaryCLI

        # ======================================================================
        # create topic <id> <type> =============================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create topic topic_A Integer\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create topic topic_B String\r"
        sleep \$one_second

        # ======================================================================
        # create partition <topic> <id> ========================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_A A_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_A A_P1\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_A A_P2\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_A A_P3\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P1\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P2\r"
        sleep \$one_second

        # ======================================================================
        # create consumer group <id> <topic> <rebalancing> =====================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer group consumer_group_A topic_A Range\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer group consumer_group_B topic_B RoundRobin\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_A consumer_I\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_A consumer_II\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_A consumer_III\r"
        sleep \$one_second

        # ----------------------------------------------------------------------

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_B consumer_I\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_B consumer_II\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_B consumer_III\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_B consumer_IV\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create producer producer_1 Integer random\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create producer producer_2 Integer random\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create producer producer_3 String random\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second

        # quit =================================================================

        # at this point there are three partitions for topic A, A_P0-P2 

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "parallel produce (producer_1, topic_A, xParallelism_Integer_0.json), (producer_2, topic_A, xParallelism_Integer_1.json), (producer_1, topic_A, xParallelism_Integer_2.json), (producer_2, topic_A, xParallelism_Integer_3.json), (producer_1, topic_A, xParallelism_Integer_4.json), (producer_2, topic_A, xParallelism_Integer_5.json), (producer_1, topic_A, xParallelism_Integer_6.json), (producer_2, topic_A, xParallelism_Integer_7.json), (producer_1, topic_A, xParallelism_Integer_8.json), (producer_2, topic_A, xParallelism_Integer_9.json), (producer_1, topic_A, xParallelism_Integer_10.json), (producer_2, topic_A, xParallelism_Integer_11.json), (producer_1, topic_A, xParallelism_Integer_12.json), (producer_2, topic_A, xParallelism_Integer_13.json), (producer_1, topic_A, xParallelism_Integer_14.json), (producer_2, topic_A, xParallelism_Integer_15.json), (producer_1, topic_A, xParallelism_Integer_16.json), (producer_2, topic_A, xParallelism_Integer_17.json)\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "parallel produce (producer_1, topic_A, xParallelism_Integer_18.json), (producer_2, topic_A, xParallelism_Integer_19.json), (producer_1, topic_A, xParallelism_Integer_20.json), (producer_2, topic_A, xParallelism_Integer_21.json), (producer_1, topic_A, xParallelism_Integer_22.json), (producer_2, topic_A, xParallelism_Integer_23.json), (producer_1, topic_A, xParallelism_Integer_24.json), (producer_2, topic_A, xParallelism_Integer_25.json), (producer_1, topic_A, xParallelism_Integer_26.json), (producer_2, topic_A, xParallelism_Integer_27.json), (producer_1, topic_A, xParallelism_Integer_28.json), (producer_2, topic_A, xParallelism_Integer_29.json), (producer_1, topic_A, xParallelism_Integer_30.json), (producer_2, topic_A, xParallelism_Integer_31.json), (producer_1, topic_A, xParallelism_Integer_32.json)\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "parallel produce (producer_1, topic_A, xParallelism_Integer_32.json), (producer_2, topic_A, xParallelism_Integer_33.json), (producer_1, topic_A, xParallelism_Integer_34.json), (producer_2, topic_A, xParallelism_Integer_35.json), (producer_1, topic_A, xParallelism_Integer_36.json), (producer_2, topic_A, xParallelism_Integer_37.json), (producer_1, topic_A, xParallelism_Integer_38.json), (producer_2, topic_A, xParallelism_Integer_39.json), (producer_1, topic_A, xParallelism_Integer_40.json), (producer_2, topic_A, xParallelism_Integer_41.json), (producer_1, topic_A, xParallelism_Integer_42.json), (producer_2, topic_A, xParallelism_Integer_43.json), (producer_1, topic_A, xParallelism_Integer_44.json), (producer_2, topic_A, xParallelism_Integer_45.json), (producer_1, topic_A, xParallelism_Integer_46.json), (producer_2, topic_A, xParallelism_Integer_47.json), (producer_1, topic_A, xParallelism_Integer_48.json), (producer_2, topic_A, xParallelism_Integer_49.json)\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show consumer group consumer_group_A\r"
        sleep \$one_second

        # ======================================================================
        
        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "consume event consumer_I A_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "consume event consumer_I A_P1\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "consume event consumer_II A_P2\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "consume event consumer_III A_P3\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "parallel consume (consumer_I, A_P0), (consumer_III, A_P3), (consumer_II, A_P2), (consumer_I, A_P1), (consumer_I, A_P0), (consumer_I, A_P0), (consumer_I, A_P1), (consumer_I, A_P0), (consumer_I, A_P1), (consumer_I, A_P1), (consumer_I, A_P1), (consumer_III, A_P3), (consumer_III, A_P3), (consumer_III, A_P3), (consumer_I, A_P0), (consumer_III, A_P3), (consumer_II, A_P2), (consumer_I, A_P0), (consumer_I, A_P0), (consumer_III, A_P3), (consumer_III, A_P3), (consumer_II, A_P2), (consumer_I, A_P0), (consumer_I, A_P1), (consumer_II, A_P2), (consumer_II, A_P2), (consumer_III, A_P3), (consumer_I, A_P0), (consumer_II, A_P2)\r"
        sleep \$one_second
         
        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "parallel consume (consumer_III, A_P3), (consumer_I, A_P1), (consumer_I, A_P1), (consumer_III, A_P3), (consumer_II, A_P2), (consumer_I, A_P1), (consumer_II, A_P2), (consumer_II, A_P2), (consumer_II, A_P2), (consumer_I, A_P1), (consumer_I, A_P0), (consumer_III, A_P3), (consumer_I, A_P1), (consumer_I, A_P0), (consumer_I, A_P1), (consumer_I, A_P0), (consumer_I, A_P0)\r"
        sleep \$one_second

        # quit =================================================================
        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "q\r"
        sleep \$two_seconds
        after \$half_second
        expect eof

EOF

else
    echo "Compilation failed."
fi
