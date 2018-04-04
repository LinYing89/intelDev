package com.bairock.iot.intelDev.device;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class DeviceTest {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("intelDev");
	}

	@After
	public void tearDown() throws Exception {
		entityManagerFactory.close();
	}

//	@Test
//	public void test() {
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		entityManager.getTransaction().begin();
//		
//		Device dev = DeviceAssistent.createDeviceByMcId(MainCodeHelper.YE_WEI, "0001");
//		String id = UUID.randomUUID().toString();
//		System.out.println(id);
//		dev.setId(id);
//		((Pressure)dev).getCollectProperty().setId(UUID.randomUUID().toString());
//		dev.setCtrlModel(CtrlModel.REMOTE);
//		System.out.println(dev.getId());
//		
//		entityManager.persist(dev);
//		entityManager.getTransaction().commit();
//		entityManager.close();
//	}
	
	@Test
	public void findByUserIdAndGroupName() {
		String mainCodeId = MainCodeHelper.KG_3LU_2TAI;
		String subCode = "0001";
		Device device = null;
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();

			TypedQuery<Device> query = entityManager
					.createQuery("from Device as u where u.mainCodeId=:mainCodeId and u.subCode=:subCode", Device.class);
			query.setParameter("mainCodeId", mainCodeId);
			query.setParameter("subCode", subCode);
			device = query.getSingleResult();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			entityManager.close();
		}
		//return device;
	}

}
