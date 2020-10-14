package com.Config;
import com.Config.DisplayConfigure;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

import static com.Application.Configures;

public class ConfigureController {
    public static DisplayConfigure getDisplayConfigure(){
        return Configures.getDisplayConfigure();
    }

    public static Route fetchDisplayConfigure = (Request request, Response resp)->{

        return resp;
    };


}
