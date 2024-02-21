<template>
    <div>
        <el-form-item label="市区" prop="college">
            <el-select v-model="cityName" @click.native="getCities"   placeholder="请选择市区">
                <el-option @click.native="getAreas(item.cityId,item.city)" v-for="item in cities" :key="item.cityId" :label="item.city" :value="item.cityId"></el-option>
            </el-select>
        </el-form-item>
    </div>
</template>

<script>
    export default {
        props: {
            provinceId:{
                type:String,
                default:[],
            },
            cityName:'',
        },
        name: "",
        data (){
            return {
                city: {},
                cities:[],

            }
        },
        methods:{
            getAreas(cityId,city){
                this.$emit("getArea",cityId,city)
            },
            getCities() {
                this.$axios.get("/cities/findAllCity/"+this.provinceId).then(res => {
                    if (res.data.code === 200){
                        this.cities = res.data.data
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
