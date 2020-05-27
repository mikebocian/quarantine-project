package co.storkie;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static double[][] toArray(List<List<Double>> doubles) {
        return doubles.stream().map(i -> i.toArray(new Double[]{})).map(ArrayUtils::toPrimitive).toArray(double[][]::new);
    }

    public static List<List<Double>> toList(double[][] doubles) {
        return Arrays.stream(doubles).map(ArrayUtils::toObject).map(Arrays::asList).collect(Collectors.toList());
    }


}
