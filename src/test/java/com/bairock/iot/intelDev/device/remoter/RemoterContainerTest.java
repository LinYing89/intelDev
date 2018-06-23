package com.bairock.iot.intelDev.device.remoter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.bairock.iot.intelDev.device.DeviceAssistent;
import com.bairock.iot.intelDev.device.MainCodeHelper;
import com.bairock.iot.intelDev.device.OrderHelper;
import com.bairock.iot.intelDev.device.remoter.RemoterContainer.OnRemoterOrderSuccessListener;

public class RemoterContainerTest {

	private RemoterContainer rc;
	@Before
	public void setUp() throws Exception {
		rc = (RemoterContainer) DeviceAssistent.createDeviceByMcId(MainCodeHelper.YAO_KONG, "0001");
		
		String mcId = MainCodeHelper.getIns().getMcId("1");
		Remoter rTv = (Remoter) DeviceAssistent.createDeviceByMcId(mcId, "1");
		RemoterKey rTvK1 = new RemoterKey();
		rTvK1.setNumber("01");
		rTv.addRemoterKey(rTvK1);
		RemoterKey rTvK2 = new RemoterKey();
		rTvK2.setNumber("02");
		rTv.addRemoterKey(rTvK2);
		
		String mcId3 = MainCodeHelper.getIns().getMcId("3");
		Remoter rCoutain = (Remoter) DeviceAssistent.createDeviceByMcId(mcId3, "1");
		RemoterKey rCoutainK1 = new RemoterKey();
		rCoutainK1.setNumber("01");
		rCoutain.addRemoterKey(rCoutainK1);
		RemoterKey rCoutainK2 = new RemoterKey();
		rCoutainK2.setNumber("02");
		rCoutain.addRemoterKey(rCoutainK2);
		
		String mcId2 = MainCodeHelper.getIns().getMcId("2");
		Remoter rAir = (Remoter) DeviceAssistent.createDeviceByMcId(mcId2, "1");
		RemoterKey rAirK1 = new RemoterKey();
		rAirK1.setNumber("01");
		rAir.addRemoterKey(rAirK1);
		RemoterKey rAirK2 = new RemoterKey();
		rAirK2.setNumber("02");
		rAir.addRemoterKey(rAirK2);
		
		rc.addChildDev(rTv);
		rc.addChildDev(rAir);
		rc.addChildDev(rCoutain);
		
		rc.setOnRemoterOrderSuccessListener(new OnRemoterOrderSuccessListener() {
			
			@Override
			public void onRemoterOrderSuccess(RemoterKey studingKey) {
				System.out.println(studingKey.getRemoter().getLongCoding() + studingKey.getNumber());
			}
		});
	}

	@Test
	public void testCoding() {
		assertEquals("D10001", rc.getCoding());
	}
	
