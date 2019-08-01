package com.siti.wisdomhydrologic.datepull.mapper;

import com.siti.wisdomhydrologic.datepull.entity.Crunode;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
public interface NodeMapper {

    @Select("select * from WDS_DEF_CRUNODE")
    List<Crunode> getCrunode();
}
