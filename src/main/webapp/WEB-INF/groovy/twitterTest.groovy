/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: Oct 20, 2010
 * Time: 11:09:53 PM
 * To change this template use File | Settings | File Templates.
 */

URL url = new URL("http://www.aucklandairport.co.nz/FlightInformation/InternationalArrivalsAndDepartures.aspx");
def res = url.get();
String wholePage = res.text.toString();
def divBeginningTag = '<div id="FlightInfo_FlightInfoUpdatePanel">'
int endOfDivBeginningTag = wholePage.indexOf(divBeginningTag)+divBeginningTag.length();
int tableEnd = wholePage.indexOf("</table>",endOfDivBeginningTag);
String dataLine = wholePage.substring(endOfDivBeginningTag, tableEnd)
println new Date();
println dataLine;
System.out << dataLine;





