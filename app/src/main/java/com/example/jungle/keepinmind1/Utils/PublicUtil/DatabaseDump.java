package com.example.jungle.keepinmind1.Utils.PublicUtil;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jungle.keepinmind1.Bean.ManageMoneyDBBean;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DataBaseUtils;

import org.litepal.LitePal;

public class DatabaseDump {
    private String mDestXmlFilename;
    private SQLiteDatabase mDb;


    public DatabaseDump(SQLiteDatabase db, String destXml) {
        mDb = db;
        mDestXmlFilename = destXml;
    }

    public void exportData() {

        try {

            //  Log.i("mdb", mDb.getPath());
            // get the tables out of the given sqlite database  
            String sql = "SELECT * FROM sqlite_master";

            Cursor cur = mDb.rawQuery(sql, new String[0]);
            cur.moveToFirst();

            String tableName;
            while (cur.getPosition() < cur.getCount()) {
                tableName = cur.getString(cur.getColumnIndex("name"));

                // don't process these two tables since they are used  
                // for metadata  
                if (!tableName.equals("android_metadata")
                        && !tableName.equals("sqlite_sequence")) {
                    writeExcel(tableName);
                }

                cur.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readExcel(String filePath) throws IOException, BiffException {
        //创建输入流
        InputStream stream = new FileInputStream(filePath);
        //获取Excel文件对象
        Workbook rwb = Workbook.getWorkbook(stream);
        //获取文件的指定工作表 默认的第一个
        Sheet sheet = rwb.getSheet(0);
        //行数(表头的目录不需要，从1开始)
        for (int i = 1; i < sheet.getRows(); i++) {
            //创建一个数组 用来存储每一列的值
            ManageMoneyDBBean mmdbb = new ManageMoneyDBBean();
            String[] str = new String[sheet.getColumns()];
            Cell cell = null;
            //列数
            for (int j = 0; j < sheet.getColumns(); j++) {
                //获取第i行，第j列的值
                cell = sheet.getCell(j, i);
                str[j] = cell.getContents();
            }
            //把刚获取的列存入list
            mmdbb.setId((int) Integer.parseInt(str[0]));
            mmdbb.setAccount(str[1]);
            mmdbb.setClassification(str[2]);
            mmdbb.setType(str[3]);
            if (!str[4].equals("")) {
                mmdbb.setMoney(Double.parseDouble(str[4]));
            }
            mmdbb.setRemarks(str[5]);
            if (!str[6].equals("")) {
                mmdbb.setTime(Long.parseLong(str[6]));
            }


            DataBaseUtils.add(mmdbb);
        }
    }


    /**
     * 生成一个Excel文件
     * <p>
     * <p>
     * 要生成的Excel文件名
     */
    public void writeExcel(String tableName) {
        WritableWorkbook wwb = null;
        String fileName;
        fileName = "/sdcard/" + tableName + ".xls";
        int r = 0;

        String sql = "select * from " + tableName;
        Cursor cur = mDb.rawQuery(sql, new String[0]);
        int numcols = cur.getColumnCount();
        int numrows = cur.getCount();
        // Log.i("row", numrows + "");  
        // Log.i("col", numcols + "");  

        String records[][] = new String[numrows + 1][numcols];// 存放答案，多一行标题行  

        if (cur.moveToFirst()) {
            while (cur.getPosition() < cur.getCount()) {
                for (int c = 0; c < numcols; c++) {
                    if (r == 0) {
                        records[r][c] = cur.getColumnName(c);
                        records[r + 1][c] = cur.getString(c);
                    } else {
                        records[r + 1][c] = cur.getString(c);
                    }
                    //  Log.i("value" + r + " " + c, records[r][c]);
                }
                cur.moveToNext();
                r++;
            }

            cur.close();
        }
        try {
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象  
            wwb = Workbook.createWorkbook(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (wwb != null) {
            // 创建一个可写入的工作表  
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置  
            WritableSheet ws = wwb.createSheet("sheet1", 0);

            // 下面开始添加单元格  
            for (int i = 0; i < numrows + 1; i++) {
                for (int j = 0; j < numcols; j++) {
                    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行  
                    Label labelC = new Label(j, i, records[i][j]);
                    //      Log.i("Newvalue" + i + " " + j, records[i][j]);
                    try {
                        // 将生成的单元格添加到工作表中  
                        ws.addCell(labelC);
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }
            }

            try {
                // 从内存中写入文件中  
                wwb.write();
                // 关闭资源，释放内存  
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }
}  