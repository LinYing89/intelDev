package com.bairock.iot.intelDev.dbtest;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bairock.iot.intelDev.linkage.timing.Timing;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.bairock.iot.intelDev.linkage.timing.ZTimer;
@Ignore
public class TimingHolderTest {

	private EntityManagerFactory entityManagerFactory;
	
	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("intelDev");
	}

	@After
	public void tearDown() throws Exception {
		entityManagerFactory.close();
	}

	@Test
	public void test() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		
		TimingHolder timingHolder = new TimingHolder();
		timingHolder.setId(UUID.randomUUID().toString());
		Timing timing = new Timing();
		timing.setId(UUID.randomUUID().toString());
		
		ZTimer ztimer = new ZTimer();
		ztimer.setId(UUID.randomUUID().toString());
		ztimer.getOnTime().setTimeStr("13:1:2");
		ztimer.getOffTime().setTimeStr("14:0:10");
		ztimer.getWeekHelper().setId(UUID.randomUUID().toString());
		ztimer.getWeekHelper().setWeeks(2,3,4);
		
		timing.addZTimer(ztimer);
		timingHolder.addTiming(timing);
		
		entityManager.persist(timingHolder);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
