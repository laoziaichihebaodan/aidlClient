package com.fundrive.navaidlclient.bean;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageInfoBean implements Serializable{
    private String name;
    private String type;
    private String tips;
    private Item item;

    public class Item implements Serializable{
        private List<SecondKey> secondKey;
        private List<ThirdKey> thirdKey;
        private List<Page> page;

        public List<SecondKey> getSecondKey() {
            return secondKey;
        }

        public void setSecondKey(List<SecondKey> secondKey) {
            this.secondKey = secondKey;
        }

        public List<ThirdKey> getThirdKey() {
            return thirdKey;
        }

        public void setThirdKey(List<ThirdKey> thirdKey) {
            this.thirdKey = thirdKey;
        }

        public List<Page> getPage() {
            return page;
        }

        public void setPage(List<Page> page) {
            this.page = page;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "secondKey=" + secondKey +
                    ", thirdKey=" + thirdKey +
                    ", page=" + page +
                    '}';
        }
    }

    public static List<PageInfoBean> getPageInfoBeanList(String strJson){
        List<PageInfoBean> list = new ArrayList<>();
        Gson gson = new Gson();//创建Gson对象
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonElements = jsonParser.parse(strJson).getAsJsonArray();//获取JsonArray对象
        for (JsonElement bean : jsonElements) {
            PageInfoBean bean1 = gson.fromJson(bean, PageInfoBean.class);//解析
            list.add(bean1);
        }
        return list;
    }

    public class SecondKey implements Serializable{
        private String nama;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        @Override
        public String toString() {
            return "SecondKey{" +
                    "nama='" + nama + '\'' +
                    '}';
        }
    }

    public class ThirdKey implements Serializable{
        private String nama;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        @Override
        public String toString() {
            return "ThirdKey{" +
                    "nama='" + nama + '\'' +
                    '}';
        }
    }

    public class Page implements Serializable{
        private String name;//"白天模式",
        private String value;//"2",
        private String valueType;//int
        private String key;//"mode",
        private String type;//"singlebutton",
        private String floor;//"2",
        private String grandParentKey;//"aaa",
        private String parentKey;//"iaAudio",
        private String titleName;//"---",
        private String lineNum;//"1"
        private List<SpinnerValue> spinnerValue;

        @Override
        public String toString() {
            return "Page{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", valueType='" + valueType + '\'' +
                    ", key='" + key + '\'' +
                    ", type='" + type + '\'' +
                    ", floor='" + floor + '\'' +
                    ", grandParentKey='" + grandParentKey + '\'' +
                    ", parentKey='" + parentKey + '\'' +
                    ", titleName='" + titleName + '\'' +
                    ", lineNum='" + lineNum + '\'' +
                    ", spinnerValue=" + spinnerValue +
                    '}';
        }

        public String getValueType() {
            return valueType;
        }

        public void setValueType(String valueType) {
            this.valueType = valueType;
        }

        public List<SpinnerValue> getSpinnerValue() {
            return spinnerValue;
        }

        public void setSpinnerValue(List<SpinnerValue> spinnerValue) {
            this.spinnerValue = spinnerValue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getGrandParentKey() {
            return grandParentKey;
        }

        public void setGrandParentKey(String grandParentKey) {
            this.grandParentKey = grandParentKey;
        }

        public String getParentKey() {
            return parentKey;
        }

        public void setParentKey(String parentKey) {
            this.parentKey = parentKey;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public String getLineNum() {
            return lineNum;
        }

        public void setLineNum(String lineNum) {
            this.lineNum = lineNum;
        }

    }

    public class SpinnerValue implements Serializable{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "SpinnerValue{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return name;
    }
}
