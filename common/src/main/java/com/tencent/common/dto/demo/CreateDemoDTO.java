package com.tencent.common.dto.demo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank.List;

/**
 * CreateDemoDTO
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: CreateDemoDTO
 */
public class CreateDemoDTO {

    private String id;

    @NotBlank
    private String name;

    private String desc;

    private String[] org;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String[] getOrg() {
        return org;
    }

    public void setOrg(String[] org) {
        this.org = org;
    }
}
