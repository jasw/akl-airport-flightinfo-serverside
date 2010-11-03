package com.agilesquared.aklairportflightinfo.service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.agilesquared.aklairportflightinfo.AklAirportFlightInfoWebpageMarshaller
import com.agilesquared.aklairportflightinfo.resources.FlightInfoList

/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Nov 1, 2010
 * Time: 10:53:57 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("/rest/v1.0/flightinfo/")
class FlightInfoService {

  @GET
  @Produces ("application/json")
  FlightInfoList getFlightInfoList() {
    URL url = new URL("http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx");
    def wholePage = url.getText().toString();
    def divBeginningTag = '<div id="FlightInfo_FlightInfoUpdatePanel">'
    int endOfDivBeginningTag = wholePage.indexOf(divBeginningTag) + divBeginningTag.length();
    int tableEnd = wholePage.indexOf("</table>", endOfDivBeginningTag);
    String dataLine = wholePage.substring(endOfDivBeginningTag, tableEnd)
    System.out<<dataLine<<"\n"
    return new AklAirportFlightInfoWebpageMarshaller().get(dataLine)

  }

  static void main(def args){
    def list =  new FlightInfoService().getFlightInfoList();
    System.out<<list;
  }
}
