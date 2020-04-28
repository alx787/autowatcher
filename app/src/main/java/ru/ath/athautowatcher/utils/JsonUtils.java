package ru.ath.athautowatcher.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import ru.ath.athautowatcher.data.Transport;

public class JsonUtils {
    public static ArrayList<Transport> getTransportListFromJson(JsonObject jsonObject) {
        ArrayList<Transport> result = new ArrayList<>();
        if (jsonObject == null) {
             return result;
        }

        JsonArray jsonArray = jsonObject.get("content").getAsJsonArray();
        for(int i = 0; i < jsonArray.size(); i++) {
            JsonObject trJsonObj = jsonArray.get(i).getAsJsonObject();
            Transport tr = convertJsonToTransport(trJsonObj);
            result.add(tr);
        }
        return result;
    }

    private static Transport convertJsonToTransport(JsonObject jsonObject) {
        Transport tr = new Transport();
        tr.setId(jsonObject.get("id").getAsInt());
        tr.setVehicletype(jsonObject.get("vehicletype").getAsString());

        tr.setVin(jsonObject.get("vin").getAsString());
        tr.setBrand(jsonObject.get("brand").getAsString());
        tr.setModel(jsonObject.get("model").getAsString());
        tr.setProdyear(jsonObject.get("prodyear").getAsString());
        tr.setColor(jsonObject.get("color").getAsString());
        tr.setEnginemodel(jsonObject.get("enginemodel").getAsString());
        tr.setPrimaryfueltype(jsonObject.get("primaryfueltype").getAsString());
        tr.setEnginepower(jsonObject.get("enginepower").getAsString());
        tr.setGrossvehicleweight(jsonObject.get("grossvehicleweight").getAsString());
        tr.setRegistrationplate(jsonObject.get("registrationplate").getAsString());
        tr.setAtinvnom(jsonObject.get("atinvnom").getAsString());
        tr.setAtinstalldate(jsonObject.get("atinstalldate").getAsString());
        tr.setAtwheelformula(jsonObject.get("atwheelformula").getAsString());
        tr.setAtdepartment(jsonObject.get("atdepartment").getAsString());
        tr.setAtautocol(jsonObject.get("atautocol").getAsString());
        tr.setAtlocation(jsonObject.get("atlocation").getAsString());
        tr.setAtbase(jsonObject.get("atbase").getAsString());
        tr.setAtres(jsonObject.get("atres").getAsString());

        return tr;
    }
}
