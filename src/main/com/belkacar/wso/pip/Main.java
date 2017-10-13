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
        properties.setProperty("Endpoint", "https://abac-test.belkacar.ru/pip/1.0/");
        System.out.println("Start Application");

        /*
        init finder module
         */
        finderModule.init(properties);

        Set<String> attrib = finderModule.getAttributeValues("ru:belkacar:rent:fare", "subject",
                "res", "action", "env", "issuer");
        System.out.println(attrib.toString());

        System.out.println("Down Application");
    }
}
