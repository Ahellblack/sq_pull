package com.siti.wisdomhydrologic.datepull.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
public interface ElementMapper {

    @Select("select NID from WDS_DEF_ELEMENT")
    List<Integer> getAll();
}
