package com.bairock.iot.intelDev.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bairock.iot.intelDev.data.Result;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpDownloadBase extends Thread {

    private String strUrl;
    private OnExecutedListener onExecutedListener;
    
    public HttpDownloadBase(String strUrl) {
        this.strUrl = strUrl;
    }
    
    public OnExecutedListener getOnExecutedListener() {
        return onExecutedListener;
    }

    public void setOnExecutedListener(OnExecutedListener onExecutedListener) {
        this.onExecutedListener = onExecutedListener;
    }

    @Override
    public void run() {
        Result<String> loginResult = new Result<>();
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Charset", "UTF-8");
//          urlConnection.setRequestProperty("Accept-Language", "zh-CN");
            
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);
            
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            
            int statusCode = urlConnection.getResponseCode();
            if(statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertStreamToString(inputStream);
                loginResult.setCode(0);
                loginResult.setData(response);
            }else {
                loginResult.setCode(statusCode);
                loginResult.setMsg("下载失败");
            }
        }catch(Exception e) {
            e.printStackTrace();
            loginResult.setCode(-1);
            loginResult.setMsg(e.getMessage());
        }finally {
            if(null != inputStream) {
                try {
                    inputStream.close();
                }catch(IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != urlConnection) {
                urlConnection.disconnect();
            }
        }
        if(null != onExecutedListener) {
            onExecutedListener.onExecuted(loginResult);
        }
    }
    
    private String convertStreamToString(InputStream inputStream) {
        BufferedReader reader;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            
            try {
                String line = reader.readLine();
                while (null != line){
                    sb.append(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }
    
    public interface OnExecutedListener{
        void onExecuted(Result<String> resultString);
    }
    
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {  
        
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
    }
}
