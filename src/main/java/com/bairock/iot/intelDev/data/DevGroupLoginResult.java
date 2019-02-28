package com.bairock.iot.intelDev.data;

public class DevGroupLoginResult {

	private String devGroupId;
	private String devGroupPetName;
	private int padPort;
	private int devPort;

	public String getDevGroupId() {
		return devGroupId;
	}
	public void setDevGroupId(String devGroupId) {
		this.devGroupId = devGroupId;
	}
	public String getDevGroupPetName() {
		return devGroupPetName;
	}
	public void setDevGroupPetName(String devGroupPetName) {
		this.devGroupPetName = devGroupPetName;
	}
	public int getPadPort() {
		return padPort;
	}
	public void setPadPort(int padPort) {
		this.padPort = padPort;
	}
	public int getDevPort() {
		return devPort;
	}
	public void setDevPort(int devPort) {
		this.devPort = devPort;
	}
	
}