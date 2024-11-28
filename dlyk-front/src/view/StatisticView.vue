<template>
    <!-- 概览统计 -->
    <el-row>
        <el-col :span="6">
            <el-statistic :value="summaryData.effectiveActivityCount">
                <template #title>
                    <div style="display: inline-flex; align-items: center">市场活动</div>
                </template>
                <template #suffix>/{{ summaryData.totalActivityCount }}</template>
            </el-statistic>
        </el-col>
        <el-col :span="6">
            <el-statistic title="线索总数" :value="summaryData.totalClueCount" />
        </el-col>

        <el-col :span="6">
            <el-statistic title="客户总数" :value="summaryData.totalCustomerCount" />
        </el-col>
        <el-col :span="6">
            <el-statistic :value="summaryData.successTranAmount">
                <template #title>
                    <div style="display: inline-flex; align-items: center">交易总额</div>
                </template>
                <template #suffix>/{{ summaryData.totalTranAmount }}</template>
            </el-statistic>
        </el-col>
    </el-row>

    <!--  销售漏斗图 为 ECharts 准备一个定义了宽高的 DOM -->
    <div
        id="saleFunnelChart"
        style="width: 48%; height: 350px; margin: 10px; float: left"
    >
        图渲染在此处
    </div>

    <!-- 线索来源饼图，为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="sourcePieChart" style="width: 48%; height: 350px; margin: 10px; float: left">
        图渲染在此处
    </div>

    <!--为ECharts定义一个div，指定好宽度和高度，用来显示市场活动统计柱状图-->
    <div
        id="activityBarChart"
        style="clear: both; width: 95%; height: 300px; margin: 15px; text-align: center"
    ></div>

    <!--为ECharts定义一个div，指定好宽度和高度，用来显示线索统计柱状图-->
    <div
        id="clueBarChart"
        style="clear: both; width: 95%; height: 300px; margin: 15px; text-align: center"
    ></div>
</template>
<script>
import { defineComponent } from 'vue';
import { doGet } from '../http/httpRequest.js';
import * as echarts from 'echarts';

