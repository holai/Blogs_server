package httpa;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {
    public static String get(String url,String param){
         return get(url,param,"UTF-8");
    }


    public static String get(String url,String param,String charset){
        String result="";
        BufferedReader in=null;
        try {
            URL http =new URL(url+"?"+param);
            URLConnection connection = http.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json;charset="+charset);
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Accept-Charset", charset);
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                // logger.error("连接出错",e2);
            }
        }
        return result;
    }


    public static String post(String url,String param){
        PrintWriter out = null;
        String result="";
        BufferedReader reader = null;

        try {
            URL http = new URL(url);
            URLConnection connection = http.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // 获取URLConnection对象对应的输出流
            OutputStream outputStream = connection.getOutputStream();
            String aa = new String(param.getBytes("UTF8"),"UTF8");
            //System.out.println("=========== "+aa);
            out = new PrintWriter(outputStream);
            // 发送请求参数
            out.print(aa);
            // flush输出流的缓冲
            out.flush();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ( (line=reader.readLine()) != null ){
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","后来的路");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            OutputStream outputStream = conn.getOutputStream();
            String aa = new String(param.getBytes("UTF8"),"UTF8");
            System.out.println("=========== "+aa);
            out = new PrintWriter(outputStream);
            // 发送请求参数
            out.print(aa);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            //logger.error("连接出错",e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
