<template>
    <div>
        <el-select v-model="delSameEmp.major" @click.native="getMajor"  placeholder="请选择专业">
            <el-option @click.native="getMajorName(item.major)" v-for="item in delSameEmp" :key="item.major" :label="item.major" :value="item.major">
            </el-option>
        </el-select>
    </div>
</template>

<script>
    export default {
        name: "",
        props:{
            majors:[],
        },
        data() {
            return {
                delSameEmp:[],
            }
        },
        methods:{
            getMajor(){
                this.delSameEmp = [];
                // let obj = {};
                // for(let i =0; i<this.majors.length; i++){
                //     if(!obj[this.majors[i].major]){
                //         this.delSameEmp.push(this.majors[i]);
                //         obj[this.majors[i].major] = true;
                //     }
                // }
                const res = new Map();  //定义常量 res,值为一个Map对象实例
                //返回arr数组过滤后的结果，结果为一个数组   过滤条件是，如果res中没有某个键，就设置这个键的值为1
                this.delSameEmp= this.majors.filter((arr) => !res.has(arr.major) && res.set(arr.major, 1))
                console.log(this.majors)
            },
            getMajorName(majorName){
                this.$emit("getMajorName",majorName)
            }
        },

        mounted() {

        },
        computed:{

        }
    }
</script>

<style scoped>

</style>
