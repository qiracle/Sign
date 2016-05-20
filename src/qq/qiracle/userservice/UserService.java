
//======================================================================
 //
 //        Copyright (C) 2016   
 //        All rights reserved
 //
 //        filename :UserService
 //        
 //
 //        created by Qiangqiang Jinag in  2016.04
 //        https://github.com/qiracle
 //		   qiracle@foxmail.com
 //
 //======================================================================
package qq.qiracle.userservice;



public interface UserService {
	public boolean userLogin(String loginName,String loginPassword,int type) throws Exception;
		
		
	public boolean modifyPwd(String loginName,String OldPassword,String NewPassword,int Type) throws Exception;
	
	public boolean sign(String scanResult,String userName) throws Exception;
	
}