package com.finalp.keanu.mark.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by miaos on 2017/3/27.
 */
public class DBinfoStu {

    //字段
    //GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName
    //数据库名称
    public static final String DB_NAME_STU = "student.db";
    public static final String DB_NAME_MARK = "marking.db";
    //学生信息表
    public static final String STU_TABLE = "stu";
    //分数表
    public static final String MARK_TABLE = "mark";

    //查询语句
    public static final String findByGroupNumber = "select * from " + STU_TABLE + " where GroupNumber=?";
    public static final String findMaxMark = "select max(totalMark) from " + MARK_TABLE;
    public static final String findByBuptNumber = "select * from " + STU_TABLE + " where BuptNumber=?";
    public static final String findByQMnuber = "select * from " + STU_TABLE + " where QMNumber=?";
    public static final String findByName = "select * from " + STU_TABLE + " where SurName like ? and ForeName like ?";
    public static final String findBySheet = "select * from " + STU_TABLE + " where SheetName like ?";

    public static final String findMarkByGroup = "select * from " + MARK_TABLE + " where GroupNumber=?";

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


}
