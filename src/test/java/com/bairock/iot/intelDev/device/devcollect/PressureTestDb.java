package com.bairock.iot.intelDev.device.devcollect;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;

@Ignore
public class PressureTestDb {

	private EntityManagerFactory em;
	
	@Before
	public void setUp() throws Exception {
		em = Persistence.createEntityManagerFactory("intelDev");
	}

	@After
	public void tearDown() throws Exception {
		em.close();
	}

	@Test
	public void test() {
		
		Device dev = createPressure();
		
		EntityManager eManager = em.createEntityManager();
		eManager.getTransaction().begin();
		
		eManager.persist(dev);
		eManager.getTransaction().commit();
		eManager.close();
	}
	
	private DevCollect createPressure(){
		DevCollect pressure = (Pressure) DeviceAssistent.createDeviceByMcId(MainCodeHelper.YE_WEI, "0001");
		pressure.setId(UUID.randomUUID().toString());
		pressure.getCollectProperty().setId(UUID.randomUUID().toString());
		pressure.getCollectProperty().setCrestValue(500f);
		pressure.getCollectProperty().setLeastValue(0f);
		pressure.handle("p16.5");
		return pressure;
	}

}
