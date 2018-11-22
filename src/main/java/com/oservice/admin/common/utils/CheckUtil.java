package com.oservice.admin.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @version 1.0.0
 * @description:检验 校验 工具类
 * @fileName:CheckUtil.java
 * @createTime:2016年4月18日 上午10:27:38
 * @author: duyubo
 */
public class CheckUtil {

    /**
     * 日期校验类型：1：校验年份 2：校验年份月份 3：校验年月日 4：校验时分 5：校验时分秒
     *
     * @fileName: EnumConstant.java
     * @author: duyubo
     * @version: 1.0.0
     * @createTime: 2016年6月18日 上午11:49:50
     */
    public enum validDateType {
        Y(1),
        YM(2),
        YMd(3),
        Hm(4),
        Hms(5);
        private int value;

        private validDateType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 获取一个字符串中中文的数量
     *
     * @param str
     * @return
     */
    public static int getChineseNumber(String str) {
        Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
        Matcher matcher = pattern.matcher(str);
        int number = 0;
        while (matcher.find()) {
            number++;
        }

        return number;
    }


    /**
     * @param email
     * @return boolean
     * @throws
     * @function:校验邮箱
     * @author:duyubo
     * @since 1.0.0
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && email.length() < 41;
    }

    /**
     * @param qq
     * @return boolean
     * @throws
     * @function:校验球球号码
     * @author:duyubo
     * @since 1.0.0
     */
    public static boolean checkQQ(String qq) {
        Pattern pattern = Pattern.compile("^[1-9]\\d{4,10}$");
        Matcher matcher = pattern.matcher(qq);
        return matcher.matches();
    }

    public static boolean checkPostCode(String postCode) {
        Pattern pattern = Pattern.compile("^\\d{6}");
        Matcher matcher = pattern.matcher(postCode);
        return matcher.matches();
    }

    /**
     * @param phone
     * @return boolean
     * @throws
     * @function:校验固话
     * @author:duyubo
     * @since 1.0.0
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern.compile("^(0\\d{2,3}-)(\\d{7,8})(-(\\d{1,8}))?$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * @param telephone
     * @return boolean
     * @throws
     * @function:校验手机
     * @author:duyubo
     * @since 1.0.0
     */
    public static boolean checkMobilePhone(String telephone) {
//		Pattern pattern = Pattern.compile("^0?(13[0-9]|15[012356789]|17[012356789]|18[0123456789]|14[57])[0-9]{8}$");
        Pattern pattern = Pattern.compile("^0?(1[3456789])[0-9]{9}$");
        Matcher matcher = pattern.matcher(telephone);
        return matcher.matches();
    }

    /**
     * 校验真实姓名
     *
     * @param realName
     * @return
     * @author duyubo
     * 2015年下午8:25:30
     */
    public static boolean checkRealName(String realName) {
        Pattern rnReg = Pattern.compile("^([\u4E00-\u9FA5]{2,6}(?:·[\u4E00-\u9FA5]{2,6})*)+$");  //中文,兼容少数名族名字
//		Pattern  rnReg = Pattern.compile("^([a-zA-Z ]{2,20}|([\u4E00-\u9FA5]{2,6}(?:·[\u4E00-\u9FA5]{2,6})*))$");  //中文  英文名(兼容空格),兼容少数名族名字
        Matcher rnMatcher = rnReg.matcher(realName);
        return rnMatcher.matches();
    }

    /**
     * @param IDNumber
     * @return boolean
     * @throws
     * @function:校验身份证
     * @author:duyubo
     * @since 1.0.0
     */
    public static boolean checkCardNo(String IDNumber) {
        boolean result = IDNumber.matches("[0-9]{15}|[0-9]{17}[0-9X]");
        if (result) {
            int year, month, date;
            if (IDNumber.length() == 15) {
                year = Integer.parseInt(IDNumber.substring(6, 8));
                month = Integer.parseInt(IDNumber.substring(8, 10));
                date = Integer.parseInt(IDNumber.substring(10, 12));
            } else {
                year = Integer.parseInt(IDNumber.substring(6, 10));
                month = Integer.parseInt(IDNumber.substring(10, 12));
                date = Integer.parseInt(IDNumber.substring(12, 14));
            }
            switch (month) {
                case 2:
                    result = (date >= 1) && (year % 4 == 0 ? date <= 29 : date <= 28);
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    result = (date >= 1) && (date <= 31);
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    result = (date >= 1) && (date <= 31);
                    break;
                default:
                    result = false;
                    break;
            }
        }
        return result;
    }

