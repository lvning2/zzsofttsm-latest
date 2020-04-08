var $, form, myid, myname,myAttendanceId;
var WorkArr = [];
//var ipAddr = 'http://192.168.15.135:8082/';
var ipAddr = '/';
var Uid;

var existEquipmentNumber;	// 用于判断是否 编号是否重复。

var table;
layui.use('table',function () {
	table = layui.table;
})

// Date format
if (!Date.prototype.format) {
	Date.prototype.format = function(fmt) {
		var o = {
			"M+": this.getMonth() + 1, //月份
			"d+": this.getDate(), //日
			"h+": this.getHours(), //小时
			"m+": this.getMinutes(), //分
			"s+": this.getSeconds(), //秒
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度
			"S": this.getMilliseconds() //毫秒
		};
		if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for (var k in o)
			if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[
				k]).substr(("" + o[k]).length)));
		return fmt;
	}
}

// 得到最近过去7天的时间，倒序
function getSevenDays() {
	var nowTime = new Date().getTime();
	var ms = 24 * 3600 * 1000;

	var date = new Date();
	var dateArr = [];
	for (let i = 0; i < 7; ++i) {
		date.setTime(nowTime - i * ms);
		dateArr.push(date.format("yyyy-MM-dd"));
	}

	return dateArr;
}


//获取当前时间
var date = new Date();
var seperator1 = "-";
var year = date.getFullYear();
var month = date.getMonth() + 1;
var strDate = date.getDate();
if (month >= 1 && month <= 9) {
	month = "0" + month;
}
if (strDate >= 0 && strDate <= 9) {
	strDate = "0" + strDate;
}
var currentdate = year + seperator1 + month + seperator1 + strDate;


layui.use(['form', 'tree', 'layer', 'laydate'], function() {
	form = layui.form;
	var tree = layui.tree;
	var layer = layui.layer;
	var element = layui.element;
	var laydate = layui.laydate;
	$ = layui.$ //重点处

	myid = localStorage.getItem("id");
	myname = localStorage.getItem("name");
	myAttendanceId=localStorage.getItem("attendanceId");

	$("#user_show").text(myname);
	$("#userShow").text(myname + '的日志:');
	showUserWork(new Date().format("yyyy-MM-dd"), myid);


	// 初始化树控件
	$.ajax({
		url: ipAddr + 'other/getGroupUsers',
		type: "get",
		success: function(data) {
			var showData = [];

			// 组名
			for (var iter of data) {
				if (iter.pId === '0') {
					showData.push({
						id: iter.id,
						pId: '0',
						title: iter.name,
						children: []
					});
				}
			} // 结束组名

			// 组员
			for (var zgroup of showData) {
				for (var zmember of data) {
					if (zmember.pId == zgroup.id) {
						zgroup.children.push({
							id: zmember.id,
							pId: zmember.pId,
							title: zmember.name,
						});
					}
				}
			}
			// 结束组员

			// 填充树
			tree.render({
				elem: '#memberTree',
				onlyIconControl: true, //是否仅允许节点左侧图标控制展开收缩
				data: showData,
				click: onClickTree,
			});
		} // 结束 success
	}); // end 初始化树控件




	//监听查看组日志的单选按钮,日志内容根据按钮切换
	form.on("radio(day)", function(data) {

		//获取单选按钮值,判断时间
		var day = data.value;
		if (day == 'today') {
			time = new Date().format("yyyy-MM-dd");
		} else if (day == 'yesterday') {
			time = (new Date(new Date().getTime() - 24 * 60 * 60 * 1000)).format("yyyy-MM-dd");
		} else if(day=="beforeyesterday"){
			time = (new Date(new Date().getTime() - 2 * 24 * 60 * 60 * 1000)).format("yyyy-MM-dd");
		}else{
			time=$("#test1").val();
		}
		$.ajax({
			url: ipAddr + 'log/getGroup',
			data: {
				"gid": Uid,
				"time": time
			},
			type: 'post',
			success: function(data) {
				if (data.code == 0) {

					var temHtml = getGroupWork(data.data);
					$("#dailyContext").empty();
					$("#dailyContext").append(temHtml);
				}
			}
		});

	});

	//日期与时间选择
	laydate.render({
		elem: '#test1',
		value: currentdate,
		//监听日期被切换
		done: function(value, date) {


			if($("input[name='day']").get(3).checked==true){

				time=$("#test1").val();

				$.ajax({
					url: ipAddr + 'log/getGroup',
					data: {
						"gid": Uid,
						"time": time
					},
					type: 'post',
					success: function(data) {
						if (data.code == 0) {

							var temHtml = getGroupWork(data.data);
							$("#dailyContext").empty();
							$("#dailyContext").append(temHtml);
						}
					}
				});
			}

		}
	});

}) // end use

