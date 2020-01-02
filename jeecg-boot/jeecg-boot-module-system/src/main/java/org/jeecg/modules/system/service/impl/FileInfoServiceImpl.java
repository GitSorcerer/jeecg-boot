package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.mapper.FileInfoMapper;
import org.jeecg.modules.system.model.FileInfoModel;
import org.jeecg.modules.system.service.IFileInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfoModel> implements IFileInfoService {

}
