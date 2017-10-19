package com.belkacar.wso.pip;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public final class Main {

    static RestAttributeFinderModule finderModule = new RestAttributeFinderModule();
    static Properties properties = new Properties();

    private Main(){}

    public static void main(String[] args) throws Exception {

        /*
        init properties for finder module
         */
        properties.setProperty("Endpoint", "http://abac-dev.belkacar.ru:8080/api/1.0/");
        System.out.println("Start Application");

        /*
        init finder module
         */
        finderModule.init(properties);

        Set<String> attribs = finderModule.getSupportedAttributes();
        System.out.println(attribs.toString());

        Set<String> attrib = finderModule.getAttributeValues(
                "2",
                "resource",
                "read",
                "env",
                "ru:belkacar:verification:working-shift:role",
                "issuer"
        );
        System.out.println(attrib.toString());

        System.out.println("Down Application");
    }
}
