<template>
    <div>
        <el-row class="row-box">
            <el-col :span="24">
                修改内容
            </el-col>
        </el-row>

        <el-row class="row-box1">

            <el-col :span="24" style="margin-bottom: 10px">
                <el-col :span="4">
                    <div class="title-box">标题:</div>
                </el-col>
                <el-col :span="14">
                    <div class="text-box">
                        <el-input v-model="title" placeholder="标题"></el-input>
                    </div>
                </el-col>
            </el-col>

            <el-col :span="24">
                <el-col :span="4">
                    <div class="title-box">标签:</div>
                </el-col>
                <el-col :span="18" class="text-box">
                    <el-tag
                            :key="tag"
                            v-for="tag in tagList"
                            closable
                            :disable-transitions="false"
                            @close="handleClose(tag)">
                        {{tag}}
                    </el-tag>
                    <el-input
                            class="input-new-tag"
                            v-if="inputVisible"
                            v-model="inputValue"
                            ref="saveTagInput"
                            size="small"
                            @keyup.enter.native="handleInputConfirm"
                            @blur="handleInputConfirm"
                    >
                    </el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
                </el-col>
            </el-col>


            <el-col :span="24" style="margin-bottom: 10px">
                <el-col :span="4">
                    <div class="title-box">展示样式:</div>
                </el-col>
                <el-col :span="18">
                    <div class="text-box">
                        <el-select v-model="show" placeholder="请选择" style="margin-right: 10px;">
                            <el-option
                                    v-for="item in showList"
                                    :key="item.value"
                                    :label="item.name"
                                    :value="item.value"
                            >
                            </el-option>
                        </el-select>
                    </div>
                </el-col>
            </el-col>

            <el-col :span="24" style="margin-bottom: 10px">
                <el-col :span="4">
                    <div class="title-box">内容:</div>
                </el-col>
                <el-col :span="14">
                    <div class="text-box" style="margin-top: 30px" v-for="(item,index) in contentList" :key="index">
                        <img :src="item.value" v-if="item.type == 'img'">
                        <div v-html="item.value" v-if="item.type == 'textarea'"
                             style="background-color: #e7e1cd;font-size: 18px"></div>
                        <el-input type="textarea" v-model="contentList[index].value" placeholder="输入变更后内容"></el-input>
                    </div>
                </el-col>
            </el-col>
            <el-col :span="24" style="text-align: center">
            <el-button style="padding: 1em 4em" type="primary" @click="changeBtn(4)">审核通过</el-button>
            <el-button style="padding: 1em 4em" type="info" @click="changeBtn(5)">审核不通过</el-button>
            </el-col>
        </el-row>

        <div style="height: 0;overflow: hidden" v-html="contentList[0].value" id="html">
        </div>
    </div>
</template>

<script>
    export default {
        name: "editContent",
        data() {
            return {
                contentInfo: '',
                title: '',
                //标签
                tagList: [],
                inputVisible: false,
                inputValue: '',
                //状态列表
                statusList: [
                    {
                        name: '审核成功',
                        value: 4,
                    },
                    {
                        name: '不通过',
                        value: 5,
                    }
                ],
                status: '',
                //展示样式
                showList: [
                    {
                        name: '底部图',
                        value: 0,
                    },
                    {
                        name: '右侧图',
                        value: 1,
                    },
                    {
                        name: '三图',
                        value: 2,
                    }
                ],
                show: '',

                contentList: [],
                imgList:[],


                html:'',
            }
        },
        methods: {
            changeBtn(status) {
                let contentInfo = this.contentInfo
                for(let i = 0;i<this.contentList.length;i++){
                    if(this.contentList[i].type == 'img'){
                        let img = {
                            src:this.contentList[i].value
                        }
                        this.imgList.push(img)
                    }
                }
                let html = document.getElementById('html')
                let imgArr = html.getElementsByTagName('img')
                if(imgArr.length>0){
                    for(let i=0;i<imgArr.length;i++){
                        let img = {
                            src:imgArr[i].currentSrc
                        }
                        this.imgList.push(img)
                    }
                }
                let data = {
                    editor:this.contentList,
                    show:this.show,
                    imgList:this.imgList
                }
                let cnt = {
                    // userId: contentInfo.upUserId, // Long 用户编号
                    // _id: contentInfo._id, // String 内容分片编号
                    // id: contentInfo.id, // Long 内容编号
                    // status: status, // Byte 状态Content.STATUS
                    // // upChannelId: upChannelId, // Long <选填> 上传专栏编号
                    // title: this.title, // String 标题
                    // tags: JSON.stringify(this.tagList), // String <选填> 标签
                    // data: JSON.stringify(data), // String 数据（JSON）
					
					
					id: contentInfo.id, // Long 内容编号
					module: 1170, // String <选填> 模块编号
					type: type, // Byte <选填> 内容类型
					status: status, // Byte <选填> 状态
					power: power, // Byte <选填> 权力:付费，会员等
					upUserId: upUserId, // Long <选填> 上传用户编号
					upChannelId: upChannelId, // Long <选填> 上传专栏编号
					tags: tags, // JSONObject <选填> 标签
					title: title, // String <选填> 标题
					data: data, // String <选填> 数据
					proviteData: proviteData, // String <选填> 私密信息
					ext: ext, // String <选填> 扩展信息
                }
                this.$api.editContent(cnt,(res=>{
                    if(res.data.rc == this.$util.RC.SUCCESS){
                        this.$router.push('examineList')
                        this.$message({
                            message: '审核完毕',
                            type: 'success'
                        });
                    }else {
                        this.$message({
                            message: res.data.c,
                            type: 'warning'
                        });
                    }
                }))
            },

            // 标签
            handleClose(tag) {
                this.tagList.splice(this.tagList.indexOf(tag), 1);
                console.log(this.tagList)
            },

            showInput() {
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },

            handleInputConfirm() {
                let that = this
                let inputValue = this.inputValue;
                if (inputValue) {
                    that.tagList.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
                console.log(this.tagList)
            }

            //end
        },
        mounted() {
            this.contentInfo = this.$route.params.info
            console.log(this.contentInfo)
            this.title = this.contentInfo.title
            this.tagList = JSON.parse(this.contentInfo.tags)
            this.status = this.contentInfo.status
            this.show = JSON.parse(this.contentInfo.data).show
            this.contentList = JSON.parse(this.contentInfo.data).editor
        }
    }
</script>

<style scoped lang="scss">
    .row-box {
        background: #fff;
        padding: 15px;
        font-size: 1.6rem;
        color: #666;
        border-left: 4px solid #67C23A;

    }

    .row-box1 {
        margin-top: 20px;
        padding: 15px;
        background: #fff;
    }

    .title-box {
        font-size: 1.6rem;
        line-height: 40px;
        letter-spacing: 2px;
        font-weight: 600;
        color: #666;
        text-align: right;
    }

    .text-box {
        margin-left: 50px;
    }

    .row-box2 {
        margin-top: 20px;
    }

    .row-box3 {
        margin-top: 20px;
        text-align: center;
        padding-bottom: 20px;
    }

    .image-box {
        margin-left: 50px;
        width: 400px;
        height: 300px;
        overflow: hidden;
        float: left;
    }

    .image-box img {
        width: 400px;
        height: 300px;
        overflow: hidden;
        cursor: pointer;
    }

    .image-load {
        float: left;
        margin-left: 50px;
        line-height: 150px;
    }

    .el-tag + .el-tag {
        margin-left: 10px;
    }

    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
    }

    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }
</style>
