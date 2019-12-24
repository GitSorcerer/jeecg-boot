package org.spring.model;

import lombok.Data;

/**
 * @Author: GH
 * @Date: 2019/12/1 21:58
 * @Version 1.0
 */
@Data
public class OneModel {
    private Integer id;
    private String model;

    public OneModel() {
        System.out.println("OneModel...");
    }
}
