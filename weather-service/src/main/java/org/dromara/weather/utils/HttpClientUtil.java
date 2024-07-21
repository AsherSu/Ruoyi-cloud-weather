package org.dromara.weather.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final CloseableHttpClient httpclient;

    static {
        // 创建连接池管理器
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(100);
        RequestConfig requestConfig = RequestConfig.custom()
            // 设置连接超时时间(单位毫秒) tcp握手时间上限
            .setConnectTimeout(500)
            // 设置请求超时时间(单位毫秒) 从服务器读取数据的时间上限
            .setSocketTimeout(1000)
            // 从连接池获取连接的超时时间(单位毫秒)
            .setConnectTimeout(2000)
            // 设置代理
//            .setProxy(host)
            .build();
        httpclient = HttpClients.custom()
            .setConnectionManager(connectionManager)
            .setDefaultRequestConfig(requestConfig)
            .build();
    }

    public static String get(String url) {
        return get(url, new Header[]{});
    }

    public static void main(String[] args) {
        String url = "https://devapi.qweather.com/v7/weather/now?location=101010100&key=ccb8740644444f77b3aa91dfe4af2c8a";
        String s = get(url);
        System.out.println(s);
    }

    /**
     * 发送 get请求
     */
    public static String get(String url, Header[] headers) {
        try {
            // 创建局部配置
//            HttpHost host = new HttpHost("localhost", 8080);
//            RequestConfig requestConfig = RequestConfig.custom()
//                // 设置连接超时时间(单位毫秒) tcp握手时间上限
//                .setConnectTimeout(500)
//                // 设置请求超时时间(单位毫秒) 从服务器读取数据的时间上限
//                .setSocketTimeout(1000)
//                // 从连接池获取连接的超时时间(单位毫秒)
//                .setConnectTimeout(2000)
//                // 设置代理
//                .setProxy(host)
//                .build();
            // 创建Get请求
            HttpGet httpget = new HttpGet(url);
            httpget.setHeaders(headers);
//            httpget.setConfig(requestConfig);
            CloseableHttpResponse response = null;
            HttpEntity entity = null;
            try {
                response = httpclient.execute(httpget);
                // 获取响应实体
                entity = response.getEntity();
                return EntityUtils.toString(entity);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("http get request error, exception: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     *
     * @return
     */
    public static String post(String url, Header[] headers, String requestJson) {
        // 创建Post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        // 创建请求实体
        StringEntity requestEntity = new StringEntity(requestJson, "UTF-8");
        requestEntity.setContentType("application/json");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpclient.execute(httpPost);
            try {
                entity = response.getEntity();
                return EntityUtils.toString(entity);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("http post request error, exception: {}", e.getMessage());
        }
        return null;
    }
}
