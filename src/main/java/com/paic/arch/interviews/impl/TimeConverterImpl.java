package com.paic.arch.interviews.impl;

import com.paic.arch.interviews.TimeConverter;
import com.paic.arch.interviews.common.TimeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * @Author: HeGuanXun
 * @Description:
 * @Date: 10:14 2018/3/6
 */
public class TimeConverterImpl implements TimeConverter {

    private static final Logger logger = LoggerFactory.getLogger(TimeConverterImpl.class);

    @Override
    public String convertTime(String aTime) {

        this.testTheTimeIsCanUse(aTime);

        String[] timeAry = StringUtils.split(aTime, ":");

        String[] dateAry = new String[5];

        // 1.处理第一排秒
        dateAry[0] = Integer.parseInt(timeAry[2]) % 2 == 0 ? TimeUtils.ShowColor.Y.name() : TimeUtils.ShowColor.O.name();

        // 2.处理第二排小时 (每个灯代表5个小时)
        int hours = Integer.parseInt(timeAry[0]);
        int totalRedNum = hours / 5;
        dateAry[1] = buildResult(TimeUtils.BASE_SHOW_FOUR, 0, totalRedNum, TimeUtils.ShowColor.R.name());

        // 3.处理第三排小时 (每个灯代表一个小时)
        totalRedNum = hours % 5;
        dateAry[2] = buildResult(TimeUtils.BASE_SHOW_FOUR, 0, totalRedNum, TimeUtils.ShowColor.R.name());

        int minute = Integer.parseInt(timeAry[1]);
        // 4.处理第四排(每个灯代表五分钟)
        totalRedNum = minute / 5;
        dateAry[3] = buildResult(TimeUtils.BASE_SHOW_ELEVEN, totalRedNum, TimeUtils.BASE_SHOW_ELEVEN.length(),
                TimeUtils.ShowColor.O.name());

        // 5.处理第五排(每个灯代表1分钟)
        totalRedNum = minute % 5;
        dateAry[4] = buildResult(TimeUtils.BASE_SHOW_FOUR, 0, totalRedNum, TimeUtils.ShowColor.Y.name());
        return String.join(System.getProperty("line.separator"),dateAry) ;
    }

    /**
     *@Author:create by HeGuanXun
     *@Description:替换字符串
     *@param: * @param source,start,end,replaceChar
     *@date:11:57 2018/3/6
     */
    public String buildResult(String source, int start, int end, String replaceChar) {
        StringBuilder replaceStr = new StringBuilder();
        for (int i = 0; i < end - start; i++) {
            replaceStr.append(replaceChar);
        }
        return new StringBuffer(source).replace(start, end, replaceStr.toString()).toString();
    }

    /**
     *@Author:create by HeGuanXun
     *@Description:测试输入的时间是否有用
     *@param: * @param aTime
     *@date:11:07 2018/3/6
     */
    private void testTheTimeIsCanUse(String aTime){
        try {
           DateUtils.parseDate(aTime, new String[]{"HH:mm:ss"});
        } catch (ParseException e) {
            logger.error("aTime[{}] parameter is error", aTime);
            throw new RuntimeException(aTime + "enter the aTime is error");
        }
    }

    public static void main(String[] args) {
        TimeConverter converter = new TimeConverterImpl();
        String convertTime = converter.convertTime("13:17:00");
        System.out.println(convertTime);
    }
}
