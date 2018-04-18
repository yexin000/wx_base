package cn.trustway.weixin.exception;

/**
 * 异常定义
 * @author hucheng
 *
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String err){
		super(err);
	}
}
