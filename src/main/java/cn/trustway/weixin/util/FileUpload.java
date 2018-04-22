package cn.trustway.weixin.util;



import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.util.ImageReadHelper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件上传
 * @version 1.0.0.1 2012-2-3
 * @date 2018-4-22下午1:42:11
 * @see	
 */
public class FileUpload {
	public static String  setHead (MultipartFile orginalFile, String filepath, String imgName) throws Exception{
		String  ffile = DateUtil.getDays(), fileUrl = "";
		if (null != orginalFile && !orginalFile.isEmpty()) {
			String filePath = filepath +Const.USERAPPHEADIMG + ffile;		//文件上传路径
			String extName = ""; // 扩展名格式：
			if (orginalFile.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = orginalFile.getOriginalFilename().substring(orginalFile.getOriginalFilename().lastIndexOf("."));
			}
			CommonsMultipartFile cf= (CommonsMultipartFile)orginalFile; //这个myfile是MultipartFile的
			DiskFileItem fi = (DiskFileItem)cf.getFileItem();
			File files = fi.getStoreLocation();

			FileUpload.createThumbnails(filePath, imgName+extName, files,300,300);
			fileUrl = Const.SDFILE+Const.USERAPPHEADIMG + ffile+"/_300/"+imgName+extName;

		}
		return fileUrl;
	}


	/**
	 * 生成300缩略图，带压缩率
	 */
	public static String createThumbnails(String dir,String fileName, File file,int width,int heigh)
			throws Exception {
		FileInputStream inStream = new FileInputStream(file);
		ImageWrapper imageWrapper = ImageReadHelper.read(inStream);
		String thumbnailsPath = dir + "/_300/";
		String realPath = thumbnailsPath + fileName;
		File rootFile = new File(thumbnailsPath);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}
		String address = thumbnailsPath + fileName;
		// 生成缩略图
		try {
			Thumbnails.of(imageWrapper.getAsBufferedImage()).outputQuality(0.95f).size(width, heigh).toFile(
					address);
		} catch (IOException e) {
			throw e;
		}
		return realPath;
	}
}

