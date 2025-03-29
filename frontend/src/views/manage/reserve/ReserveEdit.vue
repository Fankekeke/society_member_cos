<template>
  <a-modal v-model="show" title="预约审核" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="pass" type="danger" :loading="loading" @click="handleSubmit(2)">
        驳回
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit(1)">
        通过
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="reserveInfo !== null"></div>
    <br/>
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20" style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">备注</span></a-col>
        <a-col :span="24">
          <a-form-item label='' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content'
            ]"/>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'reserveEdit',
  props: {
    reserveEditVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.reserveEditVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      rowId: null,
      reserveInfo: null,
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      venue: null,
      resume: null,
      reserve: null,
      expert: null
    }
  },
  methods: {
    queryReserveDetail (id) {
      this.$get(`/cos/exercise-audit-info/queryReserveDetail/${id}`).then((r) => {
        this.venue = r.data.venue
        this.resume = r.data.resume
        this.reserve = r.data.reserve
        this.expert = r.data.expert
      })
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    setFormValues ({...reserve}) {
      this.rowId = reserve.id
      this.reserveInfo = reserve
      let fields = ['title', 'content', 'publisher', 'rackUp']
      let obj = {}
      Object.keys(reserve).forEach((key) => {
        if (key === 'images') {
          this.fileList = []
          this.imagesInit(reserve['images'])
        }
        if (key === 'rackUp') {
          reserve[key] = reserve[key].toString()
        }
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          obj[key] = reserve[key]
        }
      })
      this.form.setFieldsValue(obj)
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit (status) {
      this.form.validateFields((err, values) => {
        values.id = this.rowId
        values.status = status
        if (!err) {
          this.loading = true
          this.$post('/cos/exercise-audit-info/audit', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
