<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>[PhoneBook4]</h1>
	
	<p>
		수정 화면입니다. 아래 항목을 수정하고 "수정"버튼을 클릭하세요
	</p>
	
	<form action="/phonebook4/phone/update" method="get">
		이름(name): <input type="text" name="name" value="${pVo.name}" > <br>
		핸드폰(hp): <input type="text" name="hp" value="${pVo.hp}" > <br>
		회사(company): <input type="text" name="company" value="${pVo.company}" > <br>
		코드(id): <input type="text" name="personId" value="${pVo.personId}" readonly > <br>
		<button type = submit> 수정 </button> <br><br>
	</form>
</body>
</html>