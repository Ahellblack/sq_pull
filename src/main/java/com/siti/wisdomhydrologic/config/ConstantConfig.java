package com.siti.wisdomhydrologic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by DC on 2019/6/12.
 *
 * @data ${DATA}-14:58
 */
@Configuration
public class ConstantConfig extends WebMvcConfigurerAdapter {

  /*  public static final String NIDURL = "http://172.20.50.115:8080/sq/module/getAllNid";
    public static final String NIDTESTURL = "http://10.1.30.210:50081/sq/module/getAllNid";*/

    /**
     * nid拉取地址
     */
    @Value("${nid.url}")
    private String nidUrl;

    @Value("${nid.testUrl}")
    private String nidTestUrl;

    @Value("${nid.localUrl}")
    private String nidLocalUrl;


    public String getNidLocalUrl() {
        return nidLocalUrl;
    }

    public void setNidLocalUrl(String nidLocalUrl) {
        this.nidLocalUrl = nidLocalUrl;
    }

    public String getNidUrl() {
        return nidUrl;
    }

    public void setNidUrl(String nidUrl) {
        this.nidUrl = nidUrl;
    }

    public String getNidTestUrl() {
        return nidTestUrl;
    }

    public void setNidTestUrl(String nidTestUrl) {
        this.nidTestUrl = nidTestUrl;
    }

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
