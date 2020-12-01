package com.example.shuliao_bombrate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static final SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat longHourSdf = new SimpleDateFormat("yyyy-MM-dd HH");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String toStr(String format, Date time) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat(format);
        }
        try {
            return df.format(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate(String source) {
        String formatString = "yyyy-MM-dd hh:mm:ss";
        if (source == null || "".equals(source.trim()))
            return null;
        source = source.trim();
        if (source.matches("^\\d{4}$")) {
            formatString = "yyyy";
        } else if (source.matches("^\\d{4}-\\d{1,2}$")) {
            formatString = "yyyy-MM";
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            formatString = "yyyy-MM-dd";
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}$")) {
            formatString = "yyyy-MM-dd hh";
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            formatString = "yyyy-MM-dd hh:mm";
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            formatString = "yyyy-MM-dd hh:mm:ss";
        } else if (source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$")) {
            formatString = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            Date date = sdf.parse(source);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getHourStartTime(Date date) {
        Date dt = null;
        try {
            dt = longHourSdf.parse(longHourSdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getHourEndTime(Date date) {
        Date dt = null;
        try {
            dt = longSdf.parse(longHourSdf.format(date) + ":59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getDayStartTime(Date date) {
        Date dt = null;
        try {
            dt = shortSdf.parse(shortSdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getDayEndTime(Date date) {
        Date dt = null;
        try {
            dt = longSdf.parse(shortSdf.format(date) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static int getWeekDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(7);
        return week_of_year - 1;
    }

    public static Date getWeekStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(7) - 2;
            c.add(5, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    public static Date getWeekEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(7);
            c.add(5, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    public static Date getMonthStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(5, 1);
            dt = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getMonthEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(5, 1);
            c.add(2, 1);
            c.add(5, -1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(2, 0);
            c.set(5, 1);
            dt = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        Date dt = null;
        try {
            c.set(2, 11);
            c.set(5, 31);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getQuarterStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(2, 0);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(2, 3);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(2, 6);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(2, 9);
            }
            c.set(5, 1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getQuarterEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(2, 2);
                c.set(5, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(2, 5);
                c.set(5, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(2, 8);
                c.set(5, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(2, 11);
                c.set(5, 31);
            }
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getHalfYearStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(2, 0);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(2, 6);
            }
            c.set(5, 1);
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static Date getHalfYearEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(2) + 1;
        Date dt = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6) {
                c.set(2, 5);
                c.set(5, 30);
            } else if (currentMonth >= 7 && currentMonth <= 12) {
                c.set(2, 11);
                c.set(5, 31);
            }
            dt = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    public static int getTenDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(5);
        if (i < 11)
            return 1;
        if (i < 21)
            return 2;
        return 3;
    }

    public static Date getTenDayStartTime(Date date) {
        int ten = getTenDay(date);
        try {
            if (ten == 1)
                return getMonthStartTime(date);
            if (ten == 2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-11");
                return shortSdf.parse(simpleDateFormat.format(date));
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-21");
            return shortSdf.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTenDayEndTime(Date date) {
        int ten = getTenDay(date);
        try {
            if (ten == 1) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-10 23:59:59.999");
                return longSdf.parse(df.format(date));
            }
            if (ten == 2) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-20 23:59:59.999");
                return longSdf.parse(df.format(date));
            }
            return getMonthEndTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getYearDayIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(6);
    }

    public static int getYearWeekIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(2);
        calendar.setTime(date);
        return calendar.get(3);
    }

    public static int getYearMonthIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getYeartQuarterIndex(Date date) {
        int month = getYearMonthIndex(date);
        if (month <= 3)
            return 1;
        if (month <= 6)
            return 2;
        if (month <= 9)
            return 3;
        return 4;
    }

    public static List<Date[]> yearDayList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date st = getDayStartTime(calendar.getTime());
            Date et = getDayEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(6, 1);
        }
        return result;
    }

    public static List<Date[]> yearWeekList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        calendar.setFirstDayOfWeek(2);
        while (calendar.getTime().before(endtm)) {
            Date st = getWeekStartTime(calendar.getTime());
            Date et = getWeekEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(3, 1);
        }
        return result;
    }

    public static List<Date[]> yearMonthList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date tm = calendar.getTime();
            Date st = getMonthStartTime(tm);
            Date et = getMonthEndTime(tm);
            result.add(new Date[]{st, et});
            calendar.add(2, 1);
        }
        return result;
    }

    public static List<Date[]> yearQuarterList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date st = getQuarterStartTime(calendar.getTime());
            Date et = getQuarterEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(2, 3);
        }
        return result;
    }

    public static List<Date[]> monthTenDayList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getMonthStartTime(date);
        Date endtm = getMonthEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            Date st = getTenDayStartTime(calendar.getTime());
            Date et = getTenDayEndTime(calendar.getTime());
            result.add(new Date[]{st, et});
            calendar.add(5, 11);
        }
        return result;
    }

    public static List<Date[]> yearTenDayList(Date date) {
        List<Date[]> result = (List) new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date starttm = getYearStartTime(date);
        Date endtm = getYearEndTime(date);
        calendar.setTime(starttm);
        while (calendar.getTime().before(endtm)) {
            result.addAll(monthTenDayList(calendar.getTime()));
            calendar.add(2, 1);
        }
        return result;
    }

    private static void test() {

    }

    private static void testYearDayList() {
        List<Date[]> datas = yearDayList(new Date());
        for (int i = 0; i < datas.size(); i++) {
            Date[] date = datas.get(i);
            System.out.println("+ (i + 1) + " + sdf.format(date[0]) + " " + sdf.format(date[1]));
        }
    }

    private static void testYearWeekList() {
        List<Date[]> datas = yearWeekList(new Date());
        for (int i = 0; i < datas.size(); i++) {
            Date[] date = datas.get(i);
            System.out.println("+ (i + 1) + " + sdf.format(date[0]) + " " + sdf.format(date[1]));
        }
    }

    private static void testYearQuarterList() {
        List<Date[]> datas = yearQuarterList(new Date());
        for (int i = 0; i < datas.size(); i++) {
            Date[] date = datas.get(i);
            System.out.println("+ (i + 1) + " + sdf.format(date[0]) + " " + sdf.format(date[1]));
        }
    }

    private static void testYearMonthList() {
        List<Date[]> datas = yearMonthList(new Date());
        for (int i = 0; i < datas.size(); i++) {
            Date[] date = datas.get(i);
            System.out.println("+ (i + 1) + " + sdf.format(date[0]) + " " + sdf.format(date[1]));
        }
    }

    private static void testMonthTenDayList() {
        List<Date[]> datas = monthTenDayList(new Date());
        for (int i = 0; i < datas.size(); i++) {
            Date[] date = datas.get(i);
            System.out.println("(+ (i % 3 + 1) + " + sdf.format(date[0]) + " " + sdf.format(date[1]));
        }
    }

    private static void testyearTenDayList() {

    }
}
