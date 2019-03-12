package com.bairock.iot.intelDev.http;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.bairock.iot.intelDev.data.DeviceImg;
import com.bairock.iot.intelDev.data.Result;

public class HttpDownloadDeviceImgTask extends Thread{
	
	private String imgName;
	private int versionCode;
	private String strUrl;
	public static String imgSavePath;
	private OnExecutedListener onExecutedListener;

	public HttpDownloadDeviceImgTask(String serverName, String imgName, int versionCode) {
		this.imgName = imgName;
		this.versionCode = versionCode;
		strUrl = String.format("http://%s/devImg/%s.png", serverName, imgName);
	}

	public OnExecutedListener getOnExecutedListener() {
		return onExecutedListener;
	}

	public void setOnExecutedListener(OnExecutedListener onExecutedListener) {
		this.onExecutedListener = onExecutedListener;
	}
	
	@Override
	public void run() {
		Result<DeviceImg> downloadResult = new Result<>();
		
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

			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			int statusCode = urlConnection.getResponseCode();
			if (statusCode == 200) {
				inputStream = new BufferedInputStream(urlConnection.getInputStream());
				saveImageToDisk(inputStream);
				DeviceImg di = new DeviceImg();
				di.setCode(imgName);
				di.setVersionCode(versionCode);
				downloadResult.setData(di);
			} else {
				downloadResult.setCode(statusCode);
				downloadResult.setMsg("下载失败");
			}
		} catch (Exception e) {
			downloadResult.setCode(-1);
			downloadResult.setMsg(e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != urlConnection) {
				urlConnection.disconnect();
			}
		}
		if(null != onExecutedListener) {
			onExecutedListener.onExecuted(downloadResult);
		}
	}

	// 把从服务器获得图片的输入流InputStream写到本地磁盘
	public void saveImageToDisk(InputStream inputStream) {
		byte[] data = new byte[1024];
		int len = 0;
		FileOutputStream fileOutputStream = null;
		try {
			if(null == imgSavePath) {
				return;
			}
			createFile();
			fileOutputStream = new FileOutputStream(imgSavePath + imgName + ".png", false);
			while ((len = inputStream.read(data)) != -1) {
				fileOutputStream.write(data, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
	
	public void createFile() {
		if(null == imgSavePath) {
			return;
		}
		File f = new File(imgSavePath);
		if (!f.exists()) {
		    f.mkdirs();
		}
	}
	
	public interface OnExecutedListener{
		void onExecuted(Result<DeviceImg> result);
	}
}
