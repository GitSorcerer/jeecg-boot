package  org.jeecg.modules.project.service.impl;

import org.jeecg.modules.project.entity.Papers;
import org.jeecg.modules.project.mapper.PapersMapper;
import org.jeecg.modules.project.service.IPapersService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description: 文件
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
@Service
public class PapersServiceImpl extends ServiceImpl<PapersMapper, Papers> implements IPapersService {

    @Override
    public void uploadFile(MultipartFile[] fileList, String projectLocal, String parentId) throws Exception {

    }
}
