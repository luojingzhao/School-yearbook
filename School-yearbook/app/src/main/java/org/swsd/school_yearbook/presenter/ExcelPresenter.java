package org.swsd.school_yearbook.presenter;

import android.os.Environment;

import org.litepal.crud.DataSupport;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * author     :  张昭锡
 * time       :  2017/11/04
 * description:  实现同学录导出Excel功能
 * version:   :  1.0
 */

public class ExcelPresenter {

    /**
     * author     :  张昭锡
     * time       :  2017/11/04
     * description:  数据库内容写入Excel
     * version:   :  1.0
     */
    public static void  writeExcel(String dirName) throws WriteException,IOException {


        String filepath = Environment.getExternalStorageDirectory() + dirName;
        File appDir = new File(filepath);

        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //获取当前时间作为Excel文件名
        String fileName = System.currentTimeMillis() + ".xlsx";
        File file = new File(appDir,fileName);

        //生成Excel空表格
        WritableWorkbook wwb;
        OutputStream os;
        os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);

        //添加sheet空表格
        WritableSheet sheet = wwb.createSheet("同学信息",0);

        //添加Excel表头
        String[] title = {"序号","姓名","家庭住址","电话","微信","邮箱","QQ","个性语言"};
        Label label;
        for (int i = 0;i < title.length;i++){
            label = new Label(i,0,title[i]);
            sheet.addCell(label);
        }

        //获取数据库全部数据
        List<SchoolyearbookBean> books = DataSupport.findAll(SchoolyearbookBean.class);
        for (int i = 0;i < books.size();i++){
            SchoolyearbookBean book = books.get(i);

            //将同学信息写入Excel的sheet表
            Label id = new Label(0,i + 1, String.valueOf(book.getId()));
            Label name = new Label(1,i + 1,book.getName());
            Label address = new Label(2,i + 1,book.getAddress());
            Label phone = new Label(3,i + 1,book.getPhone());
            Label wechat = new Label(4,i + 1,book.getWechat());
            Label QQ = new Label(5,i + 1,book.getQq());
            Label signature = new Label(6,i + 1,book.getSignature());

            sheet.addCell(id);
            sheet.addCell(name);
            sheet.addCell(address);
            sheet.addCell(phone);
            sheet.addCell(wechat);
            sheet.addCell(QQ);
            sheet.addCell(signature);
        }
        wwb.write();
        wwb.close();
    }
}
