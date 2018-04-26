package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.Gear;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;

/**
 * switch device
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("DevSwitch")
public class DevSwitch extends DevHaveChild {

	public DevSwitch() {
		super();
	}

	/**
	 * 
	 * @param mcId
	 *            main code identify
	 * @param sc
	 *            sub code
	 */
	public DevSwitch(String mcId, String sc) {
		super(mcId, sc);
	}

	/**
	 * 
	 * @param sc
	 *            sub code
	 * @return
	 */
	public Device getSubDevBySc(String sc) {
		for (Device dev : getListDev()) {
			if (dev.getSubCode().equals(sc)) {
				return dev;
			}
		}
		return null;
	}

	@Override
	public String createQueueOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

	
	@Override
	public void turnOn() {
		super.turnOn();
		for(Device dev : getListDev()) {
			dev.turnOn();
		}
	}

	@Override
	public void turnOff() {
		super.turnOff();
		for(Device dev : getListDev()) {
			dev.turnOff();
		}
	}
	
	@Override
	public String createInitOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

	@Override
	public boolean handle(String state) {
		super.handle(state);
		boolean result = false;
		if (null == state || state.isEmpty()) {
			return true;
		}
		String[] msgUnits = state.split(":");
		for (String str : msgUnits) {
			analysisMsgUnit(str);
		}

		return result;
	}
	
	protected void handle7(String[] msgs) {
		if(msgs.length != 3) {
			return;
		}
		int iHexState = Integer.parseInt(msgs[2], 16);
		for(int i = 0; i < 8; i++) {
//			if(i >= getListDev().size()) {
//				break;
//			}
			SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(i + 1));
			String strState = getEnsureState(iHexState, i);
			DevStateHelper.getIns().setDsId(sd1, strState);
		}
	}

	private void analysisMsgUnit(String msgUnit) {
		if (null == msgUnit || msgUnit.isEmpty()) {
			return;
		}
		
		// msgs[0] is message sign
		char[] cMsgs = msgUnit.toCharArray();
		String[] msgs = new String[cMsgs.length];
		for(int i=0; i<cMsgs.length; i++) {
			msgs[i] = String.valueOf(cMsgs[i]);
		}
		
		int moduleNum;
		int firstSubDevSc;
		int iHexState;
		int subCode;
		String strState;
		
		switch (msgs[0]) {
		case DevSwitchMsgSign.ORDER_CTRL_FEEDBACK:
			// 7, feedback because of order control
			// msgs[1] is module number, begin with 0
			// msgs[2] is four road state of the module, the lowest bit is road one

			handle7(msgs);
//			if(msgs.length != 4) {
//				return;
//			}
//			
//			subCode = Integer.parseInt(msgs[1] + msgs[2], 16);
//			strState = msgs[3].equals("0") ? "1" : "0";
//			SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(subCode));
//			if(null == sd1) {
//				return;
//			}
//			DevStateHelper.getIns().setDsId(sd1, strState);
			//moduleNum = Integer.parseInt(msgs[1], 16);
			//first SubDev subCode number = module number * 4 + 1, subCode begin with 1
			//firstSubDevSc = moduleNum * 4 + 1;
			//iHexState = Integer.parseInt(msgs[2], 16);
			//setDevStateFromFirstSubCode(firstSubDevSc, iHexState);
			break;
		case DevSwitchMsgSign.ORDER_QUERY_FEEDBACK:
			//8, feedback because of order query
			if(msgs.length < 2) {
				return;
			}
			
			//msgs[1] is the module state begin
			for (int i = 1; i < msgs.length; i+=2) {
				String strHex = "";
				
				int step = 8;
				if(i + 1 == msgs.length) {
					strHex = msgs[i];
					iHexState = Integer.parseInt(strHex, 16);
					step = 4;
				}else {
					strHex = msgs[i] + msgs[i+1];
				}
				//module number = i - 1;, module begin with 0
				moduleNum = i - 1;
				
				//first SubDev subCode number = module number * 4 + 1, subCode begin with 1
				firstSubDevSc = moduleNum * 4 + 1;
				
				iHexState = Integer.parseInt(strHex, 16);
				setDevStateFromFirstSubCode(firstSubDevSc, iHexState, step);
			}
			break;
		case DevSwitchMsgSign.MSG_DEV_PUSHED:
			//9
			if(msgs.length != 3) {
				return;
			}
			moduleNum = Integer.parseInt(msgs[1], 16);
			int roadAndState = Integer.parseInt(msgs[2], 16);
			subCode = ((roadAndState >> 2) & 3) + moduleNum * 4;
			int iState = roadAndState & 3;
			strState = iState == 0 ? "1" : "0";
			SubDev sd = (SubDev) getSubDevBySc(String.valueOf(subCode));
			if(null == sd) {
				return;
			}
			DevStateHelper.getIns().setDsId(sd, strState);
			
			if(iState == 0) {
				sd.setGear(Gear.KAI);
			}else {
				sd.setGear(Gear.GUAN);
			}
			break;
		}
	}
	
	/**
	 * set four device state from first device subCode
	 * @param firstSubDevSc first device subCode
	 * @param iHexState a hex number 
	 */
	private void setDevStateFromFirstSubCode(int firstSubDevSc, int iHexState, int step) {
		for(int j = 0; j < step; j++) {
			int subCode = firstSubDevSc + j;
			SubDev sd = (SubDev) getSubDevBySc(String.valueOf(subCode));
			if(null != sd) {
				String strState = getEnsureState(iHexState, j);
				DevStateHelper.getIns().setDsId(sd, strState);
			}
		}
	}
	
	/**
	 * 
	 * @param iHexState a hex number 
	 * @param offset begin with 0
	 * @return
	 */
	protected String getEnsureState(int iHexState, int offset) {
		//int iHexState = Integer.parseInt(strHex);
		int iState = (iHexState >> offset) & 1;
		return iState == 0 ? "1" : "0";
	}

	public String createStateStr() {
		int moduleCount = getListDev().size() / 4;
		if(getListDev().size() % 4 != 0) {
			moduleCount++;
		}
		int[] iHexState = new int[moduleCount];
		
		for(Device dev : getListDev()) {
			//road is begin with 1
			int road = Integer.parseInt(dev.getSubCode());
			int moduleNum = (road - 1) / 4;
			
			int iState;
			if(dev.getDevStateId().equals(DevStateHelper.DS_KAI)){
				iState = 0;
			}else {
				iState = 1;
			}
			int moduleRoad = road;
			if(road > 4) {
				moduleRoad %= 4;
				if(moduleRoad == 0) {
					moduleRoad = 4;
				}
			}
			iState <<= moduleRoad - 1;
			iHexState[moduleNum] |= iState;
		}
		
		String strState = "";
		for(int hex : iHexState) {
			strState += Integer.toString(hex, 16);
		}
		return strState;
	}

	public String createStateOrder() {
		String order = OrderHelper.FEEDBACK_HEAD + getCoding() + OrderHelper.SEPARATOR + "8";
		String state = createStateStr();
		order += state;
		return OrderHelper.getOrderMsg(order);
	}

	public static void main(String[] args) {
		DevSwitch ds1 = (DevSwitch) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_1LU_2TAI, "1");
		for (int i = 0; i < 8; i++) {
			ds1.handle("8" + i);
			System.out.println(ds1.createStateOrder());
		}

		System.out.println("8-------");
		DevSwitch ds2 = (DevSwitch) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_2LU_2TAI, "1");
		for (int i = 0; i < 8; i++) {
			ds2.handle("8" + i);
			System.out.println(ds2.createStateOrder());
		}

		System.out.println("8-------");
		DevSwitch ds3 = (DevSwitch) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_3LU_2TAI, "1");
		for (int i = 0; i < 8; i++) {
			ds3.handle("8" + i);
			System.out.println(ds3.createStateOrder());
		}
		
		String[] states = new String[] {"0000", "1111", "2222", "4444", "8888", "ffff"};
		System.out.println("8-------");
		DevSwitch dsx = (DevSwitch) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_XLU_2TAI, "1");
		for (int i = 0; i < states.length; i++) {
			dsx.handle("8" + states[i]);
			System.out.println(dsx.createStateOrder());
		}
		
		System.out.println("7-------");
		for (int i = 0; i < 8; i++) {
			ds1.handle("70" + i);
			System.out.println(ds1.createStateOrder());
		}
		
		System.out.println("7-------");
		for (int i = 0; i < 8; i++) {
			ds2.handle("70" + i);
			System.out.println(ds2.createStateOrder());
		}
		
		System.out.println("7-------");
		for (int i = 0; i < 8; i++) {
			ds3.handle("70" + i);
			System.out.println(ds3.createStateOrder());
		}
		
		states = new String[] {"00", "11", "22", "33","01","02","03","04","05","06","07"};
		System.out.println("8-------");
		dsx = (DevSwitch) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_XLU_2TAI, "1");
		for (int i = 0; i < states.length; i++) {
			dsx.handle("7" + states[i]);
			System.out.println(dsx.createStateOrder());
		}
		
		states = new String[] {"04", "05"};
		System.out.println("9-------");
		for (int i = 0; i < states.length; i++) {
			ds1.handle("9" + states[i]);
			System.out.println(ds1.createStateOrder());
		}
		
		states = new String[] {"00", "01", "08", "09"};
		System.out.println("9-------");
		for (int i = 0; i < states.length; i++) {
			ds2.handle("9" + states[i]);
			System.out.println(ds2.createStateOrder());
		}
		
		states = new String[] {"00", "05", "08"};
		System.out.println("9-------");
		for (int i = 0; i < states.length; i++) {
			ds3.handle("9" + states[i]);
			System.out.println(ds3.createStateOrder());
		}
		
		states = new String[] {"10", "25", "38", "3d"};
		System.out.println("9-------");
		for (int i = 0; i < states.length; i++) {
			dsx.handle("9" + states[i]);
			System.out.println(dsx.createStateOrder());
		}
		
		System.out.println("order-------");
		SubDev sd = (SubDev)(ds1.getSubDevBySc("2"));
		System.out.println(sd.getTurnOnOrder());
		System.out.println(sd.getTurnOffOrder());
		
		sd = (SubDev)(ds2.getSubDevBySc("3"));
		System.out.println(sd.getTurnOnOrder());
		System.out.println(sd.getTurnOffOrder());
		
		sd = (SubDev)(ds3.getSubDevBySc("1"));
		System.out.println(sd.getTurnOnOrder());
		System.out.println(sd.getTurnOffOrder());
		
		System.out.println("order-------");
		sd = (SubDev)(dsx.getSubDevBySc("10"));
		System.out.println(sd.getTurnOnOrder());
		System.out.println(sd.getTurnOffOrder());
		sd = (SubDev)(dsx.getSubDevBySc("16"));
		System.out.println(sd.getTurnOnOrder());
		System.out.println(sd.getTurnOffOrder());
	}
}
