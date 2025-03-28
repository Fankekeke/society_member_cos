package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 共享文件管理
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件编号
     */
    private String code;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件内容
     */
    private String content;

    /**
     * 状态（轻、重、缓、急、一般）
     */
    private String status;

    /**
     * 来源
     */
    private String createBy;

    /**
     * 文件地址
     */
    private String images;

    /**
     * 创建时间
     */
    private String createDate;
    private String fileKey;

    /**
     * 所属会员
     */
    private Integer enterpriseId;

}
