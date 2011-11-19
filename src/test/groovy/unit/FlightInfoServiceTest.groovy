import com.agilesquared.aklairportflightinfo.service.FlightInfoService
/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: 6/03/11
 * Time: 7:45 PM
 */
class FlightInfoServiceTest extends GroovyTestCase{



    void testGetDepartures(){
        def s = new FlightInfoService();
        def flights = s.getDepartures();
        assertTrue("could not retrieve any departure flights back ",flights.allFlightInfo.size()>0)
        println flights
    }

    void testGetArrivals(){
        def s = new FlightInfoService();
        def flights = s.getArrivals()
        assertTrue("cound not retrive any arrival flights back ",flights.allFlightInfo.size()>0)
        println flights;
    }

}
