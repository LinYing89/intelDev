package com.bairock.iot.intelDev.linkage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bairock.iot.intelDev.user.DevGroup;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "linkage_type", discriminatorType = DiscriminatorType.STRING)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class")
public class LinkageHolder {
	
	@Id
	@Column(nullable = false)
	private String id;
	
	private boolean enable;
	
	@ManyToOne
	@JsonBackReference("group_linkage_holder")
	private DevGroup devGroup;
	
	@OneToMany(mappedBy = "linkageHolder",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("linkageholder_linkage")
	private List<Linkage> listLinkage;
	
	public LinkageHolder() {
		id = UUID.randomUUID().toString();
		listLinkage = Collections.synchronizedList(new ArrayList<>());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * is  enable
	 * @return
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * set enable
	 * @param enable
	 */
	public void setEnable(boolean enable) {
		if(this.enable != enable) {
			this.enable = enable;
			if(enable) {
				enabled();
			}else {
				unabled();
			}
		}
	}
	
	protected void enabled() {
		
	}
	
	protected void unabled() {
		
	}
	
	public DevGroup getDevGroup() {
		return devGroup;
	}

	public void setDevGroup(DevGroup devGroup) {
		this.devGroup = devGroup;
	}

	public List<Linkage> getListLinkage() {
		return listLinkage;
	}

	public void setListLinkage(List<Linkage> listLinkage) {
		this.listLinkage = listLinkage;
		for(Linkage l : listLinkage) {
			l.setLinkageHolder(this);
		}
	}
	
	public void addLinkage(Linkage linkage) {
		if(null == linkage){
			return;
		}
		if(!listLinkage.contains(linkage)){
			listLinkage.add(linkage);
			linkage.setLinkageHolder(this);
			linkage.setOnEnableChangedListener(new Linkage.OnEnableChangedListener() {
				
				@Override
				public void onEnableChanged(Linkage linkage, boolean enable) {
					for(Linkage subChain : getListLinkage()){
						subChain.conclutionConditionsResultAndRunEffect();
					}
				}
			});
		}
	}
	
	/**
	 * remove an sub chain
	 * @param linkage
	 * @return
	 */
	public boolean removeLinkage(Linkage linkage){
		if(null == linkage){
			return false;
		}
		linkage.setLinkageHolder(null);
		return listLinkage.remove(linkage);
	}
	
	
	/**
	 * remove an sub chain
	 * @param index
	 * @return
	 */
	public Linkage removeSubChain(int index){
		if(index >= 0){
			Linkage lv = listLinkage.remove(index);
			if(null != lv) {
				lv.setLinkageHolder(null);
				return lv;
			}
		}
		return null;
	}
	
}
