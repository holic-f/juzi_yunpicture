<template>
  <div id="pictureSearchForm">
    <div class="picture-search-form">
      <!-- 搜索表单 -->
      <a-form layout="inline" :model="searchParams" @finish="doSearch">
        <a-form-item label="关键词" name="searchText">
          <a-input
            v-model:value="searchParams.searchText"
            placeholder="从名称和简介搜索"
            allow-clear
          />
        </a-form-item>
        <a-form-item label="分类" name="category">
          <a-auto-complete
            v-model:value="searchParams.category"
            style="min-width: 180px"
            :options="categoryOptions"
            placeholder="请输入分类"
            allowClear
          />
        </a-form-item>
        <a-form-item label="标签" name="tags">
          <a-select
            v-model:value="searchParams.tags"
            style="min-width: 180px"
            :options="tagOptions"
            mode="tags"
            placeholder="请输入标签"
            allowClear
          />
        </a-form-item>
        <a-form-item label="日期" name="">
          <a-range-picker
            style="width: 400px"
            show-time
            v-model:value="dateRange"
            :placeholder="['编辑开始日期', '编辑结束时间']"
            format="YYYY/MM/DD HH:mm:ss"
            :presets="rangePresets"
            @change="onRangeChange"
          />
        </a-form-item>
        <a-form-item label="名称" name="name">
          <a-input v-model:value="searchParams.name" placeholder="请输入名称" allow-clear />
        </a-form-item>
        <a-form-item label="简介" name="introduction">
          <a-input v-model:value="searchParams.introduction" placeholder="请输入简介" allow-clear />
        </a-form-item>
        <a-form-item label="宽度" name="picWidth">
          <a-input-number v-model:value="searchParams.picWidth" />
        </a-form-item>
        <a-form-item label="高度" name="picHeight">
          <a-input-number v-model:value="searchParams.picHeight" />
        </a-form-item>
        <a-form-item label="格式" name="picFormat">
          <a-input v-model:value="searchParams.picFormat" placeholder="请输入格式" allow-clear />
        </a-form-item>
        <a-form-item>
          <a-space>
            <a-button type="primary" html-type="submit" style="width: 96px">搜索</a-button>
            <a-button html-type="reset" @click="doClear">重置</a-button>
          </a-space>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import dayjs, { type Dayjs } from 'dayjs'
import { message } from 'ant-design-vue'
import { listPictureTagCategoryUsingGet } from '@/api/pictureController.ts'

interface Props {
  onSearch?: (searchParams: API.PictureQueryRequest) => void
}

const props = defineProps<Props>()

const emptySearchParams: API.PictureQueryRequest = {
  searchText: undefined,
  category: undefined,
  tags: undefined,
  startEditTime: undefined,
  endEditTime: undefined,
  name: undefined,
  introduction: undefined,
  picWidth: undefined,
  picHeight: undefined,
  picFormat: undefined,
}

//搜索条件
const searchParams = ref<API.PictureQueryRequest>({ ...emptySearchParams })

//搜索数据
const doSearch = () => {
  props.onSearch?.({ ...searchParams.value })
}

const dateRange = ref<[Dayjs | null, Dayjs | null] | null>(null)

/**
 * 日期范围更改时触发
 * @param dates
 * @param dateStrings
 */
const onRangeChange = (
  dates: [Dayjs | null, Dayjs | null] | null,
  dateStrings: [string, string],
) => {
  if (dates?.[0] && dates?.[1]) {
    searchParams.value.startEditTime = dateStrings[0]
    searchParams.value.endEditTime = dateStrings[1]
  } else {
    searchParams.value.startEditTime = undefined
    searchParams.value.endEditTime = undefined
  }
}

const rangePresets = ref([
  { label: '过去 7 天', value: [dayjs().add(-7, 'd'), dayjs()] },
  { label: '过去 14 天', value: [dayjs().add(-14, 'd'), dayjs()] },
  { label: '过去 30 天', value: [dayjs().add(-30, 'd'), dayjs()] },
  { label: '过去 90 天', value: [dayjs().add(-90, 'd'), dayjs()] },
])

const categoryOptions = ref<{ value: string; label: string }[]>([])
const tagOptions = ref<{ value: string; label: string }[]>([])

// 获取标签和分类选项
const getTagCategoryOptions = async () => {
  const res = await listPictureTagCategoryUsingGet()
  if (res.data.code === 0 && res.data.data) {
    // 转换成下拉选项组件接受的格式
    tagOptions.value = (res.data.data.tagList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
    categoryOptions.value = (res.data.data.categoryList ?? []).map((data: string) => {
      return {
        value: data,
        label: data,
      }
    })
  } else {
    message.error('加载选项失败，' + res.data.message)
  }
}

onMounted(() => {
  getTagCategoryOptions()
})

// 清理
const doClear = () => {
  searchParams.value = { ...emptySearchParams }
  dateRange.value = null
  props.onSearch?.({ ...searchParams.value })
}
</script>

<style scoped>
.picture-search-form .ant-form-item {
  margin-top: 16px;
}
</style>
