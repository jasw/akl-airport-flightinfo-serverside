package com.agilesquared.aklairportflightinfo.config;

import com.agilesquared.aklairportflightinfo.resources.FlightInfoList;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Provider
public final class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private final JAXBContext context;

    private final Set<Class> types;

    private final Class[] cTypes = {FlightInfoList.class};

    public JAXBContextResolver() throws Exception {
        this.types = new HashSet(Arrays.asList(cTypes));
        this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), cTypes);
    }

    public JAXBContext getContext(Class<?> objectType) {
        return (types.contains(objectType)) ? context : null;
    }
}