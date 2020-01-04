package org.jeecg.modules.utils;

import cn.hutool.json.JSONTokener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.project.entity.TreeModel;
import org.jeecg.modules.project.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: GH
 * @Date: 2019/6/30 10:34
 * @Version 1.0
 * <p>
 * 通用方法
 */
public class BaseUtils {
    // slf4j-Logger
    private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);
    /**
     * 将序列化的表单转化为model
     *
     * @param model
     * @param data
     */
    private static final String DATECLASS = "java.util.Date";
    /**
     * URL UTF-8 转码
     *
     * @param str
     * @return
     */
    private static final String UNDEFINED = "undefined";

    /**
     * 日志输出(普通)
     *
     * @param str
     */
    public static void loggerDebug(String str) {
        logger.debug(str);
    }

    /**
     * 日志输出(占位符)
     *
     * @param str
     * @param obj
     */
    public static void loggerDebug(String str, Object[] obj) {
        logger.debug(str, obj);
    }

    /**
     * 警告日志输出(普通)
     *
     * @param str
     */
    public static void loggerWarn(String str) {
        logger.warn(str);
    }

    /**
     * 警告日志输出(占位符)
     *
     * @param str
     */
    public static void loggerWarn(String str, Object[] obj) {
        logger.warn(str, obj);
    }

    /**
     * 异常日志输出
     *
     * @param e
     * @return
     */
    public static String loggerError(Throwable e) {
        String msg = "";
        if (e instanceof NullPointerException) {
            msg = e.toString();
        } else {
            msg = e.getMessage();
        }
        logger.error(msg, e);
        return msg;
    }

    public static void serializeArray2Model(Object model, String data) {
        Map dataMap = jsonArrStr2Map(data);
        try {
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0); // 解决Bigdecimal为null报错问题
            //ConvertUtils.register(new DateLocaleConverter(), Date.class); // Date注册的转化器
            //2019-04-08 17:27:56
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class arg0, Object arg1) {
                    if (!(DATECLASS.equals(arg0.getName()))) {
                        return null;
                    }
                    BaseUtils.loggerDebug("注册字符串转换date类型转换器");
                    if (arg1 == null) {
                        return null;
                    }
                    // 毫秒数处理
                    if (arg1 instanceof Long && (((Long) arg1).toString()).length() == 13) {
                        // 强制转毫秒数
                        Date timeMillis = new Date();
                        try {
                            timeMillis.setTime((Long) arg1);
                            return timeMillis;
                        } catch (Exception e4) {
                            BaseUtils.loggerDebug("非时间戳字符串或暂未支持的日期字符串类型：{}", new Object[]{arg1});
                            return arg1;
                        }
                    }
                    if (!(arg1 instanceof String)) {
                        return arg1;
                    } else {
                        String str = (String) arg1;
                        if (StringUtils.isBlank(str)) {
                            return null;
                        }
                        // 开始转换日期
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            return sd.parse(str);
                        } catch (ParseException e) {
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try {
                                return sd1.parse(str);
                            } catch (ParseException e1) {
                                SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    return sd2.parse(str);
                                } catch (ParseException e2) {
                                    SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd");
                                    try {
                                        return sd3.parse(str);
                                    } catch (ParseException e3) {
                                        BaseUtils.loggerDebug("非日期字符串或暂未支持的日期字符串类型：{}", new Object[]{str});
                                        return arg1;
                                    }
                                }
                            }
                        }
                    }
                }
            }, Date.class); // Date注册的转化器（扩展）

            org.apache.commons.beanutils.BeanUtils.populate(model, dataMap);
        } catch (Exception e) {
            loggerError(e);
            throw new BusinessException("serializeArray转换model失败，" + e.getMessage());
        }
    }

    /**
     * 将request参数转化为model
     *
     * @param request
     * @return
     */
    public static void request2Model(Object model, HttpServletRequest request) { // 返回值为随意的类型传入数为request和该随意类型
        try {
            Enumeration en = request.getParameterNames(); //获得参数的一个列举
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);// 解决Bigdecimal为null报错问题
            //ConvertUtils.register(new DateLocaleConverter(), Date.class); // Date注册的转化器

            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class arg0, Object arg1) {
                    if (!DATECLASS.equals(arg0.getName())) {
                        return null;
                    }
                    BaseUtils.loggerDebug("注册字符串转换date类型转换器");
                    if (arg1 == null) {
                        return null;
                    }
                    // 毫秒数处理
                    if (arg1 instanceof Long && (((Long) arg1).toString()).length() == 13) {
                        // 强制转毫秒数
                        Date timeMillis = new Date();
                        try {
                            timeMillis.setTime((Long) arg1);
                            return timeMillis;
                        } catch (Exception e4) {
                            BaseUtils.loggerDebug("非时间戳字符串或暂未支持的日期字符串类型：{}", new Object[]{arg1});
                            return arg1;
                        }
                    }
                    if (!(arg1 instanceof String)) {
                        return arg1;
                    } else {
                        String str = (String) arg1;
                        if (StringUtils.isBlank(str)) {
                            return null;
                        }
                        // 开始转换日期
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            return sd.parse(str);
                        } catch (ParseException e) {
                            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            try {
                                return sd1.parse(str);
                            } catch (ParseException e1) {
                                SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    return sd2.parse(str);
                                } catch (ParseException e2) {
                                    SimpleDateFormat sd3 = new SimpleDateFormat("yyyy/MM/dd");
                                    try {
                                        return sd3.parse(str);
                                    } catch (ParseException e3) {
                                        SimpleDateFormat sd4 = new SimpleDateFormat("yyyy-MM");
                                        try {
                                            return sd4.parse(str);
                                        } catch (ParseException e4) {
                                            SimpleDateFormat sd5 = new SimpleDateFormat("yyyy");
                                            try {
                                                return sd5.parse(str);
                                            } catch (ParseException e5) {
                                                BaseUtils.loggerDebug("非日期字符串或暂未支持的日期字符串类型：{}", new Object[]{str});
                                                return arg1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }, Date.class); // Date注册的转化器（扩展）

            //遍历列举来获取所有的参数
            while (en.hasMoreElements()) {
                String name = (String) en.nextElement();
                String value = BaseUtils.encodeUTF8(request.getParameter(name));
                org.apache.commons.beanutils.BeanUtils.setProperty(model, name, value.trim());
            }
        } catch (Exception e) {
            BaseUtils.loggerError(e);
            throw new BusinessException("request转换model失败，" + e.getMessage());
        }
    }

    /**
     * 将JSONArray字符串转成map(支持JSON对象，数组只支持name： value：形式)，前台jquery serializeArray()方法专用
     *
     * @param data
     * @return
     */
    public static Map<String, Object> jsonArrStr2Map(String data) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 获得你字符串所属的对象
        Object json = new JSONTokener(data).nextValue();
        if (json instanceof JSONObject) {
            JSONObject jsonObj = JSONObject.parseObject(data);
            resultMap = JSONObject.parseObject(jsonObj.toJSONString(), new TypeReference<Map<String, Object>>() {
            });
        } else if (json instanceof JSONArray) {
            JSONArray jsonArray = JSONObject.parseArray(data);

            for (Object o : jsonArray) {
                JSONObject jsonObj = (JSONObject) o;
                resultMap.put(jsonObj.getString("name"), jsonObj.getString("value"));
            }
        } else {
            throw new BusinessException("无效的JSON字符串！");
        }
        return resultMap;
    }

    public static String encodeUTF8(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        }
        if (UNDEFINED.equals(str)) {
            return "";
        }
        if (str.equals(new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1))) {
            return new String(str.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).trim();
        } else {
            return str;
        }
    }


    /**
     * 获取 文件上传本地磁盘路径 资产档案
     * @return
     */
