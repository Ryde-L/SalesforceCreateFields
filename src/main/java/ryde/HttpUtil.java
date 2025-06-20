package ryde;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class HttpUtil {
    //api后缀
    static String url="/services/data/v41.0/tooling/sobjects/CustomField/";
    static boolean isCreateSuccessful(String targetURLPrefix,String token,String json) {
        try {
            System.out.println("JSON请求："+json);
            Connection.Response response = setHeader(Jsoup.connect(targetURLPrefix+url), token).ignoreContentType(true).followRedirects(false).requestBody(json).method(Connection.Method.POST).execute();
            String body = response.body();
            System.out.println("请求响应："+body);
            return body.contains("\"success\":true");
        }catch (Exception e){
            System.out.println( e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static Connection setHeader(Connection connection,String token){
        connection.header("Accept", "*/*");
        connection.header("Accept-Encoding", "gzip, deflate, br");
        connection.header("Connection", "keep-alive");
        connection.header("Content-Type", "application/json;charset=UTF-8");
        connection.header("Authorization", "Bearer "+token);
        connection.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.183 Safari/537.36");
        return connection;
    }
}
