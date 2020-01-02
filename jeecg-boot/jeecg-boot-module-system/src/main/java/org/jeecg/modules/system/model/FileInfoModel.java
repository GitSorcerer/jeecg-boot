package org.jeecg.modules.system.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 文件表
 * @Author: jeecg-boot
 * @Date:   2020-01-02
 * @Version: V1.0
 */
@Data
@TableName("file_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="file_info对象", description="文件表")
public class FileInfoModel {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**创建人*/
	@Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
	private String createBy;
	/**对象Id*/
	@Excel(name = "对象Id", width = 15)
    @ApiModelProperty(value = "对象Id")
	private String objId;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private Date createTime;
	/**文件名*/
	@Excel(name = "文件名", width = 15)
    @ApiModelProperty(value = "文件名")
	private String fileName;
	/**文件类型*/
	@Excel(name = "文件类型", width = 15)
    @ApiModelProperty(value = "文件类型")
	private String fileType;
	/**文件大小*/
	@Excel(name = "文件大小", width = 15)
    @ApiModelProperty(value = "文件大小")
	private java.math.BigDecimal fileSize;
	/**文件后缀名*/
	@Excel(name = "文件后缀名", width = 15)
    @ApiModelProperty(value = "文件后缀名")
	private String fileSuffix;
	/**原文件名*/
	@Excel(name = "原文件名", width = 15)
    @ApiModelProperty(value = "原文件名")
	private String fileOriginalName;
	/**文件路劲*/
	@Excel(name = "文件路劲", width = 15)
    @ApiModelProperty(value = "文件路劲")
	private String filePath;
	/**相对路径*/
	@Excel(name = "相对路径", width = 15)
    @ApiModelProperty(value = "相对路径")
	private String fileRelativePath;
	/**备用字段1*/
	@Excel(name = "备用字段1", width = 15)
    @ApiModelProperty(value = "备用字段1")
	private String reserve1;
	/**备用字段1*/
	@Excel(name = "备用字段1", width = 15)
    @ApiModelProperty(value = "备用字段1")
	private String reserve2;
	/**备用字段1*/
	@Excel(name = "备用字段1", width = 15)
    @ApiModelProperty(value = "备用字段1")
	private String reserve3;
}
