package com.qf.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.entity
 * @company 千锋教育
 * @date 2024/6/12 12:02
 */
@Data
@NoArgsConstructor
public class PageBean<T> {
    private int pageNum;
    private int pageSize;
    private long totalSize;
    private int pageCount;
    private List<T> data;

    /**
     * 开始 结束 页码
     */
    private int startPage;
    private int endPage;

    public PageBean(int pageNum, int pageSize, long totalSize, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
        this.data = data;

        // 总页数
        this.pageCount = (int) (totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1);
        System.out.println(pageCount);
        // 开始页码, 结束页码
        // 1.正常
        this.startPage = pageNum - 4;
        this.endPage = pageNum + 5;
        // 2.页码小于5
        if (pageNum < 5) {
            this.startPage = 1;
            this.endPage = 10;
        }
        // 3.后面页数
        if (pageNum > this.pageCount - 5) {
            this.startPage = this.pageCount - 9;
            this.endPage = this.pageCount;
        }
        // 4.总页数小于10
        if(pageCount < 10){
            this.startPage = 1;
            this.endPage = pageCount;
        }
    }
}
