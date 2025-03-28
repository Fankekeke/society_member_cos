package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.*;
import cc.mrbird.febs.cos.service.IFinanceInfoService;
import cc.mrbird.febs.cos.service.INotifyInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会员缴费 控制层
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
     * 分页获取会员缴费
     *
     * @param page        分页对象
     * @param financeInfo 会员缴费
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<FinanceInfo> page, FinanceInfo financeInfo) {
        return R.ok(financeInfoService.queryFinancePage(page, financeInfo));
    }

    /**
     * 审核会员缴费
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
        notifyInfoService.addNotify(leaveInfo.getStaffId(), "您好，您的会员缴费【" + leaveInfo.getTotalPrice() + "】已" + ("1".equals(status) ? "通过" : "驳回") + "，请等待打卡！");
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
        return R.ok(financeInfoService.update(Wrappers.<FinanceInfo>lambdaUpdate().set(FinanceInfo::getStatus, "1").set(FinanceInfo::getAuditDate, DateUtil.formatDateTime(new Date())).eq(FinanceInfo::getCode, orderCode)));
    }

    /**
     * 查询会员缴费详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(financeInfoService.getById(id));
    }

    /**
     * 查询会员缴费列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(financeInfoService.list());
    }

    /**
     * 新增会员缴费
     *
     * @param financeInfo 会员缴费
     * @return 结果
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(FinanceInfo financeInfo) throws FebsException {
        List<StaffInfo> staffInfoList = staffInfoService.list();
        if (CollectionUtil.isEmpty(staffInfoList)) {
            throw new FebsException("暂无员工信息，请先添加员工信息！");
        }
        // 待添加的缴费记录
        List<FinanceInfo> financeInfoList = new ArrayList<>();
        for (StaffInfo staffInfo : staffInfoList) {
            FinanceInfo financeAddInfo = new FinanceInfo();
            financeAddInfo.setStaffId(staffInfo.getId());
            financeAddInfo.setAuditTitle("你好，" + staffInfo.getName() + "，" + financeInfo.getAuditTitle());
            // 申请单号
            financeAddInfo.setCode("FIN-" + System.currentTimeMillis() + staffInfo.getId());
            financeAddInfo.setStatus("0");
            financeAddInfo.setTotalPrice(financeInfo.getTotalPrice());
            financeAddInfo.setContent(financeInfo.getContent());
            financeAddInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
            financeAddInfo.setImages(financeInfo.getImages());
            financeInfoList.add(financeAddInfo);
            // 添加通知
            notifyInfoService.addNotify(financeAddInfo.getStaffId(),  financeAddInfo.getAuditTitle() + ", 请前往支付！");
        }

        return R.ok(financeInfoService.saveBatch(financeInfoList));
    }

    /**
     * 审核会员缴费
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
     * 修改会员缴费
     *
     * @param financeInfo 会员缴费
     * @return 结果
     */
    @PutMapping
    public R edit(FinanceInfo financeInfo) {
        return R.ok(financeInfoService.updateById(financeInfo));
    }

    /**
     * 删除会员缴费
     *
     * @param ids ids
     * @return 会员缴费
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(financeInfoService.removeByIds(ids));
    }
}
