package co.storkie;

import java.io.Serializable;
import java.time.Duration;

public class AggregatedAudioFrame implements Serializable {
    double start_ts = 0;
    double end_ts = 0;
    double[][] data = new double[0][];


    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public long length() {
        return data.length;
    }

    public double getStart_ts() {
        return start_ts;
    }

    public void setStart_ts(double start_ts) {
        this.start_ts = start_ts;
    }

    public double getEnd_ts() {
        return end_ts;
    }

    public void setEnd_ts(double end_ts) {
        this.end_ts = end_ts;
    }

    public Duration frameTime(){
        return Duration.ofMillis((long) (end_ts - start_ts));
    }


    public AggregatedAudioFrame() {
    }
}