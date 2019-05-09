package com.bairock.iot.intelDev.http;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bairock.iot.intelDev.data.Result;
import com.bairock.iot.intelDev.user.DevGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUploadTask extends Thread {

	private String strUrl;
	private DevGroup devGroup;
	private OnExecutedListener onExecutedListener;
	
	public HttpUploadTask(DevGroup devGroup, String serverName) {
		this.strUrl = "http://" + serverName + "/group/client/groupUpload";
		this.devGroup = devGroup;
	}
	
	public OnExecutedListener getOnExecutedListener() {
		return onExecutedListener;
	}

	public void setOnExecutedListener(OnExecutedListener onExecutedListener) {
		this.onExecutedListener = onExecutedListener;
	}

	@Override
	public void run() {
		Result<Object> loginResult = new Result<>();
		InputStream inputStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("Charset", "UTF-8");
//			urlConnection.setRequestProperty("Accept-Language", "zh-CN");
			
			urlConnection.setConnectTimeout(30000);
			urlConnection.setReadTimeout(30000);
			
			urlConnection.setDoOutput(true);     //需要输出
			urlConnection.setDoInput(true);      //需要输入
			urlConnection.setUseCaches(false);   //不允许缓存
			urlConnection.setInstanceFollowRedirects(true); 
			urlConnection.setRequestMethod("POST");
			urlConnection.connect();
			
			ObjectMapper mapperWrite = new ObjectMapper();
			String jsonUser = mapperWrite.writeValueAsString(devGroup);
			
//			String json = "{\"name\":\"admin\"}";
			//建立输入流，向指向的URL传入参数
	        DataOutputStream dos=new DataOutputStream(urlConnection.getOutputStream());
	        dos.write(jsonUser.getBytes("UTF-8"));
	        dos.flush();
	        dos.close();
//			
			int statusCode = urlConnection.getResponseCode();
			if(statusCode == 200) {
				inputStream = new BufferedInputStream(urlConnection.getInputStream());
				String response = convertStreamToString(inputStream);
				ObjectMapper mapper = new ObjectMapper();
				loginResult = mapper.readValue(response, new TypeReference<Result<Object>>(){});
				
			}else {
				loginResult.setCode(statusCode);
				loginResult.setMsg("上传失败");
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();

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
    }
	
	public interface OnExecutedListener{
		void onExecuted(Result<Object> loginResult);
	}
}
