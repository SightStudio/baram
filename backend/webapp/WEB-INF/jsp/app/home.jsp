<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%-- set defaultVO --%>
<c:set var="vo" value="${defaultVO}"/>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content= "width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>시간표 경우의 수</title>

    <!-- init CSS -->
    <link rel="stylesheet" href="${CSS}/common/init/normalize.min.css">
    <link rel="stylesheet" href="${CSS}/common/init/reset.min.css">
    <link rel="stylesheet" href="${CSS}/common/init/bulma.min.css">
    
	<!-- PAGE CSS  -->
    <link rel="stylesheet" href="${CSS}/common/common.css">
    <link rel="stylesheet" href="${CSS}/common/main.css">
    
    <!-- init JS -->
    <script src="${JS}/common/jquery/jquery-3.4.0.min.js"></script>
</head>
<body>
	<%-- HEADER 영역 START --%>
	<%@ include file="../common/header.jsp" %>
	<%-- HEADER 영역 END --%>
	
	<section class="content">
		<article class="user-profile-pannel">
		
		
		</article> <!-- .user-profile-pannel END -->
	
        <article class="user-select-pannel">
            <div class="user-input-wrapper">
                <ul>
                    <li>
						<div class="select is-info is-rounded">
	                        <select class="select semester-select">
	                            <option value="2019_1" selected>2019년도 1학기</option>
	                        </select>
                        </div>
                    </li>

                    <li>
                    	<div class="select is-info is-rounded">
	                        <select class="search-select department">
	                            <option value="게임공학부" selected>게임공학부</option>
	                            <option value="경영학부">경영학부</option>
	                            <option value="교양">교양</option>
	                            <option value="기계공학과">기계공학과</option>
	                            <option value="기계설계공학과">기계설계공학과</option>
	                            <option value="나노광공학과">나노광공학과</option>
	                            <option value="디자인학부">디자인학부</option>
	                            <option value="메카트로닉스공학과">메카트로닉스공학과</option>
	                            <option value="생명화학공학과">생명화학공학과</option>
	                            <option value="신소재공학과">신소재공학과</option>
	                            <option value="에너지전기공학과">에너지전기공학과</option>
	                            <option value="전자공학부">전자공학부</option>
	                            <option value="컴퓨터공학부">컴퓨터공학부</option>
	                        </select>
                        </div>
                    </li>

                    <li>
                    	<div class="select is-info is-rounded">
                        	<select class="select search-select learn_p">
	                        	<option value="0" selected>모두</option>
	                            <option value="3">3학점</option>
	                            <option value="2">2학점</option>
	                            <option value="1">1학점</option>
	                        </select>
                        </div>
                    </li>
                    
                    <li>
                    	<div class="lecture-search-input-wrapper">
		                	<input type="search" class="input is-primary is-small lecture-search-input" size="50">
		                   	<button class="button small serach-btn">검색</button>
		                </div>
                    </li>
                </ul>
                
                
            </div> <!-- .user-input-wrapper END -->

            <div class="search-result-wrapper">
                <table class="search-result">
                    <thead>
                        <tr class="result-header">
                            <th style="width:20%">학과</th>
                            <th style="width:10%">학년</th>
                            <th style="width:50%">과목명</th>
                            <th style="width:10%">학점</th>
                            <th style="width:10%">선택</th>
                        </tr> <!-- .result-header END -->
                    </thead>
                    
                    <tbody>
                        <!-- 예시 row -->
                        <!-- 
                        <tr class="result-data">
                            <td>경영학부</td>
                            <td>2 학년</td>
                            <td>웹프로그래밍 응용</td>
                            <td>3 학점</td>
                            <th> 
                            	<label class="el-checkbox-wrapper">	   				 				 
					       			<span  class="checkmark"></span>							 
					   				<input class="el-checkbox" type="checkbox" value=\''+ JSON.stringify(data) + '\'>
				   		   		</label>
                            </th>
                        </tr>
                        -->
                    </tbody>
                </table> <!-- .search-result END -->
            </div>	  <!-- .search-result-wrapper END -->
        </article> <!-- .user-select-pannel END -->

        <article class="user-selection-pannel">
            <div class="user-selection-wrapper">
                <!-- <p class="selection-title">내가 선택한 수업</p> -->
                <table class="selection-list">
                	<thead>
                		<tr>
                			<th style="width:20%">학과</th>
                			<th style="width:56%">강의명</th>
                			<th style="width:12%">학점</th>
                			<th style="width:10%">취소</th>
                		</tr> 
                	</thead>
                
                	<tbody>
                		<!-- empty area
	                	<tr class="selection-data">
	                		<td></td>
	                		<td></td>
	                		<td></td>
	                	</tr>
	                	-->
                	</tbody>
                </table>
            </div> <!-- .user-selection-wrapper END -->
            
            <div class="user-selection-total-wrapper">
       			<p><span>선택 학점 : <em class="selectionTotal">0</em></span></p>
    			<button class="button submit-btn is-primary">시간표 확인</button>
    			
    			<c:if test="${!empty loginUser}">
    				<button class="button save-btn is-primary">선택목록 저장</button>
    			</c:if>
			</div> <!-- .user-selection-total-wrapper END-->
        </article> <!-- .user-selection-pannel END -->
    </section>
	
	<!-- MODAL area -->
	<section>
		<div class="modal">
			<div class="modal-background"></div>
			<div class="modal-content">
			    <div class="container">
				  <div class="notification">
				    	시간표 modal
				    	
				    	<!-- 
				    		미완성 
				    		시간표 경우의 수가 렌더링 되는 div
				    	 -->
				  </div>
				</div>
		  	</div>
		  	<button class="modal-close is-large" aria-label="close"></button>
		</div>
	</section>
	
    <footer id="footer">

    </footer>
