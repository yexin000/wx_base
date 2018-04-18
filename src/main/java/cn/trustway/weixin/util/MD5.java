package cn.trustway.weixin.util;



import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5的封装
 * @version 1.0.0.1 2012-2-3
 * @date 2012-2-3下午1:42:11
 * @see	
 */
public class MD5 {
	private final static String[] hexDigits = {
	      "0", "1", "2", "3", "4", "5", "6", "7",
	      "8", "9", "a", "b", "c", "d", "e", "f"};

	  /**
	   * 转换字节数组为16进制字串
	   * @param b 字节数组
	   * @return 16进制字串
	   */
		@SuppressWarnings("unused")
		private static String byteArrayToNumString(byte[] b)
		{
			StringBuffer resultSb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
			  resultSb.append(byteToNumString(b[i]));//使用本函数则返回加密结果的10进制数字字串，即全数字形式
			}
			return resultSb.toString();
		}
		/**
		 * 转换字节数组为10进制字串
		 * @param b 字节数组
		 * @return 16进制字串
		 */
		private static String byteArrayToHexString(byte[] b) 
		{
			StringBuffer resultSb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
			  resultSb.append(byteToHexString(b[i]));//若使用本函数转换则可得到加密结果的16进制表示，即数字字母混合的形式
			}
			return resultSb.toString();
		}
		
		/**
		 * 转换字节为16进制字符串
		 * @param b字节
		 * @return 16进制字符串
		 */
		private static String byteToNumString(byte b) {

			int _b = b;
			if (_b < 0) {
			  _b = 256 + _b;
			}
			return String.valueOf(_b);
		}

		/**
		 * 转换字节为10进制字符串
		 * @param b字节
		 * @return 10进制字符串
		 */
		private static String byteToHexString(byte b) {
			int n = b;
			if (n < 0) {
			  n = 256 + n;
			}
			int d1 = n / 16;
			int d2 = n % 16;
			return hexDigits[d1] + hexDigits[d2];
		}
		
		/**
		 * 将字节数组算出16进制MD5串
		 * @param origin 字节数组
		 * @return 16进制MD5字符串
		 */
		public static String Encode16(byte[] origin)throws Exception
		{
			String resultString = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString =byteArrayToHexString(md.digest(origin));
			}
			catch (Exception e) {
				throw new Exception(e.getLocalizedMessage());
			}
			return resultString;
		}
		
		/**
		 * 将字符串算出16进制MD5串，字符串按系统编码进行编码
		 * @param origin 字符串
		 * @return 16进制MD5字符串
		 * @throws NoSuchAlgorithmException 
		 */
		public static String Encode16(String origin) throws NoSuchAlgorithmException 
		{
			String resultString = null;
//			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString =byteArrayToHexString(md.digest((new String(origin)).getBytes()));
//			}
//			catch (Exception e) {
//				throw new Exception(e.getLocalizedMessage());
//			}
			return resultString;
		}
		
		/**
		 * 将文件内容算出16进制MD5串
		 * @param FileName 文件完整路径
		 * @return 16进制MD5字符串
		 */
		public static String EncodeFile16(String FileName)throws Exception
		{
			String resultString = null;
			try
			{
				File Files=new File(FileName);
				resultString=EncodeFile16(Files);
			}
			catch (Exception e)
			{
				throw new Exception(e.getLocalizedMessage());
			}
			return resultString;
		}
		
		/**
		 * 将文件内容算出16进制MD5串
		 * @param Files 文件对象
		 * @return 16进制MD5字符串
		 */
		public static String EncodeFile16(File Files)throws Exception
		{
			String resultString = null;
			FileInputStream fr=null;
			try
			{
				fr=new FileInputStream(Files);
				MessageDigest md = MessageDigest.getInstance("MD5"); 
				DigestInputStream mdfile=new DigestInputStream(fr,md);
				int len=64*1024;
				byte buf[]=new byte[len];
				while(mdfile.read(buf,0,len)!=-1){
				}
				resultString =byteArrayToHexString(md.digest());
			}
			catch (Exception e)
			{
				throw new Exception(e.getLocalizedMessage());
			}
			finally
			{
				fr.close();
				fr=null;
			}
			return resultString;
		}
		
		private MessageDigest md;
		public MD5()
		{
			try {
				this.md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * 分段计算
		 * @param input
		 * @param offset
		 * @param len
		 */
		public void update(byte[] input,int offset,int len)
		{
			this.md.update(input, offset, len);
		}
		
		/**
		 * 计算输出md5的16进制字符串
		 * @return
		 */
		public String sumHexMD5()
		{
			return byteArrayToHexString(this.md.digest());
		}
}

