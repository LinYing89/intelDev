package com.bairock.iot.intelDev.linkage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "linkage_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Linkage")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class")
public class Linkage {
	
	@Id
	@Column(nullable = false)
	private String id;
	
	@ManyToOne
	@JsonBackReference("linkageholder_linkage")
	private LinkageHolder linkageHolder;
	
	private String name;
	private boolean enable;
	private boolean deleted;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="linkage_id")
	@JsonManagedReference("linkage_effect")
	private List<Effect> listEffect;
	
	public Linkage(){
		id= UUID.randomUUID().toString();
		listEffect = Collections.synchronizedList(new ArrayList<>());
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public LinkageHolder getLinkageHolder() {
		return linkageHolder;
	}

	public void setLinkageHolder(LinkageHolder linkageHolder) {
		this.linkageHolder = linkageHolder;
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEnable() {
		return enable;
	}
	
	/**
	 * 
	 * @param enable
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return deleted;
	}
	
	/**
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	/**
	 * get effect list
	 * @return
	 */
	public List<Effect> getListEffect() {
		return listEffect;
	}

	/**
	 * set effect list
	 * @param listEffect
	 */
	public void setListEffect(List<Effect> listEffect) {
		this.listEffect = listEffect;
		for(Effect e : listEffect) {
			e.setLinkage(this);
		}
	}
	
	/**
	 * 
	 * @param effect
	 */
	public void addEffect(Effect effect){
		if(null != effect 
				&& !listEffect.contains(effect)){
			effect.setLinkage(this);
			listEffect.add(effect);
		}
	}
	
	/**
	 * 
	 * @param effect
	 * @return
	 */
	public boolean removeEffect(Effect effect){
		if(null == effect){
			return false;
		}
		effect.setLinkage(null);
		return listEffect.remove(effect);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Effect removeEffect(int index){
		Effect e = listEffect.remove(index);
		if(null != e) {
			e.setLinkage(null);
		}
		return e;
	}
	
	/**
	 * 
	 * @param linkageType chain\timing\loop
	 */
	public void effectLinkageTab(int linkageType, String dsId){
		for(Effect effect : getListEffect()) {
			LinkageTab.getIns().setChain(effect.getDevice(), linkageType, dsId);
		}
	}
	
	/**
	 * 
	 * @param linkageType
	 */
	public void effectLinkageTab(int linkageType){
		for(Effect effect : getListEffect()) {
			LinkageTab.getIns().setChain(effect.getDevice(), linkageType, effect.getDsId());
		}
	}
	
	public void run() {
		
	}
	
	public boolean getConditionResult() {
		return false;
	}
}
