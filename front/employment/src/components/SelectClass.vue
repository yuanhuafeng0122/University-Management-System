<template>
    <el-select v-model="delSameEmp.classes" @click.native="getMajor"  placeholder="请选择班级">
        <el-option @click.native="getMajorName(item.classes)" v-for="item in delSameEmp"
                   :key="item.classes" :label="item.classes" :value="item.classes">
        </el-option>
    </el-select>
</template>

<script>
    export default {
        name: "",
        props:{
            majors:[],
        },
        data(){
            return {
                delSameEmp:[],
            }
        },
        methods:{
            getMajor(){
                this.delSameEmp = [];
                let obj = {};
                for(let i =0; i<this.majors.length; i++){
                    if(!obj[this.majors[i].classes]){
                        this.delSameEmp.push(this.majors[i]);
                        obj[this.majors[i].classes] = true;
                    }
                }

            },
            getMajorName(className){
                this.$emit("getClassName",className)
            }
        },
    }
</script>

<style scoped>

</style>
