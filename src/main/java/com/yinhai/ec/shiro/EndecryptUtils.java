package com.yinhai.ec.shiro;  

import org.apache.shiro.crypto.hash.Md5Hash;
  
public class EndecryptUtils {
	private static final String SALT = "HYADMIN";
	public static String getSalt() {
		return SALT;
	}
	
	/**
	  * @package com.yinhai.ec.base.util
	  * @method md5Password 方法 
	  * @describe <p>方法说明:对密码进行md5加密 返回密文</p>
	  * @return UsernamePasswordToken 
	  * @author LIUHUITAO
	  * @date 2016-1-4
	 */
    public static String md5Password(String username,String password){ 
        //组合username,两次迭代，对密码进行加密 
        String password_cipherText= new Md5Hash(password,username+SALT,2).toHex(); 
        return password_cipherText;
    } 
   /**
     * @package com.yinhai.ec.base.util
     * @method checkMd5Password 方法 
     * @describe <p>方法说明:验证密码与明文是否相同</p>
     * @return boolean 
     * @author LIUHUITAO
     * @date 2016-1-4
    */
    public static boolean checkMd5Password(String username,String password,String md5cipherText) { 
        //组合username,两次迭代，对密码进行加密 
        String password_cipherText= new Md5Hash(password,username+SALT,2).toHex(); 
        return md5cipherText.equals(password_cipherText); 
    }
}
 