function onclickAdd(obj) {
	var tmpDate = $(obj).data("date");
	var tmpID = $(obj).data("id");

	var tmpYestarday = "";
	var tmpToday = "";

	var firstTime = new Date().format("yyyy-MM-dd");
	// if (WorkArr.length > 0) {
	// 	firstTime = WorkArr[0].time;
	// }

	for (let iter of WorkArr) {
		if (iter.uid == tmpID && iter.time == tmpDate) {
			tmpYestarday = iter.yesterdayLog;
			tmpToday = iter.todayLog;
		}
	}

	layer.open({
		area: ['800px', '600px'],
		type: 1,
		title: "添加日志",
		content: $("#addform"),
		btn: ['引用昨天', '提交', '重置'],
		btn1: function(index, layero) {

			var date = new Date(tmpDate);
			date.setDate(date.getDate() - 1);
			var time = date.format("yyyy-MM-dd");

			$.ajax({
				url: ipAddr + 'log/getOne',
				data: {
					"time": time,
					"uid": tmpID
				},
				dataType: 'json',
				type: 'post',
				success: function(data) {

					if (data.data.length == 0) {
						$("#yesterdayContent").val("什么也没有写");
						$("#todayContent").val("什么也没有写");
					} else {
						$("#yesterdayContent").val(data.data[0].yesterdayLog);
						$("#todayContent").val(data.data[0].todayLog);
					}

				},
				//请求失败，包含具体的错误信息
				error: function(e) {
					console.log(e.status);
					console.log(e.responseText);
				}
			});



		},
		btn2: function(index, layero) {
				var data = form.val('add-form');
				$.ajax({
					url: ipAddr + 'log/create',
					data: {
						"time": tmpDate,
						"uid": tmpID,
						"yesterdayLog": data.yestWork,
						"todayLog": data.todayWork,
					},
					type:'post',
					success: function(data) {
						showUserWork(firstTime, tmpID);
						layer.closeAll();
						hideEdit();
					},
					//请求失败，包含具体的错误信息
					error: function(e) {
						console.log(e.status);
						console.log(e.responseText);
					}
				});
		},
		btn3: function(index, layero) {
			//重置内容
			$("#yesterdayContent").val("");
			$("#todayContent").val("");
			return false;
		},
		cancel: function(layero, index) {
			layer.closeAll();
			hideEdit();
		},
		success: function(layero, index) {
			form.val('add-form', {
				"date": tmpDate,
				"yestWork": tmpYestarday,
				"todayWork": tmpToday,
			});
			form.render();
		}
	});
};

function getWorkList(workstr) {
	if (!workstr) {
		return '你什么也没有写';
	}
	if (workstr.length === 0) {
		return '<li>你什么也没有写</li>';
	}

	let temArr = workstr.split('\n');
	var ret = "";
	for (let iter of temArr) {
		ret = ret + '<li>' + iter + '</li>';
	}
	return ret;
}

function getGroupWork(arr) {
	var ret = "";
	for (var iter of arr) {
		let workStr = "";
		if (!iter.yesterdayLog && !iter.todayLog) {
			workStr = '<p style="color: red;"> 没有填写 </p>';
		} else {
			if (!iter.yesterdayLog) {
				workStr += '<p>昨天工作： 没有填写</p>';
			} else {
				let yetsWork = getWorkList(iter.yesterdayLog);
				workStr += '<p>昨天工作</p>' +
					'<ul>' + yetsWork +
					'</ul>';
			}

			if (!iter.todayLog) {
				workStr += '<p>今天计划： 没有填写</p>';
			} else {
				let todayWork = getWorkList(iter.todayLog);
				workStr += '<p>今天工作</p>' +
					'<ul>' + todayWork +
					'</ul>';
			}
		}

		ret += '<li class="layui-timeline-item">' +
			'<i class="layui-icon layui-timeline-axis"></i>' +
			'<div class="layui-timeline-content layui-text">' +
			'<h3 class="layui-timeline-title">' + iter.name +
			'</h3>' +
			'<p>' + workStr + '</p>' +
			'</div>' +
			'</li>';
	}

	return ret;
}

