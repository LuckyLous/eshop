package com.hello.eshop.dao;

import com.hello.eshop.bean.Category;
import com.hello.eshop.bean.CategoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
    
    /**
     * 根据分类的ID,查询下面的直接子类
     * @return
     */
    List<Category> selectUnderCategoryByParentId(Integer parentId);
}