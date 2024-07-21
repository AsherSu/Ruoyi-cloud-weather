package org.dromara.weather.domain.hefeng.common;

public class HefengRedis {
    private static final String HEFENG_MINUTELY_PREFIX = "hefeng_minutely5m_";

    private static final String HEFENG_WARNING_PREFIX = "hefeng_warning_";

    private static final String HEFENG_3d_PREFIX = "hefeng_3d_";

    private static final int HEFENG_MINUTELY_TIME = 60 * 5;

    private static final int HEFENG_WARNING_TIME = 60 * 5;

    public static String generateMinutely5mKey(String location){
        return HEFENG_MINUTELY_PREFIX + location;
    }

    public static String generateWarningKey(String cityCode){
        return HEFENG_WARNING_PREFIX + cityCode;
    }

    public static String generate3dKey(String cityCode){
        return HEFENG_3d_PREFIX + cityCode;
    }

    public static int getMinutely5mTime(){
        return HEFENG_MINUTELY_TIME;
    }

    public static int getWarningTime(){
        return HEFENG_WARNING_TIME;
    }
}