function getDateShow(daystr) {
	let arr = daystr.split('-');
	if (arr.length != 3) {
		return '';
	}

	let tmpDate = new Date();
	tmpDate.setFullYear(parseInt(arr[0]), parseInt(arr[1] - 1), parseInt(arr[2]));

	let i = tmpDate.getDay();
	let wkd = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
	return daystr + ' ' + wkd[i];
}

function getDayWorkHtml(uid, daystr, arrs) {
	let btnStr = '';
	if (myid == uid) {
		btnStr = '<div type="button" class="layui-btn-group layui-btn layui-btn-primary layui-btn-sm" ' +
			'data-id=' + uid + ' data-date=' + daystr + ' onclick=onclickAdd(this) ' +
			'style="border: none; background: transparent;">' +
			'<i class="layui-icon">&#xe642;</i>' +
			'</div>';
	}

	let showDay = getDateShow(daystr);
	for (let i = 0, l = arrs.length; i < l; ++i) {
		let iter = arrs[i];
		if (iter.time === daystr) {
			let yetsWork = getWorkList(iter.yesterdayLog);
			let todayWork = getWorkList(iter.todayLog);
			return '<li class="layui-timeline-item">' +
				'<i class="layui-icon layui-timeline-axis"></i>' +
				'<div class="layui-timeline-content layui-text">' +
				'<h3 class="layui-timeline-title">' + showDay + '<span>' +
				btnStr +
				'</span></h3>' +
				'<p>' +
				'<p>昨天工作</p>' +
				'<ul>' + yetsWork +
				'</ul>' +
				'<p>今天计划</p>' +
				'<ul>' + todayWork +
				'</ul>' +
				'</p>' +
				'</div>' +
				'</li>';
		}
	}

	return '<li class="layui-timeline-item">' +
		'<i class="layui-icon layui-timeline-axis"></i>' +
		'<div class="layui-timeline-content layui-text">' +
		'<h3 class="layui-timeline-title">' + showDay + '<span>' +
		btnStr +
		'</span></h3>' +
		'<p style="color: red;">什么也没有写' +
		'</p>' +
		'</div>' +
		'</li>';
}

function showUserWork(strTime, uid) {
	$('#daySelect').hide();
	$("#test1").hide();
	// 查个人的工作
	$.ajax({
		url: ipAddr + 'log/getOneOfLastSevenDays',
		data: {
			"uid": uid,
			"time": strTime
		},
		type: 'post',
		success: function(data) {
			if (data.code == 0) {
				WorkArr = data.data;
				$("#dailyContext").empty();
				var dateArr = getSevenDays();
				for (let i = 0; i < dateArr.length; ++i) {
					var temHtml = getDayWorkHtml(uid, dateArr[i], data.data);
					$("#dailyContext").append(temHtml);
				}
			}
		}
	});
}

function onClickTree(obj) {
	$("#userShow").text(obj.data.title + '的日志:');
	$("#dailyContext").empty();

	let uid = obj.data.id.substr(1);
	let pid = obj.data.pId;
	// 查组的工作
	if (pid === "0") {
		$('#daySelect').show();
		$('#test1').show();

		Uid = uid;

		//获取单选按钮值,判断时间
		var val = $("input[name='day']:checked").val();
		if (val == 'today') {
			time = new Date().format("yyyy-MM-dd");
		} else if (val == 'yesterday') {
			time = (new Date(new Date().getTime() - 24 * 60 * 60 * 1000)).format("yyyy-MM-dd");
		}  else if(day=="beforeyesterday"){
			time = (new Date(new Date().getTime() - 2 * 24 * 60 * 60 * 1000)).format("yyyy-MM-dd");
		}else{
			time=$("#test1").val();
		}

		$.ajax({
			url: ipAddr + 'log/getGroup',
			data: {
				"gid": uid,
				"time": time
			},
			type: 'post',
			success: function(data) {
				if (data.code == 0) {

					var temHtml = getGroupWork(data.data);
					$("#dailyContext").empty();
					$("#dailyContext").append(temHtml);
				}
			}
		});
	} else {
		showUserWork(new Date().format("yyyy-MM-dd"), uid);
	}
}


