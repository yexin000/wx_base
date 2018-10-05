package cn.trustway.weixin.bean;

/**
 * 前端上传鉴定商品
 */
public class IdentifyUpload extends Identify {
  /**
   * 图片上传到微信后的id列表
   */
  private String imageList;

  public String getImageList() {
    return imageList;
  }

  public void setImageList(String imageList) {
    this.imageList = imageList;
  }
}
