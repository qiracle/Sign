
/*Copyright (C) 2016.4
 * 
 *author:Qiangqiang Jiang
 * 
 */
package qq.qiracle.userservice;



public interface UserService {
	public boolean userLogin(String loginName,String loginPassword,int type) throws Exception;
		
		
	public boolean modifyPwd(String loginName,String OldPassword,String NewPassword,int Type) throws Exception;
	
	public boolean sign(String scanResult,String userName) throws Exception;
	
}