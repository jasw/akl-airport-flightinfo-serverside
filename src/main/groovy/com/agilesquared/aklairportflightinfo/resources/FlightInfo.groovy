package com.agilesquared.aklairportflightinfo.resources

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlElement

/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Nov 2, 2010
 * Time: 10:02:46 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "flightInfoList")
class FlightInfoList {

  @XmlElement(name = "flightInfo")
  List<FlightInfo> allFlightInfo = new ArrayList<FlightInfo>();


  @XmlAccessorType(XmlAccessType.FIELD)
  static class FlightInfo {
    String airline
    String flight
    String codeshare
    String origin
    String date
    String time
    String est
    String status


    boolean equals(o) {
      if (this.is(o)) return true;

      if (!(o instanceof FlightInfo)) return false;

      FlightInfo that = (FlightInfo) o;

      if (airline != that.airline) return false;
      if (codeshare != that.codeshare) return false;
      if (date != that.date) return false;
      if (est != that.est) return false;
      if (flight != that.flight) return false;
      if (origin != that.origin) return false;
      if (status != that.status) return false;
      if (time != that.time) return false;

      return true;
    }

    int hashCode() {
      int result;

      result = (airline != null ? airline.hashCode() : 0);
      result = 31 * result + (flight != null ? flight.hashCode() : 0);
      result = 31 * result + (codeshare != null ? codeshare.hashCode() : 0);
      result = 31 * result + (origin != null ? origin.hashCode() : 0);
      result = 31 * result + (date != null ? date.hashCode() : 0);
      result = 31 * result + (time != null ? time.hashCode() : 0);
      result = 31 * result + (est != null ? est.hashCode() : 0);
      result = 31 * result + (status != null ? status.hashCode() : 0);
      return result;
    }

    public String toString() {
      return "FlightInfo{" +
              "airline='" + airline + '\'' +
              ", flight='" + flight + '\'' +
              ", codeshare='" + codeshare + '\'' +
              ", origin='" + origin + '\'' +
              ", date='" + date + '\'' +
              ", time='" + time + '\'' +
              ", est='" + est + '\'' +
              ", status='" + status + '\'' +
              '}';
    }
  }

  void add(FlightInfo flightInfo) {
    allFlightInfo << flightInfo;

  }

  public String toString() {
    return "FlightInfoList{" +
            "allFlightInfo=" + allFlightInfo +
            '}';
  }



  boolean equals(o) {
    if (this.is(o)) return true;

    if (!(o instanceof FlightInfoList)) return false;

    FlightInfoList that = (FlightInfoList) o;

    if (allFlightInfo != that.allFlightInfo) return false;

    return true;
  }

  int hashCode() {
    return (allFlightInfo != null ? allFlightInfo.hashCode() : 0);
  }
}
