package com.bairock.iot.intelDev.device.remoter;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.bairock.iot.intelDev.device.OrderHelper;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class RemoterKey implements IRemoterKey {

	@Id
	@Column(nullable = false)
	private String id;

	@ManyToOne
	@JsonBackReference("remoter_remoterkey")
	private Remoter remoter;

	private String name = "";
	private String number = "";

	private int locationX;
	private int locationY;

	public RemoterKey() {
		setId(UUID.randomUUID().toString());
	}
	
	public RemoterKey(String number) {
		this();
		this.number = number;
	}
	
	public RemoterKey(String number, String name) {
		this(number);
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Remoter getRemoter() {
		return remoter;
	}

	public void setRemoter(Remoter remoter) {
		this.remoter = remoter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	private String createKeyOrder(String orderType) {
		return OrderHelper.getOrderMsg(OrderHelper.CTRL_HEAD + remoter.getParent().getCoding() + OrderHelper.SEPARATOR
				+ "3" + remoter.getCoding() + getNumber() + OrderHelper.SEPARATOR + orderType);
	}

	@Override
	public String createStudyKeyOrder() {
		return createKeyOrder("4");
	}

	@Override
	public String createTestKeyOrder() {
		return createKeyOrder("5");
	}

	@Override
	public String createSaveKeyOrder() {
		return createKeyOrder("6");
	}

	@Override
	public String createCtrlKeyOrder() {
		return createKeyOrder("7");
	}
}
