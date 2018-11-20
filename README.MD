# libreoffice
<p>
LibreOffice是OpenOffice.org 办公套件衍生版， 同样免费开源，以Mozilla Public License V2.0许可证分发源代码，但相比OpenOffice增加了很多特色功能。LibreOffice拥有强大的数据导入和导出功能，能直接导入PDF文档、微软Works、LotusWord，支持主要的OpenXML格式。软件本身并不局限于Debian和Ubuntu平台，OpenXML格式Windows、Mac、PRM packageLinux等多个系统平台。libreoffice现在还在不断更新，LO 不但人力多、新功能也越来越多、效能还越来越好，AOO 要怎么追也追不上 LO 的车尾灯
</p>

# 1、安装使用（linux，--winows,mac自行百度）
##  1.1使用rpm二进制包进行安装
        yum源
                http://mirrors.ustc.edu.cn/tdf/libreoffice/stable
                
##  1.2下载
        下载下面三个文件
                LibreOffice_6.0.6_Linux_x86-64_rpm.tar.gz
                LibreOffice_6.0.6_Linux_x86-64_rpm_sdk.tar.gz
                LibreOffice_6.0.6_Linux_x86-64_rpm_langpack_zh-CN.tar.gz
##  1.3开始安装
        1、自行解压
        2、进入LibreOffice_6.0.6_Linux_x86-64_rpm解压后的RPMS ，执行yum localhostinstall *.rpm
        3、为了支持中文，同上进入LibreOffice_6.0.6_Linux_x86-64_rpm_langpack_zh-CN.tar.gz 解压后的RPMS，执行yum localhostinstall *.rpm
           
##  1.4遇到的问题
        其中就是在测试启动服务的时候会出现一些共享库找不到，比如会出现如下：
        /opt/libreoffice6.0/program/soffice.bin: error while loading shared libcups: libcairo.so.2: cannot open shared object file: No such file or directory
        
### 1.4.1解决的方案

        首先确定本机上面有没有这个共享库，使用find / -name XXX.os，有可能是如果不在usr/lib 中，也不在/usr/lib64中，自行添加这个新的共享库位置就行了（自己百度下）
        但是有可能是/usr/lib（32位）中有，但是/usr/lib64中没有，首先确定本机是64位，所以是本机下载的共享库错误，应该下载64位的共享库，所以重新下载，通过yum下载会自动下载64位
        比如上面的我就使用，yum -y install cairo
        
#  2关于工程
##  2.1依赖
 其中使用的依赖可以有两个（我都做了对应的代码书写，分别是ComArtofsolving，OrgJodconverterTest）
 建议使用Org依赖，因为支持的转换格式多
##  2.2代码说明（官方代码，没有用到测试过，但是不太好需要自己去写转换的类型）
        DocumentConverter(转换)</br>
        DocumentLoader（加载，就是打开）</br>
        DocumentPrinter（打印）</br>
        DocumentSaver（保存）</b>
        JacobTest（window jacob代码,自行在本地添加dll）</br>
        OrgJodconverterTest（org包实现，）</br>
        ComArtofsolving（com包实现）</b>
        DownLoadTest（linxu ftp下载实现）</br>
##  2.3 关于jacob 以及libreoffice 以及openoffice 对比
详见 对比.excel
 