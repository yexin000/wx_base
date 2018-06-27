package cn.trustway.weixin.service;

import cn.trustway.weixin.bean.UploadImage;
import cn.trustway.weixin.util.FileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static cn.trustway.weixin.controller.BaseController.MSG;
import static cn.trustway.weixin.controller.BaseController.SUCCESS;

/**
 * 文件上传服务类
 *
 * @author yexin
 */
@Service("fileUploadService")
public class FileUploadService {
    @Value("#{pref.filePath}")
    private String filepath;

    /**
     * 根据ID查找记录
     *
     * @param headImg
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public Map uploadFile(MultipartFile headImg, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = new HashMap();

        try {
            if(headImg!=null && headImg.getSize()>(1024*1020*5)){
                //logger.info("图片大小超过规定范围，当前文件大小为："+head_img.getSize());
                context.put(SUCCESS, false);
                context.put(MSG, "图片大小超过规定范围，当前文件大小为："+headImg.getSize());
                return context;
            }
            /**
             * 根据session中的userId修改图片
             * 先不走后台，做前端测试
             */
            String fileUrl = FileUpload.uploadFile(headImg, filepath, UUID.randomUUID().toString().trim().replaceAll("-", ""));
            //String fileUrl = FileUpload.setHead(headImg, filepath, UUID.randomUUID().toString().trim().replaceAll("-", ""));
            //String msg = userpageService.updateUserHeadImg_300(head_img, this.getUSERID());
            //宽高处理
            BufferedImage image = ImageIO.read(headImg.getInputStream());
            if (image != null) {//如果image=null 表示上传的不是图片格式
                context.put("width",image.getWidth());
                context.put("height",image.getHeight());
            }
            if(StringUtils.isNotBlank(fileUrl)){
                context.put(SUCCESS, true);
                context.put(MSG, fileUrl);
            }else {
                context.put(SUCCESS, false);
            }

        } catch (Exception e) {
            context.put(SUCCESS, false);
            e.printStackTrace();
        }

        return context;
    }

    public Map uploadFile(UploadImage uploadImage, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> context = new HashMap();

        try {
            /**
             * 根据session中的userId修改图片
             * 先不走后台，做前端测试
             */
            String fileUrl = FileUpload.uploadFile(uploadImage, filepath, UUID.randomUUID().toString().trim().replaceAll("-", ""));
            if(StringUtils.isNotBlank(fileUrl)){
                context.put(SUCCESS, true);
                context.put(MSG, fileUrl);
            }else {
                context.put(SUCCESS, false);
            }

        } catch (Exception e) {
            context.put(SUCCESS, false);
            e.printStackTrace();
        }

        return context;
    }
}
