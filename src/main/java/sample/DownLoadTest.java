package sample;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/11/5.
 */
public class DownLoadTest {


    private static FTPClient ftp;
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
    public static FTPClient connectServer(String server, int port, String username,
                                          String password, String path)  {
        if(null == path){
            path = "";
        }
        try {
            ftp = new FTPClient();
            //下面四行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            // 如果使用serv-u发布ftp站点，
            // 则需要勾掉“高级选项”中的“对所有已收发的路径和文件名使用UTF-8编码”
            ftp.setControlEncoding("UTF-8");
            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
            conf.setServerLanguageCode("zh");
            ftp.configure(conf);
//			ftp.connect(server, port);
            ftp.configure(new FTPClientConfig("com.dist.dgpserver.base.filezillapatch.UnixFTPEntryParser"));
            ftp.connect(server, port);
            // 设置被动模式
//
            ftp.setDataTimeout(120000);

            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                System.out.println(server + " 拒绝连接");
            }
            boolean flag = ftp.login(username, password);
            if (flag) {
                System.out.println("FTP登录成功。");
            } else {
                System.out.println("FTP登录失败。");
            }

            ftp.enterLocalPassiveMode();
            System.out.println(ftp.printWorkingDirectory());
            return ftp;
        }catch(SocketException e){
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static  void main(String[] agrs){
        ftp = connectServer("127.0.0.1", 17739, "distftp",
                "distftp", "");
        try {
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            System.out.print("文件下载："+downloadFile(ftp,"/home/distftp/topic/国民经济和社会发展第十三个五年规划/158甘肃部署说明.docx","/tmp/158甘肃部署说明.docx"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ftp 远程文件 下载文件
    public static boolean  downloadFile(FTPClient ftp, String remotePath, String localPath){

        File file=new File(localPath);

        boolean flag=false;
        OutputStream out =  null;
        try {
            /*System.out.println("进入根目录："+ftp.changeWorkingDirectory("/"));
            String[] paths = remotePath.split("/");
            for(int i=0;i<paths.length-1;i++){
                System.out.println(paths[i]+"进入："+ftp.changeWorkingDirectory(paths[i]));
                System.out.println(ftp.printWorkingDirectory());
            }

            if (!file.exists()) {
                System.out.println("创建文件"+file.createNewFile());

            }*/
            out =  new FileOutputStream(localPath);
            flag=ftp.retrieveFile(remotePath,out);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }



    public static String parseChinese(String str, String encoding) throws UnsupportedEncodingException {
        String[] strArray=str.split("");
//		String[] strArray=str.split("");
        StringBuffer sb=new StringBuffer();
        for(String tmp:strArray) {
            if(tmp==null||"".equals(tmp))
                continue;
            //如果是中文就要进行转换
            if(tmp.matches("[\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9\uFF00-\uFFEF\u2E80-\u2EFF\u3000-\u303F\u31C0-\u31EF\u2F00-\u2FDF\u2FF0-\u2FFF\u3100-\u312F\u31A0-\u31BF\u3040-\u309F\u30A0-\u30FF\u31F0-\u31FF\uAC00-\uD7AF\u1100-\u11FF\u3130-\u318F\u4DC0-\u4DFF\uA000-\uA48F\uA490-\uA4CF\u2800-\u28FF\u3200-\u32FF\u3300-\u33FF\u2700-\u27BF\u2600-\u26FF\uFE10-\uFE1F\uFE30-\uFE4F]+")) {
                //指定装换格式
                sb.append(URLEncoder.encode(tmp,encoding));
            }else {
                sb.append(tmp);
            }
//			System.out.println(tmp);
        }
        return sb.toString();
    }
}
