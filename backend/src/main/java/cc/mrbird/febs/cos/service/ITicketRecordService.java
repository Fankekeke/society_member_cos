package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.TicketRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 投票记录 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ITicketRecordService extends IService<TicketRecord> {

    /**
     * 分页获取投票记录信息
     *
     * @param page         分页对象
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketRecord> page, TicketRecord ticketRecord);
}