//退出登录
function user_exit() {
	localStorage.removeItem("upwd");
	localStorage.removeItem("attendanceId");
	window.location.href = "login.html";
}


//显示考勤
var attendanceData;
function show_worktime() {
	$("#dailyContext").empty();
	$("#dailyContext").append('<table class="layui-hide" id="tAttendance" lay-filter="tAttendance"></table>');

	// initListTable()

	// 初始化树控件
	$.ajax({
		url: ipAddr + 'attend/getArrAndRetOfMonth',
		data: {
			"attendanceId": myAttendanceId,
			"dateStr": "2020-03"
		},
		type: "get",
		success: function(data) {
			attendanceData = data.data;
			table.render({
				elem: '#tAttendance',
				totalRow: false,
				toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
				defaultToolbar: ['exports', 'print'], // 头部右侧工具
				// cellMinWidth: 80,
				cols: [
					[{
						field: 'day',
						title: '日期',
						sort: true
					},
						{
							field: 'arrive',
							title: '上班'
						},
						{
							field: 'retreat',
							title: '下班'
						},
						{
							field: 'late',
							title: '迟到'
						},
						{
							field: 'bigLate',
							title: '大迟到'
						},
						{
							field: 'overtime',
							title: '加班',
							totalRowText: 153
						},
						{
							field: 'leave',
							title: '请假',
							totalRowText: 6
						},
						{
							field: 'holiday',
							title: '节假日',
						},
					]
				],
				data: attendanceData
			});

			layui.table.on('toolbar(tAttendance)', function(obj) {
				switch (obj.event) {
					case 'cal':
						// layer.msg('开始计算');
				};
			});

		}, // 结束 success
		error: function(jqXHR, textStatus, errorThrown) {
			layui.message("失败");
		}
	}); // end 初始化树控件

}



// 电脑设备信息
var equipmentData;

function hideEdit() {
	$("#addform").hide();
	$("#editE").hide();
}

function initEquipmentTable() {
	// $("#dailyContext").empty();
	// $("#dailyContext").append('<table class="layui-hide" id="tEquipment"  lay-filter="tEquipment"></table><table class="layui-hide" id="tEquipmentAll"  lay-filter="tEquipmentAll"></table>');
	$.ajax({
		url: ipAddr + 'computer/getByUid',
		data: {
			"uid": myid,
		},
		type: "get",
		success: function (data) {
			table.render({
				id: 'id',
				elem: '#tEquipment',
				totalRow: false,
				// toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
				// defaultToolbar: ['exports', 'print'], // 头部右侧工具
				cols: [
					[
						{type: 'radio'},
						{
							field: 'equipmentNumber',
							title: '设备编号',
							event: 'setContent'
						},
						{
							field: 'equipmentType',
							title: '设备类型',
							event: 'setContent'
						},
						{
							field: 'personName',
							title: '责任人',
							event: 'setContent'
						},
						{
							field: 'lockNumber',
							title: '锁号',
							event: 'setContent'
						}
					]
				],
				page: true,
				data: data.data,
				toolbar: '#toolbarEquipment'   //开启自定义工具行，指向自定义工具栏模板选择器

			});		// 结束layui.table.render
		}

	})		// 结束ajax



}



