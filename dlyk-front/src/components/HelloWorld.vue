<template>
    <!-- info是父组件传递过来的值 -->
    <!-- <div>我是子组件拿到父组件的值是{{info}}</div> -->
    <div>{{ cxk }}</div>
    <div>{{ info }}</div>
</template>

<script>
import { toRefs, defineProps } from 'vue';
import { defineComponent } from 'vue';
export default {
    props: ['cxk', 'info'], //主要透传条件
    mounted() {
        console.log('manmanman');
        console.log('this.info:' + `${this.cxk}`);
        console.log(`${this.cxk} - ${this.$attrs}`);
    }
};
</script>

<style></style>
