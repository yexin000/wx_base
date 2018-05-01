package cn.trustway.weixin.controller;

import cn.trustway.weixin.util.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 拍卖功能页面控制类
 * @author yexin
 *
 */
@Controller
@RequestMapping("/file")
public class FileUploadController extends BaseController {

    @Value("#{pref.filePath}")
    private String filepath;

    //文件上传
    @RequestMapping(value="/setHeadImg",method=RequestMethod.POST)
    @ResponseBody
    public  Object uploadFile(@RequestParam(required = false)MultipartFile head_img, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map=new HashMap<String, Object>();
        String info="";

            try {
                if(head_img!=null && head_img.getSize()>(1024*1020*5)){  //
                    //logger.info("图片大小超过规定范围，当前文件大小为："+head_img.getSize());
                    map.put("info", "errorSize");
                    return map;   //图片大小超过规定范围,图片大小不能超过700KB
                }
                /**
                 * 根据session中的userId修改图片
                 * 先不走后台，做前端测试
                 */
                String msg = FileUpload.setHead(head_img, filepath, UUID.randomUUID().toString().trim().replaceAll("-", ""));
                //String msg = userpageService.updateUserHeadImg_300(head_img, this.getUSERID());

                if(msg!=null){
                    info="success";
                    map.put("imgPath", msg);   //图片地址
                }else {
                    info="error";
                }

            } catch (Exception e) {
                info="error";
                e.printStackTrace();
            }


        map.put("info", info);

        return map;


    }

}
