
window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function(내비바 축소 기능)
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar (탐색 모음 축소)
    navbarShrink();

    // Shrink the navbar when page is scrolled(페이지 스크롤 시 navbar 축소)
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element(기본 탐색 요소에서 Bootstrap scrollspy 활성화)
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 72,
        });
    };

    // Collapse responsive navbar when toggler is visible(토글러가 표시되면 반응형 탐색 모음 접기)
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});


// $("input[name='chatbtn']").change(function(){
// 	var test = $("input[name='chatbtn']:checked").val();
// 	alert(test);
// });


$(document).ready(function(){

    $('#selectChat_Q').hide();
    $('#selectChat_Spt').hide();
    $('#selectChat_Ct').hide();
    $('#selectChat_Id').hide();
    $('#selectChat_Pw').hide();
    $('#selectChat_M').hide();	// 초깃값 설정

    $("input[name='chatbtn']").change(function(){
        // 자주묻는질문 선택 시.
        if($("input[name='chatbtn']:checked").val() == '자주묻는질문'){
            $('#selectChat_Spt').hide();
            $('#selectChat_Ct').hide();
            $('#selectChat_Id').hide();
            $('#selectChat_Pw').hide();
            $('#selectChat_M').hide();
            $('#selectChat_Q').show();
        }
        // 후원 선택 시.
        else if($("input[name='chatbtn']:checked").val() == '후원'){
            $('#selectChat_Q').hide();
            $('#selectChat_Ct').hide();
            $('#selectChat_Id').hide();
            $('#selectChat_Pw').hide();
            $('#selectChat_M').hide();
            $('#selectChat_Spt').show();
        }
        // 센터찾기 선택 시.
        else if($("input[name='chatbtn']:checked").val() == '센터찾기'){
            $('#selectChat_Q').hide();
            $('#selectChat_Spt').hide();
            $('#selectChat_Id').hide();
            $('#selectChat_Pw').hide();
            $('#selectChat_M').hide();
            $('#selectChat_Ct').show();
        }
        // 계정찾기 선택 시.
        else if($("input[name='chatbtn']:checked").val() == '계정찾기'){
            $('#selectChat_Q').hide();
            $('#selectChat_Spt').hide();
            $('#selectChat_Ct').hide();
            $('#selectChat_Pw').hide();
            $('#selectChat_M').hide();
            $('#selectChat_Id').show();
        }
        // 비밀번호찾기 선택 시.
        else if($("input[name='chatbtn']:checked").val() == '비밀번호찾기'){
            $('#selectChat_Q').hide();
            $('#selectChat_Spt').hide();
            $('#selectChat_Ct').hide();
            $('#selectChat_Id').hide();
            $('#selectChat_M').hide();
            $('#selectChat_Pw').show();
        }
        // 관리자와채팅 선택시
        else if($("input[name='chatbtn']:checked").val() == '관리자와채팅'){
            $('#selectChat_Q').hide();
            $('#selectChat_Spt').hide();
            $('#selectChat_Ct').hide();
            $('#selectChat_Id').hide();
            $('#selectChat_Pw').hide();
            $('#selectChat_M').show();
        }
    });
});





// contact controll(접촉 제어)
document.querySelector('#submitButton').addEventListener('click', function (event) {
    let userNo = document.querySelector('#userNo').value;
    let nickName = document.querySelector('#nickName').value;
    let message = document.querySelector('#message').value;
    let chatDate = document.querySelector('#chatDate').value;

    let params = {
        "userNo": userNo,
        "nickName": nickName,
        "phone": phone,
        "chatDate": message
    };

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/contact');

    xhr.onreadystatechange = function () {	// xhr 객체가 바뀌었을 (요청이 오갔을때 동작)
        if (xhr.readyState == 4 && xhr.status == 200) {	// 모든 통신이 끝남(4), 정상일 때(200)
            console.log(xhr);
            if (xhr.responseText == "") {
                document.querySelector('#contact__result').innerHTML = "Thank you! I will reply to you soon.";	// 동작
            } else {
                let data = JSON.parse(xhr.responseText);
                const writeDateTime = new Date(data.wdate).toString();
                document.querySelector('#contact__result').innerHTML = "You already sent message when " + writeDateTime.substring(0,writeDateTime.indexOf('GMT')) + '<br>' + " try again?";
                document.querySelector('#contact__update__btn').style.display = 'block';
            }
        }
    }
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(params));	//요청 보내기
});


function contactUpdateYes() {
    let userNo = document.querySelector('#userNo').value;
    let nickName = document.querySelector('#nickName').value;
    let message = document.querySelector('#message').value;
    let chatDate = document.querySelector('#chatDate').value;

    let params = {
        "userNo": userNo,
        "nickName": nickName,
        "message": message,
        "chatDate": chatDate
    };
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/contact/insert');
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.querySelector('#contact__result').innerHTML = "Thank you! I will reply to you soon.";	// 동작
            document.querySelector('#contact__update__btn').style.display = 'none';
        }
    }
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(params));	//요청 보내기
}

function contactUpdateNo() {
    document.querySelector('#contact__result').innerHTML = "";	// 동작
    document.querySelector('#contact__update__btn').style.display = 'none';
    document.querySelector('#submitButton').classList.remove('d-none');  // remove display none class(디스플레이 없음 클래스 제거)
    document.querySelector('#userNo').disabled = false;
    document.querySelector('#nickName').disabled = false;
    document.querySelector('#message').disabled = false;
    document.querySelector('#chatDate').disabled = false;
}

$(function() {
	$('[data-toggle="tooltip"]').tooltip();
});

function openDetail(url) {
    var width = 768;
    var height = 650;
    var left = (window.screen.width - width) / 2;
    var top = (window.screen.height - height) / 2;
    window.open(url, 'detail', 'width=' + width +', height='+height +', left='+left +', top='+top +', location=no, status=no, scrollbar=yes');
}

$(function() {
    $('[data-toggle="tooltip"]').tooltip();
});
