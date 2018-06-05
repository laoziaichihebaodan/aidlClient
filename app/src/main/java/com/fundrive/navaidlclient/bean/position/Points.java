package com.fundrive.navaidlclient.bean.position;

import org.json.JSONException;
import org.json.JSONObject;

public class Points {
    private static String point;

    public static JSONObject pointJson(int poitype, int poiLong, int poiLat, int disLong,
                                   int disLat, long iaPoiId, int childPoiNum, int compoundId,
                                   String poiName, String poiAddress, String poiPhone,
                                   String iaRegionName, String poiTypeName) {
        JSONObject startPoision = new JSONObject();
        JSONObject iaPoiPos = new JSONObject();
        JSONObject iaPoiDisPos = new JSONObject();
        try {
            startPoision.put("iaPoiType", poitype);
            iaPoiPos.put("longitude", poiLong);
            iaPoiPos.put("latitude", poiLat);
            iaPoiDisPos.put("longitude", disLong);
            iaPoiDisPos.put("latitude", disLat);

            startPoision.put("iaPoiType", iaPoiPos);
            startPoision.put("iaPoiDisPos", iaPoiDisPos);
            startPoision.put("iaPoiId", iaPoiId);
            startPoision.put("iaChildPoiNum", childPoiNum);
            startPoision.put("iaCompoundId", compoundId);
            startPoision.put("iaPoiName", poiName);
            startPoision.put("iaPoiAdress", poiAddress);
            startPoision.put("iaPoiPhone", poiPhone);
            startPoision.put("iaRegionName", iaRegionName);
            startPoision.put("iaPoiTypeName", poiTypeName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return startPoision;
    }
    /*    */
}
