package com.agilesquared.aklairportflightinfo



import com.agilesquared.aklairportflightinfo.resources.FlightInfoList
/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Oct 25, 2010
 * Time: 10:17:54 PM
 *
 * This class marshalls a partial xml (flightinfo div) into java objects (FlightInfoList).
 */
class AklAirportFlightInfoWebpageMarshaller {


  public def toFlightInfoList(def flightInfoDiv) {
    def parser = new org.cyberneko.html.parsers.SAXParser()                      
    parser.setFeature('http://xml.org/sax/features/namespaces', false)
    def parsedDiv = new XmlParser(parser).parseText(flightInfoDiv);

    FlightInfoList list = new FlightInfoList();

    /*
       Example tr line looks like:
       <tr class="">
           <td class="airline"><img src="/images/airline logos/NZ.gif" title="AIR NEW ZEALAND LIMITED. "
                                    alt="AIR NEW ZEALAND LIMITED. "/></td>
           <td class="flight">NZ124</td>
           <td class="codeshare">&nbsp;</td>
           <td class="origin">Melbourne</td>
           <td class="date">01 Nov</td>
           <td class="time">17:00</td>
           <td class="est">17:00</td>
           <td class="status">PROCESSING</td>
       </tr>
    */
    parsedDiv.BODY[0].TABLE[0].TBODY[0].TR.each {tr ->
      //the last tr does not contain flight info so we skip it.
      if (tr.attribute("class") != "last") {
        FlightInfoList.FlightInfo flightInfo = new FlightInfoList.FlightInfo();

        tr.TD.each {td ->

          def var = td.value()[0]
          def value = var.toString();
          //when class attribute is airline, the value object var could be either
          //a string or a img
          if ('airline'.equals(td.attribute("class"))) {
            if (var.class == String.class) {
              //if its a string then use the string as the airline name value.
              value = var.toString()
            } else {
              //if it is not string (then its a img), use the img title as the name of the airline.
              value = var.attribute("title")
            }
          }
          // println td.attribute("class") + " = " + value;
          flightInfo.setProperty(td.attribute("class"), value)
        }
        list.add flightInfo
        
      }                              
    }

    return list;
  }


}