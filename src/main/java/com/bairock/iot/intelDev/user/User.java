package com.bairock.iot.intelDev.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bairock.iot.intelDev.device.Device;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * User info
 * 
 * @author LinQiang
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String psd;
	private String email;
	private String tel;
	private String petName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registerTime;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("user_group")
	private List<DevGroup> listDevGroup = new ArrayList<>();

	/**
	 * 
	 */
	public User() {
	}

	/**
	 * 
	 * @param name
	 * @param psd
	 * @param email
	 * @param tel
	 * @param petName
	 * @param registerTime
	 */
	public User(String name, String psd, String email, String tel, String petName, Date registerTime) {
		this.name = name;
		this.psd = psd;
		this.email = email;
		this.tel = tel;
		this.petName = petName;
		this.registerTime = registerTime;
	}

	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * get user name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * set user name
	 * 
	 * @param userName
	 */
	public void setName(String userName) {
		this.name = userName;
	}

	/**
	 * get user password
	 * 
	 * @return
	 */
	public String getPsd() {
		return psd;
	}

	/**
	 * set user password
	 * 
	 * @param userPsd
	 */
	public void setPsd(String userPsd) {
		this.psd = userPsd;
	}

	/**
	 * get email
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get telephone number
	 * 
	 * @return
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * set telephone number
	 * 
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * get pet name
	 * 
	 * @return
	 */
	public String getPetName() {
		return petName;
	}

	/**
	 * set pet name
	 * 
	 * @param petName
	 */
	public void setPetName(String petName) {
		this.petName = petName;
	}

	/**
	 * get register time
	 * 
	 * @return
	 */
	public Date getRegisterTime() {
		return registerTime;
	}

	/**
	 * set register time
	 * 
	 * @param registerTime
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	/**
	 * 
	 * @return
	 */
	public List<DevGroup> getListDevGroup() {
		return listDevGroup;
	}

	/**
	 * 
	 * @param listDevGroup
	 */
	public void setListDevGroup(List<DevGroup> listDevGroup) {
		if(null == listDevGroup) {
			return;
		}
		this.listDevGroup.clear();
		for(DevGroup group : listDevGroup) {
			addGroup(group);
		}
	}

	/**
	 * 
	 * @param group
	 */
	public void addGroup(DevGroup group) {
		if(null != group) {
			group.setUser(this);
			listDevGroup.add(group);
		}
	}

	/**
	 * 
	 * @param group
	 */
	public void removeGroup(DevGroup group) {
		listDevGroup.remove(group);
		group.setUser(null);
	}
	
	public Device findDev(String devCoding){
		Device dev =  null;
		for(DevGroup group : listDevGroup){
			dev = group.findDeviceWithCoding(devCoding);
		}
		return dev;
	}

	public DevGroup findDevGroupByName(String devGroupName){
		for(DevGroup group : listDevGroup) {
			if(group.getName().equals(devGroupName)) {
				return group;
			}
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((petName == null) ? 0 : petName.hashCode());
		result = prime * result + ((registerTime == null) ? 0 : registerTime.hashCode());
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((psd == null) ? 0 : psd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (petName == null) {
			if (other.petName != null)
				return false;
		} else if (!petName.equals(other.petName))
			return false;
		if (registerTime == null) {
			if (other.registerTime != null)
				return false;
		} else if (!registerTime.equals(other.registerTime))
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (psd == null) {
			if (other.psd != null)
				return false;
		} else if (!psd.equals(other.psd))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = String.format("User(id:%d, Name:%s, psd:%s, email:%s, tel:%s, petName:%s)", 
				id, name, psd, email, tel, petName);
		return str;
	}
	
}
