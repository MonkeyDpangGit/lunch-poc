package com.tencent.demo.mapper;

import com.tencent.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * UserMapper
 *
 * @author torrisli
 * @date 2024/4/19
 * @Description: UserMapper
 */
public interface UserMapper {

    @Select("select * from t_sys_user where userid = #{userid}")
    User findByUserid(@Param("userid") String userid);

        @Update("insert  into  t_sys_user(username, password) values(#{userName}, #{password})")
//    @Update("update user t_sys_user set username = #{userName}, password = #{password} where userid = #{userId}")
    int update(@Param("userName") String userName, @Param("password") String password, @Param("userId") String userId);
}
