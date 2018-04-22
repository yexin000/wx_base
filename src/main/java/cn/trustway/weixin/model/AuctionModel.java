package cn.trustway.weixin.model;

/**
 * 拍卖功能模型
 *
 * @author yexin
 *
 */
public class AuctionModel extends BaseModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