</body>
<script type="text/javascript">

<%-- 유저 선택 목록 저장 컨테이너 --%>
var selectionContainer;

<%-- 전체 수업 목록 저장 컨테이너 --%>
var searchContainer;

$(document).ready(function() {
	
	<%-- Global Variable Init--%>
	selectionContainer = [];
	searchContainer    = $.getLectureListAll($('.semester-select').val());	// 전체 수업목록 최초 1회에 가져옴
	
	<%--
		내가 선택한 수업 리스트에 강의를 추가하는 함수
		
		@author Dong-Min Seol
		@since  2019.05.06
	--%>
	$(document).on('change', '.result-data .el-checkbox', function(e) {
		var selectBox = $(this);
		var data = JSON.parse(selectBox.val());
		
		$.addSelection(data);
		$(this).closest('.result-data').addClass('checked');
		
		$.validateUserListSize();
	});
	
	<%--
		내가 선택한 수업 리스트에 강의를 제거하는 함수
		
		@author Dong-Min Seol
		@since  2019.05.06
	--%>
	$(document).on('click', '.rmSelection', function(e) {
		$.rmSelection( $(this).data('selection') );
	});
	
	<%--
		강의 선택 checkBox 변경 이벤트

		@author Dong-Min Seol
		@since  2019.05.05
	--%>
	$('.search-select').change(function() {
		
		var _learn_p = $('.search-select.learn_p').val();
		
		var option = {
			searchType : _learn_p == '0' ? 'selectBoxNoLp' : 'selectBox',
			semester   : $('.search-select.semester').val(), 
			department : $('.search-select.department').val(),
			learn_p    : _learn_p
		}
		
		$.renderLectureList(option);
	});
	
	<%-- init render --%>
	setTimeout(function() {
		$('.search-select').trigger('change');
		$.renderUserSelection();
	}, 1000);
	
	<%--
		강의명 검색 버튼 click Event
		
		@author Dong-Min Seol
		@since  2019.05.14
	--%>
	$('.serach-btn').click( function() {
		
		var searchInput = $('.lecture-search-input').val();
		if ( searchInput == '' ) { return false; }
		
		var option = {
			searchType : 'searchBox',
			keyword    : searchInput 
		};
		
		$.renderLectureList(option);
	});

	<%--
		선택한 시간표 목록 submit 함수
		
		@author Dong-Min Seol
		@since  2019.05.14
	--%>
	$('.submit-btn').click(function() {
		$.submitSelection(selectionContainer);
	});
	
	
	<%--
		사용자가 선택한 시간표 목록 임시 저장함수
		
		@author Dong-Min Seol
		@since  2019.05.14
	--%>
	$('.save-btn').click(function() {
		$.saveSelection(selectionContainer);
	});
	
	<%--
		모달 창 제거 버튼
		
		@author Dong-Min Seol
		@since  2019.05.14
	--%>
	$('.modal-close').click(function() {
		$(this).closest('.modal').removeClass('is-active');		
	});
});
<%-- 
	################################################
	#	                                           #
	#             document.ready() END             #
	#                                              #
	################################################
