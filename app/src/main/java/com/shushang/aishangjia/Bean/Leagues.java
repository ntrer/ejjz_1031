package com.shushang.aishangjia.Bean;

import java.util.List;

public class Leagues {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"merchantId":"402880b766854bd701668551f7aa000d","merchantCode":"testlm1","merchantName":"联盟1测试","merchantNum":65,"merchantNickName":"测试","shengCode":"130000","shiCode":"130100","quCode":"130101","shengName":"河北省","shiName":"石家庄市","quName":"市辖区","address":"asdf","telphone":"12435679870","managerphone":"13111111111","active":"1","startTime":1538323200000,"endTime":1541001599000,"industryType":"2","induttryCategoryName":"厨房设施","industryCategory":"18","industryCategorySubName":"整体橱柜","sortId":0,"type":"7","chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1539835034000,"xgsj":1539938435000,"del":"0","imageIds":"402880b766854bd701668551f213000c","imgaes":null,"merchantInfo":"sdf","merchantHours":"123","merchantVersion":"402880b7668a413a01668a4fdd83000a","versionStartDate":1538323200000,"versionEndDate":1541001599000,"merchants":null,"leagueTotal":88,"leagueDataEnable":"0"},{"merchantId":"402880b766800be40166815040990006","merchantCode":"testlm","merchantName":"测试联盟1黄河路","merchantNum":63,"merchantNickName":"黄河路","shengCode":"230000","shiCode":"230400","quCode":"230404","shengName":"黑龙江省","shiName":"鹤岗市","quName":"南山区","address":"朝阳路","telphone":"13111111111","managerphone":"13111111111","active":"1","startTime":1538323200000,"endTime":1540742399000,"industryType":"8","induttryCategoryName":"其它","industryCategory":"76","industryCategorySubName":"其它","sortId":0,"type":"7","chuangjianren":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1539767812000,"xgsj":1539933338000,"del":"0","imageIds":"402880b766800be40166814fcad60005","imgaes":null,"merchantInfo":"","merchantHours":"","merchantVersion":"3","versionStartDate":1538323200000,"versionEndDate":1540742399000,"merchants":null,"leagueTotal":-4999872,"leagueDataEnable":null}]
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
     */

    private String ret;
    private String msg;
    private Object data;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;
    private List<DataListBean> dataList;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getIntcurrentPage() {
        return intcurrentPage;
    }

    public void setIntcurrentPage(int intcurrentPage) {
        this.intcurrentPage = intcurrentPage;
    }

    public int getIntpageSize() {
        return intpageSize;
    }

    public void setIntpageSize(int intpageSize) {
        this.intpageSize = intpageSize;
    }

    public int getIntmaxCount() {
        return intmaxCount;
    }

    public void setIntmaxCount(int intmaxCount) {
        this.intmaxCount = intmaxCount;
    }

    public int getIntmaxPage() {
        return intmaxPage;
    }

