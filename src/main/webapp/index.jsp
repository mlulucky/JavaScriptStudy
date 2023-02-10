<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1>JavaScript 에 대해 배워보자</h1>
    <p>js의 역사 : 브라우저를 동적(브라우저가 이벤트에 반응)으로 만들기 위해 개발된 언어로
        JAVA 가 굉장히 인기있는 언어라 이름에 JAVA를 붙여서 JavaScript 가 되었다.
        지금은 java 에서 이름사용을 못하게 하여 ECMAScript es로 불린다. es6는 표준화된 버전을 의미한다.
    </p>
    <p>바닐라 js : 자바스크립트로 된 라이브러리나 프레임워크(jquery, react, viewjs...)를 사용하지 않고 순수한 js 문법만 사용</p> <!-- 라이브러리, 프레임워크는 문법이 간단하해지고 살짝 무거워진다. 문법도 조금씩 다르다. 프레임워크로 공부하면 자바스크립트를 이해하기 어려워진다. 처음 공부하는 사람은 바닐라js로 공부하기는 것을 추천 -->
    <p>옛날옛날 10년전에는 바닐라 js로 개발이 불가능 했다. 지금은 es6으로 표준화되면서 가능해졌다.(크로스 브라우징)</p>
    <nav>
        <!-- ul>(li>a{js수업})*10 -->
        <!-- ul>(li>a{$})*10 $ 달러쓰면 번호 생성된다. 번호 2개생성시 $$ 두개 입력 --> <!-- <li><a href="">1</a></li> -->
        <ul>
            <li><a href="l00_variable_const.html">0.변수와 상수(var,let,const)</a></li>
            <li><a href="l01_var_hoisting.html">1. window 필드 var 변수의 hoisting 현상(*면접볼때 질문 1순위)</a></li>
            <li><a href="l02_primitive_type.html">2.기본데이터타입(number,bigint,string,boolean,symbol,null,undefined)</a></li>
            <li><a href="l03_object_type.html">3.자료형(Object {}, ProtoType)</a></li>
            <li><a href="l04_array_list.html">4.배열(ArrayList [])</a></li>
            <li><a href="l05_function.html">5.함수와 프로토타입(hoisting 현상)(*면접볼때 질문 1순위)</a></li>
            <li><a href="l06_for.html">6.반복문(for, 내부반복문, iterator for)</a></li>
            <li><a href="l07_if.html">7.if, switch</a></li>
            <li><a href="l08_array_iteration.html">8.배열의 내부 반복문(Iterator)과 정렬 (const)</a></li>
            <li><a href="l09_document_traveling.html">9.document DOMTree 와 node 객체</a></li>
            <li><a href="l10_event.html">10.event.html</a></li>
            <li><a href="l11_image_slide.html">11.JSON 과 Object</a></li>
            <li><a href="">12.브라우저 객체 window 와 document</a></li>
            <li><a href="">13.js를 참조하는 방법들(태그,파일 (defer async))</a></li>
        </ul>
    </nav>

<script>
    // HTML 에서 js의 실행 순서는 DOMTree 와 연관있다
    // HTML 문서가 브라우저에서 로딩되면서 스크립트 태그가 있거나 실행한다.
    // 만약 스크립트 문서가 참조되고 있으면 다운받고 실행한다.
    // alert("안녕하세요 스크립트로 경고창을 생성했습니다!");
    console.log("안녕하세요 스크립트로 경고창을 생성했습니다!")
</script>

<script>
    // 자바는 array와 arraylist가 2개 있는데, 자바스크립트는 arraylist 하나로 통일
    // html의 파일이름은 _ 를 이용해서 소문자로 쓰는게 좋다 -> 왜? 대소문자를 구분못하는 서버가 있다. > l00_variable_const.html
</script>

</body>
</html>