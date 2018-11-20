package sample;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/11/14.
 */
public class OrgJodconverterTest {

    private OrgJodconverterTest(){}

    private static OfficeManager singleOfficeManager = null;

    public static String getOfficeHome(){
        String osName = System.getProperty("os.name").toLowerCase();
        if(Pattern.matches("windows.*",osName))
        {
            return "C:/Program Files (x86)/OpenOffice 4";
        }
        else if(Pattern.matches("linux.*",osName))
        {
            return "/opt/libreoffice6.0";
        }
        else if (Pattern.matches("mac.*",osName))
        {
            return "/Application/openOfficeSoft";
        }
        return null;
    }


//    使用单例模式创建对象
    public static OfficeManager getOfficeManager(){

        if(singleOfficeManager == null)
        {
            DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();

            builder.setOfficeHome(getOfficeHome());
            OfficeManager officeManager = builder.build();
            try {
                officeManager.start();
                singleOfficeManager = officeManager;
            } catch (OfficeException e) {
                //打印日志
                System.out.println("start openOffice Fail!");
                e.printStackTrace();
            }
            return singleOfficeManager;
        }
        else
        {
            return singleOfficeManager;
        }
    }

    /**
     * 将office格式的文件转为pdf
     * @param sourceFilePath 源文件路径
     * @return
     */


    /**
     * 将office文档转换为pdf文档
     * @param sourceFilePath 原文件路径
     * @return
     */
    public static File office2pdf(String sourceFilePath, String after_convert_file_path){
        OfficeManager officeManager = null;
        try{
            if(null ==sourceFilePath)
            {
                //打印日志...
                return null;
            }
            File sourceFile = new File(sourceFilePath);
            if(!sourceFile.exists())
            {
                //打印日志...
                return null;
            }

            //启动openOffice
            officeManager = getOfficeManager();
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            File outputFile = new File(after_convert_file_path);
            if(!outputFile.getParentFile().exists()){
                //如果上级目录不存在也就是E:/pdfFile这个文件夹不存在则创建一个
                outputFile.getParentFile().mkdirs();
            }
            converter.convert(sourceFile,outputFile);
            return outputFile;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("转换异常");
        }finally {
            if(officeManager != null){
                try {
                    officeManager.stop();
                } catch (OfficeException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 转换文件
     * @param sourceFile 原文件
     * @param after_convert_file_path 转换后存放位置
     * @param sourceFilePath 原文件路径
     * @param converter 转换器
     * @return
     */
  /*  public static File convertFile(File sourceFile,
                                   String after_convert_file_path,String sourceFilePath,
                                   OfficeDocumentConverter converter) throws OfficeException {
       *//* File outputFile = new File(after_convert_file_path);
        if(!outputFile.getParentFile().exists()){
            //如果上级目录不存在也就是E:/pdfFile这个文件夹不存在则创建一个
            outputFile.getParentFile().mkdirs();
        }
        converter.convert(sourceFile,outputFile);
        return outputFile;*//*
    }*/



    /**
     * 获取转换后文件存放的路径
     * @param sourceFilePath 源文件
     * @return
     */


    /**
     * 获取openOffice的安装目录
     * @return
     */


    public static void main(String[] args){
        office2pdf("/usr/tomcat-admin/webapps/dgp-server-web-rl/download/topic/158甘肃部署说明.docx",
                "/tmp/b.pdf");
    }
}
