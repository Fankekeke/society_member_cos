package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.TicketRecord;
import cc.mrbird.febs.cos.dao.TicketRecordMapper;
import cc.mrbird.febs.cos.service.ITicketRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 投票记录 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
public class TicketRecordServiceImpl extends ServiceImpl<TicketRecordMapper, TicketRecord> implements ITicketRecordService {

    /**
     * 分页获取投票记录信息
     *
     * @param page         分页对象
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketRecord> page, TicketRecord ticketRecord) {
        return baseMapper.queryTicketPage(page, ticketRecord);
    }
}
