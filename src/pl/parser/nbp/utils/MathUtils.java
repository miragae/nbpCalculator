package pl.parser.nbp.utils;

import java.util.List;

/**
 * Utility methods for calculations on data
 * @author Michał Lal
 */
public class MathUtils {

    /**
     * Calculates mean measure of given data
     * @param data list of double values
     * @return mean of data, 0 if list is empty or null
     */
    public static double calculateMean(List<Double> data) {
        double size = data == null ? 0 : data.size();
        return size == 0 ? 0 : data.stream().mapToDouble(Double::doubleValue).sum() / size;
    }

    /**
     * Calculates variance measure of given data
     * @param data list of double values
     * @return variance of data, 0 if list is empty or null
     */
    public static double calculateVariance(List<Double> data) {
        double mean = calculateMean(data);
        double size = data == null ? 0 : data.size();
        return size == 0 ? 0 : data.stream().mapToDouble(Double::doubleValue).map(d -> (d - mean) * (d - mean)).sum() / size;
    }

    /**
     * Calculates standard deviation measure of given data
     * @param data list of double values
     * @return standard deviation of data, 0 if list is empty or null
     */
    public static double calculateStandardDeviation(List<Double> data) {
        double variance = calculateVariance(data);
        return variance == 0 ? 0 : Math.sqrt(variance);
    }
}
