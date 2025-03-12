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
 * 财务申请
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FinanceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请人ID
     */
    private Integer staffId;

    /**
     * 申请标题
     */
    private String auditTitle;
    private String code;

    private String totalPrice;

    /**
     * 申请内容
     */
    private String content;

    /**
     * 状态（0.未审批 1.通过 2.驳回）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 图片
     */
    private String images;

    /**
     * 审批人
     */
    private Integer auditId;

    /**
     * 审批时间
     */
    private String auditDate;

    @TableField(exist = false)
    private String staffName;

    /**
     * 所属校企
     */
    private Integer enterpriseId;


}
