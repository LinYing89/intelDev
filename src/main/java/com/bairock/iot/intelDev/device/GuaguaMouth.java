package com.bairock.iot.intelDev.device;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("GuaguaMouth")
public class GuaguaMouth extends Device {

	/**
	 * 
	 */
	public GuaguaMouth(){
		this("","");
	}
	
	/**
	 * 
	 * @param mcId
	 * @param sc
	 */
	public GuaguaMouth(String mcId, String sc) {
		super(mcId, sc);
	}

	/**
	 * 
	 */
	public String getDevOrder(int speakCount, String speakContent) {
		//String order = "C" + getCoding() + ":" + speakCount + ":" + speakContent;
		String order = "C" + getCoding() + ":" + speakContent;
		return OrderHelper.getOrderMsg(order);
	}
}
