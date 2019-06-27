package com.bairock.iot.intelDev.data;

import java.util.ArrayList;
import java.util.List;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;

public class DragDeviceHelper {

	private static DragDeviceHelper ins = new DragDeviceHelper();
	
	private DragDeviceHelper() {}
	
	public static DragDeviceHelper getIns() {
		return ins;
	}
	
	private List<DragDevice> dragDevices = new ArrayList<>();

	public void addDragDevice(DragDevice dragDevice) {
		if(null != dragDevice) {
			dragDevices.add(dragDevice);
		}
	}
	
	public void removeDragDevice(DragDevice dragDevice) {
		dragDevices.remove(dragDevice);
	}
	
	public List<DragDevice> getDragDevices() {
		return dragDevices;
	}

	public void setDragDevices(List<DragDevice> dragDevices) {
		this.dragDevices = dragDevices;
	}
	
	public List<DragDevice> findDragDevice(Device device) {
	    List<DragDevice> list = new ArrayList<>();
	    if(device == null) {
	        return list;
	    }
	    if(device instanceof DevHaveChild) {
	        for(Device dev : ((DevHaveChild) device).getListDev()) {
	            list.addAll(findDragDevice(dev));
	        }
	    }else{
	        for(DragDevice dd : this.dragDevices) {
	            if(dd.getDevice().getId().equals(device.getId())) {
	                list.add(dd);
	            }
	        }
	    }
	    return list;
	}
}