//    public static String getAssetFilePath(String localPath) {
////        String localPath = "D:"+ File.separator +"assets_management"+ File.separator +"upload"+ File.separator +"AssetFiles"+ "~usr" + File.separator + "local" + File.separator + "ZCCS";
//        if(StringUtils.isBlank(localPath)){
//            throw new BusinessException("未找到文件上传本地磁盘路径配置！");
//        }
//        String[] localPathArr = localPath.split("~");
//        String os = System.getProperty("os.name");
//        if (os.toLowerCase().startsWith("win")) {
//            return localPathArr[0];
//        } else {
//            if(localPathArr.length < 2){
//                throw new BusinessException("未找到文件上传本地磁盘Linux路径配置！");
//            }
//            return localPathArr[1];
//        }
//    }

    /**
     * 删除文件
     *
     * @param path
     * @throws Exception
     */
    public static void deleteFile(String path) {
        if (StringUtils.isNotBlank(path)) {
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 删除目录下所有文件及文件夹
     *
     * @param path
     */
    public static void deleteDirectory(String path) {
        if (StringUtils.isNotBlank(path)) {
            File file = new File(path);
            //1級文件刪除
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                //2級文件列表
                String[] filelist = file.list();
                //获取新的二級路徑
                for (int j = 0; j < filelist.length; j++) {
                    File filessFile = new File(path + "\\" + filelist[j]);
                    if (!filessFile.isDirectory()) {
                        filessFile.delete();
                    } else if (filessFile.isDirectory()) {
                        //递归調用
                        deleteDirectory(path + "\\" + filelist[j]);
                    }
                }
                file.delete();
            }
        }
    }


    /**
     * 求两个日期的时间差
     *
     * @param startTime
     * @param endTime
     * @return 相差的天数
     */
    public static int getDistanceTime(Date startTime, Date endTime) {
        int days = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        days = (int) (diff / (24 * 60 * 60 * 1000));
        return days;
    }

    /**
     * 返回日期前    年/月/天 的日期
     *
     * @param date  日期
     * @param num   前多少 年/月/天  的日期
     * @param param 年/月/天
     * @return
     */
    public static Date getDate(Date date, Integer num, String param) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        if ("DATE".equalsIgnoreCase(param) || "日".equalsIgnoreCase(param)) {//日
            calendar.add(Calendar.DATE, -num);
        } else if ("MONTH".equalsIgnoreCase(param) || "月".equalsIgnoreCase(param)) {//月
            calendar.add(Calendar.MONTH, -num);
        } else if ("YEAR".equalsIgnoreCase(param) || "年".equalsIgnoreCase(param)) {//年
            calendar.add(Calendar.YEAR, -num);
        } else if ("HOUR".equalsIgnoreCase(param) || "时".equalsIgnoreCase(param)) {
            calendar.add(Calendar.HOUR, -num);
        } else if ("WEEK".equalsIgnoreCase(param) || "周".equalsIgnoreCase(param)) {
            calendar.add(Calendar.WEEK_OF_MONTH, -num);
        }
        return calendar.getTime();
    }

    /**
     * 获取之后的时间
     *
     * @param date
     * @param num
     * @param param
     * @return
     */
    public static Date getAfterDate(Date date, Integer num, String param) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(date);//设置当前日期
        if ("DATE".equalsIgnoreCase(param) || "日".equalsIgnoreCase(param)) {//日
            calendar.add(Calendar.DATE, num);
        } else if ("MONTH".equalsIgnoreCase(param) || "月".equalsIgnoreCase(param)) {//月
            calendar.add(Calendar.MONTH, num);
        } else if ("YEAR".equalsIgnoreCase(param) || "年".equalsIgnoreCase(param)) {//年
            calendar.add(Calendar.YEAR, num);
        } else if ("HOUR".equalsIgnoreCase(param) || "时".equalsIgnoreCase(param)) {
            calendar.add(Calendar.HOUR, num);
        } else if ("WEEK".equalsIgnoreCase(param) || "周".equalsIgnoreCase(param)) {
            calendar.add(Calendar.WEEK_OF_MONTH, num);
        }
        return calendar.getTime();
    }

    /**
     * 计算两个时间差 是否是指定月前
     *
     * @param begin
     * @param end
     * @param num
     * @return
     */
    public static boolean isMonth(Date begin, Date end, Integer num) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(end);//设置当前日期
        calendar.add(Calendar.MONTH, -num);
        Date time = calendar.getTime();
        return begin.after(time);
    }

    /**
     * 获取 文件上传本地磁盘路径  文件
     *
     * @return
     */
    public static String getFilePath() {
        String localPath = "D:" + File.separator + "assets_management" + File.separator + "upload" + File.separator + "contract" + "~usr" + File.separator + "local" + File.separator + "ZCCS";
        if (StringUtils.isBlank(localPath)) {
            throw new BusinessException("未找到文件上传本地磁盘路径配置！");
        }
        String[] localPathArr = localPath.split("~");
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            return localPathArr[0];
        } else {
            if (localPathArr.length < 2) {
                throw new BusinessException("未找到文件上传本地磁盘Linux路径配置！");
            }
            return localPathArr[1];
        }
    }

    /**
     * 获取客户端浏览器类型、编码下载文件名
     *
     * @param request
     * @param fileName
     * @return String
     */
    /*public static String encodeFileName(HttpServletRequest request, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        String rtn = "";
        try {
            String new_filename = URLEncoder.encode(fileName, "UTF8");
            // 如果没有UA，则默认使用IE的方式进行编码，因为毕竟IE还是占多数的
            rtn = "filename=\"" + new_filename + "\"";
            if (userAgent != null) {
                userAgent = userAgent.toLowerCase();
                // IE浏览器，只能采用URLEncoder编码
                if (userAgent.indexOf("msie") != -1) {
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // Opera浏览器只能采用filename*
                else if (userAgent.indexOf("opera") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
                // Safari浏览器，只能采用ISO编码的中文输出
                else if (userAgent.indexOf("safari") != -1) {
                    rtn = "filename=\"" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
                }
                // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                else if (userAgent.indexOf("applewebkit") != -1) {
                    new_filename = MimeUtility.encodeText(fileName, "UTF8", "B");
                    rtn = "filename=\"" + new_filename + "\"";
                }
                // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                else if (userAgent.indexOf("mozilla") != -1) {
                    rtn = "filename*=UTF-8''" + new_filename;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return rtn;
    }
*/
    /**
     * 计算时间差，以小时为单位。如：2018-08-08 和 2018-08-07 相差24h
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public double getDistanceTime2(Date startTime, Date endTime) {
        double hour;
        double time1 = startTime.getTime();
        double time2 = endTime.getTime();

        double diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        hour = (diff / (60 * 60 * 1000));
        return hour;
    }


    /**
     * 解析IP地址
     *
     * @return
     */
    public static String analysisIP(HttpServletRequest request) {
        String IP = "";
        if (request.getHeader("x-forwarded-for") == null) {
            IP = request.getRemoteAddr();
        } else {
            IP = request.getHeader("x-forwarded-for");
        }
        if ("0:0:0:0:0:0:0:1".equals(IP)) {
            IP = "127.0.0.1";
        }
        return IP;
    }


    private static Map<String, Object> config;


    /**
     * 读取并解析配置文件
     *
     * @param fileName classpath下文件名
     * @return
     * @throws Exception
     */
    public static Map<String, Object> readProperties(String fileName) throws Exception {
        Map<String, Object> reslutMap = new HashMap<String, Object>();
        FileInputStream in = null;

        String absolutePath = BaseUtils.getAbsoluteClasspath() + File.separator + fileName.trim();
        in = new FileInputStream(absolutePath);
        Properties properties = new Properties(); //实例化
        properties.load(in); //从filePath得到键值对

        Enumeration<?> enmObject = properties.keys(); //得到所有的主键信息（这里的主键信息主要是简化的主键，也是信息的关键）

        while (enmObject.hasMoreElements()) { //对每一个主键信息进行检索处理，跟传入的返回值信息是否有相同的地方（如果有相同的地方，取出主键信息的属性，传回给返回信息）
            String curKey = ((String) enmObject.nextElement()).trim();
            if (curKey.contains("#")) { // 带#号的key为注释内容，自动忽略
                continue;
            }
            String curMessage = new String(properties.getProperty(curKey).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).trim();
            reslutMap.put(BaseUtils.encodeUTF8(curKey), BaseUtils.encodeUTF8(curMessage));
        }

        in.close();

        return reslutMap;
    }

    /**
     * 获取classpath绝对路径
     *
     * @return
     */
    public static String getAbsoluteClasspath() {
        String absolutePath = Thread.currentThread().getContextClassLoader().getResource("").toString(); //tomcat绝对路径
        absolutePath = absolutePath.replaceAll("file:/", "");
        // https://www.cnblogs.com/zhengxl5566/p/10783422.html
        absolutePath = absolutePath.replaceAll("%20", " ");
        absolutePath = File.separator + absolutePath.trim();
        BaseUtils.loggerDebug("classpath:" + absolutePath);
        return absolutePath;
    }


    /**
     * 获取时间段的时间
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param sdf       时间格式
     * @return List<String>  时间的段的集合
     */
    public static List<String> findDaysStr(String beginTime, String endTime, SimpleDateFormat sdf) {
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(beginTime);
            dEnd = sdf.parse(endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> daysStrList = new ArrayList<String>();
        daysStrList.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        assert dBegin != null;
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        assert dEnd != null;
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String dayStr = sdf.format(calBegin.getTime());
            daysStrList.add(dayStr);
        }
        return daysStrList;
    }


    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 返回字符串中的整数
     *
     * @param param 字符串
     * @return 返回的整数
     */
    public static Integer returnStrInsideInt(String param) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(param);
        String trim = matcher.replaceAll("").trim();
        return Integer.parseInt(trim);
    }

    /**
     * 将单位和数字分开
     *
     * @param param 字符串
     * @return 例如: 123.23克   return: [0] 123.23  [1] 克
     */
    public static String[] takeStrNumSeparate(String param) {
        if (StringUtils.isNotBlank(param)) {
            char[] chars = param.toCharArray();
            int i;
            for (i = 0; i < chars.length; ++i) {
                if (!Character.isDigit(chars[i])) {
                    if (chars[i] == '.') {
                        continue;
                    }
                    break;
                }
            }
            return new String[]{param.substring(0, i), param.substring(i)};
        }
        return new String[]{"", ""};
    }

    /**
     * end比begin多的天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static int differentDays(Date begin, Date end) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(begin);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //不同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //同一年
        {
            return day2 - day1;
        }
    }

    /**
     * 递归生成树
     * @param trees
     * @param parentKey
     * @return
     */
    public static List<TreeModel> recursiveGenerateTree(List<TreeModel> trees, String parentKey) {
        List<TreeModel> treeModels = new ArrayList<>();
        for (TreeModel model : trees) {
            //判断闯入parentId和当前的是否相等 相等就递归
            if (parentKey.equals(model.getParentKey())) {
                model.setChildren(recursiveGenerateTree(trees, model.getKey()));
                treeModels.add(model);
            }
        }
        return treeModels;
    }

}
