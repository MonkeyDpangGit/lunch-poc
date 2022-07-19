package com.tencent.common.dto.demo;

import javax.validation.constraints.NotBlank;

/**
 * UpdateDemoDTO
 *
 * @author torrisli
 * @date 2021/8/25
 * @Description: UpdateDemoDTO
 */
public class UpdateDemoDTO {

    @NotBlank
    private String name;

    private String desc;


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
}
