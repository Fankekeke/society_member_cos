package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import cc.mrbird.febs.cos.entity.FinanceInfo;
import cc.mrbird.febs.cos.entity.LeaveInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.service.IFinanceInfoService;
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
 * 财务申请 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/finance-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FinanceInfoController {

    private final IFinanceInfoService financeInfoService;

    private final IStaffInfoService staffInfoService;

    private final INotifyInfoService notifyInfoService;

    /**
     * 分页获取财务申请
     *
     * @param page        分页对象
     * @param financeInfo 财务申请
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<FinanceInfo> page, FinanceInfo financeInfo) {
        return R.ok(financeInfoService.queryFinancePage(page, financeInfo));
    }

    /**
     * 审核财务申请
     *
     * @param id     主键ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/setStatusByFinance")
    public R setStatusByFinance(Integer id, String status) {
        FinanceInfo leaveInfo = financeInfoService.getById(id);
        leaveInfo.setAuditDate(DateUtil.formatDateTime(new Date()));
        leaveInfo.setStatus(status);
        // 添加通知
        notifyInfoService.addNotify(leaveInfo.getStaffId(), "您好，您的财务申请【" + leaveInfo.getTotalPrice() + "】已" + ("1".equals(status) ? "通过" : "驳回") + "，请等待打卡！");
        return R.ok(financeInfoService.updateById(leaveInfo));
    }

    /**
     * 订单支付回调
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/payment")
    public R payment(String orderCode) {
        return R.ok(financeInfoService.update(Wrappers.<FinanceInfo>lambdaUpdate().set(FinanceInfo::getStatus, "1").eq(FinanceInfo::getCode, orderCode)));
    }

    /**
     * 查询财务申请详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(financeInfoService.getById(id));
    }

    /**
     * 查询财务申请列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(financeInfoService.list());
    }

    /**
     * 新增财务申请
     *
     * @param financeInfo 财务申请
     * @return 结果
     */
    @PostMapping
    public R save(FinanceInfo financeInfo) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, financeInfo.getStaffId()));
        if (staffInfo != null) {
            financeInfo.setStaffId(staffInfo.getId());
            // 设置所属公司
            financeInfo.setEnterpriseId(staffInfo.getEnterpriseId());
        }
        // 申请单号
        financeInfo.setCode("FIN-" + System.currentTimeMillis());
        financeInfo.setStatus("0");
        financeInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 添加通知
        notifyInfoService.addNotify(financeInfo.getStaffId(), "您好，您申请的" + financeInfo.getAuditTitle() + "申请已提交，请耐心等待审核！");
        return R.ok(financeInfoService.save(financeInfo));
    }

    /**
     * 审核财务申请
     *
     * @param financeInfo 申请信息
     * @return 结果
     */
    @PostMapping("/audit")
    public R audit(FinanceInfo financeInfo) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, financeInfo.getAuditId()));
        if (staffInfo != null) {
            financeInfo.setAuditId(staffInfo.getId());
        }
        financeInfo.setAuditDate(DateUtil.formatDateTime(new Date()));
        return R.ok(financeInfoService.updateById(financeInfo));
    }

    /**
     * 修改财务申请
     *
     * @param financeInfo 财务申请
     * @return 结果
     */
    @PutMapping
    public R edit(FinanceInfo financeInfo) {
        return R.ok(financeInfoService.updateById(financeInfo));
    }

    /**
     * 删除财务申请
     *
     * @param ids ids
     * @return 财务申请
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(financeInfoService.removeByIds(ids));
    }
}
