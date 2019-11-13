package com.bairock.iot.intelDev.device.devswitch;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.Gear;
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
	 * @param mcId main code identify
	 * @param sc   sub code
	 */
	public DevSwitch(String mcId, String sc) {
		super(mcId, sc);
	}

	@Override
	public String createQueryOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

	@Override
	public void turnOn() {
		super.turnOn();
		for (Device dev : getListDev()) {
			dev.turnOn();
		}
	}

	@Override
	public void turnOff() {
		super.turnOff();
		for (Device dev : getListDev()) {
			dev.turnOff();
		}
	}

	@Override
	public String createInitOrder() {
		return OrderHelper.getOrderMsg(OrderHelper.QUERY_HEAD + getCoding() + OrderHelper.SEPARATOR + "8");
	}

	@Override
	public void handleSingleMsg(String state) {
		if (null == state || state.isEmpty()) {
			return;
		}
		analysisMsgUnit(state);
	}

//	protected void handle7(String[] msgs) {
//		if(msgs.length != 3) {
//			return;
//		}
//		byte bHexState = Byte.parseByte(msgs[2], 16);
//		for(int i = 0; i < 8; i++) {
////			if(i >= getListDev().size()) {
////				break;
////			}
//			SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(i + 1));
//			String strState = getEnsureState(bHexState, i);
//			DevStateHelper.getIns().setDsId(sd1, strState);
//		}
//	}

	/**
	 * 
	 * @param msgs
	 * @param      changeGear, 是否改变档位
	 * @return
	 */
	protected Device handle7(String[] msgs, boolean touchDev) {
		if (msgs.length != 4) {
			return null;
		}

		int subCode = Integer.parseInt(msgs[1] + msgs[2], 16);
		String strState = msgs[3].equals("0") ? "1" : "0";
		SubDev sd1 = (SubDev) getSubDevBySc(String.valueOf(subCode));
		if (null == sd1) {
			return null;
		}
		if (touchDev) {
			// 触摸设备返回时要改变档位, 先改档位再改状态
			Gear.setGearModel(sd1, Integer.parseInt(strState), touchDev);
		}

		DevStateHelper.getIns().setDsId(sd1, strState);
		return sd1;
	}

	protected void handle8(String[] msgs) {
		if (msgs.length < 2) {
			return;
		}
		for (int i = 0; i < getListDev().size(); i++) {
			int moduleNum = i / 4;
			int roadNum = i % 4;
			if (moduleNum + 1 >= msgs.length) {
				return;
			}
			String strHex = msgs[moduleNum + 1];
			byte bHex = Byte.parseByte(strHex, 16);
			String strState = getEnsureState(bHex, 3 - roadNum);
			Device dev = (SubDev) getSubDevBySc(String.valueOf(i + 1));
			DevStateHelper.getIns().setDsId(dev, strState);
		}
	}

	protected void handle9(String[] msgs) {
		handle7(msgs, true);
	}

	private void analysisMsgUnit(String msgUnit) {
		if (null == msgUnit || msgUnit.isEmpty()) {
			return;
		}

		// msgs[0] is message sign
		char[] cMsgs = msgUnit.toCharArray();
		String[] msgs = new String[cMsgs.length];
		for (int i = 0; i < cMsgs.length; i++) {
			msgs[i] = String.valueOf(cMsgs[i]);
		}

		switch (msgs[0]) {
		case DevSwitchMsgSign.ORDER_CTRL_FEEDBACK:
			// 7, feedback because of order control
			// msgs[1] is module number, begin with 0
			// msgs[2] is four road state of the module, the lowest bit is road one

			// 命令返回不需改变档位
			handle7(msgs, false);
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
			// moduleNum = Integer.parseInt(msgs[1], 16);
			// first SubDev subCode number = module number * 4 + 1, subCode begin with 1
			// firstSubDevSc = moduleNum * 4 + 1;
			// iHexState = Integer.parseInt(msgs[2], 16);
			// setDevStateFromFirstSubCode(firstSubDevSc, iHexState);
			break;
		case DevSwitchMsgSign.ORDER_QUERY_FEEDBACK:
			// 8, feedback because of order query
			handle8(msgs);
//			if(msgs.length < 2) {
//				return;
//			}
//			
//			//msgs[1] is the module state begin
//			for (int i = 1; i < msgs.length; i+=2) {
//				String strHex = "";
//				
//				int step = 8;
//				if(i + 1 == msgs.length) {
//					strHex = msgs[i];
//					iHexState = Integer.parseInt(strHex, 16);
//					step = 4;
//				}else {
//					strHex = msgs[i] + msgs[i+1];
//				}
//				//module number = i - 1;, module begin with 0
//				moduleNum = i - 1;
//				
//				//first SubDev subCode number = module number * 4 + 1, subCode begin with 1
//				firstSubDevSc = moduleNum * 4 + 1;
//				
//				iHexState = Integer.parseInt(strHex, 16);
//				setDevStateFromFirstSubCode(firstSubDevSc, iHexState, step);
//			}
			break;
		case DevSwitchMsgSign.MSG_DEV_PUSHED:
			// 9
			handle9(msgs);
			break;
		}
	}

	/**
	 * 
	 * @param iHexState a hex number
	 * @param offset    begin with 0
	 * @return
	 */
	protected String getEnsureState(byte bHexState, int offset) {
		// int iHexState = Integer.parseInt(strHex);
		int iState = (bHexState >> offset) & 1;
		return iState == 0 ? "1" : "0";
	}

	public String createStateStr() {
		int moduleCount = getListDev().size() / 4;
		if (getListDev().size() % 4 != 0) {
			moduleCount++;
		}
		int[] iHexState = new int[moduleCount];

		for (Device dev : getListDev()) {
			// road is begin with 1
			int road = Integer.parseInt(dev.getSubCode());
			int moduleNum = (road - 1) / 4;

			int iState;
			if (dev.getDevStateId().equals(DevStateHelper.DS_KAI)) {
				iState = 0;
			} else {
				iState = 1;
			}
			int moduleRoad = road;
			if (road > 4) {
				moduleRoad %= 4;
				if (moduleRoad == 0) {
					moduleRoad = 4;
				}
			}
			iState <<= moduleRoad - 1;
			iHexState[moduleNum] |= iState;
		}

		String strState = "";
		for (int hex : iHexState) {
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
		System.out.println(String.valueOf(3 % 4));
	}
}