export default defineComponent({
    data() {
        return {
            //概览统计数据对象，初始值是空
            summaryData: {}
        };
    },
    mounted() {
        this.loadSummary();
        //加载销售漏斗图
        this.loadSaleFunnelChart();
        //加载线索来源饼图
        this.loadSourcePieChart();
        //加载市场活动统计柱状图
        this.loadActivityBarChart();
        //加载线索柱状图
        this.loadClueBarChart();
    },
    methods: {
        //加载概览统计数据
        loadSummary() {
            doGet('/api/summary/data', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.summaryData = resp.data.data;
                }
            });
        },
        //加载销售漏斗图
        loadSaleFunnelChart() {
            //先查询出来数据，然后再渲染图，不能先渲染图（没有数据，渲染不出来的），然后再查数据
            doGet('/api/saleFunnel/data', {}).then((resp) => {
                if (resp.data.code === 200) {
                    let saleFunelData = resp.data.data;
                    console.log(saleFunelData);

                    //拿到页面上渲染图标的dom元素
                    var chartDom = document.getElementById('saleFunnelChart');
                    //使用echarts组件对dom进行初始化,得到一个空白的图标对象
                    var myChart = echarts.init(chartDom);

                    //3、配置可选项（查看文档-->配置项手册）
                    var option = {
                        // 标题组件，包含主标题和副标题
                        title: {
                            // 主标题文本，支持使用 \n 换行。
                            text: '销售漏斗图',
                            //title 组件离容器左侧的距离。
                            left: 'center',
                            //title 组件离容器上侧的距离。
                            top: 'bottom'
                        },
                        // 提示框组件
                        tooltip: {
                            // 触发类型 item 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用
                            trigger: 'item',
                            // 提示框浮层内容格式器，支持字符串模板和回调函数两种形式。
                            // 漏斗图: {a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
                            formatter: '{a} <br/>{b} : {c}%'
                        },
                        //工具栏
                        toolbox: {
                            //各工具配置项
                            feature: {
                                //数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新
                                dataView: {
                                    //是否不可编辑（只读）。
                                    readOnly: false
                                },
                                //配置项还原
                                restore: {},
                                //保存为图片
                                saveAsImage: {}
                            }
                        },
                        //图例组件
                        legend: {
                            data: ['线索', '客户', '交易', '成交']
                        },
                        //系列
                        series: [
                            {
                                // 系列名称
                                name: '销售漏斗数据统计',
                                //图表的类型，funnel代表漏斗图
                                type: 'funnel',
                                //漏斗图组件离容器左侧的距离。
                                left: '10%',
                                //漏斗图组件离容器上侧的距离。
                                top: 60,
                                //漏斗图组件离容器下侧的距离。
                                bottom: 60,
                                width: '80%',
                                min: 0,
                                max: 100,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                // 数据项（系列中的数据内容数组， 数组项可以为单个数值，也可以是对象值）
                                data: saleFunelData
                                // [
                                //     { value: 20, name: '成交' },
                                //     { value: 60, name: '交易' },
                                //     { value: 80, name: '客户' },
                                //     { value: 100, name: '线索' }
                                // ]
                            }
                        ]
                    };

                    //如果配置了可选项,就把可选项设置到空白的图标对象中
                    option && myChart.setOption(option);
                }
            });
        },
        //加载线索来源饼图
        loadSourcePieChart() {
            doGet('/api/sourcePie/data', {}).then((resp) => {
                if (resp.data.code === 200) {
                    let sourcePieData = resp.data.data;

                    //拿到页面上渲染图标的dom元素
                    var chartDom = document.getElementById('sourcePieChart');
                    //使用echarts组件对dom进行初始化,得到一个空白的图标对象
                    var myChart = echarts.init(chartDom);
                    //3、配置可选项（查看文档-->配置项手册）
                    var option;

                    option = {
                        // 标题组件，包含主标题和副标题
                        title: {
                            // 主标题文本，支持使用 \n 换行。
                            text: '线索来源统计',
                            //title 组件离容器左侧的距离。
                            left: 'center',
                            //title 组件离容器上侧的距离。
                            top: 'bottom'
                        },
                        // 提示框组件
                        tooltip: {
                            // 触发类型 item 数据项图形触发，主要在散点图，饼图等无类目轴的图表中使用
                            trigger: 'item'
                        },
                        //图例组件
                        legend: {
                            orient: 'horizontal',
                            left: 'center'
                        },
                        //系列
                        series: [
                            {
                                name: '线索来源统计',
                                type: 'pie',
                                //饼图的半径
                                radius: '60%',
                                // 数据项（系列中的数据内容数组， 数组项可以为单个数值，也可以是对象值）
                                data: sourcePieData,
                                // [
                                //     { value: 1048, name: 'Search Engine' },
                                //     { value: 735, name: 'Direct' },
                                //     { value: 580, name: 'Email' },
                                //     { value: 484, name: 'Union Ads' },
                                //     { value: 300, name: 'Video Ads' }
                                // ],
                                //高亮状态的扇区和标签样式。
                                emphasis: {
                                    itemStyle: {
                                        //图形阴影的模糊大小
                                        shadowBlur: 10,
                                        //阴影水平方向上的偏移距离。
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    //4、如果配置了可选项，就把可选项设置到空白的图表对象中
                    option && myChart.setOption(option);
                }
            });
        },
        //加载市场活动柱状图
        loadActivityBarChart() {
            //先查询出来数据，然后再渲染图（不能颠倒，如果你先显示图表，但是没有数据，图表显示不出来）
            doGet('/api/statistic/activityBarChart', {}).then((resp) => {
                if (resp.data.code === 200) {
                    let activityBarDataArray = resp.data.data;

                    //1、根据id获取页面dom元素对象，echarts图表到时候就显示在这个dom元素里面 <div> echarts就显示在这里 </div>
                    var chartDom = document.getElementById('activityBarChart');
                    //2、用echarts对象对要显示图标的dom元素区域进行初始化
                    var myChart = echarts.init(chartDom);
                    //3、配置可选项，也就是配置图表的各种显示参数（看文档-->配置项手册）
                    var option = {
                        //标题组件，包含主标题和副标题。
                        title: {
                            //主标题文本，支持使用 \n 换行。
                            text: '市场活动数据统计',
                            //title 组件离容器上侧的距离。
                            top: 'bottom',
                            //title 组件离容器左侧的距离。
                            left: 'center'
                        },
                        //直角坐标系 grid 中的 x 轴
                        xAxis: {
                            //坐标轴类型。
                            type: 'category', //类目轴
                            //x轴的刻度名称
                            data: [
                                '1月',
                                '2月',
                                '3月',
                                '4月',
                                '5月',
                                '6月',
                                '7月',
                                '8月',
                                '9月',
                                '10月',
                                '11月',
                                '12月'
                            ]
                        },
                        //直角坐标系 grid 中的 y 轴
                        yAxis: {
                            //坐标轴类型。value表示数值轴，适用于连续数据。
                            type: 'value'
                        },
                        //提示框组件。
                        tooltip: {
                            //触发类型。
                            trigger: 'item'
                        },
                        //系列
                        series: [
                            {
                                //系列中的数据内容数组。数组项可以为单个数值
                                //data: [120, 200, 150, 80, 70, 110, 130],
                                data: activityBarDataArray,
                                type: 'bar',
                                barWidth: 25
                            }
                        ]
                    };
                    //4、如果已经配置了可选项，然后就把可选项设置到图标对象中去
                    option && myChart.setOption(option);
                }
            });
        },
        //加载线索柱状图
        loadClueBarChart() {
            //先查询出来数据，然后再渲染图（不能颠倒，如果你先显示图表，但是没有数据，图表显示不出来）
            doGet('/api/statistic/clueBarChart', {}).then((resp) => {
                if (resp.data.code === 200) {
                    let xDataArray = resp.data.data.x; //后台返回x轴数据
                    let yDataArray = resp.data.data.y; //后台返回y轴数据

                    //1、根据id获取页面dom元素对象，echarts图表到时候就显示在这个dom元素里面 <div> echarts就显示在这里 </div>
                    var chartDom = document.getElementById('clueBarChart');
                    //2、用echarts对象对要显示图标的dom元素区域进行初始化
                    var myChart = echarts.init(chartDom);
                    //3、配置可选项，也就是配置图表的各种显示参数（看文档-->配置项手册）
                    var option = {
                        //标题组件，包含主标题和副标题。
                        title: {
                            //主标题文本，支持使用 \n 换行。
                            text: '线索数据统计',
                            //title 组件离容器上侧的距离。
                            top: 'bottom',
                            //title 组件离容器左侧的距离。
                            left: 'center'
                        },
                        //直角坐标系 grid 中的 x 轴
                        xAxis: {
                            //坐标轴类型。
                            type: 'category', //类目轴
                            //x轴的刻度名称
                            //data: ['1', '2', '3', '4', '5', '6', '7', '8', '8', '10', '11', '12', '13', '14', ......],
                            data: xDataArray
                        },
                        //直角坐标系 grid 中的 y 轴
                        yAxis: {
                            //坐标轴类型。value表示数值轴，适用于连续数据。
                            type: 'value'
                        },
                        //提示框组件。
                        tooltip: {
                            //触发类型。
                            trigger: 'item'
                        },
                        //系列
                        series: [
                            {
                                //系列中的数据内容数组。数组项可以为单个数值
                                //data: [120, 200, 150, 80, 70, 110, 130],
                                data: yDataArray,
                                type: 'bar',
                                barWidth: 25
                            }
                        ]
                    };
                    //4、如果已经配置了可选项，然后就把可选项设置到图标对象中去
                    option && myChart.setOption(option);
                }
            });
        }
    }
});
</script>
<style scoped>
.el-row {
    text-align: center;
}
</style>
