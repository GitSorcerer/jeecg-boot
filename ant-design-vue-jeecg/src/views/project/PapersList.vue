<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="文件对象的Id">
              <a-input placeholder="请输入文件对象的Id" v-model="queryParam.objId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="文件对象的类型">
              <a-input placeholder="请输入文件对象的类型" v-model="queryParam.objType"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="文件名称">
                <a-input placeholder="请输入文件名称" v-model="queryParam.filename"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="文件类型(01 图片 02 PDF)">
                <a-input placeholder="请输入文件类型(01 图片 02 PDF)" v-model="queryParam.filetype"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="文件大小">
                <a-input placeholder="请输入文件大小" v-model="queryParam.filesize"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('文件')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :scroll="{ x: 1900 }"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <papers-modal ref="modalForm" @ok="modalFormOk"></papers-modal>
  </a-card>
</template>

<script>
  import PapersModal from './modules/PapersModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: 'PapersList',
    mixins: [JeecgListMixin],
    components: {
      PapersModal
    },
    data() {
      return {
        description: '文件管理页面',
        // 表头
        columns: [
          {
            title: '序号',
            dataIndex: '',
            key: 'rowIndex',
            width: 60, fixed: 'left',
            align: 'center',
            customRender: function(t, r, index) {
              return parseInt(index) + 1
            }
          },
          {
            title: '对象的Id',
            align: 'center',
            dataIndex: 'objId',
            width: 200
          },
          {
            title: '类型',
            align: 'center',
            dataIndex: 'objType',
            width: 80
          },
          {
            title: '文件名称',
            align: 'center',
            dataIndex: 'filename',
            width:120
          },
          {
            title: '文件类型',
            align: 'center',
            dataIndex: 'filetype',
            width:120
          },
          {
            title: '文件大小',
            align: 'center',
            dataIndex: 'filesize',
            width:120
          },
          {
            title: '文件路径',
            align: 'center',
            dataIndex: 'fileurl',
            width:120
          },
          {
            title: '所在文件夹',
            align: 'center',
            dataIndex: 'filepath',
            width:180
          },
          {
            title: '后缀名',
            align: 'center',
            dataIndex: 'suffixname',
            width:100
          },
          {
            title: '创建日期',
            align: 'center',
            dataIndex: 'createDate',
            width:300
          },
          {
            title: '扩展字段',
            align: 'center',
            dataIndex: 'reserve1',
            width:120
          },
          {
            title: '扩展字段',
            align: 'center',
            dataIndex: 'reserve2',
            width:80
          },
          {
            title: '扩展字段',
            align: 'center',
            dataIndex: 'reserve3',
            width:80
          },
          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
            fixed: 'right',
            width: 200
          }
        ],
        url: {
          list: '/papers/list',
          delete: '/papers/delete',
          deleteBatch: '/papers/deleteBatch',
          exportXlsUrl: '/papers/exportXls',
          importExcelUrl: '/papers/importExcel'
        }
      }
    },
    computed: {
      importExcelUrl: function() {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    },
    methods: {}
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>