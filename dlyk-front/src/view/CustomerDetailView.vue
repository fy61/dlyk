<template>
    <el-button type="primary" @click="clueShutter = true" plain>客户线索详情</el-button>
    <!--给客户创建交易的弹窗（对话框）-->
    <el-dialog v-model="clueShutter" title="创建交易" width="55%" center>
        <!--线索详情-->
        <el-form :model="clueDetail" label-width="110px" style="max-width: 95%">
            <el-form-item label="负责人">
                <el-select
                    v-model="clueDetail.ownerId"
                    placeholder="请选择负责人"
                    style="width: 100%"
                    clearable
                    disabled
                >
                    <el-option
                        v-for="item in ownerOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="所属活动">
                <el-select
                    v-model="clueDetail.activityId"
                    placeholder="请选择所属活动"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in activityOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="姓名">
                <el-input v-model="clueDetail.fullName" disabled />
            </el-form-item>

            <el-form-item label="称呼">
                <el-select
                    v-model="clueDetail.appellation"
                    placeholder="请选择称呼"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in appellationOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="手机">
                <el-input v-model="clueDetail.phone" disabled />
            </el-form-item>

            <el-form-item label="微信">
                <el-input v-model="clueDetail.weixin" disabled />
            </el-form-item>

            <el-form-item label="QQ">
                <el-input v-model="clueDetail.qq" disabled />
            </el-form-item>

            <el-form-item label="邮箱">
                <el-input v-model="clueDetail.email" disabled />
            </el-form-item>

            <el-form-item label="年龄">
                <el-input v-model="clueDetail.age" disabled />
            </el-form-item>

            <el-form-item label="职业">
                <el-input v-model="clueDetail.job" disabled />
            </el-form-item>

            <el-form-item label="年收入">
                <el-input v-model="clueDetail.yearIncome" disabled />
            </el-form-item>

            <el-form-item label="住址">
                <el-input v-model="clueDetail.address" disabled />
            </el-form-item>

            <el-form-item label="贷款">
                <el-select
                    v-model="clueDetail.needLoan"
                    placeholder="请选择是否需要贷款"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in needLoanOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="意向状态">
                <el-select
                    v-model="clueDetail.intentionState"
                    placeholder="请选择意向状态"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in intentionStateOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="意向产品">
                <el-select
                    v-model="clueDetail.intentionProduct"
                    placeholder="请选择意向产品"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in productOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="线索状态">
                <el-select
                    v-model="clueDetail.state"
                    placeholder="请选择线索状态"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in clueStateOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="线索来源">
                <el-select
                    v-model="clueDetail.source"
                    placeholder="请选择线索来源"
                    style="width: 100%"
                    disabled
                    clearable
                >
                    <el-option
                        v-for="item in sourceOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="线索描述">
                <el-input
                    v-model="clueDetail.description"
                    :rows="5"
                    type="textarea"
                    placeholder="请输入线索描述"
                    disabled
                />
            </el-form-item>

            <el-form-item label="下次联系时间">
                <el-date-picker
                    v-model="clueDetail.nextContactTime"
                    type="datetime"
                    style="width: 100%"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    placeholder="请选择下次联系时间"
                    disabled
                />
            </el-form-item>
        </el-form>

        <!--线索的历史跟踪记录-->
        <el-table :data="clueRemarkList" style="width: 100%">
            <el-table-column type="index" label="序号" width="100" />
            <el-table-column property="noteWayDO.typeValue" label="跟踪方式" />
            <el-table-column property="noteContent" label="跟踪内容" />
            <el-table-column property="createTime" label="跟踪时间" width="175" />
            <el-table-column property="createByDO.name" label="跟踪人" width="175" />
            <el-table-column property="editTime" label="编辑时间" width="175" />
            <el-table-column property="editByDO.name" label="编辑人" width="175" />
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
    </el-dialog>

    <!--客户详情-->
    <el-form
        ref="customerRemarkRefForm"
        :model="customerRemark"
        label-width="110px"
        :rules="customerRemarkRules"
        style="max-width: 95%"
    >
        <el-form-item label="意向产品">
            <el-select
                v-model="customerDetail.product"
                placeholder="请选择意向产品"
                style="width: 100%"
                clearable
                disabled
            >
                <el-option
                    v-for="item in productOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>

        <el-form-item label="客户描述">
            <el-input
                v-model="customerDetail.description"
                :rows="5"
                type="textarea"
                placeholder="请输入线索描述"
                disabled
            />
        </el-form-item>

        <el-form-item label="下次跟踪时间">
            <el-input v-model="customerDetail.nextContactTime" disabled />
        </el-form-item>

        <!--填写跟踪记录-->
        <!--给客户创建交易-->
        <el-form-item label="填写跟踪记录" prop="noteContent">
            <el-input v-model="customerRemark.noteContent" :rows="8" type="textarea" />
        </el-form-item>
        <el-form-item label="跟踪方式" prop="noteWay">
            <el-select
                v-model="customerRemark.noteWay"
                placeholder="请选择跟踪方式"
                style="width: 100%"
                clearable
            >
                <el-option
                    v-for="item in noteWayOptions"
                    :key="item.id"
                    :label="item.typeValue"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="customerRemarkSubmit">提 交</el-button>
            <el-button type="success" @click="createTran">创建交易</el-button>
            <el-button type="success" plain @click="goBack">返 回</el-button>
        </el-form-item>
    </el-form>

    <!--客户的历史跟踪记录-->
    <el-table :data="customerRemarkList" style="width: 100%">
        <el-table-column type="index" label="序号" width="100" />
        <el-table-column property="noteWayDO.typeValue" label="跟踪方式" />
        <el-table-column property="noteContent" label="跟踪内容" />
        <el-table-column property="createTime" label="跟踪时间" width="175" />
        <el-table-column property="createByDO.name" label="跟踪人" width="175" />
        <el-table-column property="editTime" label="编辑时间" width="175" />
        <el-table-column property="editByDO.name" label="编辑人" width="175" />
        <el-table-column label="操作" width="150">
            <template #default="scope">
                <a href="javascript:" @click="edit(scope.row.id)">编辑</a>
                &nbsp;
                <a href="javascript:" @click="del(scope.row.id)">删除</a>
            </template>
        </el-table-column>
    </el-table>
    <p>
        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="cusPageSize"
            :total="cusTotal"
            @prev-click="pageCustomerRemark"
            @next-click="pageCustomerRemark"
            @current-change="pageCustomerRemark"
        />
    </p>
