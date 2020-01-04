package org.jeecg.modules.base.service.impl;

import org.jeecg.modules.base.entity.FileInfo;
import org.jeecg.modules.base.mapper.FileInfoMapper;
import org.jeecg.modules.base.service.IFileInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 文件信息表
 * @Author: jeecg-boot
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

}
