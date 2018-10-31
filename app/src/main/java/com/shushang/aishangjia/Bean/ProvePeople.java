package com.shushang.aishangjia.Bean;

import java.util.List;

public class ProvePeople {


    /**
     * ret : 200
     * msg : success
     * data : null
     * dataList : [{"userId":"402880b76627e96c016627f06eb90012","userAccount":"sadmin","userPassword":"E10ADC3949BA59ABBE56E057F20F883E","userName":"超级管理员","leixing":"2","xingbie":null,"shengCode":"110000","shengName":null,"shiCode":"110100","shiName":null,"quCode":"110112","quName":null,"dizhi":"朝阳路","youxiang":null,"shoujihao":"13966555756","touxiang":null,"chengzhangzhiId":null,"dengji":null,"chengzhangzhi":null,"zxzk":null,"isVip":null,"hyzk":null,"isDisable":"1","ip":null,"caoZuoRen":"asdfghjkl123qwertg","xiugairen":null,"cjsj":1538268360000,"xgsj":1538268360000,"del":"0","merchantId":"402880b76627e96c016627f06eb20011","merchantCode":"tsj1","activity_id":null,"merchantName":"t商家1黄河路","roles":null,"merchant":null,"resources":null},{"userId":"402880b76627e96c016627f142650019","userAccount":"sadmin","userPassword":"E10ADC3949BA59ABBE56E057F20F883E","userName":"超级管理员","leixing":"2","xingbie":null,"shengCode":"110000","shengName":null,"shiCode":"110100","shiName":null,"quCode":"110112","quName":null,"dizhi":"朝阳路","youxiang":null,"shoujihao":"13966555756","touxiang":null,"chengzhangzhiId":null,"dengji":null,"chengzhangzhi":null,"zxzk":null,"isVip":null,"hyzk":null,"isDisable":"1","ip":null,"caoZuoRen":"asdfghjkl123qwertg","xiugairen":null,"cjsj":1538268415000,"xgsj":1538268415000,"del":"0","merchantId":"402880b76627e96c016627f1425f0018","merchantCode":"tsj2","activity_id":null,"merchantName":"t商家2黄河路","roles":null,"merchant":null,"resources":null},{"userId":"402880b766280b5f0166280d3ef00006","userAccount":"chenduxiu","userPassword":"E10ADC3949BA59ABBE56E057F20F883E","userName":"陈独秀","leixing":"2","xingbie":null,"shengCode":null,"shengName":null,"shiCode":null,"shiName":null,"quCode":null,"quName":null,"dizhi":null,"youxiang":null,"shoujihao":"13532432267","touxiang":null,"chengzhangzhiId":null,"dengji":null,"chengzhangzhi":null,"zxzk":null,"isVip":null,"hyzk":null,"isDisable":"1","ip":null,"caoZuoRen":"402880b76627e96c016627f06eb90012","xiugairen":null,"cjsj":1538270249000,"xgsj":1538270263000,"del":"0","merchantId":"402880b76627e96c016627f06eb20011","merchantCode":"tsj1","activity_id":null,"merchantName":"t商家1黄河路","roles":null,"merchant":null,"resources":null},{"userId":"402880b7667a706101667aef728000be","userAccount":"lidazhao","userPassword":"E10ADC3949BA59ABBE56E057F20F883E","userName":"李大钊","leixing":"2","xingbie":null,"shengCode":null,"shengName":null,"shiCode":null,"shiName":null,"quCode":null,"quName":null,"dizhi":null,"youxiang":null,"shoujihao":"13199999999","touxiang":null,"chengzhangzhiId":null,"dengji":null,"chengzhangzhi":null,"zxzk":null,"isVip":null,"hyzk":null,"isDisable":"1","ip":null,"caoZuoRen":"402880b76627e96c016627f142650019","xiugairen":null,"cjsj":1539660805000,"xgsj":1539660805000,"del":"0","merchantId":"402880b76627e96c016627f1425f0018","merchantCode":"tsj2","activity_id":null,"merchantName":"t商家2黄河路","roles":null,"merchant":null,"resources":null}]
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
         * userId : 402880b76627e96c016627f06eb90012
         * userAccount : sadmin
         * userPassword : E10ADC3949BA59ABBE56E057F20F883E
         * userName : 超级管理员
         * leixing : 2
         * xingbie : null
         * shengCode : 110000
         * shengName : null
         * shiCode : 110100
         * shiName : null
         * quCode : 110112
         * quName : null
         * dizhi : 朝阳路
         * youxiang : null
         * shoujihao : 13966555756
         * touxiang : null
         * chengzhangzhiId : null
         * dengji : null
         * chengzhangzhi : null
         * zxzk : null
         * isVip : null
         * hyzk : null
         * isDisable : 1
         * ip : null
         * caoZuoRen : asdfghjkl123qwertg
         * xiugairen : null
         * cjsj : 1538268360000
         * xgsj : 1538268360000
         * del : 0
         * merchantId : 402880b76627e96c016627f06eb20011
         * merchantCode : tsj1
         * activity_id : null
         * merchantName : t商家1黄河路
         * roles : null
         * merchant : null
         * resources : null
         */

