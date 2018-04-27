package com.bairock.iot.intelDev.linkage.timing;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.linkage.Linkage;
import com.bairock.iot.intelDev.linkage.LinkageHolder;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.linkage.LinkageTabRow;

/**
 * timing
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("TimingHolder")
public class TimingHolder extends LinkageHolder{
	
	/**
	 * 
	 */
	public TimingHolder() {
		super();
	}

	
	/**
	 * add an timing
	 * @param timing
	 */
	public void addTiming(Timing timing){
		if(null == timing){
			return;
		}
		if(!getListLinkage().contains(timing)){
			timing.setLinkageHolder(this);
			getListLinkage().add(timing);
		}
	}
	
	/**
	 * remove an timing
	 * @param timing
	 * @return
	 */
	public boolean removeTiming(Timing timing){
		if(null == timing){
			return false;
		}
		for(Linkage linkage : getListLinkage()){
			Timing sChain = (Timing)linkage;
			if(sChain.getName().equals(timing.getName())){
				timing.setLinkageHolder(null);
				return getListLinkage().remove(timing);
			}
		}
		return false;
	}
	
	/**
	 * remove an timing
	 * @param index
	 * @return
	 */
	public Timing removeTiming(int index){
		if(index >= 0){
			Timing timing = (Timing)getListLinkage().remove(index);
			if(null != timing) {
				timing.setLinkageHolder(null);
				return timing;
			}
		}
		return null;
	}
	
	@Override
	protected void unabled() {
		for (LinkageTabRow linkageTabRow : LinkageTab.getIns().getListLinkageTabRow()) {
			linkageTabRow.setTiming(-1);
		}
	}
	
	/**
	 * 
	 */
	public void run(){
		if(!isEnable()){
			for (LinkageTabRow linkageTabRow : LinkageTab.getIns().getListLinkageTabRow()) {
				linkageTabRow.setTiming(-1);
			}
		}else{
			for(Linkage subTiming : getListLinkage()){
				subTiming.run();
			}
		}
	}
}
