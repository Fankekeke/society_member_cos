package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.LeaveInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.ILeaveInfoService;
import cc.mrbird.febs.cos.service.INotifyInfoService;
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
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/leave-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LeaveInfoController {

    private final ILeaveInfoService leaveInfoService;

    private final IStaffInfoService staffInfoService;

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取请假信息
     *
     * @param page      分页对象
     * @param leaveInfo 请假信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<LeaveInfo> page, LeaveInfo leaveInfo) {
        return R.ok(leaveInfoService.selectLeavePage(page, leaveInfo));
    }

    /**
     * 设置请假状态
     *
     * @param id     主键
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/setStatusByLeave")
    public R setStatusByLeave(Integer id, String status) {
        LeaveInfo leaveInfo = leaveInfoService.getById(id);
        leaveInfo.setAuditDate(DateUtil.formatDateTime(new Date()));
        leaveInfo.setStatus(status);
        // 添加通知
        notifyInfoService.addNotify(leaveInfo.getStaffId(), "您好，您的请假已" + ("1".equals(status) ? "通过" : "驳回") + "，请查看！");
        return R.ok(leaveInfoService.updateById(leaveInfo));
    }

    /**
     * 请假审批
     *
     * @param id     主键
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/leaveAudit")
    public R leaveAudit(Integer id, String status) {
        return R.ok(leaveInfoService.update(Wrappers.<LeaveInfo>lambdaUpdate().set(LeaveInfo::getStatus, status).eq(LeaveInfo::getId, id)));
    }

    /**
     * 请假信息详情
     *
     * @param id 请假ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(leaveInfoService.getById(id));
    }

    /**
     * 请假信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(leaveInfoService.list());
    }

    /**
     * 新增请假信息
     *
     * @param leaveInfo 请假信息
     * @return 结果
     */
    @PostMapping
    public R save(LeaveInfo leaveInfo) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, leaveInfo.getStaffId()));
        if (staffInfo != null) {
            leaveInfo.setStaffId(staffInfo.getId());
            leaveInfo.setEnterpriseId(staffInfo.getEnterpriseId());
        }
        leaveInfo.setStatus("0");
        leaveInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 添加通知
        notifyInfoService.addNotify(leaveInfo.getStaffId(), "您好，您的请假已提交，请耐心等待审核！");
        return R.ok(leaveInfoService.save(leaveInfo));
    }

    /**
     * 修改请假信息
     *
     * @param leaveInfo 请假信息
     * @return 结果
     */
    @PutMapping
    public R edit(LeaveInfo leaveInfo) {
        return R.ok(leaveInfoService.updateById(leaveInfo));
    }

    /**
     * 删除请假信息
     *
     * @param ids ids
     * @return 请假信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(leaveInfoService.removeByIds(ids));
    }
}
