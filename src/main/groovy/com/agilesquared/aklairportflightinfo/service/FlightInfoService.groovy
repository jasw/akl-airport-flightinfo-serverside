package com.agilesquared.aklairportflightinfo.service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

import com.agilesquared.aklairportflightinfo.AklAirportFlightInfoWebpageMarshaller
import com.agilesquared.aklairportflightinfo.resources.FlightInfoList
import groovyx.net.http.HttpURLClient
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import java.util.logging.Logger
import groovyx.net.http.HttpResponseDecorator
import javax.ws.rs.QueryParam
import javax.ws.rs.MatrixParam
import com.agilesquared.aklairportflightinfo.FlightInfoWebPageFetcher

/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Nov 1, 2010
 * Time: 10:53:57 PM
 *
 * This class defines all REST APIs.
 */

@Path("/rest/v1.0/flightinfo/")
class FlightInfoService {


    private def fetcher = new FlightInfoWebPageFetcher()
    private def marshaller = new AklAirportFlightInfoWebpageMarshaller()

    @GET
    @Path("/departures")
    @Produces("application/json")
    FlightInfoList getDepartures() {

        def dataLine = fetcher.getDeparturesDataLine()
        return marshaller.toFlightInfoList(dataLine)

    }


    @GET
    @Path("/arrivals")
    @Produces("application/json")
    FlightInfoList getArrivals() {

        def dataLine = fetcher.getArrivalsDataLine()

        return marshaller.toFlightInfoList(dataLine)

    }
    /**
     * http://server:port/departuresWithRange;from=100;to=200
     * @param from
     * @param to
     * @return
     */
    @GET
    @Path("/departuresWithRange")
    @Produces("application/json")
    FlightInfoList getDepartures(@MatrixParam("from")int from, @MatrixParam("to") int to){
        if(!goodRange(from,to)){
            return new FlightInfoList() //TODO error feedback
        }
        def departures =  getDepartures();
        if(departures.allFlightInfo.size()<to){
            return new FlightInfoList()
        }
        def sublist = departures.getAllFlightInfo().subList(from,to)

        return new FlightInfoList(allFlightInfo: sublist);

    }
    /**
     * http://server:port/arrivalsWithRange;from=100;to=200
     * @param from
     * @param to
     * @return
     */
    @GET
    @Path("/arrivalsWithRange")
    @Produces("application/json")
    FlightInfoList getArrivals(@MatrixParam("from")int from, @MatrixParam("to") int to){
        if(!goodRange(from,to)){
            return new FlightInfoList() //TODO error feedback
        }
        def arrivals =  getArrivals();
        if(arrivals.allFlightInfo.size()<to){
            return new FlightInfoList()
        }
        def sublist = arrivals.getAllFlightInfo().subList(from,to)

       return new FlightInfoList(allFlightInfo: sublist);

    }

    private boolean goodRange(int from, int to){

        if(from < 0 || to <0){
            return false
        }
        if(from > to ){
            return false
        }
        return true

    }


}
