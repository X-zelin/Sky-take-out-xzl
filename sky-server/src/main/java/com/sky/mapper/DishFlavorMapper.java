package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);
    /**
     * 根据菜品id删除口味表数据
     * @param Dishid
     * @return
     */
    @Delete("delete from dish_flavor where dish_id = #{Dishid}")
    void deleteByDishId(Long Dishid);
    /**
     * 根据菜品id查询对应的口味数据
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);
}