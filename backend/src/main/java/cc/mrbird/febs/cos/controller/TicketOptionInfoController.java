package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.TicketOptionInfo;
import cc.mrbird.febs.cos.service.ITicketOptionInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 投票选项 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/ticket-option-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketOptionInfoController {

    private final ITicketOptionInfoService ticketOptionInfoService;

    /**
     * 分页获取投票选项信息
     *
     * @param page             分页对象
     * @param ticketOptionInfo 投票选项信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<TicketOptionInfo> page, TicketOptionInfo ticketOptionInfo) {
        return R.ok();
    }

    /**
     * 查询投票选项信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(ticketOptionInfoService.getById(id));
    }

    /**
     * 查询投票选项信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(ticketOptionInfoService.list());
    }

    /**
     * 新增投票选项信息
     *
     * @param ticketOptionInfo 投票选项信息
     * @return 结果
     */
    @PostMapping
    public R save(TicketOptionInfo ticketOptionInfo) {
        return R.ok(ticketOptionInfoService.save(ticketOptionInfo));
    }

    /**
     * 修改投票选项信息
     *
     * @param ticketOptionInfo 投票选项信息
     * @return 结果
     */
    @PutMapping
    public R edit(TicketOptionInfo ticketOptionInfo) {
        return R.ok(ticketOptionInfoService.updateById(ticketOptionInfo));
    }

    /**
     * 删除投票选项信息
     *
     * @param ids ids
     * @return 投票选项信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(ticketOptionInfoService.removeByIds(ids));
    }
}
