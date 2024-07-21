//package org.dromara.weather.utils;
//
//import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
//import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
//import org.apache.commons.math3.fitting.SimpleCurveFitter;
//import org.apache.commons.math3.fitting.WeightedObservedPoints;
//import org.dromara.weather.domain.hefeng.common.WeatherResponse;
//import org.dromara.weather.domain.hefeng.minutely.HefengMinutely;
//
//import java.util.List;
//
//public class MathUtil {
//
//    // 拟合
//    public static double[] fitting(double[] guess){
//        ParametricUnivariateFunction function = new PolynomialFunction.Parametric();/*多项式函数*/
//        // 初始化拟合
//        SimpleCurveFitter curveFitter = SimpleCurveFitter.create(function, guess);
//
//        // 添加数据点
//        WeightedObservedPoints observedPoints = new WeightedObservedPoints();
//        WeatherResponse minutely5mWeather = HefengWeatherUtil.getMinutely5mWeather("106.63,26.65");
//        List<HefengMinutely> minutely = minutely5mWeather.getMinutely();
//
//        for (int i =0 ; i<minutely.size();i++){
//            observedPoints.add(i, minutely.get(i).getPrecip());
//        }
//        /*
//         * best 为拟合结果
//         * 依次为 常数项、1次项、二次项
//         * 对应 y = a + bx + cx^2 中的 a, b, c
//         * */
//        double[] best = curveFitter.fit(observedPoints.toList());
//
//        /*
//         * 根据拟合结果重新计算
//         * */
////        int j = 0;
////        List<double[]> fitData = new ArrayList<>();
////        for (HefengMinutely point : minutely) {
////            double x = j;
////            double y = best[0] + best[1] * x + best[2] * Math.pow(x, 2) + best[3] * Math.pow(x, 3)
////                + best[4] * Math.pow(x, 4) + best[5] * Math.pow(x, 5) + best[6] * Math.pow(x, 6)
////                + best[7] * Math.pow(x, 7) + best[8] * Math.pow(x, 8) + best[9] * Math.pow(x, 9)
////                + best[10] * Math.pow(x, 10) + best[11] * Math.pow(x, 11) + best[12] * Math.pow(x, 12)
////                + best[13] * Math.pow(x, 13) + best[14] * Math.pow(x, 14) + best[15] * Math.pow(x, 15)
////                + best[16] * Math.pow(x, 16) + best[17] * Math.pow(x, 17) + best[18] * Math.pow(x, 18)
////                + best[19] * Math.pow(x, 19);
////            double[] xy = {x, y};
////            fitData.add(xy);
////            j++;
////        }
//        return best;
//    }
//
//
//    // 求交点
//    public static double[][] getIntersectionPoint(double[] fit,float value){
//
//    }
//
//    // 求导 找区间
//
//
//}
