package com.siti.wisdomhydrologic.datepull.controller;

import com.siti.wisdomhydrologic.datepull.entity.Crunode;
import com.siti.wisdomhydrologic.datepull.service.NodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @Resource
    private NodeService nodeService;

    @RequestMapping("/get")
    public List<Crunode> getCrunode() {
        return nodeService.getCrunode();
    }

}
