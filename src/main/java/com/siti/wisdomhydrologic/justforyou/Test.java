package com.siti.wisdomhydrologic.justforyou;

import com.siti.wisdomhydrologic.util.ExceptionUtil;
import com.siti.wisdomhydrologic.util.enumbean.SystemError;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * Created by DC on 2019/7/25.
 *
 * @data ${DATA}-16:28
 */
@RestController
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    @RequestMapping(value = "/rest/article", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "接口说明", httpMethod = "POST", notes = "接口发布说明")
    @ApiParam(name = "参数", value = "这是描述参数")
    public String testSwagger2(@RequestBody String name) {
        return name;
    }

    public void quartzJob() {
        System.out.println("----------------------here comes!-------------------------------");
    }
}
