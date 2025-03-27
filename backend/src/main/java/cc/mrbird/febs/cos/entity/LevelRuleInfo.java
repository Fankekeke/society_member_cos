package cc.mrbird.febs.cos.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 会员等级规则
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LevelRuleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 等级编号
     */
    private String code;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 最低打卡天数
     */
    private Integer checkDayMin;

    /**
     * 最高打卡天数
     */
    private Integer checkDayMax;

    /**
     * 创建时间
     */
    private String createDate;


}
