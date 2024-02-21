<template>
    <el-select v-model="delSameEmp.className" @click.native="getMajor"  placeholder="请选择班级">
        <el-option @click.native="getMajorName(item.className)" v-for="item in delSameEmp"
                   :key="item.className" :label="item.className" :value="item.className">
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
                    if(!obj[this.majors[i].className]){
                        this.delSameEmp.push(this.majors[i]);
                        obj[this.majors[i].className] = true;
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
