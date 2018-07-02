package com.bairock.iot.intelDev.dbtest;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.DevCollectClimateContainer;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchOneRoad;
import com.bairock.iot.intelDev.linkage.device.DeviceLinkage;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.User;

public class DeviceLinkageDbTest {
	
	public static void main(String[] args) {
		
		User user = new User("jack", "a123456", "444894216@qq.com", "15861295673", "hello", new Date());

		DevGroup group = new DevGroup("g1", "a123", "gg1");
		user.addGroup(group);
		
		EntityManagerFactory em = Persistence.createEntityManagerFactory("intelDev");
		
		DevCollectClimateContainer rc = (DevCollectClimateContainer) DeviceAssistent.createDeviceByMcId(MainCodeHelper.COLLECTOR_CLIMATE_CONTAINER, "0001");
		
		DevSwitchOneRoad ds = (DevSwitchOneRoad) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_1LU_2TAI, "0001");
		
		DeviceLinkage deviceLinkage = new DeviceLinkage();
		deviceLinkage.setTargetDevice(ds);
		deviceLinkage.setSwitchModel(2);
		deviceLinkage.setValue1(0);
		deviceLinkage.setValue2(20);
		rc.findTemperatureDev().addDeviceLinkage(deviceLinkage);
		
		group.addDevice(rc);
		group.addDevice(ds);
		
		EntityManager eManager = em.createEntityManager();
		eManager.getTransaction().begin();
		
		eManager.persist(user);
		eManager.getTransaction().commit();
		eManager.close();
		
		EntityManager eManager1 = em.createEntityManager();
		eManager1.getTransaction().begin();
		List<User> listUser = (List<User>) eManager1
				.createQuery("from User", User.class).getResultList();
		eManager1.getTransaction().commit();
		eManager1.close();
		
//		EntityManager eManager1 = em.createEntityManager();
//		eManager1.getTransaction().begin();
//		eManager1.remove(eManager1.merge(user));
//		eManager1.getTransaction().commit();
//		eManager1.close();
		
		em.close();
	}
}
