/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.storkie;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.formats.avro.AvroDeserializationSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

    static Duration lag(double ts) {
        return Duration.between(Instant.ofEpochMilli((long) (ts)), Instant.now());
    }

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();


        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");

        env
                .addSource(new FlinkKafkaConsumer011<AudioFrame>("audio", AvroDeserializationSchema.forSpecific(AudioFrame.class), properties))
                .filter(new FilterFunction<AudioFrame>() {
                    @Override
                    public boolean filter(AudioFrame value) throws Exception {
                        return lag(value.getStartTs()).toMillis() < 1000;
                    }
                })
                .process(new AudioFrameProcessFunction())
                .process(new FFTFunction())
                .addSink(new FlinkKafkaProducer011<>("localhost:9092",
                        "audio-fft", new AvroFileSerializationSchema<>()));

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }


}
