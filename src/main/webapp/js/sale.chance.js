function formatterState(val) {
    if(val==0){
        return "未分配";
    }else if(val==1){
        return "已分配";
    }else {
        return "未定义";
    }
}
function searchSaleChances(){
    $("#dg").datagrid("load",{
        createMan:$("#createMan").val(),
        customerName:$("#customerName").val(),
        createDate:$("#createDate").combobox("getValue"),
        state:$("#state").combobox("getValue")
    });
}
function openAddAccountDialog(){
    $("#fm")[0].reset();
    $("#dlg").dialog({"title":"添加营销机会记录"});
    $("#dlg").dialog("open");
}
function  closeDialog() {
    $("#dlg").dialog("close");
}
function  saveAccount() {
    var id=$("#id").val();
    var url=ctx+"/sale_chance/updateSaleChance";
    if(isEmpty(id)){
        url=ctx+"/sale_chance/addSaleChance";
    }
    //提交表单
    $("#fm").form("submit",{
        url:url,
        onSubmit:function(params){
            params.createMan=$.cookie("trueName");
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                closeDialog();
                $.messager.confirm("添加营销机会记录",data.msg,"info");
                searchSaleChances();
            }else{
                $.messager.confirm("添加营销机会记录",data.msg,"error");
            }

        }
    })
}
function openModifyAccountDialog(){
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改营销机会记录","您未选中数据!","warning");
        return;
    }
    if(selections.length>1){
        $.messager.confirm("修改营销机会记录","只能选中一条数据!!","warning");
        return;
    }
    $('#fm').form('load',{
        customerName:selections[0].customerName,
        chanceSource:selections[0].chanceSource,
        cgjl:selections[0].cgjl,
        overview:selections[0].overflow,
        linkMan:selections[0].linkMan,
        linkPhone:selections[0].linkPhone,
        description:selections[0].description,
        assignMan:selections[0].assignMan,
        id:selections[0].id,
        overview:selections[0].overview
    });
    $("#dlg").dialog({"title":"修改营销机会记录"});
    $("#dlg").dialog("open");
}
function deleteAccount(){
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改营销机会记录","您未选中数据!","warning");
        return;
    }
    var str="id=";
    for(var i=0;i<selections.length;i++){
        if(i<selections.length-1){
            str+=selections[i].id+"&id=";
        }else{
            str+=selections[i].id;
        }
    }
    $.messager.confirm("删除营销机会记录","确定要删除选中数据吗?",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/sale_chance/deleteSaleChance",
                data:str,
                dataType:"json",
                success:function(data){
                    if(data.code==200){
                        closeDialog();
                        $.messager.confirm("删除营销机会记录","删除成功!","info");
                        searchSaleChances();
                    }else {
                        $.messager.confirm("删除营销机会记录", data.msg, "error");
                    }
                }

            });
        }
    })
}