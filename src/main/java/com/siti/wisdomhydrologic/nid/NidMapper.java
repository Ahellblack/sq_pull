package com.siti.wisdomhydrologic.nid;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by dell on 2019/10/26.
 */
public interface NidMapper {

    @Select("select section_code from  config_sensor_section_module " +
            "where section_status  = '1' ")
    List<Integer> getNid();
}
