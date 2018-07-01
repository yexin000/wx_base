package cn.trustway.weixin.controller;

import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.servlet.TokenThread;
import cn.trustway.weixin.util.Const;
import cn.trustway.weixin.util.DateUtil;
import cn.trustway.weixin.util.HtmlUtil;
import cn.trustway.weixin.util.StringUtil;
import cn.trustway.weixin.util.json.JsonToolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
@RequestMapping("/jsapi")
public class JsapiController extends BaseController {
    @Value("#{pref.filePath}")
    private String filepath;

    private final static String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

    @RequestMapping(value = "/sign")
    public void sign(String url, HttpServletResponse response) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        Map<String, String> result;
        result = sign(TokenThread.jsapiTicket.getTicket(), url);
        jsonMap.put("data", result);
        HtmlUtil.writerJson(response, jsonMap);
    }

    @RequestMapping(value = "/uploadImages")
    public void uploadImages(String serverIds, HttpServletResponse response) throws Exception {
        if(StringUtils.isNotBlank(serverIds)) {
            System.out.println(serverIds);
            String[] serverList = serverIds.split("&");

            String ffile = DateUtil.getDays(), fileUrl = "";
            String rootFilePath = filepath + Const.USERAPPHEADIMG + ffile;
            File rootFile = new File(rootFilePath);
            if (!rootFile.exists()) {
                rootFile.mkdirs();
            }

            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < serverList.length; i++) {
                String requestUrl = DOWNLOAD_MEDIA_URL.replace("ACCESS_TOKEN",
                        TokenThread.gzhAccessToken.getToken()).replace("MEDIA_ID",
                        serverList[i]);
                String fileName = UUID.randomUUID().toString().trim().replaceAll("-", "");
                String extName = ".jpg"; // 扩展名格式：
                fileUrl = Const.SDFILE + Const.USERAPPHEADIMG + ffile + "/" + fileName + extName;
                FileOutputStream fo = new FileOutputStream(rootFilePath + "/" + fileName + extName);
                try {
                    URL url = new URL(requestUrl);
                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setDoInput(true);
                    conn.setRequestMethod("GET");
                    BufferedInputStream bis = new BufferedInputStream(conn
                            .getInputStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                    byte[] temp = new byte[1024];
                    int size = 0;
                    while ((size = bis.read(temp)) != -1) {
                        fo.write(temp, 0, size);
                    }

                    fo.flush();
                    fo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Map<String, Object> data = new HashMap<>();
                data.put("data", fileUrl);
                BufferedImage srcImage;
                srcImage = ImageIO.read(new File(rootFilePath + "/" + fileName + extName));
                data.put("width", srcImage.getWidth());
                data.put("height", srcImage.getHeight());
                dataList.add(data);

            }

            Map<String, Object> context = getRootMap();
            context.put(CODE, AppInitConstants.HttpCode.HTTP_SUCCESS);
            context.put("data", dataList);
            HtmlUtil.writerJson(response, context);
        } else {
            Map<String, Object> context = getRootMap();
            context.put(CODE, "-1");
            HtmlUtil.writerJson(response, context);
        }

    }


    private static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
