package com.siti.wisdomhydrologic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by DC on 2019/6/12.
 *
 * @data ${DATA}-14:58
 */
@Configuration
public class ConstantConfig extends WebMvcConfigurerAdapter {

/*

    */
/**
     * PC版本号
     *//*

    @Value("${version.pc}")
    private String pcversion;
    */
/**
     * 小版本号
     *//*

    @Value("${version.build}")
    private String build;


    public String getPcversion() {
        return pcversion;
    }

    public void setPcversion(String pcversion) {
        this.pcversion = pcversion;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }
*/
}