function show_equipment(){
	$("#dailyContext").empty();
	$("#dailyContext").append('<table class="layui-hide" id="tEquipment"  lay-filter="tEquipment"></table>');
// <table class="layui-hide" id="tEquipmentAll"  lay-filter="tEquipmentAll"></table>
	layui.use('laydate', function(){
		var laydate = layui.laydate;
		//执行一个laydate实例
		laydate.render({
			elem: '#date1' //指定元素
			,trigger: 'click'
		});

	});



	// 初始化树控件
	$.ajax({
		url: ipAddr + 'computer/getByUid',
		data: {
			"uid": myid,
		},
		type: "get",
		success: function (data) {
			equipmentData=data.data;
			let equipmentTable=initEquipmentTable();		// 初始化表格

			var SelectRadio='';		// 单选数据
			table.on('radio(tEquipment)',function (data) {
				SelectRadio=data;
			})

			layui.table.on('toolbar(tEquipment)', function(obj) {
				switch (obj.event) {
					case 'insertEquipment':
						//layer.msg('开始添加');
						// 添加设备信息弹窗开始
						layer.open({
							type: 1 //此处以iframe举例
							,title: "新增"
							,area: ['70%', '70%']
							,shade: 0
							,maxmin: true
							,content: $("#editE")
							//,content: ['/em.html']
							,btn: ['确定', '取消'] //只是为了演示
							,btn1: function(layero,index){

								if (existEquipmentNumber==1){
									return;
								}


								var data = form.val('example');
								if(data.hdCapacity1==''&&data.hdCapacity2!=''){
									data.hdCapacity=data.hdCapacity2;
								}else {

									data.hdCapacity=data.hdCapacity1+"+"+data.hdCapacity2;

								}
								data.computerUse='办公';
								data.computerUsage='在用';
								if(data.ipAddress===''){
									data.ipAddress='自动获取';
								}


								$.ajax({
									url:ipAddr+"computer/insertEquipment",
									data: data,
									type:'post',
									success:function (data) {
										if (data.code==0){

											initEquipmentTable();	// 初始化
											initListTable();

											$("#equipmentNumber").unbind();	// 移除事件
											layer.closeAll();
											hideEdit();
										}
									}	// success结束
								})	// ajax 结束
							}
							,btn2: function(){
								$("#equipmentNumber").unbind();	// 移除事件
								layer.closeAll();
								hideEdit();
							}
							,success: function(layero){		// 弹出成功后，数据表初始化
								form.val('example', {
									"id":null,
									"equipmentNumber":"",
									"equipmentType":"",
									"hdCapacity":"",
									"hdCapacity1":"",
									"hdCapacity2":"",
									"ipAddress":"",
									"osVersion":"",
									"personLiableId":myid,
									"personName":myname,
									"cpuId":"",
									"graphicsCardId":"",
									"memory":"",
									"lockNumber":"",
									"monitor":"",
									"remark":"",
									"isNetwork":"",
									"cpuName":"",
									"graphicsCardName":"",
									"hdSerialNumber":"",
									"macAddress":"",
									"osInstallTime":"",
									"brandModel":"",
									"setPosition":"研发一部",
									"useDepartment":"研发一部",
								});
								//添加 事件，判断设备编号是否重负
								$("#equipmentNumber").on('blur',function (obj) {


									if(this.value==''){
										existEquipmentNumber=1;
										$("#entext").text("不能为空")
										return;
									}else {
										existEquipmentNumber=0
										$("#entext").text("")

									}
									$.ajax({
										url:ipAddr+"computer/existEquipmentNumber",
										data:{
											"equipmentNumber":this.value
										},
										type:'get',
										success:function (data) {
											if (data.code===0){
												if (data.data===true){
													existEquipmentNumber=1;
													$("#entext").text("设备编号已经存在")
													//alert("设备编号已经存在")
												}else {
													existEquipmentNumber=0;
													$("#entext").text("")
												}
											}
										}
									})

								})

							},
							end:function () {
								$("#equipmentNumber").unbind();	// 窗口销毁 移除事件
								hideEdit();
							}
						});
						// 添加设备信息弹窗结束
						break;
					case 'deleteEquipment':
						if(!SelectRadio){
							layer.msg('请选择要删除的项');
							break;
						}
						layer.confirm('真的删除么', function(index){

							$.ajax({
								url:ipAddr+"computer/deleteEquipmentById",
								data:{
									"id":SelectRadio.data.id
								},
								type:'post',
								success:function (data) {
									if (data.code==0){
										SelectRadio.del();
										initListTable();
										layer.close(index);
										SelectRadio='';
									}
								}	// success结束
							})	// ajax 结束
						});

						//layer.msg('开始删除11');
						break;
					case 'export':
						window.open("/download/downloadEquipmentExcel");
						break;
				};
			});

		}	// 结束success

	})	//	结束ajax

	initListTable();

    // let table;
    layui.use('table',function () {				// 编辑弹框
		// table=layui.table;

		//监听行单击事件（双击事件为：rowDouble）
		table.on('tool(tEquipment)', function(obj){

			if (obj.event==='setContent'){
				// layer.alert(JSON.stringify(data), {
				// 	title: '当前行数据：'
				// });

				var tEquipmentData = obj.data;
				$.ajax({				// ajax开始，为表单赋值
					url:ipAddr+"computer/getEquipmentById",
					type:'get',
					data:{
						"id":tEquipmentData.id,
					},
					success:function(data){
						if(data.code==0){
							form.val('example', {
								"id":data.data.id,
								"equipmentNumber":data.data.equipmentNumber,
								"personLiableId":data.data.personLiableId,
								"personName":data.data.personName,
								"equipmentType":data.data.equipmentType,
								"isNetwork":data.data.isNetwork,
								"hdCapacity":data.data.hdCapacity,
								"hdCapacity1":data.data.hdCapacity1,
								"hdCapacity2":data.data.hdCapacity2,
								"cpuName":data.data.cpuName,
								"graphicsCardName":data.data.graphicsCardName,
								"memory":data.data.memory,
								"ipAddress":data.data.ipAddress,
								"hdSerialNumber":data.data.hdSerialNumber,
								"macAddress":data.data.macAddress,
								"osVersion":data.data.osVersion,
								"osInstallTime":data.data.osInstallTime,
								"brandModel":data.data.brandModel,
								"remark":data.data.remark,
								"lockNumber":data.data.lockNumber,
								"monitor":data.data.monitor,
								"setPosition":data.data.setPosition,
								"useDepartment":data.data.useDepartment,
								"uid":data.data.uid,
							});
						}
					}
				})			// ajax结束 为表单赋值结束，填充内容

				// 编辑设备信息弹窗开始
				layer.open({
					type: 1 //此处以iframe举例
					,title: tEquipmentData.equipmentNumber
					,area: ['70%', '70%']
					,shade: 0
					,maxmin: true
					// 用户名 姓名 年龄 电话 身份证 地址
					 ,content: $("#editE")
					//,content: ['/em.html']
					,btn: ['保存', '取消'] //只是为了演示
					,btn1: function(layero,index){
						var data = form.val('example');
						if (data.hdCapacity2){
							data.hdCapacity=data.hdCapacity1+"+"+data.hdCapacity2;
						}else {
							data.hdCapacity=data.hdCapacity1
						}

						data.computerUse='办公';
						data.computerUsage='在用';
						// alert(data);
						$.ajax({
							url:ipAddr+"computer/updateEquipmentById",
							data: data,
							type:'post',
							success:function (data) {
								if (data.code==0){
									initEquipmentTable();
									initListTable();
									layer.closeAll();
									hideEdit();
								}
							}	// success结束
						})	// ajax 结束

					}
					,btn2: function(){
						layer.closeAll();
						hideEdit();
					}
					,success: function(layero){

					}
				});

				// 编辑设备信息弹窗结束


			}
			//标注选中样式
			obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
		});

	});


}