        private String userId;
        private String userAccount;
        private String userPassword;
        private String userName;
        private String leixing;
        private Object xingbie;
        private String shengCode;
        private Object shengName;
        private String shiCode;
        private Object shiName;
        private String quCode;
        private Object quName;
        private String dizhi;
        private Object youxiang;
        private String shoujihao;
        private Object touxiang;
        private Object chengzhangzhiId;
        private Object dengji;
        private Object chengzhangzhi;
        private Object zxzk;
        private Object isVip;
        private Object hyzk;
        private String isDisable;
        private Object ip;
        private String caoZuoRen;
        private Object xiugairen;
        private long cjsj;
        private long xgsj;
        private String del;
        private String merchantId;
        private String merchantCode;
        private Object activity_id;
        private String merchantName;
        private Object roles;
        private Object merchant;
        private Object resources;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserAccount() {
            return userAccount;
        }

        public void setUserAccount(String userAccount) {
            this.userAccount = userAccount;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLeixing() {
            return leixing;
        }

        public void setLeixing(String leixing) {
            this.leixing = leixing;
        }

        public Object getXingbie() {
            return xingbie;
        }

        public void setXingbie(Object xingbie) {
            this.xingbie = xingbie;
        }

        public String getShengCode() {
            return shengCode;
        }

        public void setShengCode(String shengCode) {
            this.shengCode = shengCode;
        }

        public Object getShengName() {
            return shengName;
        }

        public void setShengName(Object shengName) {
            this.shengName = shengName;
        }

        public String getShiCode() {
            return shiCode;
        }

        public void setShiCode(String shiCode) {
            this.shiCode = shiCode;
        }

        public Object getShiName() {
            return shiName;
        }

        public void setShiName(Object shiName) {
            this.shiName = shiName;
        }

        public String getQuCode() {
            return quCode;
        }

        public void setQuCode(String quCode) {
            this.quCode = quCode;
        }

        public Object getQuName() {
            return quName;
        }

        public void setQuName(Object quName) {
            this.quName = quName;
        }

        public String getDizhi() {
            return dizhi;
        }

        public void setDizhi(String dizhi) {
            this.dizhi = dizhi;
        }

        public Object getYouxiang() {
            return youxiang;
        }

        public void setYouxiang(Object youxiang) {
            this.youxiang = youxiang;
        }

        public String getShoujihao() {
            return shoujihao;
        }

        public void setShoujihao(String shoujihao) {
            this.shoujihao = shoujihao;
        }

        public Object getTouxiang() {
            return touxiang;
        }

        public void setTouxiang(Object touxiang) {
            this.touxiang = touxiang;
        }

        public Object getChengzhangzhiId() {
            return chengzhangzhiId;
        }

        public void setChengzhangzhiId(Object chengzhangzhiId) {
            this.chengzhangzhiId = chengzhangzhiId;
        }

        public Object getDengji() {
            return dengji;
        }

        public void setDengji(Object dengji) {
            this.dengji = dengji;
        }

        public Object getChengzhangzhi() {
            return chengzhangzhi;
        }

        public void setChengzhangzhi(Object chengzhangzhi) {
            this.chengzhangzhi = chengzhangzhi;
        }

        public Object getZxzk() {
            return zxzk;
        }

        public void setZxzk(Object zxzk) {
            this.zxzk = zxzk;
        }

        public Object getIsVip() {
            return isVip;
        }

        public void setIsVip(Object isVip) {
            this.isVip = isVip;
        }

        public Object getHyzk() {
            return hyzk;
        }

        public void setHyzk(Object hyzk) {
            this.hyzk = hyzk;
        }

        public String getIsDisable() {
            return isDisable;
        }

        public void setIsDisable(String isDisable) {
            this.isDisable = isDisable;
        }

        public Object getIp() {
            return ip;
        }

        public void setIp(Object ip) {
            this.ip = ip;
        }

        public String getCaoZuoRen() {
            return caoZuoRen;
        }

        public void setCaoZuoRen(String caoZuoRen) {
            this.caoZuoRen = caoZuoRen;
        }

        public Object getXiugairen() {
            return xiugairen;
        }

        public void setXiugairen(Object xiugairen) {
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

        public Object getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(Object activity_id) {
            this.activity_id = activity_id;
        }

        public String getMerchantName() {
            return merchantName;
        }

        public void setMerchantName(String merchantName) {
            this.merchantName = merchantName;
        }

        public Object getRoles() {
            return roles;
        }

        public void setRoles(Object roles) {
            this.roles = roles;
        }

        public Object getMerchant() {
            return merchant;
        }

        public void setMerchant(Object merchant) {
            this.merchant = merchant;
        }

        public Object getResources() {
            return resources;
        }

        public void setResources(Object resources) {
            this.resources = resources;
        }
    }
}
