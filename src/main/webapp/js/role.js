function searchRoles() {
    $("#dg").datagrid("load",{
        roleName:$("#roleName").val()
    });
}
function openAddRoleDialog() {
    $("#fm")[0].reset();
    $("#dlg").dialog("setTitle","添加角色").dialog("open");
}
function closeDialog() {
    $("#dlg").dialog("close");
}
function saveOrUpdateRole() {
    var id=$("#id").val();
    url=ctx+"/role/updateRole";
    if(isEmpty(id)){
        url=ctx+"/role/addRole";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function (data) {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.confirm("添加角色",data.msg,"info");
            if(data.code==200){
                closeDialog();
                $("#dg").datagrid("load");
            }
        }
    });
}
function openModifyRoleDialog() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("修改角色信息","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("修改角色信息","只能选中一条数据!","warning");
        return;
    }
    $("#fm").form("load",selection[0]);
    $("#dlg").dialog("setTitle","修改角色").dialog("open");
}
function deleteRole() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("删除角色信息","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("删除用户信息","只能选中一条数据!","warning");
        return;
    }
    $.messager.confirm("来自crm","确定删除选中的"+selection.length+"条记录?",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/role/deleteRole",
                data:"id="+selection[0].id,
                dataType:"json",
                success:function(data){
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        closeDialog();
                        searchRoles();
                    }
                }
            });
        }
    });
}
function openRelatePermissionDlg() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("管理权限","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("管理权限","只能选中一条数据!","warning");
        return;
    }
    $("#rid").val(selection[0].id);
    loadModuleData();
    $("#dlg02").dialog("open");
}
var ztreeObj;
function loadModuleData() {
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules",
        data:"rid="+$("#rid").val(),
        dataType:"json",
        success:function (data) {
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes =data;
            ztreeObj= $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }
    });
}
function closeDialog02() {
    $("#dlg02").dialog("close");
}
function addPermission() {

}
function zTreeOnCheck(){
    var zCheck=ztreeObj.getCheckedNodes(true);
    var moduleIds="moduleIds=";
    for(var i=0;i<zCheck.length;i++){
        if(i<zCheck.length-1){
            moduleIds+=zCheck[i].id+"&moduleIds=";
        }else{
            moduleIds+=zCheck[i].id;
        }
    }
    $("#moduleIds").val(moduleIds);
}
function addPermission() {
    console.log("rid="+$("#rid").val()+"&"+$("#moduleIds").val())
    $.ajax({
        type:"post",
        url:ctx+"/permission/addPermission",
        data:"rid="+$("#rid").val()+"&"+$("#moduleIds").val(),
        dataType:"json",
        success:function (data) {
            $.messager.confirm("管理权限",data.msg,"info")
            if(data.code==200){
                closeDialog02();
                $("#moduleIds").val("");
            }
        }
    });
}