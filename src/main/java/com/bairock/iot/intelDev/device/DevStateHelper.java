package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.List;

/**
 * device state helper
 * @author LinQiang
 *
 */
public class DevStateHelper {

	private static DevStateHelper ins;
	
	/**
	 * identify of on state
	 */
	public static final String DS_KAI = "ds_k";
	
	/**
	 * identify of off state
	 */
	public static final String DS_GUAN = "ds_g";
	
	/**
	 * identify of stop state
	 */
	public static final String DS_TING = "ds_t";
	
	/**
	 * identify of normal
	 */
	public static final String DS_ZHENG_CHANG = "ds_zc";
	
	/**
	 * identify of abnormal
	 */
	public static final String DS_YI_CHANG = "ds_yc";
	
	public static final String CONFIGING = "configing";
	
	public static final String CONFIG_OK = "config_ok";
	
	private List<DevState> listState;
	
	private DevStateHelper() {
		listState = new ArrayList<DevState>();
		listState.add(new DevState(DS_KAI,"1","开"));
		listState.add(new DevState(DS_GUAN,"0","关"));
		listState.add(new DevState(DS_TING,"2","停"));
		listState.add(new DevState(DS_ZHENG_CHANG,"3","正常"));
		listState.add(new DevState(DS_YI_CHANG,"4","异常"));
		listState.add(new DevState(CONFIGING,"5","配置中"));
		listState.add(new DevState(CONFIG_OK,"6","配置成功"));
	}

	/**
	 * get instance of DevStateHelper
	 * @return
	 */
	public static DevStateHelper getIns(){
		if(null == ins){
			ins = new DevStateHelper();
		}
		return ins;
	}

	/**
	 * get list of state
	 * @return
	 */
	public List<DevState> getListState() {
		return listState;
	}

	/**
	 * set list of state
	 * @param listState
	 */
	public void setListState(List<DevState> listState) {
		this.listState = listState;
	}
	
	/**
	 * add an device state
	 * @param devState
	 */
	public void add(DevState devState){
		boolean haved = false;
		for(DevState dState : getListState()){
			if(dState.getDsId().equals(devState.getDsId())){
				if(!dState.getDs().equals(devState.getDs())){
					dState.setDs(devState.getDs());
					haved = true;
					break;
				}
			}
		}
		if(!haved){
			listState.add(devState);
		}
	}
	
	/**
	 * remove an device state
	 * @param devState
	 * @return
	 */
	public boolean remove(DevState devState){
		for(DevState dState : getListState()){
			if(dState.getDsId().equals(devState.getDsId())){
				if(!dState.getDs().equals(devState.getDs())){
					listState.remove(dState);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * get device state code
	 * @param dsId device state identify
	 * @return
	 */
	public String getDs(String dsId){
		if(null == dsId){
			return null;
		}
		for(DevState ds : listState){
			if(ds.getDsId().equals(dsId)){
				return ds.getDs();
			}
		}
		return null;
	}
	
	/**
	 * get device state identify
	 * @param ds device state code
	 * @return
	 */
	public String getDsId(String ds){
		if(null == ds){
			return null;
		}
		for(DevState dState : listState){
			if(dState.getDs().equals(ds)){
				return dState.getDsId();
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param dev
	 * @param state
	 */
	public void setDsId(Device dev, String state){
		if(null == dev) {
			return;
		}
		String dsId = getDsId(state);
		if(null != dsId){
			dev.setDevStateId(dsId);
		}
	}
}
