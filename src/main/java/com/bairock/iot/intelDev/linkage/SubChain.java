package com.bairock.iot.intelDev.linkage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("SubCahin")
public class SubChain extends Linkage {
	
	private boolean triggered;
	
	@OneToMany(mappedBy = "subChain",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("subchain_linkagecondition")
	private List<LinkageCondition> listCondition;
	
	/**
	 * 
	 */
	public SubChain() {
		super();
		listCondition = Collections.synchronizedList(new ArrayList<LinkageCondition>());
	}

//	public LinkageHolder getLinkageHolder() {
//		return linkageHolder;
//	}
//
//	public void setLinkageHolder(LinkageHolder linkageHolder) {
//		this.linkageHolder = linkageHolder;
//	}

	public boolean isTriggered() {
		return triggered;
	}

	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	/**
	 * get condition list
	 * @return
	 */
	public List<LinkageCondition> getListCondition() {
		return listCondition;
	}

	/**
	 * set condition list
	 * @param listCondition
	 */
	public void setListCondition(List<LinkageCondition> listCondition) {
		this.listCondition = listCondition;
		for(LinkageCondition lc : listCondition) {
			lc.setSubChain(this);
		}
	}

	/**
	 * 
	 * @param condition
	 */
	public void addCondition(LinkageCondition condition){
		if(null == condition){
			return;
		}
		if(!listCondition.contains(condition)){
			listCondition.add(condition);
			condition.setSubChain(this);
		}
	}
	
	/**
	 * 
	 * @param condition
	 * @return
	 */
	public boolean removeCondition(LinkageCondition condition){
		if(null == condition){
			return false;
		}
		condition.setSubChain(null);
		return listCondition.remove(condition);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public LinkageCondition removeCondition(int index){
		LinkageCondition lc = listCondition.remove(index);
		if(null != lc) {
			lc.setSubChain(null);
		}
		return lc;
	}

	/**
	 * get all condition result
	 * @return true if all condition is pass
	 */
	@Override
	@JsonIgnore
	public boolean getConditionResult(){
		Integer result = null;
		if(listCondition.isEmpty()){
			return false;
		}

		List<LinkageCondition> list = new ArrayList<>(listCondition);
		for(int i=0; i< list.size(); i++){
			LinkageCondition event = list.get(i);
			//获取每个条件的结果
			Integer er = event.getResult();
			if(er == null){
				continue;
			}
			if(result == null){
				result = er;
			}else{
				if(event.getLogic().equals(ZLogic.AND)){
					result *= er;
				}else{
					result += er;
				}
			}
		}
		//System.out.println("getConditionResult:" + (result != null && result > 0));
		return  result != null && result > 0;
	}
	
	/**
	 * 
	 */
	@Override
	public void run(){
		if(!isEnable() || listCondition.isEmpty() || getListEffect().isEmpty()){
			return;
		}
		if(getConditionResult()){
			effectLinkageTab(LinkageTab.CHAIN);
		}
	}
}
