package com.fundrive.navaidlclient.bean;

public class CmdBean {
    private final int CMD;
    private String name;
    private String strJson;
    private String funcNo;

    public CmdBean(int CMD, String name, String strJson) {
        this.CMD = CMD;
        this.name = name;
        this.strJson = strJson;
    }

    public int getCMD() {
        return CMD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrJson() {
        return strJson;
    }

    public void setStrJson(String strJson) {
        this.strJson = strJson;
    }

    @Override
    public String toString() {
        return name;
    }
}
