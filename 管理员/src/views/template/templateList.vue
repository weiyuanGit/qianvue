<template>
	<div>
		<el-row class="title-box">
			信息信息表付个
		</el-row>
		<el-row class="content-box">
			<el-row>
				<el-col :span="8">
					<el-form label-width="80px">
						<el-form-item label="任务类型">
							<el-select v-model="searchData.status" placeholder="请选择">
								<el-option v-for="(item,index) in TaskType" :key="index" :label="item.name" :value="item.value"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
				</el-col>
				<el-col :span="8">
					<el-form label-width="80px">
						<el-form-item label="模板类型">
							<el-select v-model="searchData.type" placeholder="请选择">
								<el-option v-for="(item,index) in templateType" :key="index" :label="item.name" :value="item.value"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
			<el-row>
				<el-button type="primary" @click="searchBtn">查询</el-button>
				<el-button type="primary" @click="getContentsBtn">默认列表</el-button>
				<el-button type="primary" style="float: right;margin-right: 50px" @click="addTemplate">添加模板</el-button>
			</el-row>
		</el-row>
		<el-row class="table-box">
			<el-table :data="tableData" border style="width: 100%">
				<el-table-column prop="name" label="名称" width="400">
				</el-table-column>
				<el-table-column prop="status" label="状态" :formatter="statusFliter">
				</el-table-column>
				<el-table-column prop="taskType" label="任务类型" :formatter="taskFliter">
				</el-table-column>
				<el-table-column prop="tempType" label="模板类型" :formatter="templateTypeFliter">
				</el-table-column>
				<el-table-column label="操作" width="100">
					<template slot-scope="scope">
						<el-button @click="delBtn(scope.row)" type="text" size="small">删除</el-button>
					</template>
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
		name: "contentList",
		data() {
			return {
				tableData: [],
				count: 10,
				page: 1,
				pageOver: true,
				searchData: {
					type: '',
					status: '',
					power: '',
					tags: '',
				},
				typeList: this.$constData.typeList,
				statusList: this.$constData.statusList,
				powerList: this.$constData.powerList,
				generalStatus: this.$constData.generalStatus,
				TaskType: this.$constData.TaskType,
				templateType: this.$constData.templateType,
			}
		},
		methods: {
			/** 过滤器*/
			typeFliter(row, col, val) {
				let typeList = this.typeList
				for (let i = 0; i < typeList.length; i++) {
					if (typeList[i].value == val) {
						return typeList[i].name
					}
				}
			},
			timeFliter(row, col, val) {
				let timer = new Date(val)
				let dataTime = timer.toLocaleDateString() + ' ' + timer.toLocaleTimeString('chinese', {
					hour12: false
				})
				return dataTime
			},
			statusFliter(row, col, val) {
				let generalStatus = this.generalStatus
				for (let i = 0; i < generalStatus.length; i++) {
					if (generalStatus[i].value == val) {
						return generalStatus[i].name
					}
				}
			},
			powerFliter(row, col, val) {
				let powerList = this.powerList
				for (let i = 0; i < powerList.length; i++) {
					if (powerList[i].value == val) {
						return powerList[i].name
					}
				}
			},
			taskFliter(row, col, val) {
				let TaskType = this.TaskType
				for (let i = 0; i < TaskType.length; i++) {
					if (TaskType[i].value == val) {
						return TaskType[i].name
					}
				}
			},
			templateTypeFliter(row, col, val) {
				let templateType = this.templateType
				for (let i = 0; i < templateType.length; i++) {
					if (templateType[i].value == val) {
						return templateType[i].name
					}
				}
			},
			/*获取内容列表*/
			getContents(cnt) {
				this.$api.getTemplates(cnt, (res) => {
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
			/* 分页*/
			changePage(page) {
				this.page = page
				//获取内容列表
				let cnt = {
					module: this.$constData.module,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
			/* 查询数据*/
			searchBtn() {
				this.page = 1
				let cnt = {
					module: this.$constData.module,
					type: this.searchData.status,
					tempType: this.searchData.type,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
			/* 删除模板*/
			delBtn(info) {
				this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async () => {
					let cnt = {
						id: info.id,
					}
					this.$api.delemplates(cnt, (res) => {
						if (res.data.rc == this.$util.RC.SUCCESS) {
							this.$message({
								type: 'success',
								message: '删除成功!'
							});
						} else {
							this.$message({
								type: 'error',
								message: '删除失败!'
							});
						}
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});
			},
			//添加模板
			addTemplate() {
				this.$router.push('/addTemplate')
			},
			//查看 详情
			infoBtn(info) {
				this.$router.push({
					path: '/svipInfoList',
					name: 'svipInfoList',
					params: {
						info: info
					}
				})
			},
			//获取默认列表
			getContentsBtn() {
				this.searchData.type = ''
				this.searchData.status = ''
				this.searchData.power = ''
				this.searchData.tags = ''
				this.page = 1
				let cnt = {
					module: this.$constData.module,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
		},
		mounted() {
			//获取内容列表
			let cnt = {
				module: this.$constData.module,
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
