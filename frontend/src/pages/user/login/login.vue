<template>
    <f7-page 
		class="user-singin"
		@page:beforein="validLoginUser">

		<!-- <f7-navbar title="로그인 하기"></f7-navbar> -->
		
		<f7-appbar>
			<div class="left">
				<f7-button color="black" back>
					<f7-icon material="arrow_back"></f7-icon>
				</f7-button>
			</div>

			<div class="left">
				<p>로그인 하기</p>
			</div>

			<div class="right">
				<f7-button color="black">
					<f7-icon f7="home"></f7-icon>
				</f7-button>
			</div>
		</f7-appbar> <!-- navbar END -->

		<logo></logo>
		
		<f7-list no-hairlines>
			<f7-list-input
				label="아이디"
				type="text"
				placeholder="ID"
				required
				validate
				clear-button
				:value="id"
    			@input="id = $event.target.value"
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>

			<f7-list-input
				label="비밀번호"
				type="password"
				placeholder="비밀번호를 입력해주세요"
				required
				validate
				clear-button
				:value="pw"
    			@input="pw = $event.target.value"
			> <f7-icon icon="demo-list-icon" slot="media"></f7-icon>
			</f7-list-input>
		</f7-list>

		<f7-block strong no-hairlines-ios>
			<f7-button fill raised @click="submit">로그인</f7-button>
			<br>
			<f7-button fill raised @click="submitKakao">카카오 로그인하기</f7-button>
			<br>
			<f7-button fill raised href="/user/signup">회원가입 하기</f7-button>
		</f7-block>
    </f7-page>
</template>

<script>
import logo 		 from '{COMPONENT}/logo/logo.vue'
import { getDevice } from '{UTIL}/device/device.js'
export default { 
	components : {
    	logo
	},
	data() {
		return {
			id : '',
			pw : '',
			deviceName : getDevice(),
		}
	},
	methods : {
		submit () {
			let id = this.id;
			let pw = this.pw;
			
			if(id && pw) {
			
			// [1] 로그인 actions 실행
			this.$store.dispatch('LOGIN', {id, pw})
					   .then( REPL_CD => {
							let REPL_MSG = REPL_CD == '000000' ? '로그인에 성공하였습니다' :
																 '로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요'
							
							this.$f7.dialog.alert(REPL_MSG, '로그인', () => {
								if(REPL_CD == '000000') {
									this.$f7router.navigate('/gmap', { clearPreviousHistory : true })
								}
							})
					   })
			}
		},
		submitKakao() {
			if(this.$f7.device.desktop) {
				this.$f7.dialog.alert('데스크탑', '장치')
			} else {
				this.$f7.dialog.alert('데스크탑 아님', '장치')
			}
		},
		/**
		 * 로그인 한 유저인지 판별하는 함수
		 */
		validLoginUser() {
			const userToken = this.$store.state.userJwt;
			if(userToken != null) {
				this.$f7.dialog.alert('이미 로그인된 유저입니다. <br> 지도로 이동합니다', '알림', () => {
					this.$f7router.navigate('/gmap', { clearPreviousHistory : true })
				});
			}
		}, // validLoginUser() end 
	}
}
</script>
<style scoped>

</style>