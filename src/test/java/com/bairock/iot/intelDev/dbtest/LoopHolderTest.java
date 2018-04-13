package com.bairock.iot.intelDev.dbtest;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bairock.iot.intelDev.device.CompareSymbol;
import com.bairock.iot.intelDev.linkage.Linkage;
import com.bairock.iot.intelDev.linkage.LinkageCondition;
import com.bairock.iot.intelDev.linkage.LinkageHolder;
import com.bairock.iot.intelDev.linkage.ZLogic;
import com.bairock.iot.intelDev.linkage.loop.LoopDuration;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.loop.ZLoop;
@Ignore
public class LoopHolderTest {

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
		
		LoopHolder loopHolder = new LoopHolder();
		loopHolder.setId(UUID.randomUUID().toString());
		loopHolder.setEnable(true);
		ZLoop zloop = new ZLoop();
		String id = UUID.randomUUID().toString();
		System.out.println(id);
		zloop.setId(id);
		
		String id2 = UUID.randomUUID().toString();
		System.out.println(id2);
		zloop.setId(id2);
		
		zloop.setName("loop1");
		zloop.setLoopCount(-1);
		
		LoopDuration loopDuration = new LoopDuration();
		loopDuration.setId(UUID.randomUUID().toString());
		loopDuration.getOnKeepTime().setTimeStr("13:1:2");
		loopDuration.getOffKeepTime().setTimeStr("14:0:10");
		
		LinkageCondition condition = new LinkageCondition();
		condition.setId(UUID.randomUUID().toString());
		condition.setLogic(ZLogic.OR);
		condition.setDevice(null);
		condition.setCompareSymbol(CompareSymbol.GREAT);
		condition.setCompareValue(70);
		
		zloop.addCondition(condition);
		zloop.addLoopDuration(loopDuration);;
		loopHolder.addLinkage(zloop);
		
		entityManager.persist(loopHolder);
		entityManager.getTransaction().commit();
		entityManager.close();
		
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
        List<LinkageHolder> result = entityManager.createQuery( "from LinkageHolder", LinkageHolder.class ).getResultList();
        LoopHolder lh = (LoopHolder)result.get(0);
        System.out.println("loopHolder " + lh.isEnable());
        for(Linkage linkage : lh.getListLinkage()){
        	ZLoop l = (ZLoop)linkage;
        	System.out.println(" zloop " + l.getName() + " : " + l.getId());
        	for(LoopDuration ldd : l.getListLoopDuration()){
        		System.out.println("  LoopDuration " + ldd.getOnKeepTime());
        		System.out.println("  LoopDuration " + ldd.getOffKeepTime());
        	}
    		for(LinkageCondition lcc : l.getListCondition()){
    			System.out.println("   LinkageCondition " + lcc.getLogic() + lcc.getCompareSymbol() + lcc.getCompareValue());
    		}
        }
        entityManager.getTransaction().commit();
        entityManager.close();
	}

}
