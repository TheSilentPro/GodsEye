package tsp.godseye.api;

/**
 * ResultSet Interface for IPAPI
 *
 * @author Silent
 */
public interface IResultSet {

    String getQuery();

    String getStatus();

    String getMessage();

    String getContinent();

    String getContinentCode();

    String getCountry();

    String getCountryCode();

    String getRegion();

    String getRegionName();

    String getCity();

    String getDistrict();

    String getZIP();

    String getLat();

    String getLon();

    String getTimezone();

    String getCurrency();

    String getISP();

    String getOrg();

    String getAS();

    String getASName();

    String getReverse();

    boolean getMobile();

    boolean getProxy();

    boolean getHosting();

}
