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
 * 会议记录
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConferenceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人
     */
    private Integer staffId;

    /**
     * 会议主题
     */
    private String title;

    /**
     * 会议内容
     */
    private String content;

    /**
     * 会议地址
     */
    private String address;

    /**
     * 图片
     */
    private String images;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 会议邀请人
     */
    private String staffIds;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 所属校企
     */
    private Integer enterpriseId;

    @TableField(exist = false)
    private String staffName;
    private String organizer;
    private String status;
}
