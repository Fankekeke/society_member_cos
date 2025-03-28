package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.TicketRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 投票记录 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface TicketRecordMapper extends BaseMapper<TicketRecord> {

    /**
     * 分页获取投票记录信息
     *
     * @param page         分页对象
     * @param ticketRecord 投票记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryTicketPage(Page<TicketRecord> page, @Param("ticketRecord") TicketRecord ticketRecord);
}
