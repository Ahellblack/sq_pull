package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.vo.RealVo;
import com.siti.wisdomhydrologic.scheduTask.mapper.RealMapper;
import com.siti.wisdomhydrologic.util.NidListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2019/9/23.
 */
@RequestMapping("/realdata")
@RestController
public class RealController {


    @Autowired
    private RealMapper realMapper;

    @GetMapping("getData")
    public List<RealVo> getData(String dateStart, String dateEnd) {

        List<Integer> nidList = NidListUtils.getNidList();
        return realMapper.gethistory5MinData(dateStart,dateEnd,nidList);
    }


}
