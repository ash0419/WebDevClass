'use strict';

//글 제목 클릭
function clkArticle(typ, i_board) {
	var url = `/bDetail?typ=${typ}&i_board=${i_board}`;
	location.href = url; // 주소값 이동
}
function chk() {
	// var frm = document.querySelector('#frm'); 아이디만 써도 안써도 된다. 생략
	console.log('frm : ' + document.forms.frm.title.value);
	if (chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')) {
		return false;
	}
}