package org.dromara.weather.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DynamicProperties {

    private static Properties properties;

    static  {
        String directoryPath = "weather-service/src/main/resources";
        properties = new Properties();
        try {
            // 获取指定目录
            Path directory = Paths.get(directoryPath);

            // 遍历目录中的所有文件
            Files.walk(directory)
                .filter(path -> path.toString().endsWith(".properties"))
                .forEach(filePath -> {
                    try {
                        // 读取每个.properties文件
                        properties.load(new FileInputStream(filePath.toFile()));
                    } catch (IOException e) {
                        System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                    }
                });
        } catch (IOException e) {
            System.err.println("Error accessing directory " + directoryPath + ": " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

    public static Integer getInteger(String key) {
        return Integer.valueOf(properties.getProperty(key));
    }

    public static Long getLong(String key) {
        return Long.valueOf(properties.getProperty(key));
    }

    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(properties.getProperty(key));
    }

    public static Double getDouble(String key) {
        return Double.valueOf(properties.getProperty(key));
    }

    public static Float getFloat(String key) {
        return Float.valueOf(properties.getProperty(key));
    }

    public static Short getShort(String key) {
        return Short.valueOf(properties.getProperty(key));
    }

    public static Byte getByte(String key) {
        return Byte.valueOf(properties.getProperty(key));
    }

    public static Object getObject(String key) {
        return properties.get(key);
    }
}
