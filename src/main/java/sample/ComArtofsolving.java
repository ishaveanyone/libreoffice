package sample;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;

//import org.artofsolving.jodconverter.OfficeDocumentConverter;
//import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
//import org.artofsolving.jodconverter.office.OfficeManager;

/**
 * Created by Administrator on 2018/11/8.
 */
public class ComArtofsolving {
    private static Logger logger = LoggerFactory.getLogger(ComArtofsolving.class);


    /**
     * 转换libreoffice支持的文件为pdf
     * @param inputfile
     * @param outputfile
     */
   /* public void libreOffice2PDF(File inputfile, File outputfile) {
        String LibreOffice_HOME = getLibreOfficeHome();
        String fileName = inputfile.getName();
        logger.info(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()) + "文件" + inputfile.getName());
        System.out.println(fileName.substring(fileName.lastIndexOf(".")));
        if (fileName.substring(fileName.lastIndexOf(".")).equalsIgnoreCase(".txt")) {
            System.out.println("处理txt文件");
            new Mian2().TXTHandler(inputfile);
        }
        DefaultOfficeManagerConfiguration configuration = new
                DefaultOfficeManagerConfiguration();
        // libreOffice的安装目录
        System.out.println(LibreOffice_HOME);
        configuration.setOfficeHome(new File(LibreOffice_HOME));
        // 端口号
        configuration.setPortNumber(8100);
        configuration.setTaskExecutionTimeout(1000 * 60 * 25L);
//         设置任务执行超时为10分钟
        configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
//         设置任务队列超时为24小时
        OfficeManager officeManager = configuration.buildOfficeManager();
        officeManager.start();
        logger.info(new Date().toString() + "开始转换......");
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        converter.getFormatRegistry();
        try {

            converter.convert(inputfile, outputfile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("转换失败");
        } finally {
            officeManager.stop();
        }


        logger.info(new Date().toString() + "转换结束....");
    }

    *//**
     * 测试的方法
     * @param args
     *//*
    public static void main(String[] args) {
        //使用Files类遍历图片文件夹的文件
        try {
          *//*  DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            for (Path p : paths) {

                File f = p.toFile();
                System.out.println(path.getParent().toString() + "/pdf/" + f.getName().substring(0, f.getName().lastIndexOf(".")) + ".pdf");
                new Mian2().libreOffice2PDF(f, new File(path.getParent().toString() + "/pdf/" + f.getName().substring(0, f.getName().lastIndexOf(".")).toString() + ".pdf"));


            }*//*
            File inf = new File("/tmp/test.xls");
            File outf = new File("/tmp/test.pdf");
            new Mian2().office2PDF("/tmp/test.mdb","/tmp/test.pdf");
        } catch (Exception e) {

            e.printStackTrace();

        }
    }*/

    /**
     * 转换txt文件编码的方法
     *
     * @param file
     * @return
     */
    public File TXTHandler(File file) {
        //或GBK
        String code = "gb2312";
        byte[] head = new byte[3];
        try {
            InputStream inputStream = new FileInputStream(file);
            inputStream.read(head);
            if (head[0] == -1 && head[1] == -2) {
                code = "UTF-16";
            } else if (head[0] == -2 && head[1] == -1) {
                code = "Unicode";
            } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
                code = "UTF-8";
            }
            inputStream.close();

            System.out.println(code);
            if (code.equals("UTF-8")) {
                return file;
            }
            String str = FileUtils.readFileToString(file, code);
            FileUtils.writeStringToFile(file, str, "UTF-8");
            System.out.println("转码结束");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    public  int office2PDF(String sourceFile, String destFile) throws FileNotFoundException {
        try {
            File inputFile = new File(sourceFile);
            if (!inputFile.exists()) {
                return -1;// 找不到源文件, 则返回-1
            }

            // 如果目标路径不存在, 则新建该路径
            File outputFile = new File(destFile);
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }

            // connect to an OpenOffice.org instance running on port 8100
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(
                    "127.0.0.1", 8100);
            connection.connect();

            // convert
            DocumentConverter converter = new OpenOfficeDocumentConverter(
                    connection);
            converter.convert(inputFile, outputFile);

            // close the connection
            connection.disconnect();

            return 0;
        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
