/opt/kafka_2.13-2.5.0/bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic audio-fft
/opt/kafka_2.13-2.5.0/bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic audio

/opt/kafka_2.13-2.5.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic audio --config retention.ms=1000000
/opt/kafka_2.13-2.5.0/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic audio-fft --config retention.ms=1000000
