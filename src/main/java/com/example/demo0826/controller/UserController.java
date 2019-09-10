package com.example.demo0826.controller;

import com.example.demo0826.entity.User;
import com.example.demo0826.service.TokenService;
import com.example.demo0826.service.UserService;
import com.example.demo0826.z_customTool.annotation.PassToken;
import com.example.demo0826.z_customTool.annotation.UserLoginToken;
import com.example.demo0826.z_customTool.result.Response;
import com.example.demo0826.z_customTool.result.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
* 控制层
* */
//@RestController注解相当于@Controller + @ResponseBody 合在一起的作用。
//@Controller
//声明是一个控制器,负责具体的业务模块流程的控制，在此层里面要调用Serice层的接口来控制业务流程。

//@ResponseBody注解的作用是将controller的方法返回的对象
// 通过适当的转换器转换为指定的格式之后，写入到response对象的body区，通常用来返回JSON数据或者是XML。
@RestController
public class UserController {
    //一般来说Controller层和Service层是有关联的，service层和dao层是有关联的。
    // 我们使用@Autowired注解或@Resource注解后，可以在controller层中装配Service，在Service层中装配Reponsitory(现在是mapper)
    @Resource
    UserService userService;
    @Autowired
    TokenService tokenService;

    public static String currentToken;
    //RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。
    //value 代表地址    method代表方式，如GET或POST
    /*
    * 测试POST，Swagger页面上会显示此请求是POST请求。
    * */
    @PassToken
    @RequestMapping(value = "/testpost",method= RequestMethod.POST)
    public String testpost(){

        return "post success!!!";
    }
    /*
     * 测试GET请求。
     * */

    @PassToken
    @RequestMapping(value = "/test",method= RequestMethod.GET)
    public String test(){
        return "test function";
    }
    /*
    * 通过ID查询用户信息。
    * */
    //@RequestParam  GET和POST请求传的参数会自动转换赋值到@RequestParam 所注解的变量上。
    @UserLoginToken
    @RequestMapping(value = "/selectById",method= RequestMethod.GET)
    public Response selectById(@RequestParam int id){

        try {
            User user=userService.serUserSelect(id);
            if (user == null) {
                return  new Response(ResponseCode.EMPTY);
            }
            return  new Response(ResponseCode.SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Response(ResponseCode.ERROR);

    }
    /*
    * 插入用户数据。
    * */
    @UserLoginToken
    @RequestMapping(value = "/insert",method= RequestMethod.GET)
    public Response insert(@RequestParam int id, @RequestParam String name, @RequestParam String Permission, String token, HttpServletRequest req){
        try {
            User user=new User();
            user.setId(id);
            user.setName(name);
            user.setPermission(Permission);
            int insertStatus=userService.serUserInsert(user);
            if(insertStatus==1){
                //通过Response类包装状态码，及data数据。
                return  new Response(ResponseCode.SUCCESS,insertStatus);
            }else{
                return  new Response(ResponseCode.ERROR,insertStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Response(ResponseCode.ERROR);
    }
    /*
    * 更新用户数据
    * */
    @UserLoginToken
    @RequestMapping(value = "/update",method= RequestMethod.GET)
    public Response update(@RequestParam int id,@RequestParam String name,@RequestParam String Permission){
        try {
            User user=new User();
            user.setId(id);
            user.setName(name);
            user.setPermission(Permission);
            int updateStatus=userService.serUserUpdate(user);
            if(updateStatus==1){
                return  new Response(ResponseCode.SUCCESS,updateStatus);
            }else{
                return  new Response(ResponseCode.ERROR,updateStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Response(ResponseCode.ERROR);
    }
    /*
    * 通过ID删除用户数据
    * */
    @UserLoginToken
    @RequestMapping(value = "/delete",method= RequestMethod.GET)
    public Response delete(@RequestParam int id){
        try {
            User user=new User();
            user.setId(id);
            int deleteStatus=userService.serUserDelete(user);
            if (deleteStatus==1){
                return  new Response(ResponseCode.SUCCESS,deleteStatus);
            }else{
                return  new Response(ResponseCode.ERROR,deleteStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Response(ResponseCode.ERROR);
    }
   /*
   * 获取token  通过输入id和name信息后返回token信息,并保存token信息至cooikes。
   * */
   @GetMapping("/login")
   public Response login(@RequestParam int id, @RequestParam String name, HttpServletResponse resp)  {
       User user=new User();
       user.setId(id);
       user.setName(name);
       //通过id查询User对象
       User userForBase=userService.serUserSelect(user.getId());
       //如果对象为空，则抛异常
       if(userForBase==null){
           return  new Response(ResponseCode.ERROR);
       }else {
           //如果对象name字段不一致，则抛异常
           if (!userForBase.getName().equals(user.getName())){
               return  new Response(ResponseCode.ERROR);
           }else {
               //信息验证成功后返回token信息
               String token = tokenService.getToken(userForBase);
               //由于token需要储存介质，可选session，cookies，redis等。
               //这里选择cookies
               Cookie cookie = new Cookie("token", token);
               resp.addCookie(cookie);
               System.out.println("ServiceToken: "+token);
               return  new Response(ResponseCode.SUCCESS,"token:"+token);


           }
       }
   }

}
