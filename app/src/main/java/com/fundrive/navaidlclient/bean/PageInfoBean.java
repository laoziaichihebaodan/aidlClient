package com.fundrive.navaidlclient.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PageInfoBean implements Serializable{
    private List<String> listType;
    private List<Lists> lists;


    private PageInfoBean(){
    }

    public static PageInfoBean getPageInfoBeanList(String strJson){
        Gson gson = new Gson();//创建Gson对象
//        JsonParser jsonParser = new JsonParser();
//        JsonArray jsonElements = jsonParser.parse(strJson).getAsJsonArray();//获取JsonArray对象
//        for (JsonElement bean : jsonElements) {
//            PageInfoBean bean1 = gson.fromJson(bean, PageInfoBean.class);//解析
//            list.add(bean1);
//        }
        PageInfoBean bean = gson.fromJson(strJson, PageInfoBean.class);//解析
        Log.i("hebaodan",bean+"");
        return bean;
    }

    public class Lists implements Serializable{
        private String name;
        private String type;
        private String tips;
        private String cmd;
        private Item item;
        private String sendJson;

        public Lists(String name, String type, String tips, String cmd, Item item) {
            this.name = name;
            this.type = type;
            this.tips = tips;
            this.cmd = cmd;
            this.item = item;
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

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public String getSendJson() {
            return sendJson;
        }

        public void setSendJson(String sendJson) {
            this.sendJson = sendJson;
        }

        @Override
        public String toString() {
            return name;
        }
    }
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

    public class SecondKey implements Serializable{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "SecondKey{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    public class ThirdKey implements Serializable{
        private String name;

        public String getNama() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ThirdKey{" +
                    "name='" + name + '\'' +
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
        private List<MutilSelectValue> mutilSelectValue;

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

        public List<MutilSelectValue> getMutilSelectValue() {
            return mutilSelectValue;
        }

        public void setMutilSelectValue(List<MutilSelectValue> mutilSelectValue) {
            this.mutilSelectValue = mutilSelectValue;
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

    public static class MutilSelectValue implements Serializable{
        private String name;
        private String value;
        private boolean isSelect;

        public MutilSelectValue(){

        }
        public MutilSelectValue(String name,String value,boolean isSelect){
            this.name = name;
            this.value = value;
            this.isSelect = isSelect;
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

        public void setSelect(boolean isSelect){
            this.isSelect = isSelect;
        }

        public boolean isSelect(){
            return isSelect;
        }

        @Override
        public String toString() {
            return "SpinnerValue{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public List<String> getListType() {
        return listType;
    }

    public void setListType(List<String> listType) {
        this.listType = listType;
    }

    public List<Lists> getLists() {
        return lists;
    }
    public List<Lists> getLists(String str_listType) {
        List<Lists> list_listType = new ArrayList<>();
        for (Lists lists_i:lists){
            if (lists_i != null && lists_i.getType().equals(str_listType)){
                list_listType.add(lists_i);
            }
        }
        return list_listType;
    }

    public void setLists(List<Lists> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "PageInfoBean{" +
                "listType=" + listType +
                ", lists=" + lists +
                '}';
    }
}
