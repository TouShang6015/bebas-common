package com.org.bebas.utils.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.org.bebas.core.model.Page;
import com.org.bebas.enums.result.ResultEnum;
import com.org.bebas.exception.CommonException;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 统一返回的实体
 *
 * @author WuHao
 * @Return
 * @since 2022/5/12 17:30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Result", description = "公共返回实体")
@Data
@Slf4j
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 状态
     */
    private boolean status;
    /**
     * 分页总数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;
    /**
     * 分页总数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;
    /**
     * 分页总数
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;
    /**
     * 分页实体
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Page<?> pageBean;

    private long timestamp = Instant.now().toEpochMilli();


    public Result() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.message = ResultEnum.SUCCESS.getMessage();
        this.status = ResultEnum.SUCCESS.isStatus();
    }

    public Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.status = resultEnum.isStatus();
    }

    public Result(ResultEnum resultEnum, T t) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.data = t;
        this.status = resultEnum.isStatus();
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        result.setData(null);
        result.setStatus(ResultEnum.SUCCESS.isStatus());
        return result;
    }

    public static <T> Result<T> success(T t) {
        Result<T> result = Result.success();
        result.setData(t);
        return result;
    }

    public static <T> Result<T> successBoolean(Boolean flag) {
        return flag ? Result.success() : Result.fail();
    }

    public static <T> Result<T> success(ResultEnum resultEnum) {
        Result<T> result = Result.success();
        result.setMessage(resultEnum.getMessage());
        return result;
    }

    public static <T> Result<T> successMsg(String msg) {
        Result<T> result = Result.success();
        result.setMessage(msg);
        return result;
    }

    public static <T> Result<List<T>> success(IPage<T> iPage) {
        Result<List<T>> result = Result.success();
        result.setPage(Math.toIntExact(iPage.getCurrent()));
        result.setSize(Math.toIntExact(iPage.getSize()));
        result.setData(iPage.getRecords());
        result.setTotal(Math.toIntExact(iPage.getTotal()));
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.FAIL.getCode());
        result.setMessage(ResultEnum.FAIL.getMessage());
        result.setData(null);
        result.setStatus(ResultEnum.FAIL.isStatus());
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> result = Result.fail();
        result.setMessage(msg);
        log.info("resultCode: {} | result： {}", result.getCode(), result.getMessage());
        return result;
    }

    public static <T> Result<T> fail(int code, String msg) {
        Result<T> result = Result.fail();
        result.setMessage(msg);
        result.setCode(code);
        log.info("resultCode: {} | result： {}", result.getCode(), result.getMessage());
        return result;
    }

    public static <T> Result<T> failData(T t) {
        Result<T> result = Result.fail();
        result.setData(t);
        log.info("resultCode: {} | result： {}", result.getCode(), result.getMessage());
        return result;
    }

    public static <T> Result<T> fail(ResultEnum resultEnum) {
        Result<T> result = Result.fail();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMessage());
        result.setStatus(resultEnum.isStatus());
        log.info("resultCode: {} | result： {}", result.getCode(), result.getMessage());
        return result;
    }

    public static <T> Result<T> fail(ResultEnum resultEnum, T t) {
        Result<T> result = Result.fail(resultEnum);
        result.setData(t);
        log.info("resultCode: {} | result： {}", result.getCode(), result.getMessage());
        return result;
    }

    public Result<T> data(T t) {
        if (this.data instanceof List) {
            ((List<T>) this.data).add(t);
        } else if (this.data instanceof Set) {
            ((Set<T>) this.data).add(t);
        } else {
            this.setData(t);
        }
        return this;
    }

    public Result<T> put(String key, Object value) {
        if (this.data == null) {
            this.data = (T) new HashMap<>();
        }
        if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).put(key, value);
            return this;
        }
        throw new CommonException("Result [put]异常");
    }

}
