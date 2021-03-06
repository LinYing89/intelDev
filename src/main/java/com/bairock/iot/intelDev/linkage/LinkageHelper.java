package com.bairock.iot.intelDev.linkage;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.bairock.iot.intelDev.device.DevHaveChild;
import com.bairock.iot.intelDev.device.Device;
import com.bairock.iot.intelDev.linkage.loop.LoopHolder;
import com.bairock.iot.intelDev.linkage.timing.TimingHolder;
import com.bairock.iot.intelDev.user.IntelDevHelper;

/**
 * 
 * @author LinQiang
 *
 */
public class LinkageHelper {

    private static LinkageHelper ins;
    private ChainHolder chain;
    private TimingHolder timing;
    private LoopHolder loop;

    private CheckLinkageThread checkLinkageThread;
    private OnRemovedListener onRemovedListener;

    /**
     * 
     */
    private LinkageHelper() {
        chain = new ChainHolder();
        timing = new TimingHolder();
        loop = new LoopHolder();
    }

    /**
     * 
     * @return
     */
    public static LinkageHelper getIns() {
        if (null == ins) {
            ins = new LinkageHelper();
        }
        return ins;
    }

    /**
     * 
     * @return
     */
    public ChainHolder getChain() {
        return chain;
    }

    /**
     * 
     * @param chain
     */
    public void setChain(ChainHolder chain) {
        this.chain = chain;
    }

    /**
     * 
     * @return
     */
    public TimingHolder getTiming() {
        return timing;
    }

    /**
     * 
     * @param timing
     */
    public void setTiming(TimingHolder timing) {
        this.timing = timing;
    }

    /**
     * 
     * @return
     */
    public LoopHolder getLoop() {
        return loop;
    }

    /**
     * 
     * @param loop
     */
    public void setLoop(LoopHolder loop) {
        this.loop = loop;
    }

    public OnRemovedListener getOnRemovedListener() {
        return onRemovedListener;
    }

    public void setOnRemovedListener(OnRemovedListener onRemovedListener) {
        this.onRemovedListener = onRemovedListener;
    }

    /**
     * 
     */
    public void startCheckLinkageThread() {
        if (null != checkLinkageThread && checkLinkageThread.isRunning) {
            return;
        }
        checkLinkageThread = new CheckLinkageThread();
        checkLinkageThread.isRunning = true;
        IntelDevHelper.executeThread(checkLinkageThread);
        ;
    }

    /**
     * 
     */
    public void stopCheckLinkageThread() {
        if (null != checkLinkageThread) {
            checkLinkageThread.interrupt();
            if (checkLinkageThread.isRunning) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
//			checkLinkageThread.isRunning = false;
            checkLinkageThread = null;
        }
    }
    
    public void removeDevice(Device device) {
        removeDevice(device, chain);
        removeDevice(device, timing);
        removeDevice(device, loop);
    }

    private boolean compareDeviceAndChild(Device sourceDevice, Device targerDevice) {
        if(targerDevice instanceof DevHaveChild) {
            for(Device dev : ((DevHaveChild) targerDevice).getListDev()) {
                boolean res = compareDeviceAndChild(sourceDevice, dev);
                if(res) {
                    return true;
                }
            }
            return false;
        }else {
            return sourceDevice.getId().equals(targerDevice.getId());
        }
    }
    
    public void removeDevice(Device device, LinkageHolder linkageHolder) {
        for (Linkage linkage : linkageHolder.getListLinkage()) {
            //删除条件设备
            if (linkage instanceof SubChain) {
                SubChain subChain = ((SubChain) linkage);
                Iterator<LinkageCondition> iterator = subChain.getListCondition().iterator();
                if (iterator.hasNext()) {
                    LinkageCondition lc = iterator.next();
                    if (compareDeviceAndChild(lc.getDevice(), device)) {
                        lc.setDevice(null);
                        iterator.remove();
                        if(onRemovedListener != null) {
                            onRemovedListener.onLinkageConditionRemoved(lc);
                        }
                    }
                }
            }
            //删除影响设备
            Iterator<Effect> iterator = linkage.getListEffect().iterator();
            if (iterator.hasNext()) {
                Effect effect = iterator.next();
                if (compareDeviceAndChild(effect.getDevice(), device)) {
                    effect.setDevice(null);
                    iterator.remove();
                    if(onRemovedListener != null) {
                        onRemovedListener.onEffectRemoved(effect);
                    }
                }
            }
        }
    }

    /**
     * 
     * @author LinQiang
     *
     */
    class CheckLinkageThread extends Thread {

        boolean isRunning = false;

        @Override
        public void run() {
            isRunning = true;
            while (!Thread.interrupted()) {
                try {
                    if (null == loop || null == chain || null == timing) {
                        Thread.sleep(1000);
                        continue;
                    }
                    loop.run();

                    // on the first, set chain and timing temporary mark to -1
                    for (LinkageTabRow deviceChain : LinkageTab.getIns().getListLinkageTabRow()) {
                        deviceChain.initIChainTem();
                    }

                    chain.run();

                    timing.run();

                    // on the end, set temporary value to the ensure value
                    for (LinkageTabRow tabRow : LinkageTab.getIns().getListLinkageTabRow()) {
                        // if temporary value is -1, not change the ensure value
                        if (tabRow.getChainTem() != -1) {
                            tabRow.setChain(tabRow.getChainTem());
                        }
                        tabRow.setTiming(tabRow.getTimingTem());
                        // System.out.println("LinkageHelper run item: " + tabRow.getITemString());
                    }

                    LinkageTab.getIns().checkTabRows();

//					for (LinkageTabRow tabRow : 
//						LinkageTab.getIns().getListLinkageTabRow()) {
//						System.out.println("LinkageHelper run: " + tabRow.getITemString());
//					}

                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception ex) {
                    isRunning = false;
                    ex.printStackTrace();
                }
            }
            isRunning = false;
        }

    }
}
