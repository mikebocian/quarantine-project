package co.storkie;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.jtransforms.fft.DoubleFFT_1D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;


public class FFTFunction extends ProcessFunction<AggregatedAudioFrame, FFTResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FFTFunction.class);

    @Override
    public void processElement(AggregatedAudioFrame fftWindow, Context ctx, Collector<FFTResult> collector) throws Exception {

        if (fftWindow.data != null && fftWindow.data.length > 0) {

            double[][] channelToSample = transposeMatrix(fftWindow.data);
            FFTResult.Builder fftResult = FFTResult.newBuilder();
            fftResult.setFft(new ArrayList<>());
            for (double[] doubles : channelToSample) {
                DoubleFFT_1D fft = new DoubleFFT_1D(doubles.length);
                double[] toFtt = Arrays.copyOf(doubles, doubles.length - (doubles.length % 2));

                double[] window = new double[toFtt.length];
                hamming(window);
                applyWindow(toFtt, window);

                fft.realForward(toFtt);
                double[] result = new double[toFtt.length / 2];
                for (int k = 0; k < toFtt.length / 2; k++) {
                    double sqrt = Math.sqrt((toFtt[2 * k] * toFtt[2 * k]) + (toFtt[2 * k + 1] * toFtt[2 * k + 1]));
                    result[k] = sqrt;
                }
                fftResult.getFft().add(Arrays.asList(ArrayUtils.toObject(result)));
            }
            fftResult.setBucketSize(44100 / 2.0 / fftResult.getFft().get(0).size())
                    .setStartTs(fftWindow.start_ts)
                    .setEndTs(fftWindow.end_ts);
            collector.collect(fftResult.build());

            LOGGER.info("Final delay: " + StreamingJob.lag(fftWindow.start_ts).toMillis());
        }
    }

    public static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }

    static void hamming(double[] data) {
        int start = (data.length - AudioFrameProcessFunction.WINDOW_SIZE) / 2;
        int stop = (data.length + AudioFrameProcessFunction.WINDOW_SIZE) / 2;
        double scale = 1.0 / (double) AudioFrameProcessFunction.WINDOW_SIZE / 0.54;
        double factor = 2 * Math.PI / (double) AudioFrameProcessFunction.WINDOW_SIZE;
        for (int i = 0; start < stop; start++, i++) {
            data[i] = scale * (25.0 / 46.0 - 21.0 / 46.0 * Math.cos(factor * i));
        }
    } // hamming()


    public static void applyWindow(double[] data, double[] window) {
        for (int i = 0; i < data.length; i++) {
            data[i] *= window[i];
        }
    }

}