package org.bravo.gaia.commons.base;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author hejianbing
 * @version @Id: PageCondition.java, v 0.1 2022年12月28日 10:04 hejianbing Exp $
 */
@Data
public class PageCondition<T> {

    /** 当前页 */
    @NotNull
    private Integer pageNum;

    /** 分页大小 */
    @NotNull
    @Max(value = 500, message = "pageSize max is 500")
    @Min(value = 1, message = "pageSize min support is 1")
    private Integer pageSize;
    @Valid
    @NotNull(message = "condition is null")
    private T       condition;
}