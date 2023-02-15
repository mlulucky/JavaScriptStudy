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
            <li><a href="l11_image_slide.html">11.이미지 슬라이드 만들기</a></li>
            <li><a href="l12_class.html">12.자바스크립트의 클래스 문법(static sugar)</a></li>
            <li><a href="l13_getter_setter.html">13.class 의 getter setter 와 캡슐화</a></li>
            <li><a href="l14_emp_list.do">14.자바에서 DTO 사용해 보기(Emp.List)</a></li>
            <li><a href="l15_timing_function.html">15.타이밍 함수 setTimeout 과 setInterval(== 멀티스레드 new Thread)</a></li>
            <li><a href="l16_this_bind.html">16.함수의 binding 과 this 와 화살표 함수</a></li>
            <li><a href="l17_promise.html">17.Promise 로 멀티스레드 동기화</a></li>
            <li><a href="l18_promise_chainning.html">18.Promise 채이닝과 프라미스화로 비동기 코드(멀티스레드)를 계속 동기화하기</a></li>
            <li><a href="l19_ajax_xmlhttprequest.html">19.Ajax 와 XMLHttpRequest 객체(프론트+백엔드)</a></li>
            <li><a href="">20.Ajax 와 fetch api 와 Promise</a></li>
            <li><a href="">21.Ajax 와 async 함수(js 수업 끝~)</a></li>
            <li><a href="">22.prototype 의 상속</a></li>
            <%--            (li>a{$.})*4--%>
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
/*

    js 배우는 이유
    1개의 언어라도 제대로 알아야 한다. -> 5년차 쯤 되야 언어를 왜 쓰는지 객체지향, 인터페이스 등 언어 특징을 알게됨
        -> 한개만 하다보면 다른 언어로 못 넘어간다. (유연성 x)
    웹 이슈 - js 이슈가 많다. js 가 대세
    js 를 잘하면 좋다.

        자바를 잘하려면
    다른 언어를 배우는 것도 좋다.(자바에 대해 더 배울수있다)
    (자바의 특징과 다른 언어들의 특징을 비교하며 배운다. => 이때 장단점이 보여야 비로소 내 언어가 된다)

    프로그래밍 갈림길
    자바만 배워서는 안된다

    언어를 배우는 것에 두려움이 없어야 한다.

        js 배우면 배울 수 있다 (특히 L12 js 클래스 ~ 22 Ajax, async)
        -> node js -> express js
        -> react js
        -> next js
        -> view js
        -> angular js

    회사에서 찾는다
*/

</script>

</body>
</html>