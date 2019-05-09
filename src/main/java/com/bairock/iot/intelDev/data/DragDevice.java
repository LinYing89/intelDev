package com.bairock.iot.intelDev.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.Device;

/**
 * 可拖拽设备
 * @author 44489
 *
 */
@Entity
public class DragDevice {

	@Id
	@Column(nullable = false)
	private String id;
	
	/**
	 * 设备图标类型,预定义图表
	 */
	public static String IMG_ICON = "icon";
	/**
	 * 设备图标类型,自选图片
	 */
	public static String IMG_PICTURE = "picture";
	
	@Transient
	private Device device;
	
	private String deviceId;
	private Integer layoutx = 0;
	private Integer layouty = 0;
	//图片类型, 预定于图标还是自选图片
	private String imageType = IMG_ICON;
	//图标名称, 如果时预定于图标, 则为图标名称, 否则为图片路径
	private String imageName = "";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Integer getLayoutx() {
		return layoutx;
	}
	public void setLayoutx(Integer layoutx) {
		this.layoutx = layoutx;
	}
	public Integer getLayouty() {
		return layouty;
	}
	public void setLayouty(Integer layouty) {
		this.layouty = layouty;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
