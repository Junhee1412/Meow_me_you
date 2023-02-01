/**
* Template Name: Logis - v1.3.0
* Template URL: https://bootstrapmade.com/logis-bootstrap-logistics-website-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
document.addEventListener('DOMContentLoaded', () => {
    "use strict";
  
    /**
     * Preloader
     */
    const preloader = document.querySelector('#preloader');
    if (preloader) {
      window.addEventListener('load', () => {
        preloader.remove();
      });
    }
  
    
    /**
     * Scroll top button
     */
    const scrollTop = document.querySelector('.scroll-top');
    if (scrollTop) {
      const togglescrollTop = function() {
        window.scrollY > 100 ? scrollTop.classList.add('active') : scrollTop.classList.remove('active');
      }
      window.addEventListener('load', togglescrollTop);
      document.addEventListener('scroll', togglescrollTop);
      scrollTop.addEventListener('click', window.scrollTo({
        top: 0,
        behavior: 'smooth'
      }));
    }
  
    /**
     * Mobile nav toggle
     */
    const mobileNavShow = document.querySelector('.mobile-nav-show');
    const mobileNavHide = document.querySelector('.mobile-nav-hide');
  
    document.querySelectorAll('.mobile-nav-toggle').forEach(el => {
      el.addEventListener('click', function(event) {
        event.preventDefault();
        mobileNavToogle();
      })
    });
  
    function mobileNavToogle() {
      document.querySelector('body').classList.toggle('mobile-nav-active');
      mobileNavShow.classList.toggle('d-none');
      mobileNavHide.classList.toggle('d-none');
    }
  
    /**
     * Hide mobile nav on same-page/hash links
     */
    document.querySelectorAll('#navbar a').forEach(navbarlink => {
  
      if (!navbarlink.hash) return;
  
      let section = document.querySelector(navbarlink.hash);
      if (!section) return;
  
      navbarlink.addEventListener('click', () => {
        if (document.querySelector('.mobile-nav-active')) {
          mobileNavToogle();
        }
      });
  
    });
  
    /**
     * Toggle mobile nav dropdowns
     */
    const navDropdowns = document.querySelectorAll('.navbar .dropdown > a');
  
    navDropdowns.forEach(el => {
      el.addEventListener('click', function(event) {
        if (document.querySelector('.mobile-nav-active')) {
          event.preventDefault();
          this.classList.toggle('active');
          this.nextElementSibling.classList.toggle('dropdown-active');
  
          let dropDownIndicator = this.querySelector('.dropdown-indicator');
          dropDownIndicator.classList.toggle('bi-chevron-up');
          dropDownIndicator.classList.toggle('bi-chevron-down');
        }
      })
    });
  
    /**
     * Initiate pURE cOUNTER
     */
    new PureCounter();
  
    /**
     * Initiate glightbox
     */
    const glightbox = GLightbox({
      selector: '.glightbox'
    });
  
    /**
     * Init swiper slider with 1 slide at once in desktop view
     */
    new Swiper('.slides-1', {
      speed: 600,
      loop: true,
      autoplay: {
        delay: 5000,
        disableOnInteraction: false
      },
      slidesPerView: 'auto',
      pagination: {
        el: '.swiper-pagination',
        type: 'bullets',
        clickable: true
      },
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      }
    });
  
    /**
     * Animation on scroll function and init
     */
    function aos_init() {
      AOS.init({
        duration: 1000,
        easing: 'ease-in-out',
        once: true,
        mirror: false
      });
    }
    window.addEventListener('load', () => {
      aos_init();
    });
  
  });

      // 결제js TEST
      // $("input[name='donateWayCode']").change(function(){
      // 	var test = $("input[name='donateWayCode']:checked").val();
      // 	alert(test);
      // });


      // 결제 관련 js
      $(document).ready(function () {

        $('#selectPay_Bank').hide();   // 초깃값 설정
        $('#selectPay_noBank').hide();	// 초깃값 설정

        $("input[name='donateWayCode']").change(function () {
          // 계좌이체 결제 선택 시.
          if ($("input[name='donateWayCode']:checked").val() == 'ACNT') {
            $('#selectPay_card').hide();
            $('#selectPay_noBank').hide();
            $('#selectPay_Bank').show();
          }
          // 무통장입금 결제 선택 시.
          else if ($("input[name='donateWayCode']:checked").val() == 'BANK') {
            $('#selectPay_card').hide();
            $('#selectPay_Bank').hide();
            $('#selectPay_noBank').show();
          }
          // 신용카드 결제 선택 시.
          else if ($("input[name='donateWayCode']:checked").val() == 'CRCRD') {
            $('#selectPay_Bank').hide();
            $('#selectPay_noBank').hide();
            $('#selectPay_card').show();
          }
        });
      });


