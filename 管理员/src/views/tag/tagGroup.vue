<template>
	<div>
		<el-row class="title-box">
			创建标签分组
		</el-row>
		<el-row style="padding: 5px">
			<el-col :span="2" style="min-height: 20px"></el-col>
			<el-col :span="20">
				<span class="title-box"> 组名称：</span>
				<el-input placeholder="请输入分组名称" v-model="tagGroupName" clearable></el-input>
			</el-col>
		</el-row>
		<el-row>
			<el-col :span="2" style="min-height: 20px"></el-col>
			<el-col :span="20">
				<span class="title-box"> 备注：</span>
				<el-input placeholder="请输入备注" v-model="remark" clearable></el-input>
			</el-col>
		</el-row>
		<el-row style="margin-top: 20px">
			<el-button type="primary" @click="subBtn" style="margin: 0 auto;display: block;padding: 15px 50px">提交
			</el-button>
		</el-row>

		<el-row class="table-box">
			<el-table :data="tableData" border style="width: 100%">
				<el-table-column prop="groupName" label="分组名称" width="400">
				</el-table-column>
				<el-table-column prop="remark" label="备注">
				</el-table-column>
			</el-table>
		</el-row>
		<el-row style="height: 80px;">
			<el-col :span="24">
				当前页数：{{page}}
				<el-button type="primary" size="small" :disabled="page==1" @click="changePage(page-1)">上一页</el-button>
				<el-button type="primary" size="small" :disabled="pageOver" @click="changePage(page+1)">下一页</el-button>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	export default {
		name: "tagGroup",
		data() {
			return {
				count: 10,
				page: 1,
				tagGroupName: '',
				remark: '',
				tableData:[],
				pageOver: true,
			}
		},

		methods: {
			subBtn() {
				if (this.tagGroupName == '') {
					this.$message({
						message: '请输入分组名称',
						type: 'error'
					});
					return
				}
				let cnt = {
					moduleId: this.$constData.module,
					group: this.tagGroupName,
					remark: this.remark,
				};
				this.$api.createContentTagGroup(cnt, (res => {
					if (res.data.rc == this.$util.RC.SUCCESS) {
						this.$message({
							message: '添加成功',
							type: 'success'
						});
						this.$router.push('/homeTag')
					} else {
						this.$message({
							message: res.data.rm,
							type: 'warning'
						});
						this.imgList = []
					}
				}))
			},
			getContents(cnt){
				this.$api.getContentTagGroup(cnt, (res) => {
					if (res.data.rc == this.$util.RC.SUCCESS) {
						this.tableData = this.$util.tryParseJson(res.data.c)
					} else {
						this.tableData = []
					}
					if (this.tableData.length < this.count) {
						this.pageOver = true
					} else {
						this.pageOver = false
					}
				})
			},
			changePage(page) {
				this.page = page
				//获取内容列表
				let cnt = {
					moduleId: this.$constData.module,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
		},
		mounted() {
			let cnt = {
				moduleId: this.$constData.module, // Long 模板编号
				count: this.count,
				offset: (this.page - 1) * this.count
			}
			this.getContents(cnt)
		}
	}
</script>

<style scoped lang="scss">
	.title-box {
		font-size: 16px;
		color: #666;
		line-height: 50px;
		padding-left: 15px;
	}

	.content-box {
		margin-top: 20px;
		padding: 20px;
	}

	.table-box {
		padding: 20px;

	}
</style>
