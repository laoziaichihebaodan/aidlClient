package com.fundrive.navaidlclient.bean;

/**
 * Created by fduser on 2019/5/20.
 */

public class RouteInfo {

    private int routeNumber;
    private String description;
    private int routeTime;
    private int routeLength;
    private int normalWayLength;
    private int highWayLength;
    private int tollStationCount;
    private int tollChargr;
    private int trafficlightCount;

    public RouteInfo(){
    }

    public RouteInfo(int routeNumber,String description,int routeTime,int routeLength,int normalWayLength,int highWayLength,int tollStationCount,int tollChargr,int trafficlightCount){
        this.routeNumber = routeNumber;
        this.description = description;
        this.routeTime = routeTime;
        this.routeLength = routeLength;
        this.normalWayLength = normalWayLength;
        this.highWayLength = highWayLength;
        this.tollStationCount = tollStationCount;
        this.tollChargr = tollChargr;
        this.trafficlightCount = trafficlightCount;
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(int routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRouteTime() {
        return routeTime;
    }

    public void setRouteTime(int routeTime) {
        this.routeTime = routeTime;
    }

    public int getRouteLength() {
        return routeLength;
    }

    public void setRouteLength(int routeLength) {
        this.routeLength = routeLength;
    }

    public int getNormalWayLength() {
        return normalWayLength;
    }

    public void setNormalWayLength(int normalWayLength) {
        this.normalWayLength = normalWayLength;
    }

    public int getHighWayLength() {
        return highWayLength;
    }

    public void setHighWayLength(int highWayLength) {
        this.highWayLength = highWayLength;
    }

    public int getTollStationCount() {
        return tollStationCount;
    }

    public void setTollStationCount(int tollStationCount) {
        this.tollStationCount = tollStationCount;
    }

    public int getTollChargr() {
        return tollChargr;
    }

    public void setTollChargr(int tollChargr) {
        this.tollChargr = tollChargr;
    }

    public int getTrafficlightCount() {
        return trafficlightCount;
    }

    public void setTrafficlightCount(int trafficlightCount) {
        this.trafficlightCount = trafficlightCount;
    }
}
