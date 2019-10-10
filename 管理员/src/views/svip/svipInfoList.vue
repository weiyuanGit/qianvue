<template>
    <div>
        <el-row class="row-box">
            <el-col :span="24">
                {{svipInfo.title}}
            </el-col>
        </el-row>

        <el-row :gutter="20" style="padding: 20px">
            <el-col :span="1" style="min-height: 2px;padding: 0"></el-col>
            <el-col :span="23" style="padding: 0">
                <span>状态：</span>
                <el-select v-model="status" placeholder="请选择" style="margin-right: 10px;">
                    <el-option
                            v-for="(item,index) in statusList"
                            :key="index"
                            :label="item.name"
                            :value="item.value">
                    </el-option>
                </el-select>
                <span>是否付费：</span>
                <el-select v-model="paid" placeholder="请选择" style="margin-right: 10px;">
                    <el-option
                            v-for="(item,index) in paidList"
                            :key="index"
                            :label="item.name"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button type="primary" @click="showType">搜索</el-button>
                <el-button type="primary" style="float: right;margin-right: 50px" @click="addContent">添加图文</el-button>
                <el-button type="primary" style="float: right;" @click="addVideo">添加视频</el-button>

            </el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="1" style="min-height: 2px"></el-col>
            <el-col :span="22" style="border: 1px solid #EBEEF5;padding: 0;border-bottom: none">
                <el-table
                        :data="tableData"
                        stripe
                        style="width: 100%">
                    <el-table-column
                            prop="id"
                            label="内容ID"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="title"
                            label="标题"
                            width="auto">
                    </el-table-column>
                    <!--              fixed="right"-->
                    <el-table-column
                            label="操作"
                            width="100">
                        <template slot-scope="scope">
                            <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                            <el-button @click="delClick(scope.row)" type="text" size="small" style="color: #f44">删除
                            </el-button>
                            <!--            <el-button type="text" size="small">编辑</el-button>-->
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "svipInfoList",
        data() {
            return {

                svipInfo: {},
                tableData: [],

                statusList: [
                    {
                        name: '已发布',
                        value: 4,
                    },
                    {
                        name: '发布失败',
                        value: 5,
                    }
                ],
                status: 4,

                paidList: [
                    {
                        name: '免费',
                        value: 0
                    },
                    {
                        name: '付费',
                        value: 1
                    }
                ],
                paid: 0,

                count: 10,
                offset: 0,

            }
        },
        methods: {
            //查看详情
            handleClick(row) {
                this.$router.push({
                    name: `contentInfo`,
                    params: {
                        info: row
                    }
                })
            },

            //删除
            delClick(row) {
                console.log(row)
                let that = this
                let cnt = {
                    userId: row.upUserId, // Long 用户编号
                    _id: row._id, // String 片区编号
                    contentId: row.id, // Long 内容编号
                };
                console.log(cnt)
                that.$api.delContentById(cnt, (res => {
                    if (res.data.rc == that.$util.RC.SUCCESS) {
                        that.$message({
                            message: '已删除',
                            type: 'success'
                        });
                        that.getContents()
                    } else {
                        this.$message({
                            message: res.data.c,
                            type: 'warning'
                        });
                    }
                }))
            },

            //跳转至添加内容页面
            addContent(){
                let row = this.svipInfo
                this.$router.push({
                    name: `addContent`,
                    params: {
                        info: row
                    }
                })
            },
            addVideo(){
                let row = this.svipInfo
                this.$router.push({
                    name: `addVideoContent`,
                    params: {
                        info: row
                    }
                })
            },

            //根据条件查询
            showType() {
                console.log(this.type)
                let that = this
                let cnt = {
                    module: 'kkqt', // String 所属模块
                    count: that.count, // Integer
                    offset: that.offset,
                };
                if (that.status !== '') {
                    cnt.status = that.status
                }
                if (that.type !== '') {
                    cnt.type = that.type
                }
                if (that.tag !== '' && that.tag != '全部') {
                    cnt.tags = that.tag
                }
                this.getChannel(cnt)
            },

            getContentByChannelId(cnt) {
                let that = this
                this.$api.getContentByChannelId(cnt, (res => {
                    if (res.data.rc == that.$util.RC.SUCCESS) {
                        this.tableData = JSON.parse(res.data.c).list
                    }
                }))
            },
        },
        mounted() {
            console.log(this.$route.params.info)
            this.svipInfo = this.$route.params.info
            let cnt = {
                module: 'kkqt', // String 隶属
                channelId: this.svipInfo.id, // Long 专栏id
                // status: 0, // Byte 专栏状态
                paid: this.paid, // Byte <选填> 是否付费
                count: this.count, // Integer
                offset: this.offset, // Integer
            }
            this.getContentByChannelId(cnt)
        }
    }
</script>

<style lang="scss" scoped>
    .row-box {
        background: #fff;
        padding: 15px;
        font-size: 1.6rem;
        color: #666;
        border-left: 4px solid #67C23A;

    }
</style>