--%>

<%--
	학과별 수업 목록을 가져오는 Ajax 함수
	
	@param  학기 ex) 2019_1
	@author Dong-Min Seol
	@since  2019.05.05
--%>
$.getLectureListAll = function(semester) {
	
	var RESTurl = '${HOME}/timetable/lectureListAll/'+semester; 
	
	$.ajax({
		type : "GET",
		url : RESTurl,
		dataType : "json",
		traditional : true,
		success : function(msg) {
			var result = msg.result;
			if(result.REPL_CD == '000000') {
				searchContainer = result.lectureList;
			} else {
				searchContainer = null;
			}
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			searchContainer = null;
		}
	});
};

<%--
	수업 목록을 테이블에 렌더링 해주는 함수
	
	@author Dong-Min Seol
	@since  2019.05.05
--%>
$.renderLectureList = function(option) {
	
	// [1] 원본 데이터에서 option에 맞는 값 검색
	var dataList = [];
	
	switch (option.searchType) 
	{
	case 'selectBox':
	//  [1-1] selectBox (학기, 학과, 학점)으로 검색
		searchContainer.forEach( function(data) {
			if ( option.learn_p    == data.learn_p  &&
				 option.department == data.department ) { dataList.push(data); }	
		});
		break;
		
	case 'selectBoxNoLp':
	//	[1-2] selectBox (학기, 학과)로 검색
		searchContainer.forEach( function(data) {
			if (option.department == data.department ) { dataList.push(data); }	
		});
		break;
		
	case 'searchBox':
	//	[1-3] 검색어로 검색
		searchContainer.forEach( function(data) {
			if (data.lecture.indexOf(option.keyword) > -1) { dataList.push(data); }	
		});
		break;
	}
	
	var tbody = $('.search-result > tbody');
	
	// [2] 기존 row 제거
	tbody.empty();
	dataList.forEach( function(data) {
		
		var isChecked = data.selected == 'T' ? ' checked' : ''; 
		
		var node = '<tr class="result-data'+isChecked+'">									                     ' +
				   '    <td>' + data.department + '</td>									                     ' +
				   '    <td>' + data.grade      + '</td>									                     ' +
				   '    <td>' + data.lecture    + '</td>									                     ' +
				   '    <td>' + data.learn_p    + '</td>									                     ' +
				   '    <td>							   								                         ' +
				   '    	<label class="el-checkbox-wrapper">	   				 			                     ' +
				   '    		<span  class="checkmark"></span>							                     ' +
				   '			<input class="el-checkbox" type="checkbox" value=\''+ JSON.stringify(data) + '\'>' +
				   '    	</label>							   							                     ' +
				   '	</td>															                         ' +
				   '</tr>																                         ';
				   
		tbody.append(node);
	});
}

<%--
	선택한 강의를 유저 선택 목록에 담는 함수
	
	@author Dong-Min Seol
	@since  2019.05.05
--%>
$.addSelection = function(data) {
	
	// [1] 전체 수업 목록에서 해당 수업 선택 처리
	searchContainer.forEach( function(origin) {
		if ( origin.lecture    == data.lecture &&
			 origin.department == data.department ) { origin.selected = 'T';}	
	});
	
	// [2] 사용자 선택 목록에서 해당 수업 추가
	selectionContainer.push(data);
	
	// [3] 사용자 선택 목록 렌더링
	$.renderUserSelection();
	
	// [4] 선택 학점 목록 렌더링
	$.renderSelectionLearnTotal();
}

<%--
	유저 선택 목록에서 해당 강의를 제거하는 함수
	
	@param  제거할 데이터
	@author Dong-Min Seol
	@since  2019.05.05
