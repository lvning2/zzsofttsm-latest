//package com.zzsoft.zzsofttsm.mapper;
//
//import com.zzsoft.zzsofttsm.config.domain.Permission;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//
//import java.util.List;
//
////@Mapper
//public interface PermissionMapper {
//
//    @Select("SELECT\n" +
//            "  p.*\n" +
//            "FROM\n" +
//            "  user AS u\n" +
//            "  LEFT JOIN tb_user_role AS ur\n" +
//            "    ON u.id = ur.user_id\n" +
//            "  LEFT JOIN tb_role AS r\n" +
//            "    ON r.id = ur.role_id\n" +
//            "  LEFT JOIN tb_role_permission AS rp\n" +
//            "    ON r.id = rp.role_id\n" +
//            "  LEFT JOIN tb_permission AS p\n" +
//            "    ON p.id = rp.permission_id\n" +
//            "WHERE u.id = #{uid}")
//    List<Permission> selectByUserId(Integer uid);
//
//
//}
