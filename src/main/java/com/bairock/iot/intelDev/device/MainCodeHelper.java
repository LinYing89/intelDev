package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * main code helper
 * @author LinQiang
 *
 */
public class MainCodeHelper {

	private static MainCodeHelper ins;
	/**
	 * 协调器
	 */
	public static final String XIE_TIAO_QI = "omc_xtq";
	/**
     * PLC
     */
    public static final String PLC = "omc_plc";
	/**
	 * 呱呱嘴
	 */
	public static final String GUAGUA_MOUTH = "omc_ggz";
	/**
	 * 门禁
	 */
	public static final String MEN_JIN = "omc_mj";
	/**
	 * 液位计
	 */
	public static final String YE_WEI = "omc_ywj";
	/**
	 * 信号采集控制器，采集电压电流等信号
	 */
	public static final String COLLECTOR_SIGNAL_CONTAINER = "signal_collector_container";
	/**
	 * 信号采集器，单个设备，一次只能采集一种信号，目前作为虚拟设备挂在信号采集控制器下使用
	 */
	public static final String COLLECTOR_SIGNAL = "signal_collector";
	/**
	 * 多功能信号传感器，采集温度、湿度和甲醛等
	 */
	public static final String COLLECTOR_CLIMATE_CONTAINER = "signal_climate_container";
	/**
	 * 烟雾、可燃气体探测器
	 */
	public static final String YAN_WU = "omc_yw";
	/**
	 * 温度，目前作为虚拟设备挂在多功能信号传感器下使用
	 */
	public static final String WEN_DU = "omc_wd";
	/**
	 * 湿度，目前作为虚拟设备挂在多功能信号传感器下使用
	 */
	public static final String SHI_DU = "omc_sd";
	/**
	 * 甲醛，目前作为虚拟设备挂在多功能信号传感器下使用
	 */
	public static final String JIA_QUAN = "omc_jq";
	/**
	 * 一路两态开关
	 */
	public static final String KG_1LU_2TAI = "mmc_kg12";
	/**
	 * 两路两态开关
	 */
	public static final String KG_2LU_2TAI = "mmc_kg22";
	/**
	 * 三路两态开关
	 */
	public static final String KG_3LU_2TAI = "mmc_kg32";
	/**
	 * 多路两态开关
	 */
	public static final String KG_XLU_2TAI = "mmc_kgx2";
	/**
	 * 三态开关
	 */
	public static final String KG_3TAI = "mmc_kgx3";
	/**
	 * 遥控器
	 */
	public static final String YAO_KONG = "mmc_ykq";
	
	/**
     * 虚拟设备, 参数设备
     */
    public static final String VT_PARAM = "mmc_vt_param";
    /**
     * 虚拟设备, 计数器
     */
    public static final String VT_COUNTER = "mmc_vt_count";
	/**
	 * 插座
	 */
	public static final String CHA_ZUO = "mmc_cz";
	//bao jing she bei lei xing 1
	public static final String BAO_JING1 = "mmc_bjq1";
	//bao jing she bei lei xing 2
	public static final String BAO_JING2 = "mmc_bjq2";
	
	//以下多为子设备
	/**
	 * 无类型设备
	 */
	public static final String SMC_WU = "smc_w";
	/**
	 * 窗帘
	 */
	public static final String SMC_REMOTER_CHUANG_LIAN = "smc_remoter_cl";
	/**
	 * 电视
	 */
	public static final String SMC_REMOTER_DIAN_SHI = "smc_remoter_ds";
	/**
	 * 空调
	 */
	public static final String SMC_REMOTER_KONG_TIAO = "smc_remoter_kt";
	/**
	 * 投影仪
	 */
	public static final String SMC_REMOTER_TOU_YING = "smc_remoter_tyy";
	/**
	 * 投影幕布
	 */
	public static final String SMC_REMOTER_MU_BU = "smc_remoter_mb";
	/**
	 * 升降架
	 */
	public static final String SMC_REMOTER_SHENG_JIANG_JIA = "smc_remoter_sjj";
	/**
	 * 自定义设备
	 */
	public static final String SMC_REMOTER_ZI_DING_YI = "smc_remoter_zdy";
	/**
	 * 灯
	 */
	public static final String SMC_DENG = "smc_d";
	/**
	 * 窗户
	 */
	public static final String SMC_CHUANG_HU = "smc_ch";
	/**
	 * 阀门
	 */
	public static final String SMC_FA_MEN = "smc_fm";
	/**
	 * 冰箱
	 */
	public static final String SMC_BING_XIANG = "smc_bx";
	/**
	 * 洗衣机
	 */
	public static final String SMC_XI_YI_JI = "smc_xyj";
	/**
	 * 微波炉
	 */
	public static final String SMC_WEI_BO_LU = "smc_wbl";
	/**
	 * 音箱
	 */
	public static final String SMC_YIN_XIANG = "smc_yx";
	/**
	 * 水龙头
	 */
	public static final String SMC_SHUI_LONG_TOU = "smc_slt";
	
