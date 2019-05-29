package com.fundrive.navaidlclient.bean;

import java.util.List;

public class PoiSearchResultBean {
    private int poiDataType;//1,
    private int pioDataPageTotal;//5,
    private int hasLoadPageTotal;//3,
    private int curPageNum;//2,
    private int curPagePoiDataNum;//3,
    private List<CurPoiDatum> curPoiDatum;//Array[3]

    public class CurPoiDatum {
        private int iaPoiType;//0,
        private IaDetailInfo iaDetailInfo;//Object{...},
        private IaPoiPos iaPoiPos;//Object{...},
        private IaPoiDisPos iaPoiDisPos;//Object{...},
        private String iaPoiId;//"4294967295",
        private int iaChildPoiNum;//0,
        private String iaPoiName;//"五角场",
        private String iaPoiAdress;//"杨浦区",
        private String iaPoiPhone;//"",
        private String iaRegionName;//"上海市杨浦区",
        private String iaPoiTypeName;//"其他地名",
        private int iaDistanceToSCenter;//11801

        public int getIaPoiType() {
            return iaPoiType;
        }

        public void setIaPoiType(int iaPoiType) {
            this.iaPoiType = iaPoiType;
        }

        public IaDetailInfo getIaDetailInfo() {
            return iaDetailInfo;
        }

        public void setIaDetailInfo(IaDetailInfo iaDetailInfo) {
            this.iaDetailInfo = iaDetailInfo;
        }

        public IaPoiPos getIaPoiPos() {
            return iaPoiPos;
        }

        public void setIaPoiPos(IaPoiPos iaPoiPos) {
            this.iaPoiPos = iaPoiPos;
        }

        public IaPoiDisPos getIaPoiDisPos() {
            return iaPoiDisPos;
        }

        public void setIaPoiDisPos(IaPoiDisPos iaPoiDisPos) {
            this.iaPoiDisPos = iaPoiDisPos;
        }

        public String getIaPoiId() {
            return iaPoiId;
        }

        public void setIaPoiId(String iaPoiId) {
            this.iaPoiId = iaPoiId;
        }

        public int getIaChildPoiNum() {
            return iaChildPoiNum;
        }

        public void setIaChildPoiNum(int iaChildPoiNum) {
            this.iaChildPoiNum = iaChildPoiNum;
        }

        public String getIaPoiName() {
            return iaPoiName;
        }

        public void setIaPoiName(String iaPoiName) {
            this.iaPoiName = iaPoiName;
        }

        public String getIaPoiAdress() {
            return iaPoiAdress;
        }

        public void setIaPoiAdress(String iaPoiAdress) {
            this.iaPoiAdress = iaPoiAdress;
        }

        public String getIaPoiPhone() {
            return iaPoiPhone;
        }

        public void setIaPoiPhone(String iaPoiPhone) {
            this.iaPoiPhone = iaPoiPhone;
        }

        public String getIaRegionName() {
            return iaRegionName;
        }

        public void setIaRegionName(String iaRegionName) {
            this.iaRegionName = iaRegionName;
        }

        public String getIaPoiTypeName() {
            return iaPoiTypeName;
        }

        public void setIaPoiTypeName(String iaPoiTypeName) {
            this.iaPoiTypeName = iaPoiTypeName;
        }

        public int getIaDistanceToSCenter() {
            return iaDistanceToSCenter;
        }

        public void setIaDistanceToSCenter(int iaDistanceToSCenter) {
            this.iaDistanceToSCenter = iaDistanceToSCenter;
        }
    }

    public class IaDetailInfo{
       private int dataType;// 33,   //33充电站  27停车场  10美食，
       private String payment;//"APP",  //支付方式
       private int chargerNum;// 9,   //充电桩数量
       private int electricBoxNum;// 0,//换电箱数量
       private String brand;//"EVCARD"   //品牌

       private int spaceTotal;//160,
       private int spaceFree;//60,
       private String openingTime;//"00:00-24:00",
       private int isOpen;//1,
       private String feeText;//"首小时9元,首小时后4元/30分钟;72元/天;1200元/月;",
       private String standards;//"包月|计次|计时|分段计价",

       private double environment;// 6.9,         //环境
       private double price;// 0.0,                //价格
       private String recommend;//"泡汤吃臭豆腐", //推荐
       private double taste;// 7.0,                //口味
       private double service;// 7.0               //服务

        public int getDataType() {
            return dataType;
        }

        public void setDataType(int dataType) {
            this.dataType = dataType;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public int getChargerNum() {
            return chargerNum;
        }

        public void setChargerNum(int chargerNum) {
            this.chargerNum = chargerNum;
        }

        public int getElectricBoxNum() {
            return electricBoxNum;
        }

        public void setElectricBoxNum(int electricBoxNum) {
            this.electricBoxNum = electricBoxNum;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getSpaceTotal() {
            return spaceTotal;
        }

        public void setSpaceTotal(int spaceTotal) {
            this.spaceTotal = spaceTotal;
        }

        public int getSpaceFree() {
            return spaceFree;
        }

        public void setSpaceFree(int spaceFree) {
            this.spaceFree = spaceFree;
        }

        public String getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(String openingTime) {
            this.openingTime = openingTime;
        }

        public int getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(int isOpen) {
            this.isOpen = isOpen;
        }

        public String getFeeText() {
            return feeText;
        }

        public void setFeeText(String feeText) {
            this.feeText = feeText;
        }

        public String getStandards() {
            return standards;
        }

        public void setStandards(String standards) {
            this.standards = standards;
        }

        public double getEnvironment() {
            return environment;
        }

        public void setEnvironment(double environment) {
            this.environment = environment;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public double getTaste() {
            return taste;
        }

        public void setTaste(double taste) {
            this.taste = taste;
        }

        public double getService() {
            return service;
        }

        public void setService(double service) {
            this.service = service;
        }
    }
    public class IaPoiPos{
       private int longitude;//12151417,
       private int latitude;//3129907

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }
    }
    public class IaPoiDisPos{
       private int longitude;//12151417,
       private int latitude;//3129907

        public int getLongitude() {
            return longitude;
        }

        public void setLongitude(int longitude) {
            this.longitude = longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public void setLatitude(int latitude) {
            this.latitude = latitude;
        }
    }

    public int getPoiDataType() {
        return poiDataType;
    }

    public void setPoiDataType(int poiDataType) {
        this.poiDataType = poiDataType;
    }

    public int getPioDataPageTotal() {
        return pioDataPageTotal;
    }

    public void setPioDataPageTotal(int pioDataPageTotal) {
        this.pioDataPageTotal = pioDataPageTotal;
    }

    public int getHasLoadPageTotal() {
        return hasLoadPageTotal;
    }

    public void setHasLoadPageTotal(int hasLoadPageTotal) {
        this.hasLoadPageTotal = hasLoadPageTotal;
    }

    public int getCurPageNum() {
        return curPageNum;
    }

    public void setCurPageNum(int curPageNum) {
        this.curPageNum = curPageNum;
    }

    public int getCurPagePoiDataNum() {
        return curPagePoiDataNum;
    }

    public void setCurPagePoiDataNum(int curPagePoiDataNum) {
        this.curPagePoiDataNum = curPagePoiDataNum;
    }

    public List<CurPoiDatum> getCurPoiDatum() {
        return curPoiDatum;
    }

    public void setCurPoiDatum(List<CurPoiDatum> curPoiDatum) {
        this.curPoiDatum = curPoiDatum;
    }
}
