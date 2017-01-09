package com.magicbooks.wp.magicbooks.utils;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;


import com.magicbooks.wp.magicbooks.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TimeUtils {
    private static final long SECOND = 1000;
    private static final long MINUTE = 60 * SECOND;
    private static final long HOUR = 60 * MINUTE;
    private static final long DAY = 24 * HOUR;
    private static final long MONTH = 12 * DAY;

    /**
     * 格式化日期时间
     *
     * @param orgDate    传入原始的日期时间
     * @param formatType 日期时间的类型
     * @return 根据类型返回格式化后的数据
     */
    public static String formatDateTime(String orgDate, int formatType) {
        SimpleDateFormat format = null;
        String data = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(orgDate);
            switch (formatType) {
                case 0:
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    data = format.format(date);
                    break;
                case 1:
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    data = format.format(date);
                    break;
                case 2:
                    format = new SimpleDateFormat("HH:mm");
                    data = format.format(date);
                    break;
                case 3://2015-07-28
                    data = orgDate.substring(orgDate.indexOf("-") + 1);
                    break;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orgDate;
    }

    public final static String getFormatDate(String s2) { //设定时间的模板
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdft = new SimpleDateFormat("MM月dd号 HH时mm分");
        Date d1 = null;

        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        long in = System.currentTimeMillis() - d1.getTime();

        if (in >= 0) {
            if (in < MINUTE) {
                return "刚刚";
            } else if (in >= MINUTE && in < HOUR) {
                return (in / MINUTE) + "分钟前";
            } else if (in >= HOUR && in < DAY) {
                return (in / HOUR) + "小时前";
            } else if (in >= DAY) {
                return sdft.format(d1);
            }
        }
        return sdft.format(d1);
    }


    /**
     * 数据上报时间专用 时分
     *
     * @param orgDate
     * @return
     */
    public static String formatDateTime(String orgDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(orgDate);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            return format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orgDate;
    }

    /**
     *  判断时间是否晚于当前时间
     * @return 返回是否晚于当前时间
     * @throws Exception
     */
    public static boolean dateCompare(String s2) throws Exception { //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        String date = sdf.format(new Date());
        Date d1 = sdf.parse(s2);
        Date d2 = sdf.parse(date);
        if (d2.getTime() - d1.getTime() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
        *  判断时间是否晚于当前时间
        * @return 返回是否晚于当前时间yyyy-MM-dd
        * @throws Exception
        */
    public static boolean dateCompare(String s2, Boolean onlyDay) throws Exception { //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //得到指定模范的时间
        String date = sdf.format(new Date());
        Date d1 = sdf.parse(s2);
        Date d2 = sdf.parse(date);
        if (d2.getTime() - d1.getTime() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * *
     * 判断是否是今天
     *
     * @param currDate
     * @return
     * @throws Exception
     */
    public static boolean isTodayData(String currDate) throws Exception {
        String orgDate = null;
        String todayDate = null;
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sf.format(date);
        try {
            Calendar cal = Calendar.getInstance();//今天
            cal.setTime(sf.parse(nowDate));
            todayDate = sf.format(cal.getTime());
        } catch (Exception e) {

        }
        orgDate = currDate.split(" ")[0].toString();
        if (orgDate.equals(todayDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * *
     * 获取日期 是今天、明天、还是未来(加上星期)
     *
     * @return
     * @throws Exception
     */
    public static String getDateWeekStr(String currDate) throws Exception {
        String orgDate = null;
        String todayDate = null;
        String nextDate = null;
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String nowDate = sf.format(date);

        try {
            Calendar cal = Calendar.getInstance();//今天
            cal.setTime(sf.parse(nowDate));
            todayDate = sf.format(cal.getTime());

            Calendar nexCal = Calendar.getInstance();//明天
            nexCal.setTime(sf.parse(nowDate));
            nexCal.add(Calendar.DAY_OF_YEAR, +1);
            nextDate = sf.format(nexCal.getTime());
            //Logger.d("dive", "获得明天日期为=" + nextDate);
        } catch (Exception e) {
            // TODO: handle exception
        }
        orgDate = currDate.split(" ")[0].toString();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd EEEE", Locale.CHINA);
        SimpleDateFormat sdfym = new SimpleDateFormat("yyyy_MM-dd EEEE", Locale.CHINA);
        String todayWeek = sdf.format(sf.parse(orgDate)).substring(sdf.format(sf.parse(orgDate)).lastIndexOf(" "));


        Date d1 = null;
        try {
            d1 = sf.parse(currDate);
        } catch (Exception e) {
            d1 = new Date();
        }
        if (orgDate.equals(todayDate)) {
            return "今天" + " " + todayWeek;
        } else if (orgDate.equals(nextDate)) {
            return "明天" + " " + todayWeek;
        } else {
//            Logger.d("dive", "3" + d1.getYear() + new Date().getYear());
            if (d1.getYear() != new Date().getYear()) {
//                Logger.d("dive", "1" + nowDate + todayWeek);
                return orgDate + todayWeek;
            } else {
//                Logger.d("dive", "2" + sdf.format(sf.parse(orgDate)));
                return sdf.format(sf.parse(orgDate));
            }
        }
    }

    /**
     * @param time       多少时间倒计时
     * @param sec        隔多久数字变一次
     * @param mTextField 控件
     * @param showString 显示前面的值
     */
    public static CountDownTimer countDown(int time, int sec, final TextView mTextField, final String showString, final String finalShowString) {
        final CountDownTimer timer = new CountDownTimer(time, sec) {
            public void onTick(long millisUntilFinished) {
                mTextField.setText(" " + millisUntilFinished / 1000 + showString);
                if (millisUntilFinished / 1000 == 1) {
                    this.cancel();
                }
            }

            public void onFinish() {
                mTextField.setText(finalShowString);
            }
        }.start();
        return timer;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNowDateAndTime() {
        String nowTime = null;
        Date rightNow = new Date();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        nowTime = format1.format(rightNow);
        return nowTime;
    }

    /**
     * "HH"
     *
     * @return
     */
    public static String getNowHourTime() {
        String nowTime = null;
        Date rightNow = new Date();
        DateFormat format1 = new SimpleDateFormat("HH");
        nowTime = format1.format(rightNow);
        return nowTime;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @return 当前之后的2小时时间
     */
    public static String getCurrTo2HourTime() {
        Date nowTime = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2);
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String retStrFormatNowDate = sdFormatter.format(nowTime);

        return retStrFormatNowDate;
    }

    /**
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNowDate() {
        String nowTime = null;
        Date rightNow = new Date();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        nowTime = format1.format(rightNow);
        return nowTime;
    }

    /**
     * 获取当前年
     *
     * @return yyyy
     */
    public static String getNowYear() {
        String nowYear = null;
        Date rightNow = new Date();
        DateFormat format1 = new SimpleDateFormat("yyyy");
        nowYear = format1.format(rightNow);
        return nowYear;
    }

    /**
     * 获取上一年/下一年
     * unit 年份间隔数
     * @return yyyy
     */
    public static String getLastYear(int unit) {
        DateFormat format1 = new SimpleDateFormat("yyyy");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.YEAR, unit);//日期减1年
        Date date = rightNow.getTime();
        return format1.format(date);
    }

    /**
     * date 当前日期
     * taktTime 和当前日期间隔天数
     *
     * @return 如果格式化出错则返回当前日期
     */
    public static String getNextDay(String remind_at, int taktTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(remind_at);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, taktTime);
            return sdf.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(new Date());
    }

    public static Date startTime;

    public static long getTimeInterval(Date stopTime) {
        return (stopTime.getTime() - startTime.getTime()) / 1000;
    }

    public static String format(long ms) {//将毫秒数换算成x天x时x分x秒x毫秒
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
//        return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;//00 00：00：00 000格式
        return (day == 0 ? "" : day + "天") + (hour == 0 ? "" : hour + "小时") + (minute == 0 ? "" : minute + "分钟");//得到天小时分格式

    }

    /**
     * 返回当前是星期值
     *
     * @param date
     * @return
     * @throws Throwable
     */
    public static String dayForWeek(String date) {
        String Week = "星期";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//也可将此值当参数传进来
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(date));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                Week += "天";
                break;
            case 2:
                Week += "一";
                break;
            case 3:
                Week += "二";
                break;
            case 4:
                Week += "三";
                break;
            case 5:
                Week += "四";
                break;
            case 6:
                Week += "五";
                break;
            case 7:
                Week += "六";
                break;
            default:
                break;
        }
        return Week;
    }


    /**
     * 日期 时间的返回处理
     *
     * @param orgDate
     * @param type
     * @return
     */
    public static String formatDateOrTime(String orgDate, int type) {
        String data = "";
        try {
            String[] dates = orgDate.split(" ");
            switch (type) {
                case 0:
                    data = orgDate;
                    break;
                case 1:
                    data = dates[0];
                    break;
                case 2:
                    if (StringUtils.strIsEmpty(dates[1]) && dates[1].length() > 5) {
                        data = dates[1].substring(0, 5);
                    } else {
                        data = dates[1];
                    }
                    break;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 格式化不要年份
     *
     * @param s2
     * @return
     */
    public static String formatDateWithoutYear(String s2) {
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdfd = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdft = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        long in = System.currentTimeMillis() - d1.getTime();
        try {
            if (in >= 0) {
                return sdft.format(d1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sdft.format(d1);
    }

    /**
     * 数据上报时间专用 时分
     *
     * @param s2
     * @return
     */
    public static String formatDateCirclesTime(String s2) {
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdfd = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdft = new SimpleDateFormat("MM-dd HH:mm");
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = null;
        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        long in = System.currentTimeMillis() - d1.getTime();
        try {
            if (in >= 0) {
                if (in < MINUTE) {
                    return "刚刚";
                } else if (in >= MINUTE && in < HOUR) {
                    return (in / MINUTE) + "分钟前";
                } else if (isYeaterday(d1, new Date()) == -1) {
//                return (in / HOUR) + "小时前";
                    return sdfd.format(d1);
                } else if (isYeaterday(d1, new Date()) == 0) {
                    return sdft.format(d1);
                } else if (d1.getYear() != new Date().getYear()) {
                    return sdfm.format(d1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sdft.format(d1);
    }

    /**
     * 附近客户时间专用 时分，超过一天就不显示时间，仅显示日期；
     *
     * @param s2
     * @return
     */
    public static String formatDateNearByTime(String s2) {
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdfd = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdft = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        long in = System.currentTimeMillis() - d1.getTime();
        try {
            if (in >= 0) {
                if (in < MINUTE) {
                    return "刚刚";
                } else if (in >= MINUTE && in < HOUR) {
                    return (in / MINUTE) + "分钟前";
                } else if (isYeaterday(d1, new Date()) == -1) {
//                return (in / HOUR) + "小时前";
                    return sdfd.format(d1);
                } else if (isYeaterday(d1, new Date()) == 0) {
                    return sdft.format(d1);
                } else if (d1.getYear() != new Date().getYear()) {
                    return sdfm.format(d1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sdft.format(d1);
    }

    /**
     * 工作报告时间专用 时分
     *
     * @param s2
     * @return
     */
    public static String formatDateDataReportTime(String s2) {
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        return sdfm.format(d1);
    }

    public static String formatDateYearTime(String s2) {
        if (TextUtils.isEmpty(s2)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        SimpleDateFormat sdft = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdfm = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        try {
            d1 = sdf.parse(s2);
        } catch (Exception e) {
            d1 = new Date();
        }
        try {
            if (d1.getYear() != new Date().getYear()) {
                return sdfm.format(d1);
            } else {
                return sdft.format(d1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sdft.format(d1);
    }

    /**
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天.
     * @author longyanliang
     */
    public static int isYeaterday(Date oldTime, Date newTime) throws Exception {
        if (newTime == null) {
            newTime = new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = format.parse(todayStr);
        //昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return 0;
        } else if ((today.getTime() - oldTime.getTime()) <= 0) { //至少是今天
            return -1;
        } else { //至少是前天
            return 1;
        }

    }

    /**
     * 获取当前的时间毫秒数 拍照保存图片用
     *
     * @return
     */
    public static String getCurrTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public static String getCurrentDateMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(new Date());
    }

    public static String getNextDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(c.getTime());
        return time;
    }

    public static String getNextDateMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM");
        String time = format.format(c.getTime());
        return time;
    }

    /**
     * 是否是当前时间24小时内
     *
     * @param create_at
     * @return
     */
    public static boolean getAfterDate(String create_at) {
        Date d1 = null;
        int day = 1000 * 60 * 60 * 24;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //得到指定模范的时间
        try {
            d1 = sdf.parse(create_at);
        } catch (Exception e) {
            d1 = new Date();
        }
        long in = System.currentTimeMillis() - d1.getTime();
        return in > day;
    }

    public static boolean isCommentToday(String currDate, String lastDate) {
        String orgDate = "", todayDate = "";
        if (!StringUtils.strIsEmpty(currDate) && !StringUtils.strIsEmpty(lastDate)) {
            orgDate = currDate.split(" ")[0].toString();
            todayDate = lastDate.split(" ")[0].toString();
        }
        return orgDate.equals(todayDate);
    }

    /**
     * *
     * 获取日期 是今天、昨天、还历史
     *
     * @return
     * @throws Exception
     */
    public static SpannableStringBuilder getDateStr(Context context, String currDate) {
        String orgDate = "", todayDate = "", lastDate = "";
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String nowDate = sf.format(date);

        try {
            Calendar cal = Calendar.getInstance();//今天
            cal.setTime(sf.parse(nowDate));
            todayDate = sf.format(cal.getTime());

            Calendar lastCal = Calendar.getInstance();//昨天
            lastCal.setTime(sf.parse(nowDate));
            lastCal.add(Calendar.DAY_OF_YEAR, -1);
            lastDate = sf.format(lastCal.getTime());
            //Logger.d("dive", "获得明天日期为=" + nextDate);
        } catch (Exception e) {
            // TODO: handle exception
        }
        orgDate = currDate.split(" ")[0].toString();

        Resources resource = (Resources) context.getResources();
        ColorStateList redColors = (ColorStateList) resource.getColorStateList(R.color.color_black);

        SpannableStringBuilder spanBuilder = null;
        if (orgDate.equals(todayDate)) {
            spanBuilder = new SpannableStringBuilder("今天");
        } else if (orgDate.equals(lastDate)) {
            spanBuilder = new SpannableStringBuilder("昨天");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date d1 = null;
            try {
                d1 = sdf.parse(currDate);
            } catch (Exception e) {
                d1 = new Date();
            }
            SimpleDateFormat sdft = new SimpleDateFormat("ddMM月");
            try {
                String resultDate = sdft.format(d1);
                String newResultDate = resultDate.substring(0, 2) + resultDate.substring(2, 4).
                        replace("01", "一").replace("02", "二").replace("03", "三")
                        .replace("04", "四").replace("05", "五").replace("06", "六")
                        .replace("07", "七").replace("08", "八").replace("09", "九")
                        .replace("10", "十").replace("11", "十一").replace("12", "十二")
                        + resultDate.substring(4);
                spanBuilder = new SpannableStringBuilder(newResultDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int textSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_30);

        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, textSize, redColors, null), 0, 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return spanBuilder;
    }

    /**
     * 判断第二个日期是否在第一个日期之后，如果是返回true，如果不是返回false
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBefore(Date date1, Date date2) {
        if (areSameDay(date1, date2)) {
            return true;
        } else {
            if (date1.before(date2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean areSameDay(Date dateA, Date dateB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(dateA);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(dateB);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是否是今天
     */
    public static boolean isToday(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        if (date.equals(nowDate)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否本周
     */
    public static boolean isCurrentWeek(String s2)throws Exception {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = sf.format(monday);

        return preMonday.equals(s2);
    }

    /**
     * 判断是否本月
     */
    public static boolean isCurrentMonth(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String nowDate = sdf.format(new Date());
        if (date.equals(nowDate)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getWeek(String pTime) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return "星期" + Week;
    }

    /***
     * 判断是否是当天之前
     * @param s2
     * @param format
     * @return
     * @throws Exception
     */
    public static boolean dateCompare(String s2, String format) throws Exception { //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat(format); //得到指定模范的时间
        String date = sdf.format(new Date());
        Date d1 = sdf.parse(s2);
        Date d2 = sdf.parse(date);
        if (d2.getTime() - d1.getTime() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 判断是否是本周之前 不含本周
     * @param s2
     * @param s2
     * @return
     * @throws Exception
     */
    public static boolean dateCompareWeek(String s2) throws Exception { //设定时间的模板
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 1) {
            mondayPlus = 0;
        } else {
            mondayPlus = 1 - dayOfWeek;
        }
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = sf.format(monday);

        Date d1 = sf.parse(preMonday);//本周第一天
        Date d2 = sf.parse(s2);
        if (d1.getTime() - d2.getTime() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 判断是否是本月
     * @param month
     * @param year
     * @return
     * @throws Exception
     */
    public static int dateCompareWithYearAndMonth(String year, String month) throws Exception { //设定时间的模板
        //Logger.d("dive",year+month);
        if(month.startsWith("0")){
            month = month.substring(1);
        }
        if(Integer.parseInt(year)==Integer.parseInt(TimeUtils.getNowYear())){
            //Logger.d("dive","同一年");
            if(Integer.parseInt(TimeUtils.getCurrentMonthWithOutZear())==Integer.parseInt(month)){
                //Logger.d("dive","同一年同一月");
                return  0;
            }else if(Integer.parseInt(TimeUtils.getCurrentMonthWithOutZear())<Integer.parseInt(month)){
                //Logger.d("dive","同一年将来月");
                return 1;//将来
            }else {
                //Logger.d("dive","同一年过去月");
                return -1;
            }
        }else if(Integer.parseInt(year)<Integer.parseInt(TimeUtils.getNowYear())){
            //Logger.d("dive","过去年");
            return -1;
        }else {
            //Logger.d("dive","将来年");
            return 1;
        }

    }

    public static String getCurrentMonthWithOutZear() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return  month+"";
    }

    /***
     * 判断是否是某天之前
     * @param currDate
     * @return
     * @throws Exception
     */
    public static boolean isAfterDate(String currDate) throws Exception { //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //得到指定模范的时间
        String lastDate = "2014-01-01";
        Date d1 = sdf.parse(lastDate);
        Date d2 = sdf.parse(currDate);
        if (d2.getTime() - d1.getTime() >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 根据月份判断获取月份最后一天
     * @param month
     * @return
     * @throws Exception
     */
    public static String getMonthLastDay(int month) { //设定时间的模板
        String lastDate = "-31";
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                lastDate = "-31";
                break;
            case 2:
                lastDate = "-28";
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                lastDate = "-30";
                break;
        }
        return lastDate;
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getCurrMonthLastDay(String yearMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(yearMonth));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return sdf.format(calendar.getTime());
    }

    public static String getTextMonthByNumMoonth(String yearMonth) {
        String newResultDate = yearMonth.
                replace("01", "一").replace("02", "二").replace("03", "三")
                .replace("04", "四").replace("05", "五").replace("06", "六")
                .replace("07", "七").replace("08", "八").replace("09", "九")
                .replace("10", "十").replace("11", "十一").replace("12", "十二");
        return newResultDate;
    }
    public static String getWithoutZeroDay(String yearMonth) {
        String newResultDate = yearMonth.
                replace("01", "1").replace("02", "2").replace("03", "2")
                .replace("04", "4").replace("05", "5").replace("06", "6")
                .replace("07", "7").replace("08", "8").replace("09", "9")
                .replace("10", "10").replace("11", "11").replace("12", "12");
        return newResultDate;
    }

    public static int isMonthType(String type){
        if(type.equals("year") || type.equals("quarter")){
            return 0;
        } else if(type.equals("other") || type.equals("month") || type.equals("week")){
            return 1;
        } else {
            return 2;
        }
    }
}
