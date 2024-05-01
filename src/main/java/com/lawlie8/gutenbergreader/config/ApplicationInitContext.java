package com.lawlie8.gutenbergreader.config;

import com.lawlie8.gutenbergreader.util.XMLReaderUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitContext implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    XMLReaderUtilService xmlReaderUtilService;



    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    public ApplicationInitContext() {
        super();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        xmlReaderUtilService.downLoadRssDailyBooks();
    }
}
