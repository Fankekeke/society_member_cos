package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.MessageInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IMessageInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/message-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageInfoController {

    private final IMessageInfoService messageInfoService;

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取消息信息
     *
     * @param page        分页对象
     * @param messageInfo 消息信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<MessageInfo> page, MessageInfo messageInfo) {
        return R.ok(messageInfoService.queryMessagePage(page, messageInfo));
    }

    /**
     * 获取ID获取审核详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(messageInfoService.getById(id));
    }

    /**
     * 消息回复
     *
     * @param messageInfo
     * @return 结果
     */
    @PostMapping("/messageReply")
    public R messageReply(MessageInfo messageInfo) {
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        messageInfo.setTaskStatus(0);
        return R.ok(messageInfoService.save(messageInfo));
    }

    /**
     * 查找聊天记录
     *
     * @param takeUser
     * @param sendUser
     * @return 结果
     */
    @GetMapping("/getMessageDetail")
    public R getMessageDetail(@RequestParam(value = "takeUser") Integer takeUser, @RequestParam(value = "sendUser") Integer sendUser) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, takeUser));
//        if (takeUser.equals(staffInfo.getUserId())) {
//            messageInfoService.update(Wrappers.<MessageInfo>lambdaUpdate().set(MessageInfo::getTaskStatus, 1)
//                    .eq(MessageInfo::getTakeUser, takeUser).eq(MessageInfo::getSendUser, sendUser));
//        } else {
//            messageInfoService.update(Wrappers.<MessageInfo>lambdaUpdate().set(MessageInfo::getTaskStatus, 1)
//                    .eq(MessageInfo::getTakeUser, sendUser).eq(MessageInfo::getSendUser, takeUser));
//        }
        return R.ok(messageInfoService.getMessageDetail(staffInfo.getId(), sendUser));
    }

    /**
     * 查询消息信息
     *
     * @param userId
     * @return 结果
     */
    @GetMapping("/messageListById")
    public R messageListById(@RequestParam Integer userId) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        return R.ok(messageInfoService.messageListById(staffInfo.getUserId()));
    }

    /**
     * 根据用户编号获取联系人
     *
     * @param userId 用户编号
     * @return 结果
     */
    @GetMapping("/contact/person")
    public R selectContactPerson(@RequestParam("userId") Integer userId, @RequestParam("flag") Integer flag) {
        StaffInfo staffInfo =  staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        return R.ok(messageInfoService.selectContactPerson(staffInfo.getId(), flag));
    }

    /**
     * 获取消息信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(messageInfoService.list());
    }

    /**
     * 新增消息信息
     *
     * @param messageInfo 消息信息
     * @return 结果
     */
    @PostMapping
    public R save(MessageInfo messageInfo) {
        messageInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        messageInfo.setTaskStatus(0);
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, messageInfo.getSendUser()));
        if (staffInfo != null) {
            messageInfo.setSendUser(staffInfo.getId());
        }
        return R.ok(messageInfoService.save(messageInfo));
    }

    /**
     * 修改消息信息
     *
     * @param messageInfo 消息信息
     * @return 结果
     */
    @PutMapping
    public R edit(MessageInfo messageInfo) {
        return R.ok(messageInfoService.updateById(messageInfo));
    }

    /**
     * 删除消息信息
     *
     * @param ids ids
     * @return 消息信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(messageInfoService.removeByIds(ids));
    }
}
