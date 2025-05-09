package com.example.ai.common.res;

import java.util.List;

public class CommonPage<T> {

    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 页大小
     */
    private Integer pageSize;
    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> list;

    
}
