currentPath=$(pwd)
batch_input=$currentPath"/paymo_input/batch_payment.txt"
stream_input=$currentPath"/paymo_input/stream_payment.txt"
output1=$currentPath"/paymo_output/output1.txt"
output2=$currentPath"/paymo_output/output2.txt"
output3=$currentPath"/paymo_output/output3.txt"
cd src
chmod 777 *.java
javac digitalpaymentfinal.java
java digitalpaymentfinal $batch_input $stream_input 1 $output1 2 $output2 4 $output3
