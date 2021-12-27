const url="/doi-tac-phu-hop";
$.ajax({
    url:url,
    method:'GET',
    data:{
        id:$('#firstMatching').val()
    },
    success: function (data){
        $("#matching").html(data);
    }
})
$.ajax({
    url:"/loi-moi-phu-hop",
    method:'GET',
    data:{
        id:$('#firstMatching').val()
    },
    success: function (data){
        let flag= -1;
        var html="<td class=\"\" colspan=\"3\"></td>";
        var html1="<td class=\"\" colspan=\"3\"></td>";
        for (let i=0;i<data.length;i++){
            if(data[i].call != null && flag){
                html = "";
                html += "<td>";
                html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"'>";
                html +=         "<div class='cutText-2 font-normal'>"+data[i].call.name+"</div>";
                html +=     "</a>"
                html += "</td>";
                html += "<td>";
                html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"'>";
                html +=         "<div class='cutText-2 font-normal text-center'>"+data[i].name+"</div>";
                html +=     "</a>"
                html += "</td>";
                html += "<td>";
                html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">";
                html +=         "<div class='w-full h-full flex items-center justify-center font-normal' style='color: #007DC5'>"+"Mới"+"</div>";
                html +=     "</a>"
                html += "</td>";
                flag=i;

            }
            if(data[i].call != null && flag!=i){
                html1 = ""
                html1 += "<td>";
                html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"'>";
                html1 +=         "<div class='cutText-2 font-normal'>"+data[i].call.name+"</div>";
                html1 +=     "</a>"
                html1 += "</td>";
                html1 += "<td>";
                html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"'>";
                html1 +=         "<div class='cutText-2 font-normal  text-center'>"+data[i].name+"</div>";
                html1 +=     "</a>"
                html1 += "</td>";
                html1 += "<td>";
                html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">";
                html1 +=         "<div class='w-full h-full flex items-center justify-center font-normal' style='color: #007DC5'>"+"Mới"+"</div>";
                html1 +=     "</a>"
                html1 += "</td>";
            }
        }
        $("#loiMoiMatching").html(html);
        $("#loiMoiMatching2").html(html1)
    }
})
function getMatchingOrg(id){
    $.ajax({
        url:url,
        method:'GET',
        data:{
            id:id
        },
        success: function (data){
            $("#matching").html(data);
        }
    })
    $.ajax({
        url:"/loi-moi-phu-hop",
        method:'GET',
        data:{
            id:id
        },
        success: function (data){
            let flag= -1;
            var html="<td class=\"\" colspan=\"3\"></td>";
            var html1="<td class=\"\" colspan=\"3\"></td>";
            for (let i=0;i<data.length;i++){
                if(data[i].call != null && flag){
                    html = "";
                    html += "<td>";
                    html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">"+data[i].call.name+"</a>"
                    html += "</td>";
                    html += "<td class='text-center'>";
                    html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">"+data[i].name+"</a>"
                    html += "</td>";
                    html += "<td class='text-center'>";
                    html +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">New</a>"
                    html += "</td>";
                    flag=i;

                }
                if(data[i].call != null && flag!=i){
                    html1 = ""
                    html1 += "<td>";
                    html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">"+data[i].call.name+"</a>"
                    html1 += "</td>";
                    html1 += "<td class='text-center'>";
                    html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">"+data[i].name+"</a>"
                    html1 += "</td>";
                    html1 += "<td class='text-center'>";
                    html1 +=     "<a href='/chi-tiet-loi-moi/"+data[i].call.targetId+"' class=\"w-full h-full\">New</a>"
                    html1 += "</td>";
                }
            }
            $("#loiMoiMatching").html(html);
            $("#loiMoiMatching2").html(html1)
        }
    })
}

function animateValue(id, start, end, duration) {
    if (start === end) return;
    var range = end - start;
    var current = start;
    var increment = end > start? 1 : -1;
    var stepTime = Math.abs(Math.floor(duration / range));
    var obj = document.getElementById(id);
    var timer = setInterval(function() {
        current += increment;
        obj.innerHTML = current;
        if (current == end) {
            clearInterval(timer);
        }
    }, stepTime);
}
$(".clickOther").select2();
$(document).on('select2:open', () => {
    document.querySelector('.select2-search__field').focus();
});