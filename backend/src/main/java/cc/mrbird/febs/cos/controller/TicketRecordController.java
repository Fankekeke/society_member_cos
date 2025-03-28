package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.TicketRecord;
import cc.mrbird.febs.cos.service.ITicketRecordService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投票记录 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/ticket-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketRecordController {
    private final ITicketRecordService ticketRecordService;

    /**
     * 分页获取投票记录信息
     *
     * @param page         分页对象
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<TicketRecord> page, TicketRecord ticketRecord) {
        return R.ok(ticketRecordService.queryTicketPage(page, ticketRecord));
    }

    /**
     * 查询投票记录信息详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(ticketRecordService.getById(id));
    }

    /**
     * 查询投票记录信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(ticketRecordService.list());
    }

    /**
     * 新增投票记录信息
     *
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    @PostMapping
    public R save(TicketRecord ticketRecord) {
        return R.ok(ticketRecordService.save(ticketRecord));
    }

    /**
     * 修改投票记录信息
     *
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(TicketRecord ticketRecord) {
        return R.ok(ticketRecordService.updateById(ticketRecord));
    }

    /**
     * 删除投票记录信息
     *
     * @param ids ids
     * @return 投票记录信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(ticketRecordService.removeByIds(ids));
    }
}
