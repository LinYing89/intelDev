package com.bairock.iot.intelDev.linkage.timing;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author LinQiang
 *
 */
@Entity
public class WeekHelper {

	@Id
	@Column(nullable = false)
	private String id;
	
	@OneToOne
	@JsonBackReference("ztimer_weekhelper")
	private ZTimer zTimer;
	
	@Transient
	@JsonIgnore
	public static String[] ARRAY_WEEKS = new String[]{"Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "sat"};
    
    private boolean sun;
    private boolean mon;
    private boolean tues;
    private boolean wed;
    private boolean thur;
    private boolean fri;
    private boolean sat;
    
	public WeekHelper() {
		id = UUID.randomUUID().toString();
	}

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public ZTimer getzTimer() {
		return zTimer;
	}

	public void setzTimer(ZTimer zTimer) {
		this.zTimer = zTimer;
	}


	public boolean isSun() {
		return sun;
	}


	public void setSun(boolean sun) {
		this.sun = sun;
	}


	public boolean isMon() {
		return mon;
	}


	public void setMon(boolean mon) {
		this.mon = mon;
	}


	public boolean isTues() {
		return tues;
	}


	public void setTues(boolean tues) {
		this.tues = tues;
	}


	public boolean isWed() {
		return wed;
	}


	public void setWed(boolean wed) {
		this.wed = wed;
	}


	public boolean isThur() {
		return thur;
	}


	public void setThur(boolean thur) {
		this.thur = thur;
	}


	public boolean isFri() {
		return fri;
	}


	public void setFri(boolean fri) {
		this.fri = fri;
	}


	public boolean isSat() {
		return sat;
	}


	public void setSat(boolean sat) {
		this.sat = sat;
	}

	/**
	 * 
	 * @param weekNum
	 * @return
	 */
	@JsonIgnore
	public boolean isSelected(int weekNum){
		switch(weekNum){
		case 0:
			return sun;
		case 1:
			return mon;
		case 2:
			return tues;
		case 3:
			return wed;
		case 4:
			return thur;
		case 5:
			return fri;
		case 6:
			return sat;
		}
		return false;
	}
	
	/**
     * get the string number of weeks
     * @return
     */
	@JsonIgnore
    public String getWeeksNum () {
    	
        StringBuilder sb = new StringBuilder();
        if(sun){
        	sb.append(0);
        }
        if(mon){
        	sb.append(1);
        }
        if(tues){
        	sb.append(2);
        }
        if(wed){
        	sb.append(3);
        }
        if(thur){
        	sb.append(4);
        }
        if(fri){
        	sb.append(5);
        }
        if(sat){
        	sb.append(6);
        }
        return sb.toString();
    }
    
    /**
     * get the string of weeks which is English
     * split with space
     * @return
     */
    @JsonIgnore
    public String getWeeksName(){
    	StringBuilder sb = new StringBuilder();
    	if(sun){
        	sb.append(ARRAY_WEEKS[0]);
        }
        if(mon){
        	sb.append(ARRAY_WEEKS[1]);
        }
        if(tues){
        	sb.append(ARRAY_WEEKS[2]);
        }
        if(wed){
        	sb.append(ARRAY_WEEKS[3]);
        }
        if(thur){
        	sb.append(ARRAY_WEEKS[4]);
        }
        if(fri){
        	sb.append(ARRAY_WEEKS[5]);
        }
        if(sat){
        	sb.append(ARRAY_WEEKS[6]);
        }
    	return sb.toString();
    }
    
    /**
     * 
     */
    public void clean(){
    	sun = mon = tues= wed = thur = fri = sat = false;
    }
    
    /**
     * 
     * @param weeks
     */
    public void setWeeks(int...weeks){
    	clean();
    	for(int i : weeks){
    		switch(i){
    		case 0:
    			sun = true;
    			break;
    		case 1:
    			mon = true;
    			break;
    		case 2:
    			tues = true;
    			break;
    		case 3:
    			wed = true;
    			break;
    		case 4:
    			thur = true;
    			break;
    		case 5:
    			fri = true;
    			break;
    		case 6:
    			sat = true;
    			break;
    		}
    	}
    }
}
