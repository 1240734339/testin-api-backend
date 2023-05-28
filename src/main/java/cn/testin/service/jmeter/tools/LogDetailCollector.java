package cn.testin.service.jmeter.tools;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WangG
 * @title: LogDetailCollector
 * @description: 收集日志到步骤详情
 */
public class LogDetailCollector {

    public static final String DEBUG = "DEBUG";
    public static final String INFO = "INFO";
    public static final String WARN = "WARN";
    public static final String ERROR = "ERROR";

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    private static final String logFormat = "%s  [%s]  %s ";

    private static final ConcurrentHashMap<String, LinkedList<String>> logCollector = new ConcurrentHashMap<String, LinkedList<String>>();

    /**
     * @param
     * @param msg
     * @param level
     * @return void
     * @Description
     **/
    public static void writeLog(String msg, String level, List<String> logList) {
        writeLog(System.currentTimeMillis(), msg, level,logList);
    }

    public static void writeLog(Long time, String msg, String level, List<String> logList) {
        Date date = new Date();
        date.setTime(time);
        String dateFormat = sdf.format(time);

        String log = "";
        if (DEBUG.equals(level)) {
            log = String.format(logFormat, dateFormat, level, msg);
        } else if (INFO.equals(level)) {
            log = String.format(logFormat, dateFormat, level, msg);
        } else if (WARN.equals(level)) {
            log = String.format(logFormat, dateFormat, level, msg);
        } else if (ERROR.equals(level)) {
            log = String.format(logFormat, dateFormat, level, msg);
        } else {
            log = String.format(logFormat, dateFormat, WARN, msg);
        }
        logList.add(log);
    }


}
