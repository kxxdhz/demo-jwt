package come.demo.jwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import come.demo.jwt.models.User;
import come.demo.jwt.utils.JWTUtil;
import come.demo.jwt.utils.ResponseData;

@Controller  
@RequestMapping("/users")
public class UserController {
	
	@RequestMapping("/login")  
    @ResponseBody  
    public ResponseData login(String username, String password) {  
        if ("lvpeng".equals(username) && "123456".equals(password)) {  
            ResponseData responseData = ResponseData.ok();  
            User user = new User();  
            user.setId(1);  
            user.setUsername(username);  
            user.setPassword(password);  
            responseData.putDataValue("user", user);  
            String token = JWTUtil.sign(user, 30L * 24L * 3600L * 1000L);  
            if (token != null) {  
                responseData.putDataValue("token", token);  
            }  
            return responseData;  
        }  
        return ResponseData.customerError().putDataValue(ResponseData.ERRORS_KEY, new String[] { "用户名或者密码错误" });  
    } 
	
	@RequestMapping("/getInfo")  
    @ResponseBody 
	public ResponseData getInfo(String token) {
        User user = JWTUtil.unsign(token, User.class);  
        if (user != null) {  
            return ResponseData.ok().putDataValue("user", user);  
        }  
        return ResponseData.customerError().putDataValue(ResponseData.ERRORS_KEY, new String[] { "token不合法" });  
	 }
}
