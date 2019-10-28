<template>
	<div>
		<el-row class="row-box">
			<el-col :span="24">
				{{taskInfo.title}}
			</el-col>
		</el-row>

		<el-col :span="24" class="row-box1" style="font-size: 18px" v-if="taskInfo.type == 1">
			<div style="font-weight: bold;font-size:22px">任务要求：</div>
			<div v-html="text.text"></div>
		</el-col>
		<el-col :span="24" style="font-size: 18px" v-if="taskInfo.type == 4">
			<p>目的：<span v-for="(item,index) in  text.purpose" :key="index">{{item.name}}</span></p>
			<p>对象：<span v-for="(item,index) in  text.obj" :key="index">{{item.name}}</span></p>
			<p>年龄：<span>{{text.age}}</span></p>
			<p>地址：<span>{{text.address}}</span></p>
			<p>电话：<span>{{text.tel}}</span></p>
		</el-col>
		<el-col :span="24" class="row-box1" style="font-size: 18px" v-if="taskInfo.type == 0">
			<div style="font-weight: bold;font-size:22px">联系电话：</div>
			<div v-html="text.tel"></div>
		</el-col>
		<el-col :span="24" style="font-size: 18px">
			<p>创建时间:{{this.timeFliter(taskInfo.createTime)}}</p>
		</el-col>
		<el-col :span="24" style="font-size: 18px">
			<p>创建者:{{taskInfo.upUserId}}</p>
		</el-col>
		<el-col :span="24" style="font-size: 18px">
			<p>类型:{{this.typeFliter(taskInfo.type)}}</p>
		</el-col>

		<el-col :span="24" style="text-align: center">
			<el-button style="padding: 1em 4em" type="primary" @click="changeBtn(true)">审核通过</el-button>
			<el-button style="padding: 1em 4em" type="info" @click="changeBtn(false)">审核不通过</el-button>
		</el-col>
	</div>
</template>

<script>
	export default {
		name: "taskInfo",
		data() {
			return {
				taskInfo: {},
				text: {},
				showTaskType: this.$constData.templateType,
			}
		},
		methods: {
			typeFliter(val) {
				let showTaskType = this.showTaskType
				for (let i = 0; i < showTaskType.length; i++) {
					if (showTaskType[i].value == val) {
						return showTaskType[i].name
					}
				}
			},
			timeFliter(val) {
				let timer = new Date(val)
				let dataTime = timer.toLocaleDateString() + ' ' + timer.toLocaleTimeString('chinese', {
					hour12: false
				})
				return dataTime
			},
			goBack() {
				this.$router.go(-1)
			},
			changeBtn(e) {
				let taskInfo = this.taskInfo
				let cnt = {
					id: taskInfo.id, // Long 任务id
					bool: e, // Boolean 是否通过
				}
				this.$api.auditTask(cnt, (res => {
					if (res.data.rc == this.$util.RC.SUCCESS) {
						this.$message({
							message: '审核完成',
							type: 'success'
						})
						this.goBack()
					} else {
						this.$message({
							message: res.data.c,
							type: 'warning'
						})
					}
				}))
			},
		},
		mounted() {
			this.taskInfo = this.$route.params.info
			this.text = JSON.parse(this.taskInfo.detail)
		}
	}
</script>

<style lang="scss" scoped>
	.row-box {
		background: #fff;
		padding: 15px;
		font-size: 3rem;
		color: #666;
		border-left: 4px solid #67C23A;
	}

	.row-box1 {
		margin-top: 40px;
		text-align: center;
		padding-bottom: 20px;
	}
</style>
