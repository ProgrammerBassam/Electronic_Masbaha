package com.bassambadr.electronicmasbaha.activities.log;

/**
 * Created by Bassam on 8/11/2015.
 */
public class LogItem {

    private String LogName;
    private String LogCount;

    public LogItem(String logName, String logCount) {
        LogName = logName;
        LogCount = logCount;
    }

    public String getLogName() {
        return LogName;
    }

    public String getLogCount() {
        return LogCount;
    }

}
