package com.bairock.iot.intelDev.linkage;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.device.Device;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * chain effect
 * 
 * @author LinQiang
 *
 */
@Entity
public class Effect {

	@Id
	private String id;

	@ManyToOne
	@JsonBackReference("linkage_effect")
	private Linkage linkage;

	@ManyToOne
	@JoinColumn(name = "dev_id")
	private Device device;

	private String dsId;
	private boolean deleted;

	private int effectCount;
	private String effectContent;

	/**
	 * 
	 */
	public Effect() {
		setId(UUID.randomUUID().toString());
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

	/**
	 * get device
	 * 
	 * @return
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * set device
	 * 
	 * @param device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * get device state identify
	 * 
	 * @return
	 */
	public String getDsId() {
		return dsId;
	}

	/**
	 * set device state identify
	 * 
	 * @param dsId
	 */
	public void setDsId(String dsId) {
		this.dsId = dsId;
	}
	
	public String effectStateStr() {
		if(dsId.equals(DevStateHelper.DS_KAI)) {
			return "ON";
		}
		return "OFF";
	}

	/**
	 * is deleted
	 * 
	 * @return
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * set deleted
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Linkage getLinkage() {
		return linkage;
	}

	public void setLinkage(Linkage linkage) {
		this.linkage = linkage;
	}

	public int getEffectCount() {
		return effectCount;
	}

	public void setEffectCount(int effectCount) {
		this.effectCount = effectCount;
	}

	public String getEffectContent() {
		return effectContent;
	}

	public void setEffectContent(String effectContent) {
		this.effectContent = effectContent;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Effect)) {
			return false;
		}
		Effect effect = (Effect) obj;
		if (!effect.getDevice().equals(getDevice())) {
			return false;
		}
		if (effect.getDsId() == null && this.dsId != null) {
			return false;
		}
		if (effect.getDsId() != null && this.dsId == null) {
			return false;
		}

		if (effect.getEffectContent() == null && this.effectContent != null) {
			return false;
		}
		if (effect.getEffectContent() != null && this.effectContent == null) {
			return false;
		}

		if (effect.getEffectCount() != effectCount) {
			return false;
		}

		if (effect.getDsId() != null && this.dsId != null) {
			if (!effect.getDsId().equals(dsId)) {
				return false;
			}
		}

		if (effect.getEffectContent() != null && this.effectContent != null) {
			if (!effect.getEffectContent().equals(effectContent)) {
				return false;
			}
		}
		return true;
	}

}
