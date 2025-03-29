<template>
  <div>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <div style="background: #ECECEC; padding: 30px;" v-if="user.roleId == 74 || user.roleId == 76">
          <a-row :gutter="16">
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月任务量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.orderNumMonth }}
                    <span style="font-size: 20px;margin-top: 3px">件</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本月任务完成量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.orderAmountMonth }}
                    <span style="font-size: 20px;margin-top: 3px">件</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年任务量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.orderNumYear }}
                    <span style="font-size: 20px;margin-top: 3px">件</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="6">
              <a-card hoverable>
                <a-row>
                  <a-col :span="24" style="font-size: 13px;margin-bottom: 8px;font-family: SimHei">本年务完成量</a-col>
                  <a-col :span="4"><a-icon type="arrow-up" style="font-size: 30px;margin-top: 3px"/></a-col>
                  <a-col :span="18" style="font-size: 28px;font-weight: 500;font-family: SimHei">
                    {{ titleData.orderAmountYear }}
                    <span style="font-size: 20px;margin-top: 3px">件</span>
                  </a-col>
                </a-row>
              </a-card>
            </a-col>
          </a-row>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px" v-if="user.roleId == 74 || user.roleId == 76" :gutter="25">
      <a-col :span="12">
        <div hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="300" :options="chartOptions1" :series="series1"></apexchart>
        </div>
      </a-col>
      <a-col :span="12">
        <div style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart  v-if="!loading" type="line" height="300" :options="chartOptions" :series="series"></apexchart>
        </div>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px">
      <a-col :span="24">
        <a-card hoverable :loading="loading" :bordered="false" title="公告信息" style="margin-top: 15px">
          <div style="padding: 0 22px">
            <a-list item-layout="vertical" :pagination="pagination" :data-source="bulletinList">
              <a-list-item slot="renderItem" key="item.title" slot-scope="item, index">
                <template slot="actions">
              <span key="message">
                <a-icon type="message" style="margin-right: 8px" />
                {{ item.date }}
              </span>
                </template>
                <a-list-item-meta :description="item.content" style="font-size: 14px">
                  <a slot="title">{{ item.title }}</a>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </div>
        </a-card>
      </a-col>
    </a-row>
    <a-row style="margin-top: 15px" v-if="user.roleId == 74 || user.roleId == 76" :gutter="25">
      <a-col :span="12">
        <div style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart  v-if="!loading" type="line" height="300" :options="chartOptions11" :series="series11"></apexchart>
        </div>
      </a-col>
      <a-col :span="12">
        <div hoverable :bordered="false" style="width: 100%">
          <a-skeleton active v-if="loading" />
          <apexchart v-if="!loading" type="bar" height="300" :options="chartOptions10" :series="series10"></apexchart>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import {mapState} from 'vuex'