layui.use(['form', 'layedit','upload'], function () {
	var form = layui.form
	form.on('select(cpuName_select)', function (data) {   //选择移交单位 赋值给input框
		$("#cpuName").val(data.value);
		$("#cpuName_select").next().find("dl").css({ "display": "none" });
		form.render();
	});

	form.on('select(graphicsCardName_select)', function (data) {   //选择移交单位 赋值给input框
		$("#graphicsCardName").val(data.value);
		$("#graphicsCardName").next().find("dl").css({ "display": "none" });
		form.render();
	});

	search1 = function () {
		var value = $("#cpuName").val();
		$("#cpuName_select").val(value);
		form.render();
		$("#cpuName_select").next().find("dl").css({ "display": "block" });
		var dl = $("#cpuName_select").next().find("dl").children();
		var j = -1;
		for (var i = 0; i < dl.length; i++) {
			if (dl[i].innerHTML.indexOf(value) <= -1) {
				dl[i].style.display = "none";
				j++;
			}
			if (j == dl.length-1) {
				$("#cpuName_select").next().find("dl").css({ "display": "none" });
			}
		}
	}		// search1 结束

	search2 = function () {
		var value = $("#graphicsCardName").val();
		$("#graphicsCardName_select").val(value);
		form.render();
		$("#graphicsCardName_select").next().find("dl").css({ "display": "block" });
		var dl = $("#graphicsCardName_select").next().find("dl").children();
		var j = -1;
		for (var i = 0; i < dl.length; i++) {
			if (dl[i].innerHTML.indexOf(value) <= -1) {
				dl[i].style.display = "none";
				j++;
			}
			if (j == dl.length-1) {
				$("#graphicsCardName_select").next().find("dl").css({ "display": "none" });
			}
		}

	}		// search2 结束

});









