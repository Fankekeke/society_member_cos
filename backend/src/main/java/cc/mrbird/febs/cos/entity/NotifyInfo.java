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
 * 消息通知
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NotifyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户编号
     */
    private Integer userId;
    private Integer staffId;

    /**
     * 消息内容
     */
    private String content;
    private String status;

    /**
     * 删除标识
     */
    private Integer delFlag;


    private Integer enterpriseId;

    /**
     * 创建时间
     */
    private String createDate;

    private String name;

    @TableField(exist = false)
    private String staffName;

    @TableField(exist = false)
    private String userName;

    public NotifyInfo(String userCode, Integer delFlag, String createDate, String name) {
        this.userCode = userCode;
        this.delFlag = delFlag;
        this.createDate = createDate;
        this.name = name;
    }
    public NotifyInfo() {}

}
