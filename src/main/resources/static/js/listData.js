var table;
var ipAddr="/";


layui.use('table', function(){          // 初始化table
    table = layui.table;
});

var listData;

function initListTable(){

    $.ajax({
        url: ipAddr + 'computer/getAllEquipment',
        type: "get",
        success: function (data) {
            listData = data.data;
            table.render({
                id:'id',
                elem: '#tEquipmentAll',
                totalRow: false,
                toolbar: '#toolbarDemo', //开启头部工具栏，并为其绑定左侧模板
                //defaultToolbar: ['exports', 'print'], // 头部右侧工具
                // cellMinWidth: 80,
                limit:10,
                page:true,
                cols: [
                    [
                        {
                            field: 'xuhao',
                            type:'numbers',
                            title: '序号1',
                            rowspan:2
                        },
                        {
                            field: 'equipmentNumber',
                            title: '设备编号',
                            rowspan:2
                        },
                        {
                            field: 'equipmentType',
                            title: '设备类型',
                            rowspan:2
                        },
                        {
                        title: '计算机配置',
                            align:'center',
                        colspan:6
                    },
                        {
                            field: 'computerUse',
                            title: '用途',
                            rowspan:2
                        },
                        {
                            field: 'setPosition',
                            title: '放置位置',
                            rowspan:2
                        },
                        {
                            field: 'useDepartment',
                            title: '使用部门',
                            rowspan:2
                        },
                        {
                            field: 'personName',
                            title: '责任人',
                            rowspan:2
                        },
                        {
                            field: 'computerUsage',
                            title: '使用情况',
                            rowspan:2
                        },
                        {
                            field: 'remark',
                            title: '备注',
                            rowspan:2
                        },
                        {
                            field: 'isNetwork',
                            title: '是否联网',
                            rowspan:2
                        },
                        {
                            field: 'brandModel',
                            title: '品牌型号',
                            rowspan:2
                        },

                        {
                            field: 'cpuName',
                            title: 'cpu',
                            rowspan:2
                        },
                        {
                            field: 'graphicsCardName',
                            title: '显卡',
                            rowspan:2
                        },
                        {
                            field: 'memory',
                            title: '内存',
                            rowspan:2
                        },
                        {
                            field: 'lockNumber',
                            title: '锁号',
                            rowspan:2
                        },
                        {
                            field: 'monitor',
                            title: '显示器尺寸',
                            rowspan:2
                        }
                    ],
                    [ {
                            field: 'hdCapacity',
                            title: '容量',
                        },
                        {
                            field: 'hdSerialNumber',
                            title: '硬盘序列号',
                        },
                        {
                            field: 'ipAddress',
                            title: 'IP地址',
                        },
                        {
                            field: 'macAddress',
                            title: 'MAC地址',
                        },
                        {
                            field: 'osVersion',
                            title: '操作系统版本',
                        },
                        {
                            field: 'osInstallTime',
                            title: '安装时间',
                        }

                    ]
                ],
                data: listData

            });                     // render 结束

        }       //success结束
    })      //ajax结束

}           // 初始化表格结束

// $(function () {
//     initListTable();
// })



