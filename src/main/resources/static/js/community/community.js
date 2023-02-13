
//  게시글 삭제시 확인 알림창
  $('.confirm_btn').click(function(e){
        if (confirm("삭제 하시겠습니까?")){    //확인
            alert("삭제 되었습니다");
         }else{
            e.preventDefault();
        };
    });