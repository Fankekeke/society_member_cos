<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="会员姓名"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.teacherName"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="支付状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.status">
                  <a-select-option value="0">未支付</a-select-option>
                  <a-select-option value="1">已支付</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
<!--        <a-button type="primary" ghost @click="add">添加会员缴费</a-button>-->
<!--        <a-button @click="batchDelete">删除</a-button>-->
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="contentShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.auditTitle }}
              </template>
              {{ record.auditTitle.slice(0, 20) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="file-search" @click="memberViewOpen(record)" title="详 情" style="margin-left: 15px"></a-icon>
          <a-icon v-if="record.status == 0" type="alipay" @click="orderPay(record)" title="支 付" style="margin-left: 15px"></a-icon>
        </template>
      </a-table>
    </div>
    <member-view
      @close="handlememberViewClose"
      :memberShow="memberView.visiable"
      :memberData="memberView.data">
    </member-view>
    <classes-add
      v-if="classesAdd.visiable"
      @close="handleclassesAddClose"
      @success="handleclassesAddSuccess"
      :classesAddVisiable="classesAdd.visiable">
    </classes-add>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import memberView from './FinanceView.vue'
import classesAdd from './FinanceAdd.vue'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'member',
  components: {memberView, RangeDate, classesAdd},
  data () {
    return {
      advanced: false,
      memberAdd: {
        visiable: false
      },
      classesAdd: {
        visiable: false
      },
      memberEdit: {
        visiable: false
      },
      memberView: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '用户名称',
        ellipsis: true,
        dataIndex: 'staffName'
      }, {
        title: '会员头像',
        dataIndex: 'staffImages',
        customRender: (text, record, index) => {
          if (!record.staffImages) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.staffImages.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.staffImages.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '所属小组',
        ellipsis: true,
        dataIndex: 'deptName'
      }, {
        title: '职位',
        ellipsis: true,
        dataIndex: 'positionName'
      }, {
        title: '缴费详情',
        dataIndex: 'auditTitle',
        ellipsis: true
      }, {
        title: '支付状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag color="#f50">未支付</a-tag>
            case '1':
              return <a-tag color="#108ee9">已支付</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '费用金额',
        dataIndex: 'totalPrice',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '创建时间',
        dataIndex: 'createDate',
        ellipsis: true,
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    orderPay (record) {
      let data = { outTradeNo: record.code, subject: `${record.createDate}缴费信息`, totalAmount: record.totalPrice, body: '' }
      this.$post('/cos/pay/alipay', data).then((r) => {
        // console.log(r.data.msg)
        // 添加之前先删除一下，如果单页面，页面不刷新，添加进去的内容会一直保留在页面中，二次调用form表单会出错
        const divForm = document.getElementsByTagName('div')
        if (divForm.length) {
          document.body.removeChild(divForm[0])
        }
        const div = document.createElement('div')
        div.innerHTML = r.data.msg // data就是接口返回的form 表单字符串
        // console.log(div.innerHTML)
        document.body.appendChild(div)
        document.forms[0].setAttribute('target', '_self') // 新开窗口跳转
        document.forms[0].submit()
      })
    },
    add () {
      this.classesAdd.visiable = true
    },
    handleclassesAddClose () {
      this.classesAdd.visiable = false
    },
    handleclassesAddSuccess () {
      this.classesAdd.visiable = false
      this.$message.success('新增财务成功')
      this.search()
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    handlememberAddClose () {
      this.memberAdd.visiable = false
    },
    handlememberAddSuccess () {
      this.memberAdd.visiable = false
      this.$message.success('新增财务成功')
      this.search()
    },
    edit (record) {
      this.$refs.memberEdit.setFormValues(record)
      this.memberEdit.visiable = true
    },
    memberViewOpen (row) {
      this.memberView.data = row
      this.memberView.visiable = true
    },
    handlememberViewClose () {
      this.memberView.visiable = false
    },
    handlememberEditClose () {
      this.memberEdit.visiable = false
    },
    handlememberEditSuccess () {
      this.memberEdit.visiable = false
      this.$message.success('修改财务成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/finance-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.status === undefined) {
        delete params.status
      }
      params.staffId = this.currentUser.userId
      this.$get('/cos/finance-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
