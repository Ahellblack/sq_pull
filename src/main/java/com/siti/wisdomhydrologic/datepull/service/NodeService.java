package com.siti.wisdomhydrologic.datepull.service;

import com.siti.wisdomhydrologic.datepull.entity.Crunode;
import com.siti.wisdomhydrologic.datepull.mapper.NodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dell on 2019/7/17.
 */
@Service
public class NodeService {

    @Resource
    private NodeMapper nodeMapper;

    public List<Crunode> getCrunode() {
        return nodeMapper.getCrunode();
    }
}
