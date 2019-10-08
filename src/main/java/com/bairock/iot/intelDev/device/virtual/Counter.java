package com.bairock.iot.intelDev.device.virtual;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.bairock.iot.intelDev.device.MainCodeHelper;

/**
 * 计数器
 * @author 44489
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Counter")
public class Counter extends DevParam {

	public Counter() {
		this(MainCodeHelper.VT_COUNTER, "");
	}

	public Counter(String mcId, String sc) {
		super(mcId, sc);
	}
	
	@Override
    public String getValue() {
        if(this.value == null || this.value.equals("")) {
            this.value = "0";
        }
        return value;
    }
	
	/**
	 * and 1 number
	 */
	public void and1() {
		String value = getValue();
		if(null == value || this.value.equals("")) {
			setValue("1");
		}else {
		    setValue(String.valueOf(Integer.parseInt(value) + 1));
		}
	}
	
	/**
	 * minus 1 number
	 */
	public void minus1() {
	    String value = getValue();
        if(null == value || this.value.equals("")) {
            setValue("-1");
        }else {
            setValue(String.valueOf(Integer.parseInt(value) - 1));
        }
	}

}
