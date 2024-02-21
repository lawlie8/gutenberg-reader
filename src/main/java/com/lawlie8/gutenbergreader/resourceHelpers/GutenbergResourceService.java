package com.lawlie8.gutenbergreader.resourceHelpers;

import org.springframework.stereotype.Service;

import java.net.URLConnection;

@Service
public class GutenbergResourceService {

    public boolean fetchDailyRDFFiles(){
        URLConnection connection = source.openConnection();
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(25000);
        return true;
    }

}
