package zhy.blog.entity;

import com.alibaba.fastjson.JSON;
import zhy.blog.util.Status;

import java.util.Date;

/**
 * Base class of all the entities.
 *
 * @author zhyyy
 * @since 20190307
 */
public abstract class BaseEntity {

    /**
     * ID of this article
     */
    private Integer id;
    /**
     * Status of entity.
     *
     * @see Status
     */
    private Integer status;

    private Date createDate;

    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public BaseEntity setId(int id) {
        this.id = id;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public BaseEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public BaseEntity setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public BaseEntity setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public String toJSON() {
        return JSON.toJSONString(this);
    }

    public BaseEntity normal() {
        return setStatus(Status.NORMAL);
    }

    public BaseEntity initialized() {
        return setStatus(Status.INITIALIZED);
    }
}
