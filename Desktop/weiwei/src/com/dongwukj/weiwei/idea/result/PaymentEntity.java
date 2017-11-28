package com.dongwukj.weiwei.idea.result;

import java.io.Serializable;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;

/**
 * Created by sunjaly on 15/4/16.
 */
@Table("paymentEntity")
public class PaymentEntity  implements Serializable{
	@PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    private long id;
    private String Key;
    private String Mid;
    private boolean ischecked;
    
    public boolean isIschecked() {
		return ischecked;
	}

	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getMid() {
        return Mid;
    }

    public void setMid(String mid) {
        Mid = mid;
    }

	@Override
	public String toString() {
		return "PaymentEntity [id=" + id + ", Key=" + Key + ", Mid=" + Mid
				+ "]";
	}

	
    
}
