<%@page import="java.util.List"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="com.kh.spring.member.model.dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원관리" name="title"/>
</jsp:include>
<table class="table w-75 mx-auto">
	<thead>
		<tr>
		  <th scope="col">번호</th>
		  <th scope="col">아이디</th>
		  <th scope="col">이름</th>
		  <th scope="col">권한</th>
		  <th scope="col">수정</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="member" varStatus="vs">
			<tr data-member-id="${member.memberId}">
				<td>${vs.count}</td>
				<td>${member.memberId}</td>
				<td>${member.name}</td>
				<td>
					<% 
						
					%>
					<input type="checkbox" name="authority" id="role-user-${vs.count}" value="ROLE_USER" <%= hasRole(pageContext, "ROLE_USER") ? "checked" : "" %>/>
					<label for="role-user-${vs.count}">일반</label>
					&nbsp;
					<input type="checkbox" name="authority" id="role-admin-${vs.count}" value="ROLE_ADMIN" <%= hasRole(pageContext, "ROLE_ADMIN") ? "checked" : "" %>/>
					<label for="role-admin-${vs.count}">관리자</label>
				</td>
				<td>
					<button type="button" class="btn btn-outline-primary btn-update-authority" value="${member.memberId}">수정</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script>
document.querySelectorAll(".btn-update-authority").forEach((btn) => {
	btn.addEventListener('click', (e) => {
		const memberId = e.target.value;
		console.log(memberId);
		const tr = document.querySelector(`[data-member-id=\${memberId}]`);
		const authorities = [...tr.querySelectorAll("[name=authority]:checked")].map((checkbox) => checkbox.value);
		console.log(authorities);
	
		const headers = {
				"${_csrf.headerName}" : "${_csrf.token}"
		};
		
		const param = {
				memberId,
				authorities
			};
		$.ajax({
			url : "${pageContext.request.contextPath}/admin/memberRoleUpdate.do",
			method : "POST",
			headers,
			contentType: 'application/json; charset=utf-8',
			data : JSON.stringify(param),
			success(response){
				console.log(response);
				const {msg} = response;
				alert(msg);
				location.reload();
			},
			error: console.log
		});
	});
});
</script>
<%!
	/**
	 * 메소드선언 -> servlet변환시에 메소드등록
	 */
	private boolean hasRole(PageContext pageContext, String role){
		Member member = (Member) pageContext.getAttribute("member");
		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) member.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(role));
	}

%>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
