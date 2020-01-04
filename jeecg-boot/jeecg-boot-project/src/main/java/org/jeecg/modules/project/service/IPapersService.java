package org.jeecg.modules.project.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.project.entity.Papers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
public interface IPapersService extends IService<Papers> {
    /**
     * 上传
     *
     * @param fileList     文件数组
     * @param projectLocal 模块文件对应的路径  warehouseinsurance
     * @param parentId     父级Id    UUID
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    void uploadFile(MultipartFile[] fileList, String projectLocal, String parentId) throws Exception;


}
