package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代办任务
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AgentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属会员
     */
    private Integer staffId;

    /**
     * 代办标题
     */
    private String taskTitle;

    /**
     * 代办内容
     */
    private String content;

    /**
     * 完成状态（0.未完成 1.已完成）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 完成时间
     */
    private String complateDate;

    /**
     * 所属校企
     */
    private Integer enterpriseId;

    @TableField(exist = false)
    private String staffName;
    private String type;
}
