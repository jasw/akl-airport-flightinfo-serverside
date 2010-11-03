package com.agilesquared.aklairportflightinfo.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.GET;
import com.agilesquared.aklairportflightinfo.resources.FlightInfoList;
import javax.ws.rs.Produces;
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
@javax.ws.rs.GET() @javax.ws.rs.Produces(value="application/json") public  com.agilesquared.aklairportflightinfo.resources.FlightInfoList getFlightInfoList() { return (com.agilesquared.aklairportflightinfo.resources.FlightInfoList)null;}
public static  void main(java.lang.String[] args) { }
protected  groovy.lang.MetaClass $getStaticMetaClass() { return (groovy.lang.MetaClass)null;}
}
