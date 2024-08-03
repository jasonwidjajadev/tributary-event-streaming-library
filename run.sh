#!/bin/dash

: <<'END_COMMENT'
Do not delete, this is the command for 5 minute video

To run: ./run.sh

# Replace it with your path in your computer
END_COMMENT

echo ""
cd /Users/jasonwidjaja/Desktop/projects/z5494973/comp2511/assignment-iii/ || exit

echo "Compiling TributaryCLI.java ..."

# javac -d app/src/main/java $(find app/src/main/java -name "*.java")
./gradlew clean build

if [ $? -eq 0 ]; then
    echo "Compilation successful. Running TributaryCLI..."
    ./run_tributary_cli.exp
else
    echo "Compilation failed."
fi
