package com.nuist.mybank.POJO.ResultBean;

import java.util.List;

public class GetChartResult {

    /**
     * message : 操作成功
     * success : true
     * code : 10000
     * data : [{"chart_id":10001,"chart_title":"Android开发学习路线","chart_pic":"images/1.jpg","article_id":0},{"chart_id":10002,"chart_title":"课程视频打包下载","chart_pic":"images/2.jpg","article_id":0},{"chart_id":10003,"chart_title":"API文档","chart_pic":"images/3.jpg","article_id":0},{"chart_id":10004,"chart_title":"商城上线","chart_pic":"images/4.jpg","article_id":0}]
     */

    private String message;
    private boolean success;
    private int code;
    private List<ChartBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ChartBean> getData() {
        return data;
    }

    public void setData(List<ChartBean> data) {
        this.data = data;
    }

    public static class ChartBean {
        /**
         * chart_id : 10001
         * chart_title : Android开发学习路线
         * chart_pic : images/1.jpg
         * article_id : 0
         */

        private int chart_id;
        private String chart_title;
        private String chart_pic;
        private String goods_name;

        public int getChart_id() {
            return chart_id;
        }

        public void setChart_id(int chart_id) {
            this.chart_id = chart_id;
        }

        public String getChart_title() {
            return chart_title;
        }

        public void setChart_title(String chart_title) {
            this.chart_title = chart_title;
        }

        public String getChart_pic() {
            return chart_pic;
        }

        public void setChart_pic(String chart_pic) {
            this.chart_pic = chart_pic;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
    }
}
