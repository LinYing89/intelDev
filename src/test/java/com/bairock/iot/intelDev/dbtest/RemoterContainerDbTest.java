package com.bairock.iot.intelDev.dbtest;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.remoter.Remoter;
import com.bairock.iot.intelDev.device.remoter.RemoterContainer;
import com.bairock.iot.intelDev.device.remoter.RemoterKey;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.User;

public class RemoterContainerDbTest {

	public static void main(String[] args) {
		
		User user = new User("jack", "a123456", "444894216@qq.com", "15861295673", "hello", new Date());

		DevGroup group = new DevGroup("g1", "a123", "gg1");
		user.addGroup(group);
		
		EntityManagerFactory em = Persistence.createEntityManagerFactory("intelDev");
		
		RemoterContainer rc = (RemoterContainer) DeviceAssistent.createDeviceByMcId(MainCodeHelper.YAO_KONG, "0001");
		
		Remoter rTv = new Remoter("1", "1");
		RemoterKey rTvK1 = new RemoterKey();
		rTvK1.setNumber("01");
		rTv.addRemoterKey(rTvK1);
		RemoterKey rTvK2 = new RemoterKey();
		rTvK2.setNumber("02");
		rTv.addRemoterKey(rTvK2);
		
		Remoter rCoutain = new Remoter("3", "1");
		RemoterKey rCoutainK1 = new RemoterKey();
		rCoutainK1.setNumber("01");
		rCoutain.addRemoterKey(rCoutainK1);
		RemoterKey rCoutainK2 = new RemoterKey();
		rCoutainK2.setNumber("02");
		rCoutain.addRemoterKey(rCoutainK2);
		
		Remoter rAir = new Remoter("2", "1");
		RemoterKey rAirK1 = new RemoterKey();
		rAirK1.setNumber("01");
		rAir.addRemoterKey(rAirK1);
		RemoterKey rAirK2 = new RemoterKey();
		rAirK2.setNumber("02");
		rAir.addRemoterKey(rAirK2);
		
		rc.addChildDev(rTv);
		rc.addChildDev(rAir);
		rc.addChildDev(rCoutain);
		
		group.addDevice(rc);
		
		EntityManager eManager = em.createEntityManager();
		eManager.getTransaction().begin();
		
		eManager.persist(user);
		eManager.getTransaction().commit();
		eManager.close();
		
		EntityManager eManager1 = em.createEntityManager();
		eManager1.getTransaction().begin();
		eManager1.remove(eManager1.merge(user));
		eManager1.getTransaction().commit();
		eManager1.close();
		
		em.close();
	}
}
