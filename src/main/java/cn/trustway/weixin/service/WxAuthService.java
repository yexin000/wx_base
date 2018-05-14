package cn.trustway.weixin.service;

import cn.trustway.weixin.common.AppInitConstants;
import cn.trustway.weixin.pojo.WxSession;
import cn.trustway.weixin.redis.RedisUtil;
import cn.trustway.weixin.util.HttpClientUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("wxAuthService")
public class WxAuthService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 根据小程序登录返回的code获取openid和session_key
     * @param code
     * @return
     */
    public WxSession getWxSession(String code){
        StringBuffer sb = new StringBuffer();
        sb.append("?appid=").append(AppInitConstants.XCX_APP_ID);
        sb.append("&secret=").append(AppInitConstants.XCX_APP_SECRET);
        sb.append("&js_code=").append(code);
        sb.append("&grant_type=").append(AppInitConstants.XCX_AUTH_TYPE);
        String httpUrl = AppInitConstants.XCX_AUTH_URL + sb;
        String httpRes = HttpClientUtil.doPost(httpUrl, "{}", "UTF-8");
        JSONObject obj = new JSONObject().fromObject(httpRes);
        WxSession wxSession = (WxSession)JSONObject.toBean(obj,WxSession.class);
        return wxSession;
    }

    /**
     * 缓存微信openId和session_key
     * @param openid		微信用户唯一标识
     * @param sessionKey	微信服务器会话密钥
     * @return
     */
    public String create3rdSession(String openid, String sessionKey){
        String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
        StringBuffer sb = new StringBuffer();
        sb.append(sessionKey).append("#").append(openid);
        redisUtil.add(thirdSessionKey, sb.toString());
        return thirdSessionKey;
    }
}
