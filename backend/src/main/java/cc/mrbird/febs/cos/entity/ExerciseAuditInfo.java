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
 * 活动预约审核
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExerciseAuditInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 会员ID
     */
    private Integer userId;

    /**
     * 活动ID
     */
    private Integer exerciseId;

    /**
     * 驳回原因
     */
    private String content;

    /**
     * 状态（0.未审核 1.通过 2.驳回）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 审核时间
     */
    private String auditDate;

    @TableField(exist = false)
    private String staffName;

    @TableField(exist = false)
    private String exerciseName;


}
