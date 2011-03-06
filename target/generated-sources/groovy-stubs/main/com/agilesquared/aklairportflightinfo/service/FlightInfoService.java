package com.agilesquared.aklairportflightinfo.service;

import groovyx.net.http.HttpResponseDecorator;
import javax.ws.rs.GET;
import groovyx.net.http.HttpResponseException;
import groovyx.net.http.HttpURLClient;
import com.agilesquared.aklairportflightinfo.resources.FlightInfoList;
import javax.ws.rs.Produces;
import java.util.logging.Logger;
import groovyx.net.http.ContentType;
import com.agilesquared.aklairportflightinfo.AklAirportFlightInfoWebpageMarshaller;
import javax.ws.rs.Path;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
import groovy.lang.*;
import groovy.util.*;

@javax.ws.rs.Path(value="/rest/v1.0/flightinfo/") public class FlightInfoService
  extends java.lang.Object  implements
    groovy.lang.GroovyObject {
public FlightInfoService
() {}
public  groovy.lang.MetaClass getMetaClass() { return (groovy.lang.MetaClass)null;}
public  void setMetaClass(groovy.lang.MetaClass mc) { }
public  java.lang.Object invokeMethod(java.lang.String method, java.lang.Object arguments) { return null;}
public  java.lang.Object getProperty(java.lang.String property) { return null;}
public  void setProperty(java.lang.String property, java.lang.Object value) { }
@javax.ws.rs.GET() @javax.ws.rs.Path(value="/departures") @javax.ws.rs.Produces(value="application/json") public  com.agilesquared.aklairportflightinfo.resources.FlightInfoList getDepartures() { return (com.agilesquared.aklairportflightinfo.resources.FlightInfoList)null;}
@javax.ws.rs.GET() @javax.ws.rs.Path(value="/arrivals") @javax.ws.rs.Produces(value="application/json") public  com.agilesquared.aklairportflightinfo.resources.FlightInfoList getArrivals() { return (com.agilesquared.aklairportflightinfo.resources.FlightInfoList)null;}
public static  java.lang.String getHiddenParameter(java.lang.Object wholePage, java.lang.Object pName) { return (java.lang.String)null;}
public static  java.lang.String getWholePage() { return (java.lang.String)null;}
public  groovyx.net.http.HttpResponseDecorator getDeparturePage() { return (groovyx.net.http.HttpResponseDecorator)null;}
protected  groovy.lang.MetaClass $getStaticMetaClass() { return (groovy.lang.MetaClass)null;}
}
