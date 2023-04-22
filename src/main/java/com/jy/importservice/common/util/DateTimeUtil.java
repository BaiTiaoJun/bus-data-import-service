package com.jy.importservice.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jy.importservice.common.constants.ExpConstants;
import com.jy.importservice.common.enums.DataIntervalUnitEnum;
import com.jy.importservice.common.enums.ReturnEnum;
import com.jy.importservice.common.exception.GlobalException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @类名 DateUtil
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/16 22:32
 * @版本 1.0
 */
public class DateTimeUtil {

    public static Date getNow() {
        return new Date();
    }

    //更新时间
    public static Date updateDataCircleTime(String unit, Integer dataIntervalValue, Date nextStartTime) throws GlobalException {
        //创建Calender
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextStartTime);

        //转换类型
        Integer unitCode = Integer.valueOf(unit);

        //根据不同的单位进行计算
        String format = null;
        if (unitCode.equals(DataIntervalUnitEnum.MONTH.getCode())) {
            calendar.add(Calendar.MONTH, dataIntervalValue);
            format = "yyyy-MM-dd";
        } else if (unitCode.equals(DataIntervalUnitEnum.DAY.getCode())) {
            calendar.add(Calendar.DAY_OF_YEAR, dataIntervalValue);
            format = "yyyy-MM-dd";
        } else if (unitCode.equals(DataIntervalUnitEnum.HOUR.getCode())) {
            calendar.add(Calendar.HOUR_OF_DAY, dataIntervalValue);
            format = "yyyy-MM-dd HH:mm";
        } else if (unitCode.equals(DataIntervalUnitEnum.MINUTES.getCode())) {
            calendar.add(Calendar.MINUTE, dataIntervalValue);
            format = "yyyy-MM-dd HH:mm";
        }
        return DateTimeUtil.format(format, calendar);
    }

    private static DateTime format(String format, Calendar calendar) {
        LocalDateTime localDateTime = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return DateUtil.parse(formatter.format(localDateTime));
    }

    public static String formatDataCircle(Date dataCircle, String unit) throws GlobalException {
        Integer unitCode = Integer.valueOf(unit);
        String dataCircleStr = dataCircle.toString();
        String[] split = null;
        if (unitCode.equals(DataIntervalUnitEnum.MONTH.getCode())) {
            int index = dataCircleStr.lastIndexOf("-");
            dataCircleStr = dataCircleStr.substring(0, index);
            split = dataCircleStr.split("-");
        } else if (unitCode.equals(DataIntervalUnitEnum.DAY.getCode())) {
            int index = dataCircleStr.lastIndexOf(" ");
            dataCircleStr = dataCircleStr.substring(0, index);
            split = dataCircleStr.split("-");
        } else if (unitCode.equals(DataIntervalUnitEnum.HOUR.getCode())) {
            int index = dataCircleStr.indexOf(":");
            dataCircleStr = dataCircleStr.substring(0, index);
            split = dataCircleStr.split("-|\\s");
        } else if (unitCode.equals(DataIntervalUnitEnum.MINUTES.getCode())) {
            int index = dataCircleStr.lastIndexOf(":");
            dataCircleStr = dataCircleStr.substring(0, index);
            split = dataCircleStr.split("-|\\s|:");
        }
        if (ObjectUtil.isNull(split)) {
            throw new GlobalException(ReturnEnum.SERVER_ERROR.getCode(), ExpConstants.FORMAT_PARSE_EXP);
        }
        return StringUtil.concat(new StringBuffer(), split);
    }
}
