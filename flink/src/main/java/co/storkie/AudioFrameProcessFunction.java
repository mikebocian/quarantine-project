package co.storkie;

import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.collect.EvictingQueue;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static co.storkie.StreamingJob.lag;

public class AudioFrameProcessFunction extends ProcessFunction<AudioFrame, AggregatedAudioFrame> {


    public static final int WINDOW_SIZE = 1024 * 8;


    private static final Logger LOGGER = LoggerFactory.getLogger(AudioFrameProcessFunction.class);


    private final EvictingQueue<AudioFrame> slidingWindow = EvictingQueue.create(10000);


    public AudioFrameProcessFunction() {
    }

    @Override
    public void processElement(AudioFrame value, Context ctx, Collector<AggregatedAudioFrame> out) {
        concat(value);
        AggregatedAudioFrame trim = trim();
        if (trim != null) {
            LOGGER.info(String.format("Delay after aggregation: start: %s, end: %s, timeLength: %s, frameSize: %s", lag(trim.start_ts).toMillis(), lag(trim.end_ts).toMillis(), trim.frameTime().toMillis(), trim.data.length));
            out.collect(trim);
        }
    }


    public void concat(AudioFrame value) {
        value.setData(value.getData().stream().map(a -> a.stream().map(i -> i * 4.0).collect(Collectors.toList())).collect(Collectors.toList()));
        slidingWindow.add(value);
    }

    public AggregatedAudioFrame trim() {

        List<AudioFrame> list = new ArrayList<>(slidingWindow);
        Collections.reverse(list);

        long totalNoOfSamples = list.stream().mapToLong(i -> i.getData().size()).sum();
        if (totalNoOfSamples < WINDOW_SIZE)
            return null;

        AggregatedAudioFrame aggregatedAudioFrame = null;

        List<List<Double>> resultBuffer = new ArrayList<>();
        for (AudioFrame audioFrame : list) {
            if (aggregatedAudioFrame == null) {
                aggregatedAudioFrame = new AggregatedAudioFrame();
                aggregatedAudioFrame.start_ts = audioFrame.end_ts;
                LOGGER.info("Starting new aggregated frame: " + lag(aggregatedAudioFrame.start_ts).toMillis());
            }

            if (resultBuffer.size() == WINDOW_SIZE) {
                LOGGER.info("WindowSize: " + WINDOW_SIZE);
                break;
            }

            int spaceLeft = WINDOW_SIZE - resultBuffer.size();
            aggregatedAudioFrame.end_ts = audioFrame.getStartTs();
            resultBuffer.addAll((audioFrame.getData()).subList(Math.max(0, audioFrame.getData().size() - spaceLeft), audioFrame.getData().size()));

        }

        if (aggregatedAudioFrame != null) {
            aggregatedAudioFrame.data = Utils.toArray(resultBuffer);

            return aggregatedAudioFrame;
        }

        return null;

    }
}