--%>
$.rmSelection = function(data) {
	
	var searchList = $('.search-result > tbody');
	
	// [1] 전체 수업 목록에서 해당 수업 선택 해제 처리
	searchContainer.forEach( function(origin) {
		if ( origin.lecture    == data.lecture &&
		 	 origin.department == data.department ) { data.selected = "F";}	
	});
	
	
	// [2] 사용자 선택 목록에서 해당 수업 제거
	selectionContainer.forEach( function(user, idx) {
		if ( user.lecture    == data.lecture &&
			 user.department == data.department ) { selectionContainer.splice(idx, 1); }
	});
	
	// [3] 사용자 선택 목록 렌더링
	$.renderUserSelection();
	
	// [4] 선택 학점 계산 결과 렌더링
	$.renderSelectionLearnTotal();
	
	// [5] 기존에 렌더링 되어있는 데이터 display:hidden 제거
	searchList.find('.result-data.checked').each( function() {
		
		if ( $(this).children('td').eq(2).text() == data.lecture && 
			 $(this).children('td').eq(0).text() == data.department) {
			
			// [5-1] check class 상태 제거
			$(this).removeClass();
			$(this).addClass('result-data');
			
			// [5-2] check box 초기화
			$(this).find('input[type="checkbox"]').prop('checked', false);
			return false;
		}
	});
}

<%--
	유저 선택 목록에 데이터를 렌더링 하는 함수
	
	@author Dong-Min Seol
	@since  2019.05.05
--%>
$.renderUserSelection = function() {
	
	var selection = $('.selection-list > tbody');
	
	// [1] 기존 목록 초기화
	selection.empty();
	
	// [2] 사용자 선택 목록 렌더링
	selectionContainer.forEach( function(data) {	
		var node =  
			'<tr class="selection-data">																' + 
			'	<td>'+ 	data.department + '</td> 														' +
			'	<td>'+	data.lecture    + '</td> 														' +
			'	<td>'+	data.learn_p    + '</td> 														' +
			'	<td>																					' +
			'		<button class="btn rmSelection" data-selection=\''+ JSON.stringify(data) + '\'>		' +
			'			X 																				' +
			'		</button>																			' +
			'	</td>																					' +
			'</tr>					      																';

		selection.append(node);
	});
}

<%--
	유저가 선택학 수업 목록들의 학점 총합을 계산 후
	화면에 렌더링해주는 함수
--%>
$.renderSelectionLearnTotal = function() {
	var learn_p = 0;
	
	// [1] 유저목록 학점 계산
	selectionContainer.forEach(function (data){
		learn_p += parseInt(data.learn_p);	
	});
	
	$('.selectionTotal').text(learn_p);
}

<%--
	유저가 선택한 수업 목록이 24 학점을 넘는지
	검증하는 함수
	
	@author Dong-Min Seol
	@since  2019.05.13
--%>
$.validateUserListSize = function() {
	var learn_p = 0;			// 학점
	
	// [1] 유저목록 학점 계산
	selectionContainer.forEach(function (data){
		learn_p += parseInt(data.learn_p);	
	});
	
	// [2] 24 학점 초과시 마지막 선택목록 제거
	if(learn_p > 24) {
		var lastSelection = selectionContainer.pop(); // 유저 선택 배열 마지막 제거
		$.rmSelection(lastSelection) 				  // 마지막 선택 목록 제거			
		$.validateUserListSize();					  // 검증 재귀호출
	}
}

<%--
	유저가 선택한 수업목록을 서버로 전송하는  함수
	
	@author Dong-Min Seol
	@since  2019.05.13
--%>
$.submitSelection = function(jsonArr) {
	
	var RESTurl = '${HOME}/timetable/getComputedTimeTable';
	var param = {
		'lectureList' : JSON.stringify(jsonArr),
		'semester'    : $('.semester-select').val()
	}
	
	$.ajax({
		type : "GET",
		url : RESTurl,
		dataType : "json",
		data : param, 
		traditional : true,
		success : function(msg) {
			var result = msg.result;
			if(result.REPL_CD == '000000') {
				console.log(msg.result.computedList);
			} else {
				alert(result.REPL_CD);
			}
		}
	});
}

</script>

</html>