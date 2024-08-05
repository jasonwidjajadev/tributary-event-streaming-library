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
        send "create partition topic_A A_P4\r"
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

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P3\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P4\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P5\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P6\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create partition topic_B B_P7\r"
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
        send "create consumer consumer_group_A consumer_V\r"
        sleep \$one_second

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

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create consumer consumer_group_B consumer_V\r"
        sleep \$one_second
        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "delete consumer consumer_V\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create producer producer_1 Integer manual\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "create producer producer_2 String random\r"
        sleep \$one_second

        # ======================================================================
        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second
        # quit =================================================================
        
        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_0_event_0.json A_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_1_event_1.json A_P1\r"
        sleep \$one_second



        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_2_event_2.json A_P2\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_3_event_3.json A_P3\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_4_event_4.json A_P4\r"
        sleep \$one_second


        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second
        # quit =================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_1_event_1.json A_P0\r"
        sleep \$one_second



        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_2_event_2.json A_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_3_event_3.json A_P0\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_1 topic_A integer_key_4_event_4.json A_P0\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second
        # quit =================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_0_event_0.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_1_event_1.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_2_event_2.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_3_event_3.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_4_event_4.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_5_event_5.json\r"
        sleep \$one_second

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "produce event producer_2 topic_B string_key_6_event_6.json\r"
        sleep \$one_second

        # ======================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_A\r"
        sleep \$one_second
        # quit =================================================================

        expect "Enter a command:"
        sleep \$two_seconds
        after \$half_second
        send "show topic topic_B\r"
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
