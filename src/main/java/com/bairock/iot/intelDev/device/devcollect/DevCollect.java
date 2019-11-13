package com.bairock.iot.intelDev.device.devcollect;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.CtrlCodeHelper;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.IValueDevice;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.user.IntelDevHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * collect device
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevCollect")
public class DevCollect extends Device implements IValueDevice{

	@OneToOne(mappedBy = "devCollect", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("devcollect_property")
	private CollectProperty collectProperty;
	
	@Transient
	@JsonIgnore
	private CalibrationnListener calibrationnListener;
	
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
		collectProperty  = new CollectProperty();
		collectProperty.setDevCollect(this);
	}
	
	public CollectProperty getCollectProperty() {
		if(null == collectProperty) {
			collectProperty = new CollectProperty();
			collectProperty.setDevCollect(this);
		}
		return collectProperty;
	}

	public void setCollectProperty(CollectProperty collectProperty) {
		if(null != collectProperty){
			this.collectProperty = collectProperty;
			this.collectProperty.setDevCollect(this);
		}
	}
	
	public CalibrationnListener getCalibrationnListener() {
		return calibrationnListener;
	}

	public void setCalibrationnListener(CalibrationnListener calibrationnListener) {
		this.calibrationnListener = calibrationnListener;
	}

	@Override
	public void setDevStateId(String dsId) {
		super.setDevStateId(dsId);
		if(dsId.equals(DevStateHelper.DS_YI_CHANG)) {
			getCollectProperty().setCurrentValueExceptListener(null);
		}
	}

	protected void setRatio() {
		CollectProperty cp = getCollectProperty();
		if (null == cp.getCrestValue() || null == cp.getLeastValue() || null == cp.getPercent()) {
			return;
		}
		float ratio = cp.getPercent() / 100;
		ratio = ratio * (cp.getCrestReferValue() - cp.getLeastReferValue()) + cp.getLeastValue();
		cp.setCurrentValue(IntelDevHelper.scale(ratio));
	}
	
	@Override
	public String createQueryOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}
	
	/**
	 * 创建标定报文, 精度0.01, 传入参数为原始数据, 方法内会乘以100
	 * @param order
	 * @return
	 */
	public String createCalibrationOrder(float order) {
		int iOrder = (int) (order * 100);
		String strHex = Integer.toHexString(iOrder);
		return OrderHelper.getOrderMsg(OrderHelper.SET_HEAD + getCoding() + OrderHelper.SEPARATOR + "B" + strHex);
	}
	
	@Override
	public String createInitOrder() {
		return createQueryOrder();
	}

	public String createPrecentOrder() {
		String order = OrderHelper.FEEDBACK_HEAD + getCoding() + OrderHelper.SEPARATOR 
				+ CtrlCodeHelper.getIns().getDct(CtrlCodeHelper.DCT_PRESSURE_PER_VALUE)
				+ collectProperty.getPercent();
		return OrderHelper.getOrderMsg(order);
	}
	
	@Override
	public void handleSingleMsg(String state) {
		if (null != state) {
			if (state.length() < 2) {
				return;
			}
			if(state.startsWith("C")) {
				//标定返回
				if(null != calibrationnListener) {
					calibrationnListener.calibration(true);
				}
			}
		}
	}
	
	/**
	 * 标定监听器
	 * @author 44489
	 *
	 */
	public interface CalibrationnListener{
		/**
		 * 标定结果返回
		 * @param result true为成功, false为失败
		 */
		void calibration(boolean result);
	}
}
