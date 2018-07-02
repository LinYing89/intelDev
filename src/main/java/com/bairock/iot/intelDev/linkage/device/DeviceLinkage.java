package com.bairock.iot.intelDev.linkage.device;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bairock.iot.intelDev.device.Coordinator;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.alarm.DevAlarm;
import com.bairock.iot.intelDev.device.devcollect.Formaldehyde;
import com.bairock.iot.intelDev.device.devcollect.Humidity;
import com.bairock.iot.intelDev.device.devcollect.Temperature;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 协调器内部设备连锁
 * @author 44489
 *
 */
@Entity
public class DeviceLinkage {

	@Id
	@Column(nullable = false)
	private String id;
	
	//源设备，触发连锁的设备源
	@ManyToOne
	@JsonBackReference("device_linkage")
	private Device sourceDevice;
	
	//目标设备，被连锁的设备
	@ManyToOne
	@JoinColumn(name = "targetDev_id", foreignKey = @ForeignKey(name = "DEV_ID_FK"))
	private Device targetDevice;
	
	//值类型，值1、值2的数据类型,0整型、1浮点型
	
	//开关模式
	//0表示等于值1开，等于值2关
	//1表示小于值1开，大于值2关
	//2 表示小于值1关，大于值2开
	private int switchModel;
	
	//值1
	private float value1;
	//值2
	private float value2;
	
	//是否激活，协调器内有此连锁表示激活
	//协调器内无此连锁表示未激活
	//每次开机都要查询协调器内的连锁，防止pad显示和协调器设备不对应
	private boolean active;
	
	public DeviceLinkage() {
		setId(UUID.randomUUID().toString());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Device getSourceDevice() {
		return sourceDevice;
	}
	public void setSourceDevice(Device sourceDevice) {
		this.sourceDevice = sourceDevice;
		if(this.sourceDevice != null) {
			
		}
	}
	public Device getTargetDevice() {
		return targetDevice;
	}
	public void setTargetDevice(Device targetDevice) {
		this.targetDevice = targetDevice;
	}
	
	public int getSwitchModel() {
		return switchModel;
	}
	public void setSwitchModel(int switchModel) {
		this.switchModel = switchModel;
	}
	public float getValue1() {
		return value1;
	}
	public void setValue1(float value1) {
		this.value1 = value1;
	}
	public float getValue2() {
		return value2;
	}
	public void setValue2(float value2) {
		this.value2 = value2;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String createSetOrder() {
		String sp = OrderHelper.SEPARATOR;
		String order = "R" + findRootDevice(sourceDevice) +sp + findRootDevice(targetDevice) + 
				sp + createMessageType() + createValueType() + getSwitchModel() + 
				sp + getValue1() + sp + getValue2();
		return OrderHelper.getOrderMsg(order);
	}
	
	private Device findRootDevice(Device device) {
		Device dev = sourceDevice.getParent();
		if(!(dev instanceof Coordinator)) {
			findRootDevice(dev);
		}
		return dev;
	}
	
	private String createMessageType() {
		if(sourceDevice instanceof DevAlarm) {
			return "0";
		}else if(sourceDevice instanceof Temperature) {
			return "3";
		}else if(sourceDevice instanceof Humidity) {
			return "4";
		}else if(sourceDevice instanceof Formaldehyde) {
			return "5";
		}
		return "0";
	}
	
	private int createValueType() {
		if(sourceDevice instanceof DevAlarm) {
			return 0;
		}
		return 1;
	}
	
//	private CollectProperty.OnCurrentValueChangedListener valueListener = new CollectProperty.OnCurrentValueChangedListener(){
//
//		@Override
//		public void onCurrentValueChanged(DevCollect dev, Float value) {
//			if(switchModel == 1) {
//				
//			}
//		}
//		
//	};
	
}
