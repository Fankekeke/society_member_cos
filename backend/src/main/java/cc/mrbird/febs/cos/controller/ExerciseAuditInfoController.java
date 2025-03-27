package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ExerciseAuditInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IExerciseAuditInfoService;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 活动预约审核 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/exercise-audit-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExerciseAuditInfoController {

    private final IExerciseAuditInfoService exerciseAuditInfoService;

    private final IStaffInfoService staffInfoService;

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取活动预约信息
     *
     * @param page              分页对象
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ExerciseAuditInfo> page, ExerciseAuditInfo exerciseAuditInfo) {
        return R.ok(exerciseAuditInfoService.queryAuditPage(page, exerciseAuditInfo));
    }

    /**
     * 查询活动预约信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(exerciseAuditInfoService.getById(id));
    }

    /**
     * 查询活动预约信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(exerciseAuditInfoService.list());
    }

    /**
     * 新增活动预约信息
     *
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    @PostMapping
    public R save(ExerciseAuditInfo exerciseAuditInfo) {
        // 获取用户信息
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, exerciseAuditInfo.getUserId()));
        if (staffInfo == null) {
            return R.error("用户信息不存在");
        }
        exerciseAuditInfo.setUserId(staffInfo.getId());
        exerciseAuditInfo.setStatus("0");
        exerciseAuditInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(exerciseAuditInfoService.save(exerciseAuditInfo));
    }

    /**
     * 审核活动预约信息
     *
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    @PostMapping("/audit")
    public R audit(ExerciseAuditInfo exerciseAuditInfo) {
        exerciseAuditInfo.setAuditDate(DateUtil.formatDateTime(new Date()));
        // 添加消息通知
        if ("1".equals(exerciseAuditInfo.getStatus())) {
            // 通知用户审核通过
            notifyInfoService.addNotify(exerciseAuditInfo.getUserId(), "您好，您的活动预约【" + exerciseAuditInfo.getContent() + "】已通过审核！");
        } else {
            // 通知用户审核不通过
            notifyInfoService.addNotify(exerciseAuditInfo.getUserId(), "您好，您的活动预约【" + exerciseAuditInfo.getContent() + "】审核不通过，请重新预约！");
        }
        return R.ok(exerciseAuditInfoService.updateById(exerciseAuditInfo));
    }

    /**
     * 修改活动预约信息
     *
     * @param exerciseAuditInfo 活动预约信息
     * @return 结果
     */
    @PutMapping
    public R edit(ExerciseAuditInfo exerciseAuditInfo) {
        return R.ok(exerciseAuditInfoService.updateById(exerciseAuditInfo));
    }

    /**
     * 删除活动预约信息
     *
     * @param ids ids
     * @return 活动预约信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(exerciseAuditInfoService.removeByIds(ids));
    }
}
