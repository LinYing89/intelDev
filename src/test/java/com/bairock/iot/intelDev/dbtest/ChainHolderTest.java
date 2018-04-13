package com.bairock.iot.intelDev.dbtest;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.SubChain;
@Ignore
public class ChainHolderTest {

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
		
		ChainHolder chainHolder = new ChainHolder();
		chainHolder.setId(UUID.randomUUID().toString());
		
		SubChain linkageDevValue = new SubChain();
		linkageDevValue.setId(UUID.randomUUID().toString());
		chainHolder.addLinkage(linkageDevValue);
		entityManager.persist(chainHolder);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}
