<template>
    <div>
        <el-form-item label="县区" prop="college">
            <el-select v-model="areaName" @click.native="getArea"   placeholder="请选择县区">
                <el-option  @click.native="getAreaName(item.area)" v-for="item in areas" :key="item.areaId" :label="item.area" :value="item.areaId"></el-option>
            </el-select>
        </el-form-item>
    </div>
</template>

<script>
    export default {
        name: "",
        props: {
            cityId:{
                type:String,
                default:[],
            },
            areaName:'',
        },
        data (){
            return {
                area: {},
                areas:[],
            }
        },
        methods: {
            getArea(){
                this.$axios.get("/areas/findAllAre/"+this.cityId).then(res => {
                    if (res.data.code === 200){
                        this.areas = res.data.data
                    }
                })
            },
            getAreaName(area){
                this.$emit("getAreaName",area)
            }
        },
        mounted() {

        }
    }
</script>

<style scoped>

</style>
