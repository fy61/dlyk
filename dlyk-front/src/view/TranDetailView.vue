<template>
    <el-form
        ref="tranRefForm"
        :model="tranRemark"
        label-width="120px"
        :rules="tranRemarkRules"
    >
        <el-form-item label="ID：">
            <!--<el-input v-model="tranDetail.id" disabled/>-->
            <div style="background: azure; width: 100%">{{ tranDetail.id }}</div>
        </el-form-item>

        <el-form-item label="交易流水号：">
            <!--<el-input v-model="tranDetail.tranNo" disabled/>-->
            {{ tranDetail.tranNo }}
        </el-form-item>

        <el-form-item label="交易金额">
            {{ tranDetail.money }}
        </el-form-item>

        <el-form-item label="预计成交时间">
            {{ tranDetail.expectedDate }}
        </el-form-item>

        <el-form-item label="交易阶段">
            <el-select
                v-model="tranDetail.stage"
                placeholder="请选择交易阶段"
                style="width: 100%"
                disabled
            >
                <el-option
                    v-for="item in stageOptions"
                    :key="item.id"
                    :label="item.typeValue"
                    :value="item.id"
                />
            </el-select>
        </el-form-item>

        <el-form-item label="交易描述">
            <el-input
                v-model="tranDetail.description"
                :rows="5"
                type="textarea"
                disabled
            />
        </el-form-item>

        <el-form-item label="下次跟踪时间">
            <el-input v-model="tranDetail.nextContactTime" disabled />
        </el-form-item>

        <el-form-item label="创建时间">
            <el-input v-model="tranDetail.createTime" disabled />
        </el-form-item>

        <el-form-item label="创建人">
            <el-input v-model="tranDetail.createByPO.name" disabled />
        </el-form-item>

        <el-form-item label="编辑时间">
            <el-input v-model="tranDetail.editTime" disabled />
        </el-form-item>

        <el-form-item label="编辑人">
            <el-input v-model="tranDetail.editByPO.name" disabled />
        </el-form-item>

        <el-form-item>
            <el-table :data="tranRemarkList" style="width: 100%">
                <el-table-column type="index" label="序号" width="100" />
                <el-table-column property="noteWayPO.typeValue" label="跟踪方式" />
                <el-table-column property="noteContent" label="跟踪内容" />
                <el-table-column property="createTime" label="跟踪时间" width="175" />
                <el-table-column property="createByPO.name" label="跟踪人" width="175" />
                <el-table-column property="editTime" label="编辑时间" width="175" />
                <el-table-column property="editByPO.name" label="编辑人" width="175" />
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
                    :page-size="pageSize"
                    :total="total"
                    @prev-click="page"
                    @next-click="page"
                    @current-change="page"
                />
            </p>
        </el-form-item>

        <el-form-item label="填写跟踪记录" prop="noteContent">
            <el-input v-model="tranRemark.noteContent" :rows="8" type="textarea" />
        </el-form-item>
        <el-form-item label="跟踪方式" prop="noteWay">
            <el-select
                v-model="tranRemark.noteWay"
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
            <el-button type="primary" @click="tranRemarkSubmit">提 交</el-button>
            <el-button type="success" @click="updateTranStage">更新交易阶段</el-button>
            <el-button type="success" plain @click="goBack">返 回</el-button>
        </el-form-item>

        <el-form-item>
            <el-table :data="tranHistoryList" style="width: 100%">
                <el-table-column type="index" label="序号" width="100" />
                <el-table-column property="stagePO.typeValue" label="交易阶段" />
                <el-table-column property="money" label="交易金额" />
                <el-table-column property="expectedDate" label="预计成交时间" />
                <el-table-column property="createTime" label="创建时间" />
                <el-table-column property="createByPO.name" label="创建人" />
            </el-table>
        </el-form-item>
    </el-form>

    <!--更新交易阶段的弹窗（对话框）-->
    <el-dialog v-model="tranHistoryDialogVisible" title="更新交易阶段" width="55%" center>
        <el-form
            ref="tranHistoryRefForm"
            :model="tranHistoryQuery"
            label-width="110px"
            :rules="tranHistoryRules"
        >
            <el-form-item label="交易阶段" prop="stage">
                <el-select
                    v-model="tranHistoryQuery.stage"
                    placeholder="请选择交易阶段"
                    style="width: 100%"
                >
                    <el-option
                        v-for="item in stageOptions"
                        :key="item.id"
                        :label="item.typeValue"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="交易金额" prop="money">
                <el-input v-model="tranHistoryQuery.money" />
            </el-form-item>

            <el-form-item label="预计成交时间" prop="expectedDate">
                <el-date-picker
                    v-model="tranHistoryQuery.expectedDate"
                    type="datetime"
                    style="width: 100%"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    placeholder="请选择预计成交时间"
                />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="tranHistoryDialogVisible = false">关 闭</el-button>
                <el-button type="primary" @click="tranHistorySubmit">提 交</el-button>
            </span>
        </template>
    </el-dialog>
