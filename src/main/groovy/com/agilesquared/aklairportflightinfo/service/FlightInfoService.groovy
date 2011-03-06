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

/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Nov 1, 2010
 * Time: 10:53:57 PM
 * To change this template use File | Settings | File Templates.
 */

@Path("/rest/v1.0/flightinfo/")
class FlightInfoService {

    private static final Logger log = Logger.getLogger(FlightInfoService.class.getName())
    private static HttpURLClient http = new HttpURLClient(url: url.toString())
    private final static URL url = new URL("http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx")
    //9.5 seconds. right under 10 seconds gae limitation.
    private static final int TIME_OUT = 9500

    @GET
    @Path("/departures")
    @Produces("application/json")
    FlightInfoList getDepartures() {
        log.info("departures list is requested...")
        def res = getDeparturePage();
        def responseStr = new StringBuilder();
        if (res != null && res.isSuccess()) {
            //res.data.str worked beautifully locally because res is a instance of StringReader, but GAE hate it.
            res.data.each{line->
                responseStr.append(line);
            }
        } else {
            return new FlightInfoList();
        }


        def divBeginningTag = 'FlightInfo_FlightInfoUpdatePanel'
        int endOfDivBeginningTag = responseStr.toString().indexOf(divBeginningTag) + divBeginningTag.length();
        int tableEnd = wholePage.indexOf("</table>", endOfDivBeginningTag);
        String dataLine = responseStr.toString().substring(endOfDivBeginningTag, tableEnd)

        return new AklAirportFlightInfoWebpageMarshaller().getFlightInfoList(dataLine)

    }




    @GET
    @Path("/arrivals")
    @Produces("application/json")
    FlightInfoList getArrivals() {
        def wholePage = getWholePage()
        def divBeginningTag = '<div id="FlightInfo_FlightInfoUpdatePanel">'
        int endOfDivBeginningTag = wholePage.indexOf(divBeginningTag) + divBeginningTag.length();
        int tableEnd = wholePage.indexOf("</table>", endOfDivBeginningTag);
        String dataLine = wholePage.substring(endOfDivBeginningTag, tableEnd)
        System.out << dataLine << "\n"

        return new AklAirportFlightInfoWebpageMarshaller().getFlightInfoList(dataLine)

    }

    static String getHiddenParameter(def wholePage, def pName) {

        def value;
        wholePage.eachLine {
            it ->
            if (it.contains(pName)) {
                //<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="" />

                value = it.substring(it.indexOf('value="') + 7, it.lastIndexOf('"'))
                return;
            }
        }
        value;
    }

    static String getWholePage() {
        def res = http.request(
            url:url,
            method:'GET',
            contentType: ContentType.TEXT,
            timeout:TIME_OUT
            )
        def builder = new StringBuilder()
        if(res!=null && res.isSuccess()){
            res.data.each{line->
                builder.append(line)
            }
        }
        return builder.toString()
    }


    def  HttpResponseDecorator getDeparturePage() {
        def wholePage = getWholePage();
        if (wholePage == null) {
            log.info("Error getting the page at:" + url)
        }
        def viewState = getHiddenParameter(wholePage, '__VIEWSTATE')
        def eventValidation = getHiddenParameter(wholePage, '__EVENTVALIDATION')
        log.info("sucessfully parsed viewstate and eventValidation:" + eventValidation)
        def postData = [
                'FlightInfo$FlightInfoScriptManager': 'FlightInfo$FlightInfoScriptManager|FlightInfo$departuresButton',
                'Header1$txtSearch': 'Search',
                'FlightInfo$flightNumberTB': 'Type flight no. here',
                'FlightInfo$hiddenPrevSortBy': 'none',
                'FlightInfo$hiddenPrevSortDir': 'none',
                'FlightInfo$hiddenShowResultCount': 'False',
                'FlightInfo$hiddenRange': 'I',
                'FlightInfo$hiddenAirline': '',
                'FlightInfo$hiddenLocation': '',
                '__EVENTTARGET': 'FlightInfo$departuresButton',
                '__EVENTARGUMENT': '',
                '__VIEWSTATE': viewState,
                '__EVENTVALIDATION': eventValidation,
                '__ASYNCPOST': 'true'
        ]
        def res;
        try {
            res = http.request(
                    url:url,
                    method: 'POST',
                    //have to use ContectType.TEXT otherwise it will use HTML which cause the request method to give back parsed
                    //data.
                    contentType: ContentType.TEXT,
                    requestContentType: ContentType.URLENC,
                    body: postData,
                    timeout: TIME_OUT,
                    headers: ['User-Agent': 'Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.6; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13',
                            'X-MicrosoftAjax': 'Delta=true',
                            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
                            'Accept-Language': 'en-us,en;q=0.5',
                            'Accept-Encoding': 'gzip,deflate',
                            'Referer': 'http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx',
                            'Pragma': 'no-cache',
                            'Accept-Charset': 'ISO-8859-1,utf-8;q=0.7,*;q=0.7']
            )
        } catch (HttpResponseException he) {
            log.severe("Error posting to aucklandairport website:" + he.localizedMessage);
        }
        log.info("Request properties:" + http.properties.toMapString())
        log.info("Posting to aucklandairport webform got back :" + res == null ? "NULL" : res.statusLine.statusCode.toString())
        return res;

    }
}
