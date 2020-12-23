'use strict';

//글 제목 클릭
function clkArticle(i_board) {
	var url = `detail?i_board=${i_board}`;
	location.href = url; //주소값 이동
}

// 삭제 버튼 클릭
function clkDel(i_board, typ) {
	if (confirm('삭제 하시겠습니까?')) {
		location.href = `del?i_board=${i_board}&typ=${typ}`;
	}
}

function clkCmtDel(i_cmt, i_board) {
	if (confirm('삭제 하시겠습니까?')) {
		location.href = `cmt/del?i_cmt=${i_cmt}&i_board=${i_board}`;
	}
}

// 지금은 사용 X, 혹시나 나중에 욕이 있는지 체크하는 용도로 사용
function chk() {
	// var frm = document.querySelector('#frm'); 아이디만 써도 안써도 된다. 생략
	console.log('frm : ' + document.forms.frm.title.value);
	if (chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')) {
		return false;
	}
}

// 댓글에서 수정버튼 클릭 > 수정FORM 나타나게 처리
function clkCmtMod(i_cmt) {
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.remove('cmd_mod_form'); // class element만 유일하게 접근할 때 classList를 사용
	console.log(trForm);
}

function clkCmtClose(i_cmt) {
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.add('cmd_mod_form');
}
