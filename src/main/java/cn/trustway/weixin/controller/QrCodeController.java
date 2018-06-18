package cn.trustway.weixin.controller;

import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.servlet.TokenThread;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.json.JsonToolUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 二维码生成控制类
 *
 * @author yexin
 */
@Controller
@RequestMapping("/qrCode")
public class QrCodeController extends BaseController {
    @Value("#{pref.filePath}")
    private String filepath;
    public static final String SCENE_PRE = "ITEMID_";

    /**
     * 商品分享二维码生成
     *
     * @param itemId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getItemQrCode")
    public void getItemQrCode(String itemId, HttpServletRequest request, HttpServletResponse response) {
        String scene = SCENE_PRE;
        if(StringUtils.isNotBlank(itemId)) {
            scene += itemId;
        } else {
            scene += 0;
        }

        String itemQrCodePath = filepath + "qrCode/" + "QrCode_" + scene + ".jpg";
        File codeFile = new File(itemQrCodePath);
        if(codeFile.exists()) {
            Map<String, Object> context = new HashMap<>();
            context.put(SUCCESS, true);
            context.put("codeUrl", "qrCode/" + "QrCode_" + scene + ".jpg");
            HtmlUtil.writerJson(response, context);
            return;
        }

        if(null != TokenThread.accessToken && StringUtils.isNotBlank(TokenThread.accessToken.getToken())) {
            String codeUrl = AppInitConstants.XCX_QR_CODE_URL  + "?access_token=" + TokenThread.accessToken.getToken();
            RestTemplate rest = new RestTemplate();
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                Map<String,Object> param = new HashMap<>();
                param.put("scene", scene);
                param.put("page", "pages/index/index");
                param.put("width", 430);
                param.put("auto_color", false);
                Map<String,Object> line_color = new HashMap<>();
                line_color.put("r", 0);
                line_color.put("g", 0);
                line_color.put("b", 0);
                param.put("line_color", line_color);
                MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                HttpEntity requestEntity = new HttpEntity(param, headers);
                ResponseEntity<byte[]> entity = rest.exchange(codeUrl, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
                System.out.println("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
                byte[] result = entity.getBody();
                inputStream = new ByteArrayInputStream(result);

                File file = new File(itemQrCodePath);
                if (!file.exists()){
                    file.createNewFile();
                }
                outputStream = new FileOutputStream(file);
                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.flush();

                    Map<String, Object> context = new HashMap<>();
                    context.put(SUCCESS, true);
                    context.put("codeUrl", "qrCode/" + "QrCode_" + scene + ".jpg");
                    HtmlUtil.writerJson(response, context);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(outputStream != null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Map<String, Object> context = new HashMap<>();
            context.put(SUCCESS, true);
            context.put("codeUrl", "qrCode/QrCode_ITEMID_0.jpg");
            HtmlUtil.writerJson(response, context);
        }

    }
}