    public void setIntmaxPage(int intmaxPage) {
        this.intmaxPage = intmaxPage;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * merchantId : 402880b766854bd701668551f7aa000d
         * merchantCode : testlm1
         * merchantName : 联盟1测试
         * merchantNum : 65
         * merchantNickName : 测试
         * shengCode : 130000
         * shiCode : 130100
         * quCode : 130101
         * shengName : 河北省
         * shiName : 石家庄市
         * quName : 市辖区
         * address : asdf
         * telphone : 12435679870
         * managerphone : 13111111111
         * active : 1
         * startTime : 1538323200000
         * endTime : 1541001599000
         * industryType : 2
         * induttryCategoryName : 厨房设施
         * industryCategory : 18
         * industryCategorySubName : 整体橱柜
         * sortId : 0
         * type : 7
         * chuangjianren : asdfghjkl123qwertg
         * xiugairen : asdfghjkl123qwertg
         * cjsj : 1539835034000
         * xgsj : 1539938435000
         * del : 0
         * imageIds : 402880b766854bd701668551f213000c
         * imgaes : null
         * merchantInfo : sdf
         * merchantHours : 123
         * merchantVersion : 402880b7668a413a01668a4fdd83000a
         * versionStartDate : 1538323200000
         * versionEndDate : 1541001599000
         * merchants : null
         * leagueTotal : 88.0
         * leagueDataEnable : 0
         */

        private String merchantId;
        private String merchantCode;
        private String merchantName;
        private int merchantNum;
        private String merchantNickName;
        private String shengCode;
        private String shiCode;
        private String quCode;
        private String shengName;
        private String shiName;
        private String quName;
        private String address;
        private String telphone;
        private String managerphone;
        private String active;
        private long startTime;
        private long endTime;
        private String industryType;
        private String induttryCategoryName;
        private String industryCategory;
        private String industryCategorySubName;
        private int sortId;
        private String type;
        private String chuangjianren;
        private String xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String imageIds;
        private Object imgaes;
        private String merchantInfo;
        private String merchantHours;
        private String merchantVersion;
        private long versionStartDate;
        private long versionEndDate;
        private Object merchants;
        private double leagueTotal;
        private String leagueDataEnable;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getMerchantCode() {
            return merchantCode;
        }

        public void setMerchantCode(String merchantCode) {
            this.merchantCode = merchantCode;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public int getMerchantNum() {
            return merchantNum;
        }

        public void setMerchantNum(int merchantNum) {
            this.merchantNum = merchantNum;
        }

        public String getMerchantNickName() {
            return merchantNickName;
        }

        public void setMerchantNickName(String merchantNickName) {
            this.merchantNickName = merchantNickName;
        }

        public String getShengCode() {
            return shengCode;
        }

        public void setShengCode(String shengCode) {
            this.shengCode = shengCode;
        }

        public String getShiCode() {
            return shiCode;
        }

        public void setShiCode(String shiCode) {
            this.shiCode = shiCode;
        }

        public String getQuCode() {
            return quCode;
        }

        public void setQuCode(String quCode) {
            this.quCode = quCode;
        }

        public String getShengName() {
            return shengName;
        }

        public void setShengName(String shengName) {
            this.shengName = shengName;
        }

        public String getShiName() {
            return shiName;
        }

        public void setShiName(String shiName) {
            this.shiName = shiName;
        }

        public String getQuName() {
            return quName;
        }

        public void setQuName(String quName) {
            this.quName = quName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTelphone() {
            return telphone;
        }

        public void setTelphone(String telphone) {
            this.telphone = telphone;
        }

        public String getManagerphone() {
            return managerphone;
        }

        public void setManagerphone(String managerphone) {
            this.managerphone = managerphone;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getIndustryType() {
            return industryType;
        }

        public void setIndustryType(String industryType) {
            this.industryType = industryType;
        }

        public String getInduttryCategoryName() {
            return induttryCategoryName;
        }

        public void setInduttryCategoryName(String induttryCategoryName) {
            this.induttryCategoryName = induttryCategoryName;
        }

        public String getIndustryCategory() {
            return industryCategory;
        }

        public void setIndustryCategory(String industryCategory) {
            this.industryCategory = industryCategory;
        }

        public String getIndustryCategorySubName() {
            return industryCategorySubName;
        }

        public void setIndustryCategorySubName(String industryCategorySubName) {
            this.industryCategorySubName = industryCategorySubName;
        }

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getChuangjianren() {
            return chuangjianren;
        }

        public void setChuangjianren(String chuangjianren) {
            this.chuangjianren = chuangjianren;
        }

        public String getXiugairen() {
            return xiugairen;
        }

        public void setXiugairen(String xiugairen) {
            this.xiugairen = xiugairen;
        }

        public long getCjsj() {
            return cjsj;
        }

        public void setCjsj(long cjsj) {
            this.cjsj = cjsj;
        }

        public long getXgsj() {
            return xgsj;
        }

        public void setXgsj(long xgsj) {
            this.xgsj = xgsj;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getImageIds() {
            return imageIds;
        }

        public void setImageIds(String imageIds) {
            this.imageIds = imageIds;
        }

        public Object getImgaes() {
            return imgaes;
        }

        public void setImgaes(Object imgaes) {
            this.imgaes = imgaes;
        }

        public String getMerchantInfo() {
            return merchantInfo;
        }

        public void setMerchantInfo(String merchantInfo) {
            this.merchantInfo = merchantInfo;
        }

        public String getMerchantHours() {
            return merchantHours;
        }

        public void setMerchantHours(String merchantHours) {
            this.merchantHours = merchantHours;
        }

        public String getMerchantVersion() {
            return merchantVersion;
        }

        public void setMerchantVersion(String merchantVersion) {
            this.merchantVersion = merchantVersion;
        }

        public long getVersionStartDate() {
            return versionStartDate;
        }

        public void setVersionStartDate(long versionStartDate) {
            this.versionStartDate = versionStartDate;
        }

        public long getVersionEndDate() {
            return versionEndDate;
        }

        public void setVersionEndDate(long versionEndDate) {
            this.versionEndDate = versionEndDate;
        }

        public Object getMerchants() {
            return merchants;
        }

        public void setMerchants(Object merchants) {
            this.merchants = merchants;
        }

        public double getLeagueTotal() {
            return leagueTotal;
        }

        public void setLeagueTotal(double leagueTotal) {
            this.leagueTotal = leagueTotal;
        }

        public String getLeagueDataEnable() {
            return leagueDataEnable;
        }

        public void setLeagueDataEnable(String leagueDataEnable) {
            this.leagueDataEnable = leagueDataEnable;
        }
    }
}
