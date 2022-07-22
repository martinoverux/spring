<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8" />
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="Menu REST API" name="title"/>
</jsp:include>
	<style>
	.menu-test, .result {
		width : 75%;
		margin : 0 auto; 
	}
	</style>
	<div id="menu-container" class="text-center">
		<!-- 1.GET /menu-->
        <div class="menu-test">
            <h4>전체메뉴조회(GET)</h4>
            <input type="button" class="btn btn-block btn-outline-success btn-send" id="btn-menus" value="전송" />
        </div>
        <div class="result" id="menus-result"></div>
        
        <script>
        document.querySelector("#btn-menus").addEventListener("click", (e) => {
        	$.ajax({
        		url : "${pageContext.request.contextPath}/menu",
        		method : "GET", 
        		success(data) {
        			console.log(data);
        			renderResultTable(data, "menus-result");
        		},
        		error: console.log
        	});
        });
        
        const renderResultTable = (data, target) => {
        	
        	const container = document.querySelector("#" + target);
        	const table = document.createElement("table");
        	table.classList.add('table');
        	const header = document.createElement("tr");
        	const td1 = document.createElement("td");
        	td1.innerHTML = "번호";
        	const td2 = document.createElement("td");
        	td2.innerHTML = "음식점";
        	const td3 = document.createElement("td");
        	td3.innerHTML = "메뉴";
        	const td4 = document.createElement("td");
        	td4.innerHTML = "가격";
        	const td5 = document.createElement("td");
        	td5.innerHTML = "타입";
        	const td6 = document.createElement("td");
        	td6.innerHTML = "맛";
        	header.append(td1, td2, td3, td4, td5, td6);
        	table.append(header);
        	
        	if(data.length){
        		data.forEach((menu) => {
        			const {id, restaurant, name, price, type, taste} = menu;
        			console.log(id, restaurant, name, price, type, taste);
        			
        			const tr = document.createElement("tr");
        			const td1 = document.createElement("td");
                	td1.innerHTML = id;
                	const td2 = document.createElement("td");
                	td2.innerHTML = restaurant;
                	const td3 = document.createElement("td");
                	td3.innerHTML = name;
                	const td4 = document.createElement("td");
                	td4.innerHTML = price.toLocaleString()+"원";
                	const td5 = document.createElement("td");
                	const span5 = document.createElement("span");
                	span5.classList.add("badge");
                	switch(type){
                	case 'kr' : span5.classList.add("badge-primary"); break;
                	case 'ch' : span5.classList.add("badge-secondary"); break;
                	case 'jp' : span5.classList.add("badge-light"); break;
                	}
                	span5.innerHTML = type;
                	td5.append(span5);
                	const td6 = document.createElement("td");
                	const span6 = document.createElement("span");
                	span6.classList.add("badge");
                	switch(taste){
                	case 'hot' : span6.classList.add("badge-danger"); break;
                	case 'mild' : span6.classList.add("badge-warning"); break;
                	}
                	span6.innerHTML = taste;
                	td6.append(span6);
                	tr.append(td1, td2, td3, td4, td5, td6);
                	table.append(tr);
        		});
        	}
        	else {
        		const tr = document.createElement("tr");
        		const td = document.createElement("td");
        		td.colSpan = 6;
        		td.innerHTML = "검색된 결과가 없습니다.";
        		tr.append(td);
        		table.append(tr);
        	}
        	container.innerHTML = ""; // 초기화
        	container.append(table);
        	
        };
        
        </script>
                <!-- 2.GET /menu/type/kr /menu/type/ch /menu/type/jp 타입별 조회 -->
        <div class="menu-test">
            <h4>메뉴 타입별 조회(GET)</h4>
            <select class="form-control" id="typeSelector">
            <option value="" disabled selected>음식타입선택</option>
            <option value="kr">한식</option>
            <option value="ch">중식</option>
            <option value="jp">일식</option>
            </select>
        </div>
        <div class="result" id="menuType-result"></div>
        <script>
        document.querySelector("#typeSelector").addEventListener('change', (e) => {
        	const selected = e.target.value;
        	console.log(selected);
        	
        	$.ajax({
        		url : `${pageContext.request.contextPath}/menu/type/\${selected}`,
        		method : "GET", 
        		success(data) {
        			console.log(data);
        			renderResultTable(data, "menuType-result");
        		},
        		error: console.log
        	});
        });
        </script>
        
    <!-- 3.GET menu/taste/mild menu/taste/hot -->
    <div class="menu-test">
        <h4>메뉴 맛별 조회(GET)</h4>
        <div class="form-check form-check-inline">
            <input type="radio" class="form-check-input" name="taste" id="get-hot" value="hot">
            <label for="get-hot" class="form-check-label">매운맛</label>&nbsp;
            <input type="radio" class="form-check-input" name="taste" id="get-mild" value="mild">
            <label for="get-mild" class="form-check-label">순한맛</label>
        </div>
    </div> 
    <div class="result" id="menuTaste-result"></div>

    <script>
    document.querySelectorAll(".form-check-input").forEach((checkbox) =>{
    	checkbox.addEventListener('click', (e) => {
    		const taste = e.target.value;
    		console.log(e.target);
    		$.ajax({
    			url : `${pageContext.request.contextPath}/menu/taste/\${taste}`,
    			method : "GET",
    			success(data){
    				console.log(data);
    				renderResultTable(data, "menuTaste-result");
    			},
    			error: console.log
    		});
    	});
    });
    </script>
    <!-- POST /menu -->
    <div class="menu-test">
        <h4>메뉴 등록하기(POST)</h4>
        <form id="menuEnrollFrm" name="menuEnrollFrm">
            <input type="text" name="restaurant" placeholder="음식점" class="form-control" required/>
            <br />
            <input type="text" name="name" placeholder="메뉴" class="form-control" required/>
            <br />
            <input type="number" name="price" placeholder="가격" class="form-control" required/>
            <br />
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="type" id="post-kr" value="kr" checked>
                <label for="post-kr" class="form-check-label">한식</label>&nbsp;
                <input type="radio" class="form-check-input" name="type" id="post-ch" value="ch">
                <label for="post-ch" class="form-check-label">중식</label>&nbsp;
                <input type="radio" class="form-check-input" name="type" id="post-jp" value="jp">
                <label for="post-jp" class="form-check-label">일식</label>&nbsp;
            </div>
            <br />
            <div class="form-check form-check-inline">
                <input type="radio" class="form-check-input" name="taste" id="post-hot" value="hot" checked>
                <label for="post-hot" class="form-check-label">매운맛</label>&nbsp;
                <input type="radio" class="form-check-input" name="taste" id="post-mild" value="mild">
                <label for="post-mild" class="form-check-label">순한맛</label>
            </div>
            <br />
            <input type="submit" class="btn btn-block btn-outline-success btn-send" value="등록" >
        </form>
    </div>
		<script>
		document.menuEnrollFrm.addEventListener("submit", (e) => {
			e.preventDefault();
			
			const frm = e.target;
		/* 	const menu = {
				restaurant : frm.restaurant.value,
				name : frm.name.value,
				price : frm.price.value,
				type : frm.type.value,
				taste : frm.taste.value
			}; */
			const menu = {};
			const frmData = new FormData(frm);
			frmData.forEach((v, k) => menu[k] = v);
			
			console.log(menu);
			console.log(JSON.stringify(menu));
			

			// POST /menu 등록 insert
			$.ajax({
				url : '${pageContext.request.contextPath}/menu',
				method : "POST",
				contentType: 'application/json; charset=utf-8',
				data : JSON.stringify(menu),
				success(response) {
					console.log(response);
					const {msg} = response;
					alert(msg);
					frm.reset();
				},
				error: console.log
				
			})
			
		});
		</script>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>













