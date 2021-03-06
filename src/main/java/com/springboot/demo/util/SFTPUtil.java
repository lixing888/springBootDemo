package com.springboot.demo.util;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;


/**
 * @program: bigfile
 * @description: java通过sftp上传下载文件
 * @author: lixing
 * @create: 2020-11-21 08:07
 **/
public class SFTPUtil {
    private transient Logger log = LoggerFactory.getLogger(this.getClass());

    private ChannelSftp sftp;

    private Session session;
    /**
     * FTP 登录用户名
     */
    private String username;
    /**
     * FTP 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * FTP 服务器地址IP地址
     */
    private String host;
    /**
     * FTP 端口
     */
    private int port;


    /**
     * 构造基于密码认证的sftp对象
     *
     * @param username
     * @param password
     * @param host
     * @param port
     */
    public SFTPUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     *
     * @param username
     * @param host
     * @param port
     * @param privateKey
     */
    public SFTPUtil(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SFTPUtil() {
    }

    /**
     * 连接sftp服务器
     *
     * @throws Exception
     */
    public void login() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
                log.info("sftp connect,path of private key file：{}", privateKey);
            }
            log.info("sftp connect by host:{} username:{}", host, username);

            session = jsch.getSession(username, host, port);
            log.info("Session is build");
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            log.info("Session is connected");

            Channel channel = session.openChannel("sftp");
            channel.connect();
            log.info("channel is connected");

            sftp = (ChannelSftp) channel;
            log.info(String.format("sftp server host:[%s] port:[%s] is connect successfull", host, port));
        } catch (JSchException e) {
            log.error("Cannot connect to specified sftp server : {}:{} \n Exception message is: {}", new Object[]{host, port, e.getMessage()});
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
                log.info("sftp is closed already");
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
                log.info("sshSession is closed already");
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件
     *
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            log.warn("directory is not exist");
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);
        log.info("file:{} is upload successful", sftpFileName);
    }

    /**
     * 上传单个文件
     *
     * @param directory  上传到sftp目录
     * @param uploadFile 要上传的文件,包括路径
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException {
        File file = new File(uploadFile);
        upload(directory, file.getName(), new FileInputStream(file));
    }

    /**
     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param byteArr      要上传的字节数组
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param dataStr      待上传的数据
     * @param charsetName  sftp上的文件，按该字符编码保存
     * @throws UnsupportedEncodingException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     * @throws SftpException
     * @throws FileNotFoundException
     * @throws Exception
     */
    public void download(String directory, String downloadFile, String saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
        log.info("file:{} is download successful", downloadFile);
    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     * @throws SftpException
     * @throws IOException
     * @throws Exception
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);

        byte[] fileData = IOUtils.toByteArray(is);

        log.info("file:{} is download successful", downloadFile);
        return fileData;
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @throws SftpException
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 下载文件夹下面的所有文件
     *
     * @param viDirectory 文件夹
     * @param viHost      主机名
     * @param viPort      端口号
     * @param viUserName  用户名
     * @param viPassWord  用户密码
     * @param viSaveDir   保存路径
     * @return
     */
    public List<String> downloadDirFile(String viDirectory, String viHost, int viPort, String viUserName, String viPassWord, String viSaveDir) {
        ChannelSftp nChannelSftp = null;
        List<String> nFileNameList = null;
        try {
            // 1.实例化nSftpUtil工具类
            // 2.建立Sftp通道
            SFTPUtil nSftpUtil = new SFTPUtil(viUserName, viPassWord, viHost, 22);
            // 3.获取目录下面所有文件
            Vector nVector = nChannelSftp.ls(viDirectory);
            // 4.循环遍历文件
            for (int i = 0; i < nVector.size(); i++) {
                // 5.进入服务器文件夹
                nChannelSftp.cd(viDirectory);
                // 6.实例化文件对象
                String nFileName = nVector.get(i).toString().substring(56, nVector.get(i).toString().length());
                if (!nFileName.contains("csv")) {
                    continue;
                }
                File nFile = new File(viSaveDir + File.separator + nFileName);
                // 7.下载文件
                nChannelSftp.get(nFileName, new FileOutputStream(nFile));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            nChannelSftp.disconnect();
        }
        return nFileNameList;
    }

    /**
     * @Author：lixing
     * @Description：获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件，所以不用递归获取
     * @Date：2020-11-22
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                String fileName = tempList[i].getName();
                System.out.println("文件名称：" + fileName);
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
                String dirName = tempList[i].getName();
                path = path + "/" + dirName;
                //getFiles(path);
            }
        }
        return files;
    }

    public static void main(String[] args) throws SftpException, IOException {
        //建立Sftp通道
        SFTPUtil sftp = new SFTPUtil("lanhuigu", "123456", "192.168.200.12", 22);
        sftp.login();
        //byte[] buff = sftp.download("/opt", "start.sh");
        //System.out.println(Arrays.toString(buff));
        File file = new File("D:\\upload\\index.html");
        InputStream is = new FileInputStream(file);
        //将输入流的数据上传到sftp作为文件
        sftp.upload("/data/work", "test_sftp_upload.csv", is);
        //上传单个文件
        sftp.upload("root/datatang/bizfile/before", "D:\\aliyun_java_sdk_3.10.2.zip");
        //列出目录下的文件
        sftp.listFiles("root/datatang/bizfile/before");
        sftp.logout();

        String meString = "http://www.baidu.com";
        try {
            //模拟了http 请求
            String mes = IOUtils.toString(new URL(meString), "utf-8");
            System.out.println(mes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取某个目录下所有直接下级文件，不包括目录下的子目录的下的文件
        List<String> files = getFiles("D:\\bizhi");
        for (String fileInfo : files) {
            System.out.println("文件：" + fileInfo);
        }

    }
}
