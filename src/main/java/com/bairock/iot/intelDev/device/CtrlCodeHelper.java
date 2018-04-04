package com.bairock.iot.intelDev.device;

import java.util.ArrayList;
import java.util.List;

/**
 * device control code
 * @author LinQiang
 *
 */
public class CtrlCodeHelper {

	private static CtrlCodeHelper ins;
	
	public static final String DCT_SUB_CODE = "dct_sc";
	public static final String DCT_NORMAL = "dct_nor";
	public static final String DCT_XIETIAO_RESET = "dct_xtq_reset";
	public static final String DCT_XIETIAO_PANID = "dct_xtq_panid";
	public static final String DCT_KAIGUAN_KAI = "dct_kg_k";
	public static final String DCT_KAIGUAN_GUAN = "dct_kg_g";
	public static final String DCT_KAIGUAN_TING = "dct_kg_t";
	public static final String DCT_KAI_KEEP_TIME = "dct_kg_kt";
	public static final String DCT_STATE_FEEDBACK = "dct_kg_s";
	public static final String DCT_YAOKONG_STUDY_CODE = "dct_ykq_sc";
	public static final String DCT_YAOKONG_STUDY = "dct_ykq_xx";
	public static final String DCT_YAOKONG_TEST = "dct_ykq_cs";
	public static final String DCT_YAOKONG_SAVE = "dct_ykq_bc";
	public static final String DCT_YAOKONG_CTRL = "dct_ykq_kz";
	public static final String DCT_YAOKONG_CLEAN = "dct_ykq_qc";
	public static final String DCT_YAOKONG_TYPE = "dct_ykq_yklx";
	public static final String DCT_CAIJI_WENDU = "dct_dcj_wd";
	public static final String DCT_CAIJI_SHIDU = "dct_dcj_sd";
	public static final String DCT_CAIJI_JIAQUAN = "dct_dcj_jq";
	public static final String DCT_CAIJI_GUANGZHAO = "dct_dcj_gz";
	public static final String DCT_CAIJI_SHUIFEN = "dct_dcj_sf";
	public static final String DCT_PRESSURE_VALUE = "dct_ywj_yl";
	public static final String DCT_PRESSURE_PER_VALUE = "dct_ywj_per_yl";
	public static final String DCT_BAOJING = "dct_bjq2_bj";
	
	private List<CtrlCode> listCtrlCode;
	
	private CtrlCodeHelper(){
		listCtrlCode = new ArrayList<CtrlCode>();
		listCtrlCode.add(new CtrlCode(DCT_SUB_CODE, "1", "subCode"));
		
		listCtrlCode.add(new CtrlCode(DCT_NORMAL, "2", "isNormal"));
		
		listCtrlCode.add(new CtrlCode(DCT_XIETIAO_RESET, "3", "xtqReset"));
		listCtrlCode.add(new CtrlCode(DCT_XIETIAO_PANID, "4", "xtqPanid"));
		
		listCtrlCode.add(new CtrlCode(DCT_KAIGUAN_KAI, "3", "orderKai"));
		listCtrlCode.add(new CtrlCode(DCT_KAIGUAN_GUAN, "4", "orderGuan"));
		listCtrlCode.add(new CtrlCode(DCT_KAIGUAN_TING, "5", "orderTing"));
		listCtrlCode.add(new CtrlCode(DCT_KAI_KEEP_TIME, "6", "kaiKeepTime"));
		listCtrlCode.add(new CtrlCode(DCT_STATE_FEEDBACK, "7", "switchStateFeedback"));
		
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_STUDY_CODE, "3", "study remote code，size is four"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_STUDY, "4", "study"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_TEST, "5", "test"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_SAVE, "6", "save"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_CTRL, "7", "control"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_CLEAN, "8", "clean"));
		listCtrlCode.add(new CtrlCode(DCT_YAOKONG_TYPE, "9", "type of remove，0：hongwai，1:315MHz，2:433MHz"));
		
		listCtrlCode.add(new CtrlCode(DCT_CAIJI_WENDU, "3", "duoGongNengCaiJiQi，wenDu"));
		listCtrlCode.add(new CtrlCode(DCT_CAIJI_SHIDU, "4", "shiDu"));
		listCtrlCode.add(new CtrlCode(DCT_CAIJI_JIAQUAN, "5", "jiaQuan"));
		listCtrlCode.add(new CtrlCode(DCT_CAIJI_GUANGZHAO, "6", "guangZhao"));
		listCtrlCode.add(new CtrlCode(DCT_CAIJI_SHUIFEN, "7", "shuiFen"));
		
		listCtrlCode.add(new CtrlCode(DCT_PRESSURE_VALUE, "8", "value of pressure"));
		listCtrlCode.add(new CtrlCode(DCT_PRESSURE_PER_VALUE, "p", "percent value of pressure"));
		
		listCtrlCode.add(new CtrlCode(DCT_BAOJING, "3", "code of duoGoneNengBaoJingQi"));
	}
	
	/**
	 * get instance of CtrlCodeHelper
	 * @return
	 */
	public static CtrlCodeHelper getIns(){
		if(null == ins){
			ins = new CtrlCodeHelper();
		}
		return ins;
	}

	/**
	 * get control code list
	 * @return
	 */
	public List<CtrlCode> getListCtrlCode() {
		return listCtrlCode;
	}

	/**
	 * set control code list
	 * @param listCtrlCode
	 */
	public void setListCtrlCode(List<CtrlCode> listCtrlCode) {
		this.listCtrlCode = listCtrlCode;
	}
	
	/**
	 * add a control code
	 * @param cc
	 */
	public void add(CtrlCode cc){
		boolean haved = false;
		for(CtrlCode ctrlCode : listCtrlCode){
			if(ctrlCode.getDctId().equals(cc.getDctId())){
				if(!ctrlCode.getDct().equals(cc.getDct())){
					ctrlCode.setDct(cc.getDct());
					haved = true;
					break;
				}
			}
		}
		if(!haved){
			listCtrlCode.add(cc);
		}
	}
	
	/**
	 * remove a control code
	 * @param cc
	 * @return
	 */
	public boolean remove(CtrlCode cc){
		for(CtrlCode ctrlCode : listCtrlCode){
			if(ctrlCode.getDctId().equals(cc.getDctId())){
				if(!ctrlCode.getDct().equals(cc.getDct())){
					listCtrlCode.remove(ctrlCode);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * get control code
	 * @param dctId control code identify
	 * @return
	 */
	public String getDct(String dctId){
		if(null == dctId){
			return null;
		}
		for(CtrlCode ctrlCode : listCtrlCode){
			if(ctrlCode.getDctId().equals(dctId)){
				return ctrlCode.getDct();
			}
		}
		return null;
	}
	
	/**
	 * get control code identify
	 * @param dct control code
	 * @return
	 */
	public String getDctId(String dct){
		if(null == dct){
			return null;
		}
		for(CtrlCode ctrlCode : listCtrlCode){
			if(ctrlCode.getDct().equals(dct)){
				return ctrlCode.getDctId();
			}
		}
		return null;
	}
	
}
