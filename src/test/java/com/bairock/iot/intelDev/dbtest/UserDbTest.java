package com.bairock.iot.intelDev.dbtest;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.Coordinator;
import com.bairock.iot.intelDev.device.CtrlModel;
import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.devcollect.Pressure;
import com.bairock.iot.intelDev.device.devswitch.DevSwitchThreeRoad;
import com.bairock.iot.intelDev.linkage.ChainHolder;
import com.bairock.iot.intelDev.linkage.Effect;
import com.bairock.iot.intelDev.linkage.LinkageCondition;
import com.bairock.iot.intelDev.linkage.SubChain;
import com.bairock.iot.intelDev.linkage.guagua.GuaguaHolder;
import com.bairock.iot.intelDev.linkage.loop.LoopDuration;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.loop.ZLoop;
import com.bairock.iot.intelDev.linkage.timing.Timing;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.bairock.iot.intelDev.linkage.timing.WeekHelper;
import com.bairock.iot.intelDev.linkage.timing.ZTimer;
import com.bairock.iot.intelDev.user.DevGroup;
import com.bairock.iot.intelDev.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserDbTest {

	private EntityManagerFactory em;
	
	@Before
	public void setUp() throws Exception {
		em = Persistence.createEntityManagerFactory("intelDev");
	}
	
	@After
	public void tearDown() throws Exception{
		em.close();
	}

	@Test
	public void test() {
		User user = new User("jack", "a123456", "444894216@qq.com", "15861295673", "hello", new Date());

		DevGroup group = createGroup();
		user.addGroup(group);
		
//		Device dev = createDevSwitchThreeRoad();
//		group.addDevice(dev);
		
		Coordinator c = createCoordinator();
		
		Device dev = createDevSwitchThreeRoad();
		c.addChildDev(dev);
		
		Device dev2 = createPressure();
		c.addChildDev(dev2);
		
		group.addDevice(c);
		
		ChainHolder ch = createChainHolder(((DevHaveChild)dev).getListDev().get(0), dev2);
		group.setChainHolder(ch);
		
		TimingHolder th = createTimingHolder(((DevHaveChild)dev).getListDev().get(1));
		group.setTimingHolder(th);
		
		LoopHolder lh = createLoopHolder(((DevHaveChild)dev).getListDev().get(0), dev2);
		group.setLoopHolder(lh);
		
		GuaguaHolder gh = createGuaguaHolder(((DevHaveChild)dev).getListDev().get(0), dev2);
		group.setGuaguaHolder(gh);
		
		EntityManager eManager = em.createEntityManager();
		eManager.getTransaction().begin();
		
		eManager.persist(user);
		eManager.getTransaction().commit();
		eManager.close();
		
		EntityManager eManager2 = em.createEntityManager();
		eManager2.getTransaction().begin();
		List<User> listUser = (List<User>) eManager2.createQuery("from User", User.class).getResultList();
		for(User u : listUser){
//			for(DevGroup dg : u.getListDevGroup()){
//				for(Device d : dg.getListDevice()){
//					if(d instanceof DevHaveChild){
//						((DevHaveChild)d).getListDev();
//					}
//				}
//			}
			String json = getUserJson(u);
			System.out.println("pack: " + getUserJson(u));
			
			User UU = getUserFromJson(json);
			System.out.println("unpack: " + getUserJson(UU));
//			System.out.println("user:"+ u.toString());
		}
		eManager2.getTransaction().commit();
		eManager2.close();
		em.close();
	}
	
	private String getUserJson(User u){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(u);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	private User getUserFromJson(String json){
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(json, User.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	private DevGroup createGroup(){
		DevGroup group = new DevGroup("g1", "a123", "gg1");
		//group.setId(UUID.randomUUID().toString());
		return group;
	}
	
	private Coordinator createCoordinator(){
		Coordinator c = (Coordinator) DeviceAssistent.createDeviceByMcId(MainCodeHelper.XIE_TIAO_QI, "0001");
		//c.setId(UUID.randomUUID().toString());
		return c;
	}
	
	private DevSwitchThreeRoad createDevSwitchThreeRoad(){
		DevSwitchThreeRoad dst = (DevSwitchThreeRoad) DeviceAssistent.createDeviceByMcId(MainCodeHelper.KG_3LU_2TAI, "0001");
//		for(Device dd : dst.getListDev()) {
//			//dd.setId(UUID.randomUUID().toString());
//		}
		//dst.setId(UUID.randomUUID().toString());
		dst.setCtrlModel(CtrlModel.REMOTE);
		return dst;
	}
	
	private Pressure createPressure(){
		Pressure pressure = (Pressure) DeviceAssistent.createDeviceByMcId(MainCodeHelper.YE_WEI, "0001");
		//pressure.setId(UUID.randomUUID().toString());
		pressure.getCollectProperty().setId(UUID.randomUUID().toString());
		pressure.getCollectProperty().setCrestValue(500f);
		pressure.getCollectProperty().setLeastValue(0f);
		pressure.handle("p36.5");
		return pressure;
	}
	
	private ChainHolder createChainHolder(Device effDev, Device conditionDev){
		ChainHolder ch = new ChainHolder();
		//ch.setId(UUID.randomUUID().toString());
		
		SubChain ldv = new SubChain();
		//ldv.setId(UUID.randomUUID().toString());
		
		Effect eff = new Effect();
		//eff.setId(UUID.randomUUID().toString());
		eff.setDevice(effDev);
		
		ldv.addEffect(eff);
		
		LinkageCondition lc = new LinkageCondition();
		//lc.setId(UUID.randomUUID().toString());
		lc.setDevice(conditionDev);
		
		ldv.addCondition(lc);
		
		ch.addLinkage(ldv);
		return ch;
	}
	
	private TimingHolder createTimingHolder(Device effDev){
		TimingHolder th = new TimingHolder();
		//th.setId(UUID.randomUUID().toString());
		
		Timing timing = new Timing();
		//timing.setId(UUID.randomUUID().toString());
		
		Effect eff = new Effect();
		//eff.setId(UUID.randomUUID().toString());
		eff.setDevice(effDev);
		
		timing.addEffect(eff);
		
		ZTimer zt = new ZTimer();
		//zt.setId(UUID.randomUUID().toString());
		zt.getOnTime().setId(UUID.randomUUID().toString());
		zt.getOffTime().setId(UUID.randomUUID().toString());
		zt.getOnTime().setTimeStr("00:01:10");
		zt.getOffTime().setTimeStr("00:02:20");
		WeekHelper wh = new WeekHelper();
		//wh.setId(UUID.randomUUID().toString());
		zt.setWeekHelper(wh);
		
		timing.addZTimer(zt);
		
		th.addTiming(timing);
		return th;
	}
	
	private LoopHolder createLoopHolder(Device effDev, Device conditionDev){
		LoopHolder lh = new LoopHolder();
		//lh.setId(UUID.randomUUID().toString());
		
		ZLoop loop = new ZLoop();
		//loop.setId(UUID.randomUUID().toString());
		
		Effect eff = new Effect();
		//eff.setId(UUID.randomUUID().toString());
		eff.setDevice(effDev);
		
		loop.addEffect(eff);
		
		LoopDuration ld = new LoopDuration();
		//ld.setId(UUID.randomUUID().toString());
		ld.getOnKeepTime().setId(UUID.randomUUID().toString());
		ld.getOffKeepTime().setId(UUID.randomUUID().toString());
		
		loop.addLoopDuration(ld);
		
		LinkageCondition lc = new LinkageCondition();
		//lc.setId(UUID.randomUUID().toString());
		lc.setDevice(conditionDev);
		
		loop.addCondition(lc);
		
		lh.addLinkage(loop);
		
		return lh;
	}
	
	private GuaguaHolder createGuaguaHolder(Device effDev, Device conditionDev){
		GuaguaHolder gh = new GuaguaHolder();
		//gh.setId(UUID.randomUUID().toString());
		
		SubChain gg = new SubChain();
		//gg.setId(UUID.randomUUID().toString());
		gg.setTriggered(false);
		
		Effect eff = new Effect();
		//eff.setId(UUID.randomUUID().toString());
		eff.setDevice(effDev);
		eff.setEffectContent("guagua");
		eff.setEffectCount(3);
		
		gg.addEffect(eff);
		
		LinkageCondition lc = new LinkageCondition();
		//lc.setId(UUID.randomUUID().toString());
		lc.setDevice(conditionDev);
		
		gg.addCondition(lc);
		
		gh.addLinkage(gg);
		
		return gh;
	}
	
	public String objToStr(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		String json1 = null;
		try {
			json1 = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json1;
	}


}
