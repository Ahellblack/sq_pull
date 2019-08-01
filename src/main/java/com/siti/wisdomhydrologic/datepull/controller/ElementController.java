package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.service.ElementService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
@RestController
@RequestMapping("/element")
public class ElementController {

    @Resource
    private ElementService elementService;

    @RequestMapping("/get")
    public List<Integer> getElement() {
        List<Integer> list = elementService.getElement();
        return list;
    }


}
