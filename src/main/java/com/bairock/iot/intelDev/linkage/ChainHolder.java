package com.bairock.iot.intelDev.linkage;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * chain
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ChainHolder")
public class ChainHolder extends LinkageHolder{
	
	/**
	 * 
	 */
	public ChainHolder() {
		super();
	}

	/**
	 * 
	 */
	public void run(){
		if(!isEnable()){
			for (LinkageTabRow linkageTabRow : LinkageTab.getIns().getListLinkageTabRow()) {
				linkageTabRow.setChain(-1);
			}
		}else{
			for(Linkage subChain : getListLinkage()){
				subChain.run();
			}
		}
	}
}
