package com.shushang.aishangjia.Bean;

import java.util.List;

/**
 * Created by YD on 2018/7/23.
 */

public class Login {


    /**
     * ret : 200
     * msg : success
     * data : {"token_id":"a40874dbdf8547b4b7378802dd41584f","xingming":"联盟超级管理员","shoujihao":"13111111111","touxiang":"","type":"7","shangjia_code":"testlm1","shangjia_id":"402880b766854bd701668551f7aa000d","shangjia_name":"联盟1测试","activity_id":null,"resources":[{"resourceId":"resource444444444444444resource","resourceNum":"2000","resourceType":null,"resourceName":"联盟超级权限","resourceIcon":null,"sortId":2000,"url":"联盟所有权限","ziyuanjibie":null,"ziyuanlujing":null,"groupName":null,"type":"7","beizhu":null,"enable":"1","isDelete":"0","ip":"192.168.0.117","caoZuoRen":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1539782045000,"xgsj":1539782045000,"merchantEnable":null,"companyEnable":null}],"leagueFlag":"1"}
     * dataList : null
     * intcurrentPage : 0
     * intpageSize : 0
     * intmaxCount : 0
     * intmaxPage : 0
     */

    private String ret;
    private String msg;
    private DataBean data;
    private Object dataList;
    private int intcurrentPage;
    private int intpageSize;
    private int intmaxCount;
    private int intmaxPage;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(Object dataList) {
        this.dataList = dataList;
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

    public static class DataBean {
        /**
         * token_id : a40874dbdf8547b4b7378802dd41584f
         * xingming : 联盟超级管理员
         * shoujihao : 13111111111
         * touxiang :
         * type : 7
         * shangjia_code : testlm1
         * shangjia_id : 402880b766854bd701668551f7aa000d
         * shangjia_name : 联盟1测试
         * activity_id : null
         * resources : [{"resourceId":"resource444444444444444resource","resourceNum":"2000","resourceType":null,"resourceName":"联盟超级权限","resourceIcon":null,"sortId":2000,"url":"联盟所有权限","ziyuanjibie":null,"ziyuanlujing":null,"groupName":null,"type":"7","beizhu":null,"enable":"1","isDelete":"0","ip":"192.168.0.117","caoZuoRen":"asdfghjkl123qwertg","xiugairen":"asdfghjkl123qwertg","cjsj":1539782045000,"xgsj":1539782045000,"merchantEnable":null,"companyEnable":null}]
         * leagueFlag : 1
         */

        private String token_id;
        private String xingming;
        private String shoujihao;
        private String touxiang;
        private String type;
        private String shangjia_code;
        private String shangjia_id;
        private String shangjia_name;
        private Object activity_id;
        private String leagueFlag;
        private List<ResourcesBean> resources;

        public String getToken_id() {
            return token_id;
        }

        public void setToken_id(String token_id) {
            this.token_id = token_id;
        }

        public String getXingming() {
            return xingming;
        }

        public void setXingming(String xingming) {
            this.xingming = xingming;
        }

        public String getShoujihao() {
            return shoujihao;
        }

        public void setShoujihao(String shoujihao) {
            this.shoujihao = shoujihao;
        }

        public String getTouxiang() {
            return touxiang;
        }

        public void setTouxiang(String touxiang) {
            this.touxiang = touxiang;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getShangjia_code() {
            return shangjia_code;
        }

        public void setShangjia_code(String shangjia_code) {
            this.shangjia_code = shangjia_code;
        }

        public String getShangjia_id() {
            return shangjia_id;
        }

        public void setShangjia_id(String shangjia_id) {
            this.shangjia_id = shangjia_id;
        }

        public String getShangjia_name() {
            return shangjia_name;
        }

        public void setShangjia_name(String shangjia_name) {
            this.shangjia_name = shangjia_name;
        }

        public Object getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(Object activity_id) {
            this.activity_id = activity_id;
        }

        public String getLeagueFlag() {
            return leagueFlag;
        }

        public void setLeagueFlag(String leagueFlag) {
            this.leagueFlag = leagueFlag;
        }

        public List<ResourcesBean> getResources() {
            return resources;
        }

        public void setResources(List<ResourcesBean> resources) {
            this.resources = resources;
        }

        public static class ResourcesBean {
            /**
             * resourceId : resource444444444444444resource
             * resourceNum : 2000
             * resourceType : null
             * resourceName : 联盟超级权限
             * resourceIcon : null
             * sortId : 2000
             * url : 联盟所有权限
             * ziyuanjibie : null
             * ziyuanlujing : null
             * groupName : null
             * type : 7
             * beizhu : null
             * enable : 1
             * isDelete : 0
             * ip : 192.168.0.117
             * caoZuoRen : asdfghjkl123qwertg
             * xiugairen : asdfghjkl123qwertg
             * cjsj : 1539782045000
             * xgsj : 1539782045000
             * merchantEnable : null
             * companyEnable : null
             */

            private String resourceId;
            private String resourceNum;
            private Object resourceType;
            private String resourceName;
            private Object resourceIcon;
            private int sortId;
            private String url;
            private Object ziyuanjibie;
            private Object ziyuanlujing;
            private Object groupName;
            private String type;
            private Object beizhu;
            private String enable;
            private String isDelete;
            private String ip;
            private String caoZuoRen;
            private String xiugairen;
            private long cjsj;
            private long xgsj;
            private Object merchantEnable;
            private Object companyEnable;

            public String getResourceId() {
                return resourceId;
            }

            public void setResourceId(String resourceId) {
                this.resourceId = resourceId;
            }

            public String getResourceNum() {
                return resourceNum;
            }

            public void setResourceNum(String resourceNum) {
                this.resourceNum = resourceNum;
            }

            public Object getResourceType() {
                return resourceType;
            }

            public void setResourceType(Object resourceType) {
                this.resourceType = resourceType;
            }

            public String getResourceName() {
                return resourceName;
            }

            public void setResourceName(String resourceName) {
                this.resourceName = resourceName;
            }

            public Object getResourceIcon() {
                return resourceIcon;
            }

            public void setResourceIcon(Object resourceIcon) {
                this.resourceIcon = resourceIcon;
            }

            public int getSortId() {
                return sortId;
            }

            public void setSortId(int sortId) {
                this.sortId = sortId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getZiyuanjibie() {
                return ziyuanjibie;
            }

            public void setZiyuanjibie(Object ziyuanjibie) {
                this.ziyuanjibie = ziyuanjibie;
            }

            public Object getZiyuanlujing() {
                return ziyuanlujing;
            }

            public void setZiyuanlujing(Object ziyuanlujing) {
                this.ziyuanlujing = ziyuanlujing;
            }

            public Object getGroupName() {
                return groupName;
            }

            public void setGroupName(Object groupName) {
                this.groupName = groupName;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getBeizhu() {
                return beizhu;
            }

            public void setBeizhu(Object beizhu) {
                this.beizhu = beizhu;
            }

            public String getEnable() {
                return enable;
            }

            public void setEnable(String enable) {
                this.enable = enable;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getCaoZuoRen() {
                return caoZuoRen;
            }

            public void setCaoZuoRen(String caoZuoRen) {
                this.caoZuoRen = caoZuoRen;
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

            public Object getMerchantEnable() {
                return merchantEnable;
            }

            public void setMerchantEnable(Object merchantEnable) {
                this.merchantEnable = merchantEnable;
            }

            public Object getCompanyEnable() {
                return companyEnable;
            }

            public void setCompanyEnable(Object companyEnable) {
                this.companyEnable = companyEnable;
            }
        }
    }
}
