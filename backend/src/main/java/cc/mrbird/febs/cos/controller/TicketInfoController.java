package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.TicketInfo;
import cc.mrbird.febs.cos.entity.TicketOptionInfo;
import cc.mrbird.febs.cos.entity.TicketRecord;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.cos.service.ITicketInfoService;
import cc.mrbird.febs.cos.service.ITicketOptionInfoService;
import cc.mrbird.febs.cos.service.ITicketRecordService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 投票管理 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/ticket-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketInfoController {

    private final ITicketInfoService ticketInfoService;

    private final ITicketOptionInfoService ticketOptionInfoService;

    private final ITicketRecordService ticketRecordService;

    private final IStaffInfoService staffInfoService;

    /**
     * 分页获取投票管理信息
     *
     * @param page       分页对象
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<TicketInfo> page, TicketInfo ticketInfo) {
        return R.ok(ticketInfoService.queryTicketPage(page, ticketInfo));
    }

    /**
     * 投票详情
     *
     * @param id 主键
     * @return 结果
     */
    @GetMapping("/queryDetail/{id}")
    public R queryDetail(@PathVariable("id") Integer id) {
        return R.ok(ticketInfoService.queryDetail(id));
    }

    /**
     * 查询投票管理信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(ticketInfoService.getById(id));
    }

    /**
     * 查询投票管理信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(ticketInfoService.list());
    }

    /**
     * 新增投票管理信息
     *
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public R save(TicketInfo ticketInfo) throws FebsException {
        ticketInfo.setCode("TC-" + System.currentTimeMillis());
        ticketInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        // 添加投票选项
        List<TicketOptionInfo> ticketOptionInfoList = JSONUtil.toList(ticketInfo.getOptionStr(), TicketOptionInfo.class);
        if (CollectionUtil.isEmpty(ticketOptionInfoList)) {
            throw new FebsException("投票选项不能为空");
        }
        ticketInfoService.save(ticketInfo);
        for (TicketOptionInfo ticketOptionInfo : ticketOptionInfoList) {
            ticketOptionInfo.setTicketId(ticketInfo.getId());
        }
        return R.ok(ticketOptionInfoService.saveBatch(ticketOptionInfoList));
    }

    /**
     * 校验用户是否已经投票
     *
     * @param ticketId 投票管理ID
     * @param userId   用户ID
     * @return 结构
     */
    @GetMapping("/checkUserTicket")
    public R checkUserTicket(Integer ticketId, Integer userId) {
        // 获取用户信息
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        if (staffInfo == null) {
            return R.error("用户信息不存在");
        }
        // 判断用户是否已经投票
        int count = ticketRecordService.count(Wrappers.<TicketRecord>lambdaQuery().eq(TicketRecord::getTicketId, ticketId).eq(TicketRecord::getUserId, staffInfo.getId()));
        return R.ok(count > 0);
    }

    /**
     * 投票
     *
     * @param ticketId 投票ID
     * @param userId   用户ID
     * @param optionId 选项ID
     * @return 结果
     */
    @GetMapping("/vote")
    public R vote(Integer ticketId, Integer userId, Integer optionId) {
        // 获取用户信息
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, userId));
        if (staffInfo == null) {
            return R.error("用户信息不存在");
        }
        // 添加投票
        TicketRecord ticketRecord = new TicketRecord();
        ticketRecord.setTicketId(ticketId);
        ticketRecord.setOptionId(optionId);
        ticketRecord.setUserId(staffInfo.getId());
        ticketRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(ticketRecordService.save(ticketRecord));
    }

    /**
     * 修改投票管理信息
     *
     * @param ticketInfo 投票管理信息
     * @return 结果
     */
    @PutMapping
    public R edit(TicketInfo ticketInfo) {
        return R.ok(ticketInfoService.updateById(ticketInfo));
    }

    /**
     * 删除投票管理信息
     *
     * @param ids ids
     * @return 投票管理信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(ticketInfoService.removeByIds(ids));
    }
}
