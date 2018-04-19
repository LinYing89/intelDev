package com.bairock.iot.intelDev.linkage.guagua;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.GuaguaMouth;
import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.Linkage;
import com.bairock.iot.intelDev.linkage.SubChain;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("GuaguaHolder")
public class GuaguaHolder extends ChainHolder{
	
	/**
	 * 
	 */
	public GuaguaHolder() {
		super();
	}

	/**
	 * 
	 */
	public void run(){
		if(isEnable()){
			for(Linkage linkageDevValue : getListLinkage()){
				runEffect((SubChain)linkageDevValue);
			}
		}
	}
	
	private void runEffect(SubChain linkageDevValue){
		if(!isEnable() || linkageDevValue.getListCondition().isEmpty() 
				|| linkageDevValue.getListEffect().isEmpty()){
			return;
		}
		if(linkageDevValue.getConditionResult()){
			if(!linkageDevValue.isTriggered()){
				linkageDevValue.setTriggered(true);
				List<Effect> list = new ArrayList<>(linkageDevValue.getListEffect());
				for(Effect effect : list){
					if(effect.getDevice() instanceof GuaguaMouth){
						GuaguaMouth guagua = (GuaguaMouth)(effect.getDevice());
						String order = guagua.getDevOrder(effect.getEffectCount(), effect.getEffectContent());
						GuaguaHelper.getIns().stateChangedListener(guagua, order, guagua.getCtrlModel());
					}
				}
			}
		}else{
			linkageDevValue.setTriggered(false);
		}
	}
}
