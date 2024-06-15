axios.defaults.baseURL = 'http://localhost:8080/xiaomi/';
axios.defaults.headers.common['Authorization'] = 'token';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

function ajaxCheckUserName(username) {
    return axios({
        url: 'userservlet?method=checkUserName&username=' + username,
        method: 'get'
    })
}

function ajaxgoodstypelist() {
    return axios({
        url: 'goodstypeservlet?method=goodstypelist',
        method: 'get'
    })
}

function ajaxupdatecart(pid, n) {
    return axios({
        url: 'cartservlet?method=addCartAjax&goodsId=' + pid + '&number=' + n,
        method: "get",
    })
}

function ajaxclearcart(){
    return axios({
        url:'cartservlet?method=clearCartAjax',
        method:'get'
    })
}

function ajaxgetaddress(){
    return axios({
        url:'userservlet?method=getAddress',
        method:'get'
    })
}

function ajaxdeleteaddress(id){
    return axios({
        url:'userservlet?method=deleteAddress&id='+id,
        method:'get'
    })
}

function ajaxupdateaddress(id,name,phone,detail){
    return axios({
        url:'userservlet?method=updateAddress&id='+id+'&name='+name+'&phone='+phone+'&detail='+detail,
        method:'get'
    })
}

function ajaxdefaultaddress(id){
    return axios({
        url: 'userservlet?method=defaultAddress&id='+id,
        method:'get'
    })
}