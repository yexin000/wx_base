package cn.trustway.weixin.util;


import cn.trustway.weixin.bean.UploadImage;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.util.ImageReadHelper;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * 文件上传
 *
 * @version 1.0.0.1 2012-2-3
 * @date 2018-4-22下午1:42:11
 * @see
 */
public class FileUpload {
    /**
     * 直接上传文件，不做压缩处理
     * @param orginalFile
     * @param filePath
     * @param imgName
     * @return
     * @throws Exception
     */
    public static String uploadFile(MultipartFile orginalFile, String filePath, String imgName) throws Exception {
        String ffile = DateUtil.getDays(), fileUrl = "";
        String rootFilePath = filePath + Const.USERAPPHEADIMG + ffile;
        File rootFile = new File(rootFilePath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        if (null != orginalFile && !orginalFile.isEmpty()) {
            String extName = ""; // 扩展名格式：
            if (orginalFile.getOriginalFilename().lastIndexOf(".") >= 0) {
                extName = orginalFile.getOriginalFilename().substring(orginalFile.getOriginalFilename().lastIndexOf("."));
            }

            fileUrl = Const.SDFILE + Const.USERAPPHEADIMG + ffile + "/" + imgName + extName;
            FileOutputStream fo = new FileOutputStream(rootFilePath + "/" + imgName + extName);
            InputStream stream = orginalFile.getInputStream();
            byte[] buffer = new byte[1024 * 1024];
            int byteRead;
            while ((byteRead = stream.read(buffer)) != -1) {
                fo.write(buffer, 0, byteRead);
                fo.flush();
            }
            fo.close();
            stream.close();

        }
        return fileUrl;
    }

    public static String uploadFile(UploadImage orginalFile, String filePath, String imgName) throws Exception {
        String ffile = DateUtil.getDays(), fileUrl = "";
        String rootFilePath = filePath + Const.USERAPPHEADIMG + ffile;
        File rootFile = new File(rootFilePath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        if (null != orginalFile && StringUtils.isNotBlank(orginalFile.getData())) {
            try {
                String extName = "jpg"; // 扩展名格式：
                fileUrl = Const.SDFILE + Const.USERAPPHEADIMG + ffile + "/" + imgName + extName;
                FileOutputStream fo = new FileOutputStream(rootFilePath + "/" + imgName + extName);
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] b = decoder.decodeBuffer(orginalFile.getData());
                for(int i=0;i<b.length;++i) {
                    if(b[i]<0) {
                        //调整异常数据
                        b[i]+=256;
                    }
                }

                fo.write(b);
                fo.flush();
                fo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return fileUrl;
    }

    public static String setHead(MultipartFile orginalFile, String filepath, String imgName) throws Exception {
        String ffile = DateUtil.getDays(), fileUrl = "";
        if (null != orginalFile && !orginalFile.isEmpty()) {
            String filePath = filepath + Const.USERAPPHEADIMG + ffile;        //文件上传路径
            String extName = ""; // 扩展名格式：
            if (orginalFile.getOriginalFilename().lastIndexOf(".") >= 0) {
                extName = orginalFile.getOriginalFilename().substring(orginalFile.getOriginalFilename().lastIndexOf("."));
            }
            CommonsMultipartFile cf = (CommonsMultipartFile) orginalFile; //这个myfile是MultipartFile的
            DiskFileItem fi = (DiskFileItem) cf.getFileItem();
            File files = fi.getStoreLocation();

            FileUpload.createThumbnails(filePath, imgName + extName, files, 300, 300);
            fileUrl = Const.SDFILE + Const.USERAPPHEADIMG + ffile + "/_300/" + imgName + extName;

        }
        return fileUrl;
    }


    /**
     * 生成300缩略图，带压缩率
     */
    public static String createThumbnails(String dir, String fileName, File file, int width, int heigh)
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
            width = imageWrapper.getWidth();
            heigh = imageWrapper.getHeight();
            Thumbnails.of(imageWrapper.getAsBufferedImage()).outputQuality(1f).size(width, heigh).toFile(
                    address);
        } catch (IOException e) {
            throw e;
        }
        return realPath;
    }
}

