package org.jeecg.modules.project.entity;

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
 * @Description: 文件
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
@Data
@TableName("papers")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="papers对象", description="文件")
public class Papers {
    
	/**自增id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "自增id")
	private java.lang.Integer id;
	/**文件对象的Id 合同对象的Id或者UUid*/
	@Excel(name = "文件对象的Id 合同对象的Id或者UUid", width = 15)
    @ApiModelProperty(value = "文件对象的Id 合同对象的Id或者UUid")
	private java.lang.String objId;
	/**文件对象的类型  经营  外包*/
	@Excel(name = "文件对象的类型  经营  外包", width = 15)
    @ApiModelProperty(value = "文件对象的类型  经营  外包")
	private java.lang.String objType;
	/**文件名称*/
	@Excel(name = "文件名称", width = 15)
    @ApiModelProperty(value = "文件名称")
	private java.lang.String filename;
	/**文件类型(01 图片 02 PDF)*/
	@Excel(name = "文件类型(01 图片 02 PDF)", width = 15)
    @ApiModelProperty(value = "文件类型(01 图片 02 PDF)")
	private java.lang.String filetype;
	/**文件大小  KB*/
	@Excel(name = "文件大小  KB", width = 15)
    @ApiModelProperty(value = "文件大小  KB")
	private java.lang.Integer filesize;
	/**文件路径  相对路径*/
	@Excel(name = "文件路径  相对路径", width = 15)
    @ApiModelProperty(value = "文件路径  相对路径")
	private java.lang.String fileurl;
	/**所在文件夹*/
	@Excel(name = "所在文件夹", width = 15)
    @ApiModelProperty(value = "所在文件夹")
	private java.lang.String filepath;
	/**后缀名 pdf jpg*/
	@Excel(name = "后缀名 pdf jpg", width = 15)
    @ApiModelProperty(value = "后缀名 pdf jpg")
	private java.lang.String suffixname;
	/**创建日期*/
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
	private java.util.Date createDate;
	/**扩展字段1*/
	@Excel(name = "扩展字段1", width = 15)
    @ApiModelProperty(value = "扩展字段1")
	private java.lang.String reserve1;
	/**扩展字段2*/
	@Excel(name = "扩展字段2", width = 15)
    @ApiModelProperty(value = "扩展字段2")
	private java.lang.String reserve2;
	/**扩展字段3*/
	@Excel(name = "扩展字段3", width = 15)
    @ApiModelProperty(value = "扩展字段3")
	private java.lang.String reserve3;
}
