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
          this.value = autoHypenPhone(_val);
  }



function printdate()  {

    const birthyear = document.getElementById('yy').value;
    const birthmonth = document.getElementById('mm').value;
    const birthday = document.getElementById('dd').value;
    const objBirth = birthyear + birthmonth + birthday;

    document.getElementById("brth").value = objBirth;

}

function comma(str) {
        str = String(str);
        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
    }

    function uncomma(str) {
        str = String(str);
        return str.replace(/[^\d]+/g, '');
    }

    function inputNumberFormat(obj) {
        obj.value = comma(uncomma(obj.value));
    }

    function inputOnlyNumberFormat(obj) {
        obj.value = onlynumber(uncomma(obj.value));
    }

    function onlynumber(str) {
	    str = String(str);
	    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1');
	}

function removeComma(str){
		n = parseInt(str.replace(/,/g,""));
		return n;
	}

function ValidChecked() {

   var n_RegExp = /^[가-힣a-zA-Z\s]+$/;
   var p_RegExp = /^(?:(010-\d{4})|(01[1|6|7|8|9]-\d{3,4}))-(\d{4})$/;
   var a_RegExp = /^([3-9]|\w{2,})000$/;
   var cN_RegExp = /^(\d{4})-(\d{4})-(\d{4})-(\d{4})$/;
   var cExpir_RegExp = /^\d{4}$/;
   var cvc_RegExp = /^\d{3}$/;
   var pass_RegExp = /^\d{2}$/;
   var account_RegExp = /^\d{11,14}$/;

   var objdonateBusinessCode = document.getElementById("donateBusinessCode");
   var objPhone = document.getElementById("sign_ph");
   var objBirth = document.getElementById("brth");
   var objName = document.getElementById("sign_name");
   var objDay = document.getElementById("dd");
   var objMonth = document.getElementById("mm");
   var objYear = document.getElementById("yy");
   var objAmount = document.getElementById("sign_amount");
   var objCreditNumber = document.getElementById("cc-number");
   var objCvc = document.getElementById("cc-cvc");
   var objCreditPassword = document.getElementById("cc-password");
   var objCreditExpiration = document.getElementById("cc-expiration");
   var objCreditCompany = document.getElementById("cc-company");
   var objNoBankCompany = document.getElementById("noBankCompany");
   var objAccountCompany = document.getElementById("accountCompany");
   var objAccountNumber = document.getElementById("ac-number");
   var objAccountPassword = document.getElementById("ac-password");

   const today = new Date(); // 오늘 날짜를 가져옴
   const yearNow = today.getFullYear(); // 올해 연도 가져옴

      //링크로 바로 오는 것을 방지(사업 선택하고 와야함)
      if (objdonateBusinessCode.value == "") {
          alert("후원 홈에서 후원 사업을 선택해주세요.");
          objdonateBusinessCode.focus();
          return false;
        };

      //이름 유효성 검사
      if (objName.value == "") {
        alert("이름을 입력하세요.");
        objName.focus();
        return false;
      };
      if (!n_RegExp.test(objName.value)) {
        alert("이름은 한글과 영어만 입력가능합니다.");
        objName.focus();
        return false;
      };

        //생년월일 유효성 검사
      if (objBirth.value == "") {
        alert("생년월일을 입력하세요.");
        objBirth.focus();
        return false;
      };
      if (objBirth.value.length <=8) {
      if (1900 > objYear.value || objYear.value > yearNow){    // 연도의 경우 1900 보다 작거나 yearNow 보다 크다면 false를 반환합니다.
         alert("1900년 이전은 입력할 수 없습니다.");
         objYear.focus();
         return false;
      } else if (objMonth.value == "") {
        alert("월을 선택해주세요");
        return false;
      } else if (objDay.value < 1 || objDay.value > 31) {
        alert("해당 월은 1일부터 31일까지 입력할 수 있습니다.");
        objDay.focus();
         return false;
      } else if ((objMonth.value==4 || objMonth.value==6 || objMonth.value==9 || objMonth.value==11) && objDay.value==31) {
      alert("해당 월은 1일부터 30일까지 입력할 수 있습니다.");
      objDay.focus();
         return false;
      } else if (objMonth.value == 2) { // 2월달일때
         // 2월 29일(윤년) 체크
         const isleap = (objYear.value % 4 == 0 && (objYear.value % 100 != 0 || objYear.value % 400 == 0));
         if (objDay.value>29 || (objDay.value==29 && !isleap)) {
         alert("해당 월은 1일부터 28일까지 입력할 수 있습니다.");
         objDay.focus();
            return false;
         }
      }
   } else { // 입력된 생년월일이 8자 초과할때 : false
        alert("생년월일이 유효하지 않습니다.");
        objDay.focus();
      return false;
   }

      //휴대폰번호 유효성검사
      if (objPhone.value == "") {
           alert("휴대폰번호를 입력하세요.");
           objPhone.focus();
           return false;
         };
      if(!p_RegExp.test(objPhone.value)){
          alert("휴대폰번호가 올바르지 않습니다.");
          objPhone.focus();
          return false;
        };

      // 계좌이체 결제 선택 시.
      if ($("input[name='donateWayCode']:checked").val() == 'ACNT') {

        document.querySelector('.noBank_view').value = "";
        document.querySelector('.creditcard_view').value = "";

        //계좌은행 선택 유효성검사
           if (objAccountCompany.value == "") {
           alert("은행명을 선택하세요.");
           objAccountCompany.focus();
           return false;
         };

        //계좌번호 입력 유효성 검사
        if (objAccountNumber.value == "") {
            alert("계좌번호를 입력하세요.");
            objAccountNumber.focus();
            return false;
          };
          if (!account_RegExp.test(objAccountNumber.value)) {
            alert("계좌번호가 유효하지 않습니다!\n국민 : 12, 14자리, 신한 : 11, 12자리, NH : 13자리");
            objAccountNumber.focus();
            return false;
          };
      }

      // 무통장입금 결제 선택 시.
      else if ($("input[name='donateWayCode']:checked").val() == 'BANK') {
        document.querySelector('.creditcard_view').value = "";
        document.querySelector('.account_view').value = "";

        //무통장입금 은행 유효성 검사
           if (objNoBankCompany.value == "") {
           alert("은행명을 선택하세요.");
           objNoBankCompany.focus();
           return false;
         };

      }

      // 신용카드 결제 선택 시.

      else if ($("input[name='donateWayCode']:checked").val() == 'CRCRD') {
        document.querySelector('.noBank_view').value = "";
        document.querySelector('.account_view').value = "";

           //카드사 유효성 검사
           if (objCreditCompany.value == "") {
           alert("카드사를 선택하세요.");
           objCreditCompany.focus();
           return false;
         };

           //카드번호 유효성 검사
          if (objCreditNumber.value == "") {
            alert("카드번호를 입력하세요.");
            objCreditNumber.focus();
            return false;
          };
          if (!cN_RegExp.test(objCreditNumber.value)) {
            alert("유효하지 않은 카드번호입니다!");
            objCreditNumber.focus();
            return false;
          };

        //유효기간 유효성 검사
        if (objCreditExpiration.value == "") {
        alert("유효기간을 입력하세요.");
        objCreditExpiration.focus();
        return false;
      };
      if (!cExpir_RegExp.test(objCreditExpiration.value)) {
        alert("유효기간은 YYMM 형식의 4자리 숫자로 입력해주세요!");
        objCreditExpiration.focus();
        return false;
      };

        //CVC 유효성 검사
        if (objCvc.value == "") {
          alert("CVC를 입력하세요.");
          objCvc.focus();
          return false;
        };
        if (!cvc_RegExp.test(objCvc.value)) {
          alert("CVC는 숫자 3자리로만 입력 가능합니다.");
          objCvc.focus();
          return false;
        };

        //신용카드 비밀번호 유효성 검사
        if (objCreditPassword.value == "") {
          alert("비밀번호를 입력하세요.");
          objCreditPassword.focus();
          return false;
        };
        if (!pass_RegExp.test(objCreditPassword.value)) {
          alert("비밀번호는 앞 2자리만 입력해주세요.");
          objCreditPassword.focus();
          return false;
        };
      };

      //  후원금액 유효성 검사
      if (objAmount.value == "") {
        alert("후원금액을 입력하세요.");
        objAmount.focus();
        return false;
      };

      if (!a_RegExp.test(removeComma(objAmount.value))) {
        alert("후원금액은 3,000원 이상 1,000원단위 이상으로만 입력 가능합니다.");
        objAmount.focus();
        return false;
        }



   document.querySelector("input[name='donateAmount']").value = removeComma(objAmount.value);
      return true;
   };


//신용카드 오토하이픈

var cardNumber = document.getElementById('cc-number');

cardNumber.onkeyup = function(event){
    event = event || window.event;
    var _val = this.value.trim();
    this.value = autoHypenCard(_val) ;
};

function autoHypenCard(str){
    str = str.replace(/[^0-9]/g, '');
    var tmp = '';
    if( str.length < 4){
        return str;
    }
    else if(str.length < 8){
        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4,4);
        return tmp;
    }else if(str.length < 12){
        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4, 4);
        tmp += '-';
        tmp += str.substr(8,4);
        return tmp;
    }else if(str.length < 17) {

        tmp += str.substr(0, 4);
        tmp += '-';
        tmp += str.substr(4, 4);
        tmp += '-';
        tmp += str.substr(8,4);
        tmp += '-';
        tmp += str.substr(12,4);
        return tmp;
    }
    return str;
    };