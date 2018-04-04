package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * collect device
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollect")
public class DevCollect extends Device{

	@OneToOne(mappedBy = "devCollect", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("devcollect_property")
	private CollectProperty collectProperty;
	
	/**
	 * 
	 */
	public DevCollect() {
		this(MainCodeHelper.SMC_WU, "");
	}

	/**
	 * 
	 * @param mcId
	 * @param sc
	 */
	public DevCollect(String mcId, String sc) {
		super(mcId, sc);
	}
	
	public CollectProperty getCollectProperty() {
		if(null == collectProperty) {
			collectProperty = new CollectProperty();
		}
		return collectProperty;
	}

	public void setCollectProperty(CollectProperty collectProperty) {
		if(null != collectProperty){
			this.collectProperty = collectProperty;
			this.collectProperty.setDevCollect(this);
		}
	}
	
	@Override
	public String createQueueOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

	public String createPrecentOrder() {
		String order = OrderHelper.FEEDBACK_HEAD + getCoding() + OrderHelper.SEPARATOR 
				+ CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_PRESSURE_PER_VALUE)
				+ collectProperty.getPercent();
		return OrderHelper.getOrderMsg(order);
	}
}
