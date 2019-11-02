function resetValue() {
    $("#fm")[0].reset();
}
function saveCustomerService() {
    $("#fm").form("submit",{
        url:ctx+"/customer_serve/insert",
        onSubmit:function (data) {
            data.createPeople=$.cookie("trueName");
            return $("#fm").form("validate")
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.confirm("保存客户服务",data.msg,"info");
            if(data.code==200){
               resetValue();
            }
        }
    })
}