package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.List;

/**
 * main code helper
 * @author LinQiang
 *
 */
public class MainCodeHelper {

	private static MainCodeHelper ins;
	//xie tiao qi
	public static final String XIE_TIAO_QI = "omc_xtq";
	//guagua mouth
	public static final String GUAGUA_MOUTH = "omc_ggz";
	//men jin
	public static final String MEN_JIN = "omc_mj";
	//
	public static final String DGN_CAI_JI = "omc_dcj";
	//ye wei ji
	public static final String YE_WEI = "omc_ywj";
	//xin hao cai ji kong zhi qi
	public static final String COLLECTOR_SIGNAL_CONTAINER = "signal_collector_container";
	//xin hao cai ji qi
	public static final String COLLECTOR_SIGNAL = "signal_collector";
	//duo gong neng xin hao chuan gan qi
	public static final String COLLECTOR_CLIMATE_CONTAINER = "signal_climate_container";
	//yan wu tan ci qi
	public static final String YAN_WU = "omc_yw";
	//wen du ji
	public static final String WEN_DU = "omc_wd";
	//shi du ji
	public static final String SHI_DU = "omc_sd";
	//jian quan
	public static final String JIA_QUAN = "omc_jq";
	//kai guan 1 lu 2 tai
	public static final String KG_1LU_2TAI = "mmc_kg12";
	//kai guan 2 lu 2 tai
	public static final String KG_2LU_2TAI = "mmc_kg22";
	//kai guan 3 lu 2 tai
	public static final String KG_3LU_2TAI = "mmc_kg32";
	//kai guan x lu 2 tai
	public static final String KG_XLU_2TAI = "mmc_kgx2";
	//kai guan 3 tai
	public static final String KG_3TAI = "mmc_kgx3";
	//yao kong qi
	public static final String YAO_KONG = "mmc_ykq";
	//cha zuo
	public static final String CHA_ZUO = "mmc_cz";
	//bao jing she bei lei xing 1
	public static final String BAO_JING1 = "mmc_bjq1";
	//bao jing she bei lei xing 2
	public static final String BAO_JING2 = "mmc_bjq2";
	//yi xia duo wei zi she bei
	//wu lei xing she bei(sub device main code)
	public static final String SMC_WU = "smc_w";
	//chuang lian
	public static final String SMC_REMOTER_CHUANG_LIAN = "smc_remoter_cl";
	//dian shi
	public static final String SMC_REMOTER_DIAN_SHI = "smc_remoter_ds";
	//kong tiao
	public static final String SMC_REMOTER_KONG_TIAO = "smc_remoter_kt";
	//tou ying yi
	public static final String SMC_REMOTER_TOU_YING = "smc_remoter_tyy";
	//mu bu
	public static final String SMC_REMOTER_MU_BU = "smc_remoter_mb";
	//sheng jiang jia
	public static final String SMC_REMOTER_SHENG_JIANG_JIA = "smc_remoter_sjj";
	//zi ding yi she bei
	public static final String SMC_REMOTER_ZI_DING_YI = "smc_remoter_zdy";
	//deng
	public static final String SMC_DENG = "smc_d";
	//chuang hu
	public static final String SMC_CHUANG_HU = "smc_ch";
	//fa men
	public static final String SMC_FA_MEN = "smc_fm";
	//cha zuo
	public static final String SMC_CHA_ZUO = "smc_cz";
	//bing xiang
	public static final String SMC_BING_XIANG = "smc_bx";
	//xi yi ji
	public static final String SMC_XI_YI_JI = "smc_xyj";
	//wei bo lu
	public static final String SMC_WEI_BO_LU = "smc_wbl";
	//yin xiang
	public static final String SMC_YIN_XIANG = "smc_yx";
	//shui long tou
	public static final String SMC_SHUI_LONG_TOU = "smc_slt";
	
	private List<MainCode> listMainCode;

	/**
	 * 
	 */
	private MainCodeHelper(){
		listMainCode = new ArrayList<MainCode>();
		listMainCode.add(new MainCode(XIE_TIAO_QI, "A1", "xietiaoqi"));
		listMainCode.add(new MainCode(GUAGUA_MOUTH, "R1", "guaguazui"));
		listMainCode.add(new MainCode(MEN_JIN, "c1", "menjin"));
		listMainCode.add(new MainCode(DGN_CAI_JI, "x1", "duogongnengcaijiqi"));
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
		listMainCode.add(new MainCode(CHA_ZUO, "E1", "chazuo"));
		listMainCode.add(new MainCode(BAO_JING1, "a1", "duogongnengbaojing1"));
		listMainCode.add(new MainCode(BAO_JING2, "a2", "duogongnengbaojing2"));
		
		listMainCode.add(new MainCode(SMC_WU, "0", "wu"));
		listMainCode.add(new MainCode(SMC_REMOTER_CHUANG_LIAN, "1", "chuanglian"));
		listMainCode.add(new MainCode(SMC_REMOTER_DIAN_SHI, "2", "dianshi"));
		listMainCode.add(new MainCode(SMC_REMOTER_KONG_TIAO, "3", "kongtiao"));
		listMainCode.add(new MainCode(SMC_REMOTER_TOU_YING, "4", "touyingyi"));
		listMainCode.add(new MainCode(SMC_REMOTER_MU_BU, "5", "mubu"));
		listMainCode.add(new MainCode(SMC_REMOTER_SHENG_JIANG_JIA, "6", "shenjiangjia"));
		listMainCode.add(new MainCode(SMC_REMOTER_ZI_DING_YI, "7", "zidingyi"));
		listMainCode.add(new MainCode(SMC_DENG, "10", "deng"));
		listMainCode.add(new MainCode(SMC_CHUANG_HU, "11", "chuanghu"));
		listMainCode.add(new MainCode(SMC_FA_MEN, "12", "famen"));
		listMainCode.add(new MainCode(SMC_CHA_ZUO, "13", "chazuo"));
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
			return null;
		}
		if(!getListMainCode().isEmpty()){
			for(MainCode mc : listMainCode){
				if(mc.getMcId().equals(mcId)){
					return mc.getMc();
				}
			}
		}
		return null;
	}
	
	/**
	 * get main code identify
	 * @param mc main code
	 * @return
	 */
	public String getMcId(String mc){
		if(null == mc){
			return null;
		}
		if(!getListMainCode().isEmpty()){
			for(MainCode mainCode : listMainCode){
				if(mainCode.getMc().equals(mc)){
					return mainCode.getMcId();
				}
			}
		}
		return null;
	}
	
}
