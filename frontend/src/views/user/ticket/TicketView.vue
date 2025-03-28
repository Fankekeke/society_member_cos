<template>
  <a-modal v-model="show" title="投票详情" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="venueData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">投票信息</span></a-col>
        <a-col :span="8"><b>投票编号：</b>
          {{ venueData.code }}
        </a-col>
        <a-col :span="8"><b>投票标题：</b>
          {{ venueData.title ? venueData.title : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>结束时间：</b>
          {{ venueData.endDate }}
        </a-col>
        <a-col :span="8"><b>开始时间：</b>
          {{ venueData.startDate ? venueData.startDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ venueData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">投票内容</span></a-col>
        <a-col :span="24">
          {{ venueData.content == null ? '- -' : venueData.content }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="margin-top: 15px" :gutter="25">
        <a-col :span="24">
          <div hoverable :bordered="false" style="width: 100%">
            <a-skeleton active v-if="chartLoading" />
            <apexchart v-if="!chartLoading" type="bar" height="300" :options="chartOptions1" :series="series1"></apexchart>
          </div>
        </a-col>
      </a-row>
    </div>
  </a-modal>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
import moment from 'moment'
moment.locale('zh-cn')
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
export default {
  name: 'venueView',
  props: {
    venueShow: {
      type: Boolean,
      default: false
    },
    venueData: {
      type: Object
    }
  },
  computed: {
    show: {
      get: function () {
        return this.venueShow
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      series1: [{
        name: '票量',
        data: []
      }],
      chartOptions1: {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: '投票统计',
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
              return val + ' 票'
            }
          }
        }
      },
      chartLoading: false,
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      userInfo: null
    }
  },
  watch: {
    venueShow: function (value) {
      if (value) {
        this.queryDetail(this.venueData.id)
      }
    }
  },
  methods: {
    queryDetail (id) {
      this.chartLoading = true
      this.$get(`/cos/ticket-info/queryDetail/${id}`).then((r) => {
        console.log(r.data.ticketOptionInfo)
        this.series1[0].data = r.data.ticketOptionInfo.map(obj => { return obj.count })
        this.chartOptions1.xaxis.categories = r.data.ticketOptionInfo.map(obj => { return obj.name })
        setTimeout(() => {
          this.chartLoading = false
        }, 500)
      })
    },
    local (venueData) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let point = new BMap.Point(venueData.longitude, venueData.latitude)
      baiduMap.pointAdd(point)
      baiduMap.findPoint(point, 16)
      // let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions:{map: baiduMap.rMap(), autoViewport: true}});
      // driving.search(new BMap.Point(this.nowPoint.lng,this.nowPoint.lat), new BMap.Point(scenic.point.split(",")[0],scenic.point.split(",")[1]));
    },
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    onClose () {
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
