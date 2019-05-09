package com.bairock.iot.intelDev.data;

import java.util.ArrayList;
import java.util.List;

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
}
