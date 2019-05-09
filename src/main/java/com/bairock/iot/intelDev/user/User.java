package com.bairock.iot.intelDev.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.Device;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String userid;
	private String username;
	private String password;
	private String petname;
	//性别,true为男, false为女
	@Column(columnDefinition="boolean default true")
	private boolean gender;
	private String email;
	private String tel;
	@Column(columnDefinition="boolean default false")
	private boolean enable=false;
	private String remark;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date registerTime;

	@Transient
	@JsonManagedReference("user_group")
	private List<DevGroup> listDevGroup = new ArrayList<>();

	/**
	 * 
	 */
	public User() {
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPetname() {
		return petname;
	}

	public void setPetname(String petname) {
		this.petname = petname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
			group.setUserid(userid);
			group.setUserPetname(petname);
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
			if(null != dev) {
				return dev;
			}
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
	
	/**
     * 根据组id寻找组
     * @param devGroupId 组id
     * @return 组对象
     */
//    public DevGroup findDevGroupById(long devGroupId){
//        for(DevGroup group : listDevGroup){
//            if(group.getId() == devGroupId){
//                return group;
//            }
//        }
//        return null;
//    }
	public DevGroup findDevGroupById(String devGroupId){
        for(DevGroup group : listDevGroup){
            if(group.getId() == devGroupId){
                return group;
            }
        }
        return null;
    }

}
