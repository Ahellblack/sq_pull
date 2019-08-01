package com.siti.wisdomhydrologic.datepull.service;

import com.siti.wisdomhydrologic.datepull.mapper.ElementMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
@Service
public class ElementService {

    @Resource
    private ElementMapper elementMapper;

    public List<Integer> getElement() {
        return elementMapper.getAll();
    }
}