	private List<MainCode> listMainCode;

	/**
	 * 
	 */
	private MainCodeHelper(){
		listMainCode = new ArrayList<MainCode>();
		listMainCode.add(new MainCode(XIE_TIAO_QI, "A1", "xietiaoqi"));
		listMainCode.add(new MainCode(PLC, "Ax", "PLC"));
		listMainCode.add(new MainCode(GUAGUA_MOUTH, "R1", "guaguazui"));
		listMainCode.add(new MainCode(MEN_JIN, "w1", "menjin"));
		listMainCode.add(new MainCode(YE_WEI, "y1", "qiyachuanganyeweiji"));
		listMainCode.add(new MainCode(COLLECTOR_SIGNAL, "b1", "xinhaocaijiqi"));
		listMainCode.add(new MainCode(COLLECTOR_SIGNAL_CONTAINER, "bx", "xinhaocaijikongzhiqi"));
		listMainCode.add(new MainCode(COLLECTOR_CLIMATE_CONTAINER, "x1", "duogongnengqihouchuanganqi"));
		listMainCode.add(new MainCode(YAN_WU, "z1", "yanwutanciqi"));
		listMainCode.add(new MainCode(WEN_DU, "e1", "wendu"));
		listMainCode.add(new MainCode(SHI_DU, "e2", "shidu"));
		listMainCode.add(new MainCode(JIA_QUAN, "e3", "jiaquan"));
		listMainCode.add(new MainCode(KG_1LU_2TAI, "B1", "1lu2taikaiguan"));
		listMainCode.add(new MainCode(KG_2LU_2TAI, "B2", "2lu2taikaiguan"));
		listMainCode.add(new MainCode(KG_3LU_2TAI, "B3", "3lu2taikaiguan"));
		listMainCode.add(new MainCode(KG_XLU_2TAI, "Bx", "xlu2taikaiguan"));
		listMainCode.add(new MainCode(KG_3TAI, "C1", "3taikaiguan"));
		listMainCode.add(new MainCode(YAO_KONG, "D1", "xuexiyaokongqi"));
		listMainCode.add(new MainCode(VT_PARAM, "V1", "canshushibei"));
		listMainCode.add(new MainCode(VT_COUNTER, "V2", "jishuqi"));
		listMainCode.add(new MainCode(CHA_ZUO, "E1", "chazuo"));
		listMainCode.add(new MainCode(BAO_JING1, "a1", "duogongnengbaojing1"));
		listMainCode.add(new MainCode(BAO_JING2, "a2", "duogongnengbaojing2"));
		
		listMainCode.add(new MainCode(SMC_WU, "0", "wu"));
		listMainCode.add(new MainCode(SMC_REMOTER_CHUANG_LIAN, "3", "chuanglian"));
		listMainCode.add(new MainCode(SMC_REMOTER_DIAN_SHI, "1", "dianshi"));
		listMainCode.add(new MainCode(SMC_REMOTER_KONG_TIAO, "2", "kongtiao"));
		listMainCode.add(new MainCode(SMC_REMOTER_TOU_YING, "4", "touyingyi"));
		listMainCode.add(new MainCode(SMC_REMOTER_MU_BU, "5", "mubu"));
		listMainCode.add(new MainCode(SMC_REMOTER_SHENG_JIANG_JIA, "6", "shenjiangjia"));
		listMainCode.add(new MainCode(SMC_REMOTER_ZI_DING_YI, "7", "zidingyi"));
		listMainCode.add(new MainCode(SMC_DENG, "10", "deng"));
		listMainCode.add(new MainCode(SMC_CHUANG_HU, "11", "chuanghu"));
		listMainCode.add(new MainCode(SMC_FA_MEN, "12", "famen"));
		listMainCode.add(new MainCode(SMC_BING_XIANG, "14", "bingxiang"));
		listMainCode.add(new MainCode(SMC_XI_YI_JI, "15", "xiyiji"));
		listMainCode.add(new MainCode(SMC_WEI_BO_LU, "16", "weibolu"));
		listMainCode.add(new MainCode(SMC_YIN_XIANG, "17", "yinxiang"));
		listMainCode.add(new MainCode(SMC_SHUI_LONG_TOU, "18", "shuilongtou"));
	}
	