    //是否为数字
    public static boolean checkDigital(String Digital) {
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(Digital);
        return matcher.matches();
    }

    //只能输入两位小数
    //是否为数字
    public static boolean towDecimal(String decimal) {
        Pattern pattern = Pattern.compile("(^(\\d+)(\\.(\\d){1,2})*$)");
        Matcher matcher = pattern.matcher(decimal);
        return matcher.matches();
    }


    //判断字符串中是否包含空格
    public static boolean isContainBlank(String str) {
        boolean containBlank = false;
        if (!StringUtils.isEmpty(str)) {
            if (str.contains(" ")) {
                containBlank = true;
            }
        }
        return containBlank;
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 校验所有字符串参数中是否有为空的
     *
     * @param strs 待校验字符串
     * @return
     * @time 2015年下午5:38:09
     * @author duyubo
     */
    public static Boolean isEmptys(String... strs) {
        for (String parm : strs) {
            if (StringUtils.isBlank(parm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否包含null
     *
     * @param objs
     * @return
     * @version 1.0.0
     * @createTime: 2016年11月3日 上午10:23:39
     */
    public static Boolean hasNull(Object... objs) {
        for (Object obj : objs) {
            if (null == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param str
     * @return boolean
     * @throws
     * @function: 验证金额
     * @since 1.0.0
     */
    public static boolean isAmount(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 去掉开头的第一个逗号
     *
     * @param str
     * @return
     * @fileName: CheckUtil.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年5月24日 下午7:10:23
     */
    public static String delFirstWithComma(String str) {
        if (StringUtils.isNotBlank(str) && str.startsWith(",")) {
            str = str.substring(1);
        }
        return str;
    }

    /**
     * 去掉多余的逗号
     *
     * @param str
     * @return
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2017年1月19日 下午7:18:23
     */
    public static String delExtraComma(String str) {
        if (StringUtils.isNotBlank(str)) {
            str = str.replaceAll(",{1,}", ",");
            if (str.endsWith(",")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.startsWith(",")) {
                str = str.substring(1);
            }
        }
        return str;
    }

    /**
     * 是否在取值枚举内
     *
     * @param value
     * @param targetValues
     * @return
     * @fileName: CheckUtil.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月17日 下午6:21:42
     */
    public static boolean isInValues(String value, String targetValues) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        //去掉两边空格
        value = value.trim();
        String[] targValueArr = targetValues.split(",");
        for (String tmpValue : targValueArr) {
            if (value.equals(tmpValue.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否在取值范围内
     *
     * @param value
     * @param targetValues
     * @return
     * @fileName: CheckUtil.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月17日 下午6:21:42
     */
    public static boolean isInRange(Double value, String targetValues) {
        if (null == value) {
            return false;
        }
        String[] targValueArr = targetValues.split(",");
        Double minRange = Double.parseDouble(targValueArr[0]);
        Double maxRange = Double.parseDouble(targValueArr[1]);
        //大于1，小于-1，等于0
        if (value.compareTo(minRange) >= 0 && value.compareTo(maxRange) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 时间是否在指定日期范围中
     *
     * @param value     比如： 2016 、 2016-06
     * @param dateRange
     * @param validType 1:校验年份  2：校验年月
     * @return
     * @fileName: CheckUtil.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月18日 下午1:33:02
     */
    public static boolean isInDateRange(String value, String dateRange, Integer validType) {
        if (StringUtils.isBlank(value)) return false;
        String[] dateRangeArr = dateRange.split(",");
        //转成纯形式
        String minDateStr = dateRangeArr[0].replace("-", "").replace(":", "").replace(" ", "");
        String maxDateStr = dateRangeArr[1].replace("-", "").replace(":", "").replace(" ", "");

        //如果是比较 时分
        if (validType == validDateType.Hm.getValue()) {
            Integer minRangeHm = Integer.parseInt(minDateStr.substring(0, 4));
            Integer maxRangeHm = Integer.parseInt(maxDateStr.substring(0, 4));
            value = value.replace(":", "").replace(" ", "").substring(0, 4);
            Integer nowHhmm = Integer.parseInt(value);
            return (nowHhmm >= minRangeHm && nowHhmm <= maxRangeHm);
        }

        String[] valArr = value.split("-");
        String yearStr = valArr[0];
        String monthStr = null;
        //月：0 补齐
        if (valArr.length > 1 && valArr[1].length() <= 2) {
            monthStr = valArr[1].length() == 1 ? "0".concat(valArr[1]) : valArr[1];
        }

        //如果只校验年份
        if (validType == validDateType.Y.getValue()) {
            //只取年份
            Integer minRangeYear = Integer.parseInt(minDateStr.substring(0, 4));
            Integer maxRangeYear = Integer.parseInt(maxDateStr.substring(0, 4));
            Integer orgYear = Integer.parseInt(yearStr);
            if (orgYear >= minRangeYear && orgYear <= maxRangeYear) {
                return true;
            }
        }

        //如果校验年-月
        if (validType == validDateType.YM.getValue()) {
            //取年月
            Integer minRangeYm = Integer.parseInt(minDateStr.substring(0, 6));
            Integer maxRangeYm = Integer.parseInt(maxDateStr.substring(0, 6));
            Integer orgYm = Integer.parseInt(yearStr.concat(monthStr));
            if (orgYm >= minRangeYm && orgYm <= maxRangeYm) {
                return true;
            }
        }
        return false;
    }


    /**
     * 校验枚举型值
     *
     * @param fieldName
     * @param value
     * @param values
     * @param isRequired 是否必填
     * @return
     * @fileName: AssetPoolServiceImpl.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月18日 上午9:26:18
     */
    public static String validValue(String fieldName, Object value, String values, boolean isRequired) {
        String pfxSub = "资金计划";
        if (isRequired) {
            if (null == value || StringUtils.isBlank(value.toString())) {
                return fieldName + "为空";
            }
            if (StringUtils.isBlank(values)) {
                return pfxSub + fieldName + "为空";
            }
        }
        if (null != value && StringUtils.isNotBlank(values)) {
            if (!CheckUtil.isInValues(value.toString(), values)) {
                return fieldName + "【" + value + "】" + "不匹配";
            }
        }
        return null;
    }


    /**
     * 校验区间型值
     *
     * @param fieldName
     * @param value
     * @param range
     * @param isRequired 是否必填
     * @return
     * @fileName: AssetPoolServiceImpl.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月18日 上午9:41:33
     */
    public static String validRange(String fieldName, Object value, String range, boolean isRequired) {
        String pfxSub = "资金计划";
        if (isRequired) {
            if (null == value) {
                return fieldName + "为空";
            }
            if (StringUtils.isBlank(range)) {
                return pfxSub + fieldName + "为空";
            }
        }
        if (null != value && StringUtils.isNotBlank(range)) {
            if (!CheckUtil.isInRange(Double.parseDouble(value.toString()), range)) {
                return fieldName + "【" + value + "】" + "不匹配";
            }
        }
        return null;
    }

    /**
     * 时间是否在指定日期范围中   1:校验年份 2：校验年月
     *
     * @param fieldName
     * @param value
     * @param range
     * @param validType
     * @param isRequired
     * @return
     * @fileName: AssetPoolServiceImpl.java
     * @author: duyubo
     * @version 1.0.0
     * @createTime: 2016年6月18日 下午1:41:30
     */
    public static String validDateRange(String fieldName, String value, String range, Integer validType, boolean isRequired) {
        String pfxSub = "资金计划";
        if (isRequired) {
            if (null == value) {
                return fieldName + "为空";
            }
            if (StringUtils.isBlank(range)) {
                return pfxSub + fieldName + "为空";
            }
        }
        if (StringUtils.isNoneBlank(value, range)) {
            if (!CheckUtil.isInDateRange(value, range, validType)) {
                return fieldName + "【" + value + "】" + "不匹配";
            }
        }
        return null;
    }

	
/*
	public static void main(String[] args){
		String ipStr = "192.168.1.1,192.168.16.203,192.165.256.1";
		String a = ",1,2,3,4,5,6";
		System.err.println(delFirstWithComma(a));
		System.err.println(isInValues("12", "1,2,3,4,3.432,fjdskla,jj.jjk,123, 12 ,jj"));
		System.err.println(isInDateRange("2014-6", "2014-03,2015-11", 2));
		System.err.println(delExtraComma(",aa,b,,,,,,,b,,cc,,,dd,,ee"));
		System.err.println(CheckUtil.getChineseNumber("aaa广东12省33"));
	}
*/
}