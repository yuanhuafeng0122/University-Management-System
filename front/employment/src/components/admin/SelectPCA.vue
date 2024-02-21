<template>
    <el-cascader
            ref="cascader"
            v-model="selectedOptions"
            :options="regionOption"
            :props="regionParams"
            @change="regionOptionChange"
            clearable></el-cascader>
</template>

<script>
    export default {
        name: ""
    }
</script>

<style scoped>

</style>
