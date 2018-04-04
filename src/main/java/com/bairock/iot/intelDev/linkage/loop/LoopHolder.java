package com.bairock.iot.intelDev.linkage.loop;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.linkage.Linkage;
import com.bairock.iot.intelDev.linkage.LinkageHolder;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.linkage.LinkageTabRow;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("LoopHolder")
public class LoopHolder extends LinkageHolder{
	
	/**
	 * 
	 */
	public LoopHolder() {
		super();
	}
	
	/**
	 * 
	 */
	public void run(){
		if(!isEnable()){
			for (LinkageTabRow linkageTabRow : LinkageTab.getIns().getListLinkageTabRow()) {
				linkageTabRow.setLoop(-1);
			}
		}else{
			for(Linkage subLoop : getListLinkage()){
				subLoop.run();
			}
		}
	}
}
