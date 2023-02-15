async function getDate(area_select){
    const url='http://apis.data.go.kr/1543061/animalShelterSrvc/shelterInfo?serviceKey=1u9OcPZBNH0dLUlpKbDQ9ZTFjhByrP49Hmk5dWIYwJl8QRbOWm4kj7NsH9%2FxJzsCtUnLpdYzVW%2BpnTIu2JznFQ%3D%3D&numOfRows=186&pageNo=1&_type=json';
    // serviceKey : 인증키 / numOfRows : 총 센터가 186개라서 / pageNo : 한페이지에 186개 다 뽑겠다. / _type : json형식으로 받겠다.
    const response=await fetch(url);
    const data=await response.json();
    console.log(data);

    const locations=data.response.body.items.item.filter(spot => spot.orgNm.substr(0,2)==area_select);

    //drawMap(locations);
    drawMap(locations, area_select);
}


getDate(); // 이거 없으면 처음에 지도 안뜨다가 select에서 바꿔주면 그때뜸


function selectArea(){
    var area_select=document.getElementById("area");
    area_select=area_select.options[area_select.selectedIndex].value;
    getDate(area_select);
}


// 카카오 - 여러개 마커에 이벤트 등록하기1
// https://apis.map.kakao.com/web/sample/multipleMarkerEvent/

function drawMap(locations, area_select){

    var center_area=null;

// https://apis.map.kakao.com/web/sample/addMapClickEventWithMarker/ <- 마크 찍으면 위도경도 알려줌
    if(area_select=='대구'){
        center_area={
            center: new kakao.maps.LatLng(35.871389, 128.601564),
            level: 7
        };}
    else if(area_select=='부산'){
        center_area={
            center: new kakao.maps.LatLng(35.268543, 129.075210),
            level: 7
        };}
        else if(area_select=='대전'){
        center_area={
            center: new kakao.maps.LatLng(36.350539, 127.384857),
            level: 4
        };}
    else if(area_select=='광주'){
        center_area={
                center: new kakao.maps.LatLng(35.159967, 126.851773),
                level: 5
        };}
    else if(area_select=='제주'){
        center_area={
            center: new kakao.maps.LatLng(33.367575, 126.529260),
            level: 7
    };}
    else if(area_select=='인천'){
        center_area={
            center: new kakao.maps.LatLng(37.524418, 126.716414),
            level: 7
    };}
    else {center_area={
        center: new kakao.maps.LatLng(37.541, 126.986), // 서울, 경기
        level: 9 // 지도의 확대 레벨
    };}

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = center_area;

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 마커를 표시할 위치와 title 객체 배열입니다
    var positions = [];
    for(var i=0; i<locations.length; i++){
        positions[i]={
            title:locations[i].careNm,
            address:locations[i].careAddr,
            latlng: new kakao.maps.LatLng(locations[i].lat, locations[i].lng),
            lat:locations[i].lat,
            lng:locations[i].lng
        }
    }

    // 마커 이미지의 이미지 주소입니다 - 없어도 됨
    //var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    for (var i = 0; i < positions.length; i ++) {

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
        });
        // 마커에 표시할 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            content: '<div style="width: 200px; height: 80px; overflow:auto; padding:5px;"><a href="https://map.kakao.com/link/map/'
                +positions[i].title+','+positions[i].lat+','+positions[i].lng
                +'" target="_blank">'+positions[i].title
                +'</a><br>'+positions[i].address+'</div>', // 인포윈도우에 표시할 내용
            removable : true
        });

        // 마커에 mouseover 이벤트와 mouseout 이벤트를 등록합니다
        // 이벤트 리스너로는 클로저를 만들어 등록합니다
        // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
        kakao.maps.event.addListener(marker, 'click', makeOverListener(map, marker, infowindow));
        //kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));

    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다
    function makeOverListener(map, marker, infowindow) {
        return function() {
            infowindow.open(map, marker);
        };
    }

    // 인포윈도우를 닫는 클로저를 만드는 함수입니다
    /*function makeOutListener(infowindow) {
        return function() {
            infowindow.close();
        };
    }*/
}}