	@Test
	public void testStudy() {
		Remoter r = (Remoter) rc.findDevByCoding("11");
		assertEquals(OrderHelper.getOrderMsg("CD10001:31101:4"), r.findKeyByNumber("01").createStudyKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:31102:4"), r.findKeyByNumber("02").createStudyKeyOrder());
		
		Remoter rAir = (Remoter) rc.findDevByCoding("21");
		assertEquals(OrderHelper.getOrderMsg("CD10001:32101:4"), rAir.findKeyByNumber("01").createStudyKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:32102:4"), rAir.findKeyByNumber("02").createStudyKeyOrder());
		
		Remoter rCoutain = (Remoter) rc.findDevByCoding("31");
		assertEquals(OrderHelper.getOrderMsg("CD10001:33101:4"), rCoutain.findKeyByNumber("01").createStudyKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:33102:4"), rCoutain.findKeyByNumber("02").createStudyKeyOrder());
	}
	
	@Test
	public void testTest() {
		Remoter r = (Remoter) rc.findDevByCoding("11");
		assertEquals(OrderHelper.getOrderMsg("CD10001:31101:5"), r.findKeyByNumber("01").createTestKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:31102:5"), r.findKeyByNumber("02").createTestKeyOrder());
		
		Remoter rAir = (Remoter) rc.findDevByCoding("21");
		assertEquals(OrderHelper.getOrderMsg("CD10001:32101:5"), rAir.findKeyByNumber("01").createTestKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:32102:5"), rAir.findKeyByNumber("02").createTestKeyOrder());
		
		Remoter rCoutain = (Remoter) rc.findDevByCoding("31");
		assertEquals(OrderHelper.getOrderMsg("CD10001:33101:5"), rCoutain.findKeyByNumber("01").createTestKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:33102:5"), rCoutain.findKeyByNumber("02").createTestKeyOrder());
	}
	
	@Test
	public void testSave() {
		Remoter r = (Remoter) rc.findDevByCoding("11");
		assertEquals(OrderHelper.getOrderMsg("CD10001:31101:6"), r.findKeyByNumber("01").createSaveKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:31102:6"), r.findKeyByNumber("02").createSaveKeyOrder());
		
		Remoter rAir = (Remoter) rc.findDevByCoding("21");
		assertEquals(OrderHelper.getOrderMsg("CD10001:32101:6"), rAir.findKeyByNumber("01").createSaveKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:32102:6"), rAir.findKeyByNumber("02").createSaveKeyOrder());
		
		Remoter rCoutain = (Remoter) rc.findDevByCoding("31");
		assertEquals(OrderHelper.getOrderMsg("CD10001:33101:6"), rCoutain.findKeyByNumber("01").createSaveKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:33102:6"), rCoutain.findKeyByNumber("02").createSaveKeyOrder());
	}
	
	@Test
	public void testCtrl() {
		Remoter r = (Remoter) rc.findDevByCoding("11");
		assertEquals(OrderHelper.getOrderMsg("CD10001:31101:7"), r.findKeyByNumber("01").createCtrlKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:31102:7"), r.findKeyByNumber("02").createCtrlKeyOrder());
		
		Remoter rAir = (Remoter) rc.findDevByCoding("21");
		assertEquals(OrderHelper.getOrderMsg("CD10001:32101:7"), rAir.findKeyByNumber("01").createCtrlKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:32102:7"), rAir.findKeyByNumber("02").createCtrlKeyOrder());
		
		Remoter rCoutain = (Remoter) rc.findDevByCoding("31");
		assertEquals(OrderHelper.getOrderMsg("CD10001:33101:7"), rCoutain.findKeyByNumber("01").createCtrlKeyOrder());
		assertEquals(OrderHelper.getOrderMsg("CD10001:33102:7"), rCoutain.findKeyByNumber("02").createCtrlKeyOrder());
	}
	
	@Test
	public void testHandler() {
		rc.handle("31101:90");
		rc.handle("31102:91");
		rc.handle("32101:90");
		rc.handle("32102:93");
		rc.handle("33101:90");
		rc.handle("33102:93");
	}
	
	@Test
	public void testCreateRemoter() {
		Remoter r = rc.createRemoter("1");
		assertEquals("2", r.getSubCode());
		
		Remoter r1 = rc.createRemoter("2");
		rc.addChildDev(r1);
		assertEquals("2", r1.getSubCode());
		Remoter r2 = rc.createRemoter("2");
		assertEquals("3", r2.getSubCode());
	}
	
//	@Test
//	public void testCreateSubCode() {
//		assertEquals("2", rc.createSubCode("1"));
//		rc.addChildDev(new Remoter("1", "2"));
//		assertEquals("3", rc.createSubCode("1"));
//		rc.addChildDev(new Remoter("1", "3"));
//		
//		assertEquals("2", rc.createSubCode("2"));
//		rc.addChildDev(new Remoter("2", "2"));
//		assertEquals("3", rc.createSubCode("2"));
//		rc.addChildDev(new Remoter("2", "3"));
//		assertEquals("4", rc.createSubCode("2"));
//		rc.addChildDev(new Remoter("2", "4"));
//		
//		assertEquals("2", rc.createSubCode("3"));
//		rc.addChildDev(new Remoter("3", "2"));
//		assertEquals("3", rc.createSubCode("3"));
//		rc.addChildDev(new Remoter("3", "3"));
//		assertEquals("4", rc.createSubCode("3"));
//		rc.addChildDev(new Remoter("3", "4"));
//		assertEquals("5", rc.createSubCode("3"));
//		rc.addChildDev(new Remoter("3", "5"));
//		
//		assertEquals("4", rc.createSubCode("1"));
//		rc.addChildDev(new Remoter("1", "4"));
//	}
}
