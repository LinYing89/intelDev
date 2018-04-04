package com.bairock.iot.intelDev.linkage.loop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bairock.iot.intelDev.device.DevStateHelper;
import com.bairock.iot.intelDev.linkage.SubChain;
import com.bairock.iot.intelDev.linkage.LinkageTab;
import com.bairock.iot.intelDev.user.IntelDevHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("ZLoop")
public class ZLoop extends SubChain{

	private int loopCount;
	
	@OneToMany(mappedBy="zLoop", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("zloop_loopduration")
	private List<LoopDuration> listLoopDuration;
	
	@Transient
	@JsonIgnore
	private LoopThread loopThread;
	
	@Transient
	@JsonIgnore
	private int loopCountThread;
	
	public ZLoop() {
		super();
		listLoopDuration = Collections.synchronizedList(new ArrayList<LoopDuration>());
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLoopCount() {
		return loopCount;
	}

	/**
	 * 
	 * @param loopCount
	 */
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
		loopCountThread = loopCount;
	}

	/**
	 * 
	 * @return
	 */
	public List<LoopDuration> getListLoopDuration() {
		return listLoopDuration;
	}

	/**
	 * 
	 * @param listLoopDuration
	 */
	public void setListLoopDuration(List<LoopDuration> listLoopDuration) {
		this.listLoopDuration = listLoopDuration;
		for(LoopDuration ld : listLoopDuration) {
			ld.setzLoop(this);
		}
	}
	
	/**
	 * 
	 * @param loopDuration
	 */
	public void addLoopDuration(LoopDuration loopDuration){
		if(null != loopDuration 
				&& !listLoopDuration.contains(loopDuration)){
			loopDuration.setzLoop(this);
			listLoopDuration.add(loopDuration);
		}
	}
	
	/**
	 * 
	 * @param loopDuration
	 * @return
	 */
	public boolean removeLoopDuration(LoopDuration loopDuration){
		if(null == loopDuration){
			return false;
		}
		loopDuration.setzLoop(null);
		return listLoopDuration.remove(loopDuration);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public LoopDuration removeLoopDuration(int index){
		LoopDuration ld = listLoopDuration.remove(index);
		if(null != ld) {
			ld.setzLoop(null);
		}
		return ld;
	}
	
	/**
	 * 
	 * @param dsId
	 */
	public void execute(String dsId){
		effectLinkageTab(LinkageTab.LOOP, dsId);
	}
	
	/**
	 * 
	 */
	@Override
	public void run(){
		if(listLoopDuration.isEmpty() || getListEffect().isEmpty()){
			return;
		}
		if(loopCount != -1 && loopCount <= 0){
			return;
		}
		
		if(getConditionResult()){
//			if(loopThread != null) {
//				System.out.println("ZLoop run loopThread.isAlive(): " + loopThread.isAlive());
//			}else {
//				System.out.println("ZLoop run loopThread == null: ");
//			}
			if(loopCountThread != -1 && loopCountThread <= 0) {
				return;
			}
			if(loopThread != null && loopThread.isRunnung){
				return;
			}
			//System.out.println("ZLoop run create loop thread");
			loopThread = new LoopThread();
			IntelDevHelper.executeThread(loopThread);
		}else {
			loopCountThread = loopCount;
		}
	}
	
	/**
	 * 
	 * @author LinQiang
	 *
	 */
	class LoopThread extends Thread{

		boolean isRunnung = false;
		@Override
		public void run() {
			isRunnung = true;
			if(listLoopDuration.isEmpty()){
				isRunnung = false;
				return;
			}
			
			try{
				//int count = loopCountThread;
				int i = 0;
				LoopDuration loopDuration;
				while(isEnable() && getConditionResult()){
					if(loopCountThread != -1 && loopCountThread <= 0){
						break;
					}
					synchronized (listLoopDuration) {
						if (i >= listLoopDuration.size()) {
							i = 0;
						}
						loopDuration = listLoopDuration.get(i);
					}
					execute(DevStateHelper.DS_KAI);
//					System.out.println("LoopThread" + DevStateHelper.DS_KAI + " sleep " 
//							+ loopDuration.getOnKeepTime().getSec());
					TimeUnit.SECONDS.sleep(loopDuration.getOnKeepTime().getSec());
					if(!isEnable()){
						break;
					}
					
					execute(DevStateHelper.DS_GUAN);
//					System.out.println("LoopThread" + DevStateHelper.DS_GUAN + " sleep " 
//							+ loopDuration.getOffKeepTime().getSec());
					TimeUnit.SECONDS.sleep(loopDuration.getOffKeepTime().getSec());
					if(loopCountThread != -1){
						loopCountThread--;
					}
					i++;
				}
			}catch(Exception e){
				e.printStackTrace();
				//setRunning(false);
			}finally{
				execute(null);
			}
			isRunnung = false;
			//System.out.println("ZLoop LoopThread:run end");
		}
		
	}

}
