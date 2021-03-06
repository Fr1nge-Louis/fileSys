package cn.lhs.filesys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.alibaba.fastjson.JSON;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MyFile extends Model<MyFile> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.AUTO)
    private Integer fileId;

    private Integer uploaderId;

    private String fileUrl;

    private String fileOriginName;

    private String fileName;

    private String fileFormats;

    private String fileType;

    private Long fileSize;

    private Date uploadTime;

    private Integer downloadTimes;

    private String isShared;

    private String isDel;


    @Override
    protected Serializable pkVal() {
        return this.fileId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
