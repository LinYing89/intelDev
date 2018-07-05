package com.bairock.iot.intelDev.dbtest;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.alarm.AlarmInfo;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.User;

public class AlarmInfoDbTest {
	
	public static void main(String[] args) {

		User user = new User("jack", "a123456", "444894216@qq.com", "15861295673", "hello", new Date());

		DevGroup group = new DevGroup("g1", "a123", "gg1");
		user.addGroup(group);

		EntityManagerFactory em = Persistence.createEntityManagerFactory("intelDev");

		Device rc = DeviceAssistent.createDeviceByMcId(MainCodeHelper.YAO_KONG, "0001");

		AlarmInfo ai = new AlarmInfo();
		ai.setInfo("alarm");
		ai.setAlarmTime(new Date());
		rc.addAlarmInfo(ai);
		AlarmInfo ai2 = new AlarmInfo();
		ai2.setInfo("alarm2");
		ai2.setAlarmTime(new Date());
		rc.addAlarmInfo(ai2);
		
		group.addDevice(rc);

		EntityManager eManager = em.createEntityManager();
		eManager.getTransaction().begin();

		eManager.persist(user);
		eManager.getTransaction().commit();
		eManager.close();

//		EntityManager eManager1 = em.createEntityManager();
//		eManager1.getTransaction().begin();
//		eManager1.remove(eManager1.merge(user));
//		eManager1.getTransaction().commit();
//		eManager1.close();

		em.close();
	}
}
