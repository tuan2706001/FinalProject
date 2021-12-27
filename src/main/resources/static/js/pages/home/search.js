$(".search-box").click(function (){
   $(".result").find("ul").show();
   var keyword = $(this).val();
   var url = "/search-homepage";
   var urlView = $(this).attr("data-url");
   $.ajax({
      url: url,
      method: "POST",
      data: {
         keyword: keyword
      },
      success: function (data){
         // console.log(data);
         $(".result").find("ul").empty();
         $.each(data.object,function (index,item){
            var name = item.name.toLowerCase().replaceAll("đ", "d")
                .replaceAll("\\.","-")
                .replaceAll(" ", "-")
                .replaceAll("/","-")
                .replaceAll("\\\\","-")
                .replaceAll(";","-")
                .replaceAll("\\[", "-")
                .replaceAll("]", "-");
            var urll = urlView + ""+ item.targetId
            $('.result').find("ul").append("<li class='border border-gray-200 cursor-pointer hover:bg-gray-200 p-2'><a class='block' href='"+ urll+"'>"+item.name+"</a></li>");
         });
      }
   });
});
$(".search-box").keypress(function (){
   var keyword = $(this).val();
   var url = "/search-homepage";
   var urlView = $(this).attr("data-url");
   $.ajax({
      url: url,
      method: "POST",
      data: {
         keyword: keyword
      },
      success: function (data){
         // console.log(data);
         $(".result").find("ul").empty();
         $.each(data.object,function (index,item){
            var name = item.name.toLowerCase().replaceAll("đ", "d")
                .replaceAll("\\.","-")
                .replaceAll(" ", "-")
                .replaceAll("/","-")
                .replaceAll("\\\\","-")
                .replaceAll(";","-")
                .replaceAll("\\[", "-")
                .replaceAll("]", "-");
            var urll = urlView + ""+ item.targetId
            $('.result').find("ul").append("<li class='border border-gray-200 cursor-pointer hover:bg-gray-200 p-2'><a class='block' href='"+ urll+"'>"+item.name+"</a></li>");
         });
      }
   });
});
$(window).click(function (e){
   if (!$(e.target).closest("input.search-box").length){
      $(".result").find("ul").hide();
   }
});