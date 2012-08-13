package funktest

import com.agilesquared.aklairportflightinfo.service.FlightInfoService
/**
 * Created by IntelliJ IDEA.
 * User: jasonw
 * Date: 6/03/11
 * Time: 7:45 PM
 */
class FlightInfoServiceTest extends GroovyTestCase{

    def s = new FlightInfoService()

    void testGetDepartures(){
        def flights = s.getDepartures();
        assertTrue("could not retrieve any departure flights back ",flights.allFlightInfo.size()>0)
        println flights
    }

    void testGetArrivals(){
        def flights = s.getArrivals()
        assertTrue("cound not retrive any arrival flights back ",flights.allFlightInfo.size()>0)
        println flights;
    }

    void testGetDeparturesWithRange(){
        def flights = s.getDepartures(100,100000)
        flights = s.getDepartures(-100,100000)
        assertTrue(flights.allFlightInfo.size() == 0)
    }

    void testGetArrivalsWithRange(){
        def
        flights = s.getArrivals(0,1)
        assertTrue(flights.allFlightInfo.size()==1)
    }
}