</template>
<script>
import { doGet, doPost } from '../http/httpRequest.js';
import { goBack, messageTip } from '../util/util.js';
export default {
    data() {
        return {
            //定义客户线索详情弹窗是否弹出来
            clueShutter: false,
            //线索详情数据对象，初始值是空
            clueDetail: {},
            //线索跟踪记录的List，初始值是空
            clueRemarkList: [
                {
                    createByPO: {},
                    editByPO: {},
                    noteWayPO: {}
                }
            ],
            //负责人的下拉选项数据，初始值是空
            ownerOptions: [{}],
            //市场活动的下拉选项数据，初始值是空
            activityOptions: [{}],
            //意向产品的下拉选项
            productOptions: [{}],
            appellationOptions: [{}],
            needLoanOptions: [{}],
            intentionStateOptions: [{}],
            clueStateOptions: [{}],
            sourceOptions: [{}],
            //分页时每页显示多少条数据
            pageSize: 0,
            //总共有多少条
            total: 0,
            //线索ID，初始值是0
            clueId: 0,
            //客户详情对象，初始值是空
            customerDetail: {},
            //客户跟踪记录对象，初始值是空
            customerRemark: {},
            //跟踪方式的下拉选项数据，初始值是空
            noteWayOptions: [{}],
            //提交客户跟踪记录的验证规则
            customerRemarkRules: {
                noteContent: [
                    { required: true, message: '跟踪内容不能为空', trigger: 'blur' },
                    {
                        min: 5,
                        max: 255,
                        message: '跟踪内容长度为5-255个字符',
                        trigger: 'blur'
                    }
                ],
                noteWay: [
                    {
                        required: true,
                        message: '请选择跟踪方式',
                        trigger: ['blur', 'change']
                    }
                ]
            },
            //客户跟踪记录的List，初始值是空
            customerRemarkList: [
                {
                    createByDO: {},
                    editByDO: {},
                    noteWayDO: {}
                }
            ],
            //分页时每页显示多少条数据
            cusPageSize: 0,
            //总共有多少条
            cusTotal: 0
        };
    },
    mounted() {
        this.loadCustomerDetail();
        this.loadOwner();
        this.loadActivity();
        this.loadProduct();
        this.loadDicValue('appellation');
        this.loadDicValue('needLoan');
        this.loadDicValue('intentionState');
        this.loadDicValue('clueState');
        this.loadDicValue('source');
        this.loadDicValue('noteWay');
        this.loadDicValue('stage');
    },
    methods: {
        goBack,
        //加载客户详情
        loadCustomerDetail() {
            //从动态路由地址中获取客户id参数
            let id = this.$route.params.id; //id参数名要和路由中配置的path : 'customer/:id', 的参数名相同
            doGet('/api/customer/detail/' + id, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.customerDetail = resp.data.data;
                    this.clueId = this.customerDetail.clueId;
                    console.log(this.customerDetail);
                    console.log(this.clueId);
                    this.loadClueDetail();
                    this.loadClueRemarkList(1);
                    this.loadCustomerRemarkList(1);
                }
            });
        },

        //加载负责人
        loadOwner() {
            doGet('/api/owner', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.ownerOptions = resp.data.data;
                }
            });
        },

        //加载市场活动
        loadActivity() {
            doGet('/api/activity/all', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.activityOptions = resp.data.data;
                }
            });
        },

        //加载意向产品
        loadProduct() {
            doGet('/api/product/sale', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.productOptions = resp.data.data;
                }
            });
        },

        //加载字典数据
        loadDicValue(typeCode) {
            doGet('/api/dicvalue/' + typeCode, {}).then((resp) => {
                if (resp.data.code === 200) {
                    if (typeCode === 'appellation') {
                        this.appellationOptions = resp.data.data;
                    } else if (typeCode === 'needLoan') {
                        this.needLoanOptions = resp.data.data;
                    } else if (typeCode === 'intentionState') {
                        this.intentionStateOptions = resp.data.data;
                    } else if (typeCode === 'clueState') {
                        this.clueStateOptions = resp.data.data;
                    } else if (typeCode === 'source') {
                        this.sourceOptions = resp.data.data;
                    } else if (typeCode === 'noteWay') {
                        this.noteWayOptions = resp.data.data;
                    } else if (typeCode === 'stage') {
                        this.stageOptions = resp.data.data;
                    }
                }
            });
        },

        //加载线索详情
        loadClueDetail() {
            doGet('/api/clue/detail/' + this.clueId, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.clueDetail = resp.data.data;
                }
            });
        },

        //分页加载线索跟踪记录列表信息
        loadClueRemarkList(current) {
            // /api/clue/3/remark
            doGet('/api/clue/remark', {
                current: current,
                clueId: this.clueId
            }).then((resp) => {
                if (resp.data.code === 200) {
                    this.clueRemarkList = resp.data.data.list; // resp.data  =  R 对象
                    console.log(this.clueRemarkList);
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                }
            });
        },

        //分页函数
        page(number) {
            //number值是element-plus回调时传给我们的，number这个值就是当前页
            this.loadClueRemarkList(number);
        },

        //分页加载客户跟踪记录列表信息
        loadCustomerRemarkList(current) {
            // /api/customer/3/remark
            doGet('/api/customer/remark', {
                current: current,
                customerId: this.customerDetail.id
            }).then((resp) => {
                if (resp.data.code === 200) {
                    this.customerRemarkList = resp.data.data.list; // resp.data  =  R 对象
                    this.cusPageSize = resp.data.data.pageSize;
                    this.cusTotal = resp.data.data.total;
                }
            });
        },

        //分页函数
        pageCustomerRemark(number) {
            //number值是element-plus回调时传给我们的，number这个值就是当前页
            this.loadCustomerRemarkList(number);
        }
    }
};
</script>
<style scoped>
.el-form {
    margin: 0;
    width: 100%;
}
</style>
