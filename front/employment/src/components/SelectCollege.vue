<template>
    <div>
        <el-form-item label="学院" prop="college">
            <el-select v-model="this.collegeName"  placeholder="请选择">
                <el-option @click.native="getCollege(item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
    </div>
</template>

<script>
    export default {
        name: "",
        props:{
            collegeName:''
        },
        data() {
            return {
                colleges:[],
                college:'',
            }
        },
        methods: {
            getCollege(name){
                this.$emit("fun",name)
            },
            getCol(){
                this.$axios.get("/college/allCollege").then(res => {
                    if (res.data.code === 200){
                        this.colleges = res.data.data;
                    }else {
                        this.$message.error("获取学院数据错误！")
                    }
                })
            }
        },
        created() {
                let data = {};
                this.getCol()
        }
    }
</script>

<style scoped>
.el-form-item{
    display: flex;
}
</style>
