package com.ai.slp.mall.web.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public final class SftpUtil {
	
	/**
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public static final ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		Session session = null;
		Channel channel = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + " success");
		} catch (Exception e) {
			System.out.println("Connected to " + host + " failure");
			if (channel != null && channel.isConnected()) {
				channel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 * @throws Exception
	 */
	public static final String upload(String directory, MultipartFile uploadFile, ChannelSftp sftp) throws Exception {
		String fileName = uploadFile.getOriginalFilename();
		try {
			sftp.cd(directory);
		} catch (SftpException sException) {
			if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
				makeDir(directory, sftp);
				sftp.cd(directory);
			}
		}
		try {
			System.out.println(sftp.pwd());
			String dateString = DateUtil.getDateString(DateUtil.yyyyMMddHHmmssSSS);
			int indexOf = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, indexOf) + "_" + dateString + fileName.substring(indexOf);
			sftp.put(uploadFile.getInputStream(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private static final void makeDir(String directory, ChannelSftp sftp) throws Exception {
		System.out.println(directory);
		System.out.println(sftp.pwd());
		String parentPath = new File(directory).getParentFile().getPath().replace("\\", "/");
		if (parentPath.equals("/")) {
			sftp.mkdir(directory.substring(1));
		} else {
			try {
				sftp.cd(parentPath);
			} catch (SftpException sException) {
				if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
					makeDir(parentPath, sftp);
				}
			}
			sftp.mkdir(directory);
		}
	}
	
	/*
     * 关闭连接
     */
    public static void disconnect(ChannelSftp sftp){
    	if(sftp == null){
    		return;
    	}
        try {
			if (sftp.getSession() != null && sftp.getSession().isConnected()) {
				sftp.getSession().disconnect();
			}
			if (sftp.isConnected()) {
				sftp.disconnect();
			}
		} catch (JSchException e) {
			e.printStackTrace();
		}
    }

    /**
     * 下载文件
     * 
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件
     * @param saveFile
     *            存在本地的路径
     * @param sftp
     */

    public static final void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 下载文件到输出流
     * @param directory
     * @param fileName
     * @param response
     * @param sftp
     * @throws IOException 
     * @throws SftpException 
     */
    public static final void download(String directory, String fileName, HttpServletResponse response, ChannelSftp sftp) throws IOException, SftpException {
        	OutputStream os = response.getOutputStream();// 取得输出流
        	response.reset();// 清空输出流
			response.setContentType("application/msexcel");// 定义输出类型
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "UTF-8"));// 设定输出文件头
			sftp.cd(directory);
			// 获取文件
			sftp.get(fileName, os);
			os.flush();
			os.close();
    }

}
