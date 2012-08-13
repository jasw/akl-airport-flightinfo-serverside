package com.agilesquared.aklairportflightinfo

import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.HttpURLClient

import java.util.logging.Logger

/**
 * Created with IntelliJ IDEA.
 * User: jasonw
 * Date: 14/08/12
 * Time: 12:27 AM
 *
 * This class makes all http get/post operations to retrieve the departure/attrival web pages.
 */
class FlightInfoWebPageFetcher {

    private static final Logger log = Logger.getLogger(FlightInfoWebPageFetcher.class.getName())

    private static final int TIME_OUT = 20 * 1000 //20 seconds

    final static URL url = new URL("http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx")


    public def getArrivalsDataLine() {
        def wholePage = url.getText().toString();
        return getDataline(wholePage)
    }

    public def getDeparturesDataLine() {
        def res = getDeparturePage()
        def responseStr = new StringBuilder();
        if (res != null && res.isSuccess()) {
            //res.data.str worked beautifully locally because res is a instance of StringReader, but somehow GAE hate it.
            res.data.each {line ->
                responseStr.append(line);
            }
        } else {
            log.info("Failed to get departure data:"+res)
            return "";
        }
        return getDataline(responseStr.toString())
    }

    private def getDataline(String webPage) {
        def divBeginningTag = 'FlightInfo_FlightInfoUpdatePanel'
        int endOfDivBeginningTag = webPage.indexOf(divBeginningTag) + divBeginningTag.length();
        int tableEnd = webPage.indexOf("</table>", endOfDivBeginningTag);
        return webPage.substring(endOfDivBeginningTag, tableEnd)
    }

    /**
     * To get departure page, we have to do a HTTP GET first,
     * then parse the __VIEWSTATE, do a post with __VIEWSTATE to
     * request for the departure page.
     * @return the departure page in HttpResponseDecorator
     */
    private def HttpResponseDecorator getDeparturePage() {
        def http = new HttpURLClient(url: url.toString());
        def wholePage = getWholePageByHttpURLClient();
        if (wholePage == null) {
            log.info("Error getting the page at:" + url)
        }
        def viewState = getHiddenParameter(wholePage, '__VIEWSTATE')
        def eventValidation = getHiddenParameter(wholePage, '__EVENTVALIDATION')
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
        log.info("Posting to aucklandairport webform got back :" + res == null ? "NULL" : res.statusLine.statusCode.toString())
        return res;

    }

    private static def getWholePageByHttpURLClient() {
        def http = new HttpURLClient()
        def res = http.request(
                url: url,
                method: 'GET',
                contentType: ContentType.TEXT,
                timeout: TIME_OUT
        )
        def builder = new StringBuilder()
        if (res != null && res.isSuccess()) {
            res.data.each {line ->
                builder.append(line + '\n')
            }
        }
        return builder.toString()
    }

    private static String getHiddenParameter(def wholePage, def pName) {

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

}
