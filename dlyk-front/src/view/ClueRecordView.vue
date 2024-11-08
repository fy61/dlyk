<template>
    <el-form
        ref="clueRefForm"
        :model="clueQuery"
        :rules="clueRules"
        label-width="100px"
        style="max-width: 95%"
    >
        <el-form-item label="负责人">
            <el-select
                v-model="clueQuery.ownerId"
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
                v-model="clueQuery.activityId"
                placeholder="请选择所属活动"
                style="width: 100%"
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

        <el-form-item label="姓名" prop="fullName">
            <el-input v-model="clueQuery.fullName" />
        </el-form-item>

        <el-form-item label="称呼">
            <el-select
                v-model="clueQuery.appellation"
                placeholder="请选择称呼"
                style="width: 100%"
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

        <el-form-item label="手机" v-if="clueQuery.id > 0"
            ><!--此时是编辑-->
            <el-input v-model="clueQuery.phone" disabled />
        </el-form-item>

        <el-form-item label="手机" prop="phone" v-else
            ><!--此时是录入-->
            <el-input v-model="clueQuery.phone" />
        </el-form-item>

        <el-form-item label="微信">
            <el-input v-model="clueQuery.weixin" />
        </el-form-item>

        <el-form-item label="QQ" prop="qq">
            <el-input v-model="clueQuery.qq" />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
            <el-input v-model="clueQuery.email" />
        </el-form-item>

        <el-form-item label="年龄" prop="age">
            <el-input v-model="clueQuery.age" />
        </el-form-item>

        <el-form-item label="职业">
            <el-input v-model="clueQuery.job" />
        </el-form-item>

        <el-form-item label="年收入" prop="yearIncome">
            <el-input v-model="clueQuery.yearIncome" />
        </el-form-item>

        <el-form-item label="住址">
            <el-input v-model="clueQuery.address" />
        </el-form-item>

        <el-form-item label="贷款">
            <el-select
                v-model="clueQuery.needLoan"
                placeholder="请选择是否需要贷款"
                style="width: 100%"
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
                v-model="clueQuery.intentionState"
                placeholder="请选择意向状态"
                style="width: 100%"
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
                v-model="clueQuery.intentionProduct"
                placeholder="请选择意向产品"
                style="width: 100%"
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
                v-model="clueQuery.state"
                placeholder="请选择线索状态"
                style="width: 100%"
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
                v-model="clueQuery.source"
                placeholder="请选择线索来源"
                style="width: 100%"
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

        <el-form-item label="线索描述" prop="description">
            <el-input
                v-model="clueQuery.description"
                :rows="5"
                type="textarea"
                placeholder="请输入线索描述"
            />
        </el-form-item>

        <el-form-item label="下次联系时间">
            <el-date-picker
                v-model="clueQuery.nextContactTime"
                type="datetime"
                style="width: 100%"
                value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择下次联系时间"
            />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="addClueSubmit">提 交</el-button>
            <el-button type="success" plain @click="goBack">返 回</el-button>
        </el-form-item>
    </el-form>
</template>
<script>
import { defineComponent } from 'vue';
import { doGet, doPost, doPut } from '../http/httpRequest.js';

export default defineComponent({
    data() {
        return {
            //线索表单对象，初始值是空
            clueQuery: {},
            //负责人的下拉选项
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
            //录入线索验证规则
            clueRules: {}
        };
    },
    mounted() {
        this.loadDicValue('appellation');
        this.loadDicValue('needLoan');
        this.loadDicValue('intentionState');
        this.loadDicValue('clueState');
        this.loadDicValue('source');
        this.loadDicValue('activity');
        this.loadDicValue('product');
        this.loadLoginUser();
    },
    methods: {
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
                    } else if (typeCode === 'activity') {
                        this.activityOptions = resp.data.data;
                    } else if (typeCode === 'product') {
                        this.productOptions = resp.data.data;
                    }
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

        //加载当前登录用户
        loadLoginUser() {
            doGet('/api/login/info', {}).then((resp) => {
                console.log(resp);
                let user = resp.data.data;
                this.clueQuery.ownerId = user.id;
            });
        }
    }
});
</script>
<style scoped>
.el-form {
    width: auto;
    margin: 0%;
}
</style>
