package tsp.godseye.api;

import org.json.simple.JSONObject;

/**
 * ResultSet for IPAPI
 *
 * @author Silent
 */
public class ResultSet implements IResultSet {

    JSONObject obj;

    public ResultSet(JSONObject obj) {
        this.obj = obj;
    }

    @Override
    public String getQuery() {
        return obj.get("query").toString();
    }

    @Override
    public String getStatus() {
        return obj.get("status").toString();
    }

    @Override
    public String getMessage() {
        return obj.get("message") == null ? "Fetching Successful" : obj.get("message").toString();
    }

    @Override
    public String getContinent() {
        return obj.get("continent").toString();
    }

    @Override
    public String getContinentCode() {
        return obj.get("continentCode").toString();
    }

    @Override
    public String getCountry() {
        return obj.get("country").toString();
    }

    @Override
    public String getCountryCode() {
        return obj.get("countryCode").toString();
    }

    @Override
    public String getRegion() {
        return obj.get("region").toString();
    }

    @Override
    public String getRegionName() {
        return obj.get("regionName").toString();
    }

    @Override
    public String getCity() {
        return obj.get("city").toString();
    }

    @Override
    public String getDistrict() {
        return obj.get("district").toString();
    }

    @Override
    public String getZIP() {
        return obj.get("zip").toString();
    }

    @Override
    public String getLat() {
        return obj.get("lat").toString();
    }

    @Override
    public String getLon() {
        return obj.get("lon").toString();
    }

    @Override
    public String getTimezone() {
        return obj.get("timezone").toString();
    }

    @Override
    public String getCurrency() {
        return obj.get("currency").toString();
    }

    @Override
    public String getISP() {
        return obj.get("isp").toString();
    }

    @Override
    public String getOrg() {
        return obj.get("org").toString();
    }

    @Override
    public String getAS() {
        return obj.get("as").toString();
    }

    @Override
    public String getASName() {
        return obj.get("asname").toString();
    }

    @Override
    public String getReverse() {
        return obj.get("reverse").toString();
    }

    @Override
    public boolean getMobile() {
        return Boolean.parseBoolean(obj.get("mobile").toString());
    }

    @Override
    public boolean getProxy() {
        return Boolean.parseBoolean(obj.get("proxy").toString());
    }

    @Override
    public boolean getHosting() {
        return Boolean.parseBoolean(obj.get("hosting").toString());
    }

}
