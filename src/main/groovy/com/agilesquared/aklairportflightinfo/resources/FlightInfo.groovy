package com.agilesquared.aklairportflightinfo.resources

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlRootElement
import javax.xml.bind.annotation.XmlElement
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Nov 2, 2010
 * Time: 10:02:46 PM
 *
 * This class represents list of flight info. Java value object
 * for Json data.
 *
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



  }

  void add(FlightInfo flightInfo) {
    allFlightInfo << flightInfo;

  }


}
