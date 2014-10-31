package com.fjnu.service;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import com.fjnu.domain.User;

@WebService
public interface UserService {

	/** 注册 */
	public boolean register(@WebParam(name = "user") String strUser);

	/** 登陆 */
	public String login(@WebParam(name = "email") String email,
			@WebParam(name = "password") String password);

	/** 获得个人信息 */
	public String getUserInfo(@WebParam(name = "email") String email);// 下次整改，留有安全隐患

	/** 获得所有用户 */
	public String getAllUser();

	/** 修改用户资料 */
	public boolean updateUserInfo(@WebParam(name = "user") String strUser);

	/** 加好友 */
	public int makeFriend(@WebParam(name = "friend") String strFriend);

	/** 删除好友 */
	public boolean deleteFriend(@WebParam(name = "friend") String strFriend);
	
	/** 获得全部好友方法*/
	public String getAllFriend(@WebParam(name="email")String email);
	// 查询该用户时候存在
}
