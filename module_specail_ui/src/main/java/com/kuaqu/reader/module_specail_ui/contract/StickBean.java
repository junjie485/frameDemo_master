package com.kuaqu.reader.module_specail_ui.contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StickBean implements Serializable{
    private int type;
    private int img;
    private List<String> titleList;
    private List<ListBean> list;

    public StickBean(int type, int img, List<String> titleList, List<ListBean> list) {
        this.type = type;
        this.img = img;
        this.titleList = titleList;
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public List<String> getTitleList() {
        if (titleList == null) {
            return new ArrayList<>();
        }
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        private int img;
        private String name;
        private String price;
        private String market_price;

        public ListBean(int img, String name, String price, String market_price) {
            this.img = img;
            this.name = name;
            this.price = price;
            this.market_price = market_price;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMarket_price() {
            return market_price == null ? "" : market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }
    }
}
