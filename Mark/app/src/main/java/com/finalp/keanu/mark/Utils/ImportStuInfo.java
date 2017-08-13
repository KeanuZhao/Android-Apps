package com.finalp.keanu.mark.Utils;

import android.content.Context;

import com.finalp.keanu.mark.Entitys.StuEntitys;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miaos on 2017/3/26.
 * 根据构造函数创建实例
 * 调用importInfo方法将该excel文件的信息导入到数据库中，并返回结果
 */
public class ImportStuInfo {

    private Context context;
    private String filePath;
    private POIexcel poIexcel;

    public ImportStuInfo(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
        poIexcel = new POIexcel(filePath);
    }

    public boolean importInfo() {

        //临时存储学生信息的列表
        List<StuEntitys> list = new ArrayList<StuEntitys>();

        //获取每个sheet，并将每个sheet中的信息导入到数据库中
        int sheetNumber = poIexcel.getSheetNum();
        for (int i = 0; i < sheetNumber; i++) {
            HSSFSheet hssfSheet = poIexcel.getSheetX(i);
            String hssfSheetName = hssfSheet.getSheetName();
            System.out.println("hssfSheetName " + i + ":" + hssfSheetName);
            int sheetLine = hssfSheet.getLastRowNum();
            for (int j = 1; j <= sheetLine; j++) {
                try {
                    HSSFRow tempRow = hssfSheet.getRow(j);
                    //字段 GroupNumber、BuptNumber、QMNumber、SurName、ForeName、SheetName
                    int GroupNumber = string2int(getValue(tempRow.getCell(0)));
                    int BuptNumber = string2int(getValue(tempRow.getCell(1)));
                    int QMNumber = string2int(getValue(tempRow.getCell(2)));
                    String SurName = getValue(tempRow.getCell(3));
                    String ForeName = getValue(tempRow.getCell(4));
//                    String SheetName = getValue(tempRow.getCell(5));
                    System.out.println("import:" + GroupNumber + " " + BuptNumber + " " + SurName + " " + ForeName + " ");
                    list.add(new StuEntitys(GroupNumber, BuptNumber, QMNumber,SurName, ForeName, hssfSheetName));
                } catch (Exception e) {
                    e.printStackTrace();
                   // continue;
                }

            }
        }

        //将获得的学生列表信息存储到数据库中
        DBserviceStu dBserviceStu = new DBserviceStu(context,true);
        dBserviceStu.addAll(list);

        return true;
    }

    //将字符串转化为int
    private int string2int(String str) {
        double temp = Double.parseDouble(str);
        return (int)temp;
    }

    //从单元格中获取到数据
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
