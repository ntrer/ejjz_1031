package com.shushang.aishangjia.Bean;

import java.util.List;

public class UpDateInfo {

    private List<info> dataList;

    public List<info> getDataList() {
        return dataList;
    }

    public void setDataList(List<info> dataList) {
        this.dataList = dataList;
    }


    public static class info {

        private String customerActionId;
        private String qdsuccess;

        public String getCustomerActionId() {
            return customerActionId;
        }

        public void setCustomerActionId(String customerActionId) {
            this.customerActionId = customerActionId;
        }

        public String getQdsuccess() {
            return qdsuccess;
        }

        public void setQdsuccess(String qdsuccess) {
            this.qdsuccess = qdsuccess;
        }

        public String getQdsj() {
            return qdsj;
        }

        public void setQdsj(String qdsj) {
            this.qdsj = qdsj;
        }

        public String getLqsuccess() {
            return lqsuccess;
        }

        public void setLqsuccess(String lqsuccess) {
            this.lqsuccess = lqsuccess;
        }

        public String getLqsj() {
            return lqsj;
        }

        public void setLqsj(String lqsj) {
            this.lqsj = lqsj;
        }

        private String qdsj;
        private String lqsuccess;
        private String lqsj;


    }



}
