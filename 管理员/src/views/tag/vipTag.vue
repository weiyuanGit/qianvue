<template>
	<div>
		<el-row class="title-box">
			信息信息表付个
		</el-row>
		<el-row class="content-box">
			<el-row>
				<el-col :span="8">
					<el-form label-width="80px">
						<el-form-item label="状态">
							<el-select v-model="searchData.status" placeholder="请选择状态">
								<el-option v-for="(item,index) in tagstatus" :key="index" :label="item.name" :value="item.value"></el-option>
							</el-select>
						</el-form-item>
					</el-form>
				</el-col>
			</el-row>
			<el-row>
				<el-button type="primary" @click="searchBtn">查询</el-button>
				<el-button type="primary" @click="getContentsBtn">默认列表</el-button>
			</el-row>
			<el-popover placement="right" width="400" trigger="click">
				<el-col :span="20">
					<el-input placeholder="请输入标签名称" v-model="tagTitle" style="display: inline-block;width: 400px"></el-input>
					<el-button @click="subBtn">提交</el-button>
				</el-col>
				<el-button slot="reference">添加vip页标签</el-button>
			</el-popover>
		</el-row>
		<el-row class="table-box">
			<el-table :data="tableData" border style="width: 100%">
				<el-table-column prop="name" label="名称" width="400">
				</el-table-column>
				<el-table-column prop="status" label="状态" :formatter="tagStatusFliter">
				</el-table-column>
				<el-table-column prop="sortSize" label="排序">
				</el-table-column>
				<el-table-column label="操作" width="400">
					<template slot-scope="scope">
						<el-button @click="closeBtn(scope.row)" type="text" size="small" v-if="scope.row.status==1">禁用</el-button>
						<el-button @click="closeBtn(scope.row)" type="text" size="small" v-if="scope.row.status==0">启用</el-button>
						<el-button @click="updateBtn(scope.row)" type="text" size="small">设置排序</el-button>
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
		name: "homeTag",
		data() {
			return {
				tagTitle: '',
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
				tagstatus: this.$constData.tagstatus,
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
			tagStatusFliter(row, col, val) {
				let tagstatus = this.tagstatus
				for (let i = 0; i < tagstatus.length; i++) {
					if (tagstatus[i].value == val) {
						return tagstatus[i].name
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
			/*获取vip页标签列表*/
			getContents(cnt) {
				this.$api.getContentTag(cnt, (res) => {
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
				//获取列表
				let cnt = {
					moduleId: this.$constData.module,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
			/* 查询数据*/
			searchBtn() {
				this.page = 1
				let cnt = {
					moduleId: this.$constData.module,
					group: 'vip',
					status: this.searchData.status,
					count: this.count,
					offset: (this.page - 1) * this.count
				}
				this.getContents(cnt)
			},
			/* 禁用/启用标签*/
			closeBtn(info) {
				this.$confirm('是否继续?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async () => {
					let cnt = {
						moduleId: this.$constData.module,
						group: 'vip',
						name: info.name,
						status: 0,
					}
					if (info.status == 1) {
						cnt.status = 0
					} else if (info.status == 0) {
						cnt.status = 1
					}
					this.$api.editteContentTag(cnt, (res) => {
						if (res.data.rc == this.$util.RC.SUCCESS) {
							this.$message({
								type: 'success',
								message: '成功!'
							});
						} else {
							this.$message({
								type: 'error',
								message: '操作失败!'
							});
						}
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消'
					});
				});
			},
			/* 添加标签*/
			subBtn() {
				let cnt = {
					moduleId: this.$constData.module, // Long 模板编号
					group: 'VIP', // String 分组编号
					name: this.tagTitle,
				}
				this.$api.createContentTag(cnt, (res) => {
					if (res.data.rc == this.$util.RC.SUCCESS) {
						this.$message({
							type: 'success',
							message: '成功!'
						});
					} else {
						this.$message({
							type: 'error',
							message: '操作失败!'
						});
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
					moduleId: this.$constData.module,
					group: 'VIP',
					count: this.count,
					offset: (this.page - 1) * this.count,
				}
				this.getContents(cnt)
			},
			updateBtn(info) {
				this.$prompt('请输入排序大小,数值越大越靠前', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then(({
					value
				}) => {
					let cnt = {
						moduleId: 1170,
						group: 'VIP',
						name: info.name,
						sort: value
					}
					this.$api.editteContentTag(cnt, (res) => {
						if (res.data.rc == this.$util.RC.SUCCESS) {
							this.$message({
								type: 'success',
								message: '成功!'
							});
						} else {
							this.$message({
								type: 'error',
								message: '操作失败!'
							});
						}
						let cnt = {
							moduleId: this.$constData.module,
							group: 'VIP',
							count: this.count,
							offset: (this.page - 1) * this.count,
						}
						this.getContents(cnt)
					})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '取消输入'
					});
				});
			}
		},
		mounted() {
			//获取vip页标签列表
			let cnt = {
				moduleId: this.$constData.module,
				group: 'VIP',
				count: this.count,
				offset: (this.page - 1) * this.count,
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
