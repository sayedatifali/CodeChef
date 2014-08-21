python generator.py > input
cat input|./a.out > output_cpp
cat input|java Main > output_java
diff --brief <(sort output_cpp) <(sort output_java) >/dev/null
comp_value=$?
counter=0
while [ $comp_value -eq 0 ]
do
    echo "PASS "$counter
    python generator.py > input
    cat input|./a.out > output_cpp
    cat input|java Main > output_java
    diff --brief <(sort output_cpp) <(sort output_java) >/dev/null
    comp_value=$?
    cat output_cpp
    ((counter++))
done

echo "They are different !"
