package com.xys.libzxing.zxing.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ActivityBean {

    @Id(autoincrement = true)
    private Long id;
    private String active;
    private int activityCode;
    private String activityId;
    private String activityName;
    private String chuangjianren;
    private long cjsj;
    private String coverImageId;
    private String del;
    private long eventEnd;
    private long eventStart;
    private String isUnderLine;
    private String merchantId;
    private String qdjpname;
    private long sceneEnd;
    private long sceneStart;
    private long sellCardEnd;
    private long sellCardStart;
    private String shengCode;
    private String shengName;
    private String shiCode;
    private String shiName;
    private long xgsj;
    private String xiugairen;
    private String yxjpname;
    @Generated(hash = 216790468)
    public ActivityBean(Long id, String active, int activityCode, String activityId,
            String activityName, String chuangjianren, long cjsj,
            String coverImageId, String del, long eventEnd, long eventStart,
            String isUnderLine, String merchantId, String qdjpname, long sceneEnd,
            long sceneStart, long sellCardEnd, long sellCardStart, String shengCode,
            String shengName, String shiCode, String shiName, long xgsj,
            String xiugairen, String yxjpname) {
        this.id = id;
        this.active = active;
        this.activityCode = activityCode;
        this.activityId = activityId;
        this.activityName = activityName;
        this.chuangjianren = chuangjianren;
        this.cjsj = cjsj;
        this.coverImageId = coverImageId;
        this.del = del;
        this.eventEnd = eventEnd;
        this.eventStart = eventStart;
        this.isUnderLine = isUnderLine;
        this.merchantId = merchantId;
        this.qdjpname = qdjpname;
        this.sceneEnd = sceneEnd;
        this.sceneStart = sceneStart;
        this.sellCardEnd = sellCardEnd;
        this.sellCardStart = sellCardStart;
        this.shengCode = shengCode;
        this.shengName = shengName;
        this.shiCode = shiCode;
        this.shiName = shiName;
        this.xgsj = xgsj;
        this.xiugairen = xiugairen;
        this.yxjpname = yxjpname;
    }
    @Generated(hash = 513267866)
    public ActivityBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getActive() {
        return this.active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    public int getActivityCode() {
        return this.activityCode;
    }
    public void setActivityCode(int activityCode) {
        this.activityCode = activityCode;
    }
    public String getActivityId() {
        return this.activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getActivityName() {
        return this.activityName;
    }
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    public String getChuangjianren() {
        return this.chuangjianren;
    }
    public void setChuangjianren(String chuangjianren) {
        this.chuangjianren = chuangjianren;
    }
    public long getCjsj() {
        return this.cjsj;
    }
    public void setCjsj(long cjsj) {
        this.cjsj = cjsj;
    }
    public String getCoverImageId() {
        return this.coverImageId;
    }
    public void setCoverImageId(String coverImageId) {
        this.coverImageId = coverImageId;
    }
    public String getDel() {
        return this.del;
    }
    public void setDel(String del) {
        this.del = del;
    }
    public long getEventEnd() {
        return this.eventEnd;
    }
    public void setEventEnd(long eventEnd) {
        this.eventEnd = eventEnd;
    }
    public long getEventStart() {
        return this.eventStart;
    }
    public void setEventStart(long eventStart) {
        this.eventStart = eventStart;
    }
    public String getIsUnderLine() {
        return this.isUnderLine;
    }
    public void setIsUnderLine(String isUnderLine) {
        this.isUnderLine = isUnderLine;
    }
    public String getMerchantId() {
        return this.merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    public String getQdjpname() {
        return this.qdjpname;
    }
    public void setQdjpname(String qdjpname) {
        this.qdjpname = qdjpname;
    }
    public long getSceneEnd() {
        return this.sceneEnd;
    }
    public void setSceneEnd(long sceneEnd) {
        this.sceneEnd = sceneEnd;
    }
    public long getSceneStart() {
        return this.sceneStart;
    }
    public void setSceneStart(long sceneStart) {
        this.sceneStart = sceneStart;
    }
    public long getSellCardEnd() {
        return this.sellCardEnd;
    }
    public void setSellCardEnd(long sellCardEnd) {
        this.sellCardEnd = sellCardEnd;
    }
    public long getSellCardStart() {
        return this.sellCardStart;
    }
    public void setSellCardStart(long sellCardStart) {
        this.sellCardStart = sellCardStart;
    }
    public String getShengCode() {
        return this.shengCode;
    }
    public void setShengCode(String shengCode) {
        this.shengCode = shengCode;
    }
    public String getShengName() {
        return this.shengName;
    }
    public void setShengName(String shengName) {
        this.shengName = shengName;
    }
    public String getShiCode() {
        return this.shiCode;
    }
    public void setShiCode(String shiCode) {
        this.shiCode = shiCode;
    }
    public String getShiName() {
        return this.shiName;
    }
    public void setShiName(String shiName) {
        this.shiName = shiName;
    }
    public long getXgsj() {
        return this.xgsj;
    }
    public void setXgsj(long xgsj) {
        this.xgsj = xgsj;
    }
    public String getXiugairen() {
        return this.xiugairen;
    }
    public void setXiugairen(String xiugairen) {
        this.xiugairen = xiugairen;
    }
    public String getYxjpname() {
        return this.yxjpname;
    }
    public void setYxjpname(String yxjpname) {
        this.yxjpname = yxjpname;
    }



}