export default {
  name: 'Home',
  computed: {
    ...mapState({
      multipage: state => state.setting.multipage,
      user: state => state.account.user
    })
  },
  data () {
    return {
      pagination: {
        onChange: page => {
          console.log(page)
        },
        pageSize: 2
      },
      bulletinList: [],
      titleData: {
        orderNumMonth: 0,
        orderAmountMonth: 0,
        orderNumYear: 0,
        orderAmountYear: 0,
        monthPutNum: 0,
        monthPutPrice: 0,
        yearPutNum: 0,
        yearPutPrice: 0
      },
      loading: false,
      series11: [{
        name: '订单量',
        data: []
      }],
      chartOptions11: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天内费用单统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series10: [{
        name: '费用',
        data: []
      }],
      chartOptions10: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天费用金额统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 元'
            }
          }
        }
      },
      series: [{
        name: '单量',
        data: []
      }],
      chartOptions: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天内任务研究完成统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series1: [{
        name: '单量',
        data: []
      }],
      chartOptions1: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天研究任务统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 单'
            }
          }
        }
      },
      series2: [{
        name: '单量',
        data: []
      }],
      chartOptions2: {
        chart: {
          type: 'line',
          height: 300
        },
        xaxis: {
          categories: []
        },
        stroke: {
          curve: 'stepline'
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: '近十天内入库统计',
          align: 'left'
        },
        markers: {
          hover: {
            sizeOffset: 4
          }
        }
      },
      series3: [{
        name: '单量',
        data: []
      }],
      chartOptions3: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '近十天内入库单量统计',
          align: 'left'
        },
        plotOptions: {
          bar: {
            horizontal: false,
            columnWidth: '55%'
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          show: true,
          width: 2,
          colors: ['transparent']
        },
        xaxis: {
          categories: []
        },
        yaxis: {
          title: {
            text: ''
          }
        },
        fill: {
          opacity: 1
        },
        tooltip: {
          y: {
            formatter: function (val) {
              return val + ' 单'
            }
          }
        }
      }
    }
  },
  mounted () {
    this.loading = true
    this.selectHomeData()
    setTimeout(() => {
      this.loading = false
    }, 200)
  },
  methods: {
    selectHomeData () {
      this.$get('/cos/agent-info/homeData', {enterpriseId: this.user.roleId == 76 ? this.user.userId : null}).then((r) => {
        // let titleData = { outNum: r.data.outNum, putNum: r.data.putNum, orderPrice: r.data.orderPrice, registerNum: r.data.registerNum }
        // this.$emit('setTitle', titleData)
        this.titleData.orderNumMonth = r.data.orderNumMonth
        this.titleData.orderAmountMonth = r.data.orderAmountMonth
        this.titleData.orderNumYear = r.data.orderNumYear
        this.titleData.orderAmountYear = r.data.orderAmountYear
        //
        // this.titleData.monthPutNum = r.data.monthPutNum
        // this.titleData.monthPutPrice = r.data.monthPutPrice
        // this.titleData.yearPutNum = r.data.yearPutNum
        // this.titleData.yearPutPrice = r.data.yearPutPrice
        this.bulletinList = r.data.bulletin
        let values = []
        // if (r.data.orderNumWithinDays !== null && r.data.orderNumWithinDays.length !== 0) {
        //   if (this.chartOptions1.xaxis.categories.length === 0) {
        //     this.chartOptions1.xaxis.categories = r.data.orderNumWithinDays.map(obj => { return obj.days })
        //   }
        //   let itemData = { name: '出库统计', data: r.data.orderNumWithinDays.map(obj => { return obj.count }) }
        //   values.push(itemData)
        //   this.series1 = values
        // }
        this.series1[0].data = r.data.orderNumDays.map(obj => { return obj.count })
        this.chartOptions1.xaxis.categories = r.data.orderNumDays.map(obj => { return obj.days })

        this.series[0].data = r.data.orderAmountDays.map(obj => { return obj.count })
        this.chartOptions.xaxis.categories = r.data.orderAmountDays.map(obj => { return obj.days })


        this.series10[0].data = r.data.payAmountDays.map(obj => { return obj.count })
        this.chartOptions10.xaxis.categories = r.data.payAmountDays.map(obj => { return obj.days })

        this.series11[0].data = r.data.payNumDays.map(obj => { return obj.count })
        this.chartOptions11.xaxis.categories = r.data.payNumDays.map(obj => { return obj.days })

        // if (r.data.putNumWithinDays !== null && r.data.putNumWithinDays.length !== 0) {
        //   if (this.chartOptions2.xaxis.categories.length === 0) {
        //     this.chartOptions2.xaxis.categories = r.data.putNumWithinDays.map(obj => { return obj.days })
        //   }
        //   let itemData = { name: '出库统计', data: r.data.putNumWithinDays.map(obj => { return obj.count }) }
        //   values.push(itemData)
        //   this.series2 = values
        // }
        // this.series3[0].data = r.data.putPriceWithinDays.map(obj => { return obj.price })
        // this.chartOptions3.xaxis.categories = r.data.putPriceWithinDays.map(obj => { return obj.days })
      })
    }
  }
}
</script>

<style scoped>

</style>
