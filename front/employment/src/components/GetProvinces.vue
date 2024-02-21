<template>
    <div>
        <el-form-item label="省份" prop="college">
            <el-select v-model="provinceName" @click.native="getProvince"  placeholder="请选择省份">
                <el-option @click.native="getCities(item.provinceId,item.province)" v-for="item in provinces" :key="item.provinceId" :label="item.province" :value="item.provinceId">

                </el-option>
            </el-select>
        </el-form-item>
    </div>
</template>

<script>
    export default {
        name: "",
        props:{
            provinceName:'',
        },
        data(){
            return {
                provinces:[],
                province:{},
            }
        },
        computed:{
        },
        methods:{
            getCities(provinceId,province) {
                this.$emit("getCity",provinceId,province)
            },
            getProvince(){
                this.$axios.get("/provinces/findAllPro").then(res => {
                    if (res.data.code === 200){
                        this.provinces = res.data.data;
                    }
                })
            }
        },
        mounted() {

        }

    }
</script>

<style scoped>

</style>