	/**
	 * get instance of MainCodeHelper
	 * @return
	 */
	public static MainCodeHelper getIns(){
		if(null == ins){
			ins = new MainCodeHelper();
		}
		return ins;
	}
	
	/**
	 * get main code list
	 * @return
	 */
	public List<MainCode> getListMainCode() {
		if(null == listMainCode){
			listMainCode = new ArrayList<MainCode>();
		}
		return listMainCode;
	}

	/**
	 * set main code list
	 * @param listMainCode
	 */
	public void setListMainCode(List<MainCode> listMainCode) {
		this.listMainCode = listMainCode;
	}
	
	/**
	 * add an main code
	 * @param mc
	 */
	public void add(MainCode mc){
		boolean haved = false;
		for(MainCode mainCode : getListMainCode()){
			if(mainCode.getMcId().equals(mc.getMcId())){
				if(!mainCode.getMc().equals(mc.getMc())){
					mainCode.setMc(mc.getMc());
					haved = true;
					break;
				}
			}
		}
		if(!haved){
			listMainCode.add(mc);
		}
	}
	
	/**
	 * remove an main code
	 * @param mc
	 * @return
	 */
	public boolean remove(MainCode mc){
		for(MainCode mainCode : getListMainCode()){
			if(mainCode.getMcId().equals(mc.getMcId())){
				if(!mainCode.getMc().equals(mc.getMc())){
					listMainCode.remove(mainCode);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * get main code
	 * @param mcId main code identify
	 * @return
	 */
	public String getMc(String mcId){
		if(null == mcId){
			return "";
		}
		if(!getListMainCode().isEmpty()){
			for(MainCode mc : listMainCode){
				if(mc.getMcId().equals(mcId)){
					return mc.getMc();
				}
			}
		}
		return mcId;
	}
	
	/**
	 * get main code identify
	 * @param mc main code
	 * @return
	 */
	public String getMcId(String mc){
		if(null == mc){
			return "";
		}
		if(!getListMainCode().isEmpty()){
			for(MainCode mainCode : listMainCode){
				if(mainCode.getMc().equals(mc)){
					return mainCode.getMcId();
				}
			}
		}
		return mc;
	}
	
	/**
	 * 获取主编码描述
	 * @param mcId 主编吗id
	 * @return
	 */
	public String getMainCodeInfo(String mcId) {
		if(null == mcId){
			return "";
		}
		if(mcId.equals(SMC_WU)) {
			return getMc(mcId);
		}
		for (MainCode mc : listMainCode) {
			if (mc.getMcId().equals(mcId)) {
				return mc.getInfo();
			}
		}
		return mcId;
	}
	
	/**
	 * 设置设备主编码对应的主编码描述信息，一般需要在程序初始化时设置为中文描述信息
	 * map的key为主编码id，map的value为主编码描述信息
	 * @param map key为主编码id，value为主编码描述信息
	 */
	public void setManCodeInfo(Map<String, String> map) {
		for(String key : map.keySet()) {
			for(MainCode mc : listMainCode) {
				if(mc.getMcId().equals(key)) {
					mc.setInfo(map.get(key));
				}
			}
		}
	}
	
}
