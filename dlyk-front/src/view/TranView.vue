<template>
    <el-table
        :data="tranList"
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
        <el-table-column type="selection" width="50" />
        <el-table-column type="index" label="序号" width="65" />
        <el-table-column label="交易流水号">
            <template #default="scope">
                <a href="javascript:" @click="view(scope.row.id)">{{
                    scope.row.tranNo
                }}</a>
            </template>
        </el-table-column>
        <el-table-column property="cluePO.fullName" label="客户姓名" />
        <el-table-column property="cluePO.phone" label="客户手机" />
        <el-table-column property="money" label="交易金额" />
        <el-table-column property="expectedDate" label="预计成交时间" />
        <el-table-column property="tdvPO.typeValue" label="交易阶段" />
        <el-table-column property="nextContactTime" label="下次跟踪时间" />
        <el-table-column property="createTime" label="创建时间" />
        <el-table-column fixed="right" label="操作" width="90">
            <template #default="scope">
                <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
            </template>
        </el-table-column>
    </el-table>

    <p>
        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="pageSize"
            :total="total"
            @prev-click="page"
            @next-click="page"
            @current-change="page"
        />
    </p>
</template>
<script>
import { doGet } from '../http/httpRequest';
export default {
    data() {
        return {
            tranList: [
                {
                    cluePO: {},
                    tdvPO: {}
                }
            ],
            //分页时每页显示多少条数据
            pageSize: 0,
            //总共有多少条
            total: 0
        };
    },
    //页面渲染后执行该函数钩子
    mounted() {
        this.getData(1);
    },
    methods: {
        //获取用户分页列表数据
        getData(current) {
            doGet('/api/trans', {
                current: current //当前页，前面是参数名，后面是参数值
            }).then((resp) => {
                if (resp.data.code === 200) {
                    this.tranList = resp.data.data.list; // resp.data  =  R 对象
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                    console.log(this.tranList);
                }
            });
        },
        //分页函数
        page(number) {
            //number值是element-plus回调时传给我们的，number这个值就是当前页
            this.getData(number);
        },
        //查看交易详情
        view(id) {
            this.$router.push('/dashboard/tran/detail/' + id);
        }
    }
};
</script>
<style scoped></style>