</template>
<script>
import { doGet, doPost } from '../http/httpRequest';
import { goBack, messageTip } from '../util/util.js';
export default {
    //注入
    inject: ['reload'],
    data() {
        return {
            //交易详情对象，初始值是空
            tranDetail: {
                createByPO: {},
                editByPO: {}
            },
            //交易跟踪记录对象，初始值是空
            tranRemark: {},
            //跟踪方式的下拉选项数据，初始值是空
            noteWayOptions: [{}],
            //定义提交交易跟踪记录表单的验证规则
            tranRemarkRules: {
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
            //更新交易阶段的弹窗是否弹出来，false不弹，true弹出来
            tranHistoryDialogVisible: false,
            //交易历史对象，初始值是空
            tranHistoryQuery: {},
            //交易阶段的下拉选项数据，初始值是空
            stageOptions: [{}],
            //定义更新交易阶段表单的验证规则
            tranHistoryRules: {
                stage: [
                    {
                        required: true,
                        message: '请选择交易阶段',
                        trigger: ['blur', 'change']
                    }
                ],
                money: [
                    { required: true, message: '请输入交易金额', trigger: 'blur' },
                    {
                        pattern: /^[0-9]+(\.[0-9]{2})?$/,
                        message: '交易金额必须是整数或者两位小数',
                        trigger: 'blur'
                    }
                ],
                expectedDate: [
                    {
                        required: true,
                        message: '请选择预计成交时间',
                        trigger: ['blur', 'change']
                    }
                ]
            },
            //交易跟踪记录列表对象，初始值是空
            tranRemarkList: [
                {
                    noteWayPO: {},
                    createByPO: {},
                    editByPO: {}
                }
            ],
            //分页时每页显示多少条数据
            pageSize: 0,
            //总共有多少条
            total: 0,
            //交易历史列表对象，初始值是空
            tranHistoryList: [
                {
                    stagePO: {},
                    createByPO: {}
                }
            ]
        };
    },
    mounted() {
        this.loadTranDetail();
        this.loadDicValue('noteWay');
        this.loadDicValue('stage');
        this.loadTranRemarkList(1);
        this.loadTranHistoryList();
    },
    methods: {
        goBack,

        //加载交易详情
        loadTranDetail() {
            let tranId = this.$route.params.id;
            doGet('/api/tran/' + tranId, {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.tranDetail = resp.data.data;
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
        //分页加载交易跟踪记录列表信息
        loadTranRemarkList(current) {
            // /api/tran/3/remark
            let tranId = this.$route.params.id;
            doGet('/api/tran/remark', {
                current: current,
                tranId: tranId
            }).then((resp) => {
                if (resp.data.code === 200) {
                    this.tranRemarkList = resp.data.data.list; // resp.data  =  R 对象
                    this.pageSize = resp.data.data.pageSize;
                    this.total = resp.data.data.total;
                }
            });
        },
        //分页函数
        page(number) {
            //number值是element-plus回调时传给我们的，number这个值就是当前页
            this.loadTranRemarkList(number);
        },
        //提交交易跟踪记录
        tranRemarkSubmit() {
            this.$refs.tranRefForm.validate((isValid) => {
                if (isValid) {
                    doPost('/api/tran/submit', {
                        tranId: this.tranDetail.id,
                        noteWay: this.tranRemark.noteWay,
                        noteContent: this.tranRemark.noteContent
                    }).then((resp) => {
                        if (resp.data.code === 200) {
                            //提交交易跟踪记录成功，提示一下
                            messageTip('提交交易记录成功', 'success');
                            //刷新一下页面
                            this.reload();
                        } else {
                            //提交交易记录失败，提示一下
                            messageTip(
                                '提交交易记录失败，原因：' + resp.data.msg,
                                'error'
                            );
                        }
                    });
                }
            });
        },
        //加载交易的历史阶段记录
        loadTranHistoryList() {
            // /api/tran/3/remark
            let tranId = this.$route.params.id;
            doGet('/api/tran/' + tranId + '/history', {}).then((resp) => {
                if (resp.data.code === 200) {
                    this.tranHistoryList = resp.data.data; // resp.data  =  R 对象
                }
            });
        },
        //更新交易阶段
        updateTranStage() {
            this.tranHistoryDialogVisible = true;
        },

        //更新交易阶段（提交保存）
        tranHistorySubmit() {
            this.$refs.tranHistoryRefForm.validate((isValid) => {
                if (isValid) {
                    doPost('/api/tran/history', {
                        tranId: this.tranDetail.id,
                        stage: this.tranHistoryQuery.stage,
                        money: this.tranHistoryQuery.money,
                        expectedDate: this.tranHistoryQuery.expectedDate
                    }).then((resp) => {
                        if (resp.data.code === 200) {
                            //更新交易阶段成功，提示一下
                            messageTip('更新交易阶段成功', 'success');
                            //刷新一下页面
                            this.reload();
                        } else {
                            //更新交易阶段失败，提示一下
                            messageTip(
                                '更新交易阶段失败，原因：' + resp.data.msg,
                                'error'
                            );
                        }
                    });
                }
            });
        }
    }
};
</script>
<style scoped>
.el-form {
    margin: 0px;
    width: 100%;
}
</style>
