package cn.trustway.weixin.bean;

/**
 * 前端上传拍卖会
 */
public class AuctionUpload extends Auction {
  /**
   * 开始类型：0-立即开始,1-预展三天开始
   */
  private String startType;

  /**
   * 上传人wxid
   */
  private String wxid;

  private String imageList;

  public String getStartType() {
    return startType;
  }

  public void setStartType(String startType) {
    this.startType = startType;
  }

  public String getWxid() {
    return wxid;
  }

  public void setWxid(String wxid) {
    this.wxid = wxid;
  }

  public String getImageList() {
    return imageList;
  }

  public void setImageList(String imageList) {
    this.imageList = imageList;
  }
}
