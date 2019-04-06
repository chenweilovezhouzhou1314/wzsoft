package com.cw.auction.mapper.auctionDmb;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 
 * 系统代码表配置实体
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * Administrator 	1.0  2019年04月05日 Created
 *
 * </pre>
 * @since 1.
 */
public class AuctionDmb implements Serializable {
    private static final long serialVersionUID = 1301394002180956L;
    
    /***/
    private String zb;
    /***/
    private String dm;
    /***/
    private String dmmc;
    /***/
    private String bz1;
    /***/
    private String bz2;

    public String getZb() {
        return zb;
    }
    public void setZb(String zb) {
        this.zb = zb;
    }

    public String getDm() {
        return dm;
    }
    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getDmmc() {
        return dmmc;
    }
    public void setDmmc(String dmmc) {
        this.dmmc = dmmc;
    }

    public String getBz1() {
        return bz1;
    }
    public void setBz1(String bz1) {
        this.bz1 = bz1;
    }

    public String getBz2() {
        return bz2;
    }
    public void setBz2(String bz2) {
        this.bz2 = bz2;
    }



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}