var url_href = window.location.href;
var url = new URL(url_href);
var donateCode = url.searchParams.get("donateBusinessCode");
$('input[name=donateBusinessCode]').attr('value',donateCode);


  function autoHypenPhone(str){
              str = str.replace(/[^0-9]/g, '');
              var tmp = '';
              if( str.length < 4){
                  return str;
              }else if(str.length < 7){
                  tmp += str.substr(0, 3);
                  tmp += '-';
                  tmp += str.substr(3);
                  return tmp;
              }else if(str.length < 11){
                  tmp += str.substr(0, 3);
                  tmp += '-';
                  tmp += str.substr(3, 3);
                  tmp += '-';
                  tmp += str.substr(6);
                  return tmp;
              }else{
                  tmp += str.substr(0, 3);
                  tmp += '-';
                  tmp += str.substr(3, 4);
                  tmp += '-';
                  tmp += str.substr(7);
                  return tmp;
              }
              return str;
          }

  var cellPhone = document.getElementById('sign_ph');
  cellPhone.onkeyup = function(event){
          event = event || window.event;
          var _val = this.value.trim();
          this.value = autoHypenPhone(_val) ;
  }



function printdate()  {

    const birthyear = document.getElementById('yy').value;
    const birthmonth = document.getElementById('mm').value;
    const birthday = document.getElementById('dd').value;
    const strbirth = birthyear + birthmonth + birthday;

    document.getElementById("brth").value = strbirth;

}


function validBirthDay(strbirth){
    if(birthdayCheck(strbirth)==false){
        alert('올바른 생년월일을 입력해주세요.');
        return false;
    }
}


function birthdayCheck(strbirth) {

   var RegExp = /^[a-zA-Z0-9]{4,12}$/;
   var n_RegExp = /^[가-힣]{2,15}$/;

   var objbirth = document.getElementById("brth").value; // '-' 문자 모두 '' 변경


   const year = Number(objbirth.substr(0, 4)); // 입력한 값의 0~4자리까지 (연)
   const month = Number(objbirth.substr(4,2)); // 입력한 값의 4번째 자리부터 2자리 숫자 (월)
   const day = Number(objbirth.substr(6,2)); // 입력한 값 6번째 자리부터 2자리 숫자 (일)
   const today = new Date(); // 오늘 날짜를 가져옴
   const yearNow = today.getFullYear(); // 올해 연도 가져옴


        //이름 유효성 검사
        if(objName.value ==''){
            alert("이름을 입력해주세요.");
            return false;
        }
        if(!n_RegExp.test(objName.value)){
            alert("특수문자,영어,숫자는 사용할수 없습니다. 한글만 입력하여주세요.");
            return false;
        }
//    생년월일 유효성 검사
   if (objbirth.length <=8) {
      if (1900 > year || year > yearNow){    // 연도의 경우 1900 보다 작거나 yearNow 보다 크다면 false를 반환합니다.
         return false;
      } else if (month < 1 || month > 12) {
         return false;
      } else if (day < 1 || day > 31) {
         return false;
      } else if ((month==4 || month==6 || month==9 || month==11) && day==31) {
         return false;
      } else if (month == 2) { // 2월달일때
         // 2월 29일(윤년) 체크
         const isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
         if (day>29 || (day==29 && !isleap)) {
            return false;
         } else {
            return true;
         } //end of if (day>29 || (day==29 && !isleap))
      } else {
         return true;
      }//end of if
   } else { // 입력된 생년월일이 8자 초과할때 : false
      return false;
   }
}
