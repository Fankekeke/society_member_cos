package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.EnterpriseInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 会员信息管理 service层
 *
 * @author FanK
 */
public interface IEnterpriseInfoService extends IService<EnterpriseInfo> {

    /**
     * 分页获取会员信息信息
     *
     * @param page           分页对象
     * @param enterpriseInfo 会员信息信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectEnterprisePage(Page<EnterpriseInfo> page, EnterpriseInfo enterpriseInfo);


    /**
     * 导入会员信息列表
     *
     * @param file 文件
     * @return 结果
     */
    String importExcel(MultipartFile file) throws Exception;
}
