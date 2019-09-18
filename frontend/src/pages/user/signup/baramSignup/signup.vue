<!--
    회원 가입 페이지

    @author Dong-Min Seol
    @since  2019.05.16
-->
<template>
  <f7-page>
    <f7-appbar>
		<div class="left">
			<f7-button color="black" back>
				<f7-icon material="arrow_back"></f7-icon>
			</f7-button>
		</div>

		<div class="left">
			<p> 회원 가입 </p>
		</div>

		<div class="right">
			<f7-button href="/" color="black back">
				<f7-icon f7="home"></f7-icon>
			</f7-button>
		</div>
	</f7-appbar> <!-- navbar END -->

    <logo></logo>

    <f7-list>
		<f7-list-input
			name="id"
			label="아이디"
			type="text"
			placeholder="id"
			required
			validate
			clear-button
			:value="id"
    		@input="id = $event.target.value"
		></f7-list-input>

		<f7-list-input
			name="pw"
			label="비밀번호"
			type="password"
			placeholder="pw"
			info="Default pw validation"
			required
			validate
			clear-button
			:value="pw"
    		@input="pw = $event.target.value"
		></f7-list-input>

		<f7-list-input
			name="email"
			label="E-mail"
			type="email"
			placeholder="Your e-mail"
			info="Default e-mail validation"
			required
			validate
			clear-button
			:value="email"
    		@input="email = $event.target.value"
		></f7-list-input>

		<f7-list-input
			name="name"
			label="이름"
			type="text"
			placeholder="이름"
			info="With custom error message"
			required
			validate
			clear-button
			:value="name"
    		@input="name = $event.target.value"
		></f7-list-input>
    </f7-list>

    <f7-block strong no-hairlines-ios>
      <f7-row>
        <f7-col>
          <f7-button fill raised @click="submit">회원 가입하기</f7-button>
        </f7-col>
      </f7-row>
    </f7-block>
  </f7-page>
</template>

<script>
import logo from '{COMPONENT}/logo/logo.vue'
export default {
  components : {
    logo
  },
  data() {
	  return {
		id        : '',
		pw        : '',
		name      : '',
		email     : '',
		phone_num : '',
	  }
  },
  methods : {
	submit : function() {

		let data = new URLSearchParams();
		data.append('id'       , this.id);
		data.append('pw'       , this.pw);
		data.append('name'     , this.name);
		data.append('email'    , this.email);
		data.append('phone_num', this.phone_num);

		this.$http.post('api/user/signin', data)
			.then ( (res) => {
				let response = res.data.response
				let REPL_MSG = REPL_CD == '000000' ? '회원가입에 성공하였습니다' :
												     '회원가입에 실패하였습니다. 확인해주세요'
				
				this.$f7.dialog.alert(REPL_MSG, '로그인', () => {
					if(REPL_CD == '000000') {
						this.$f7router.navigate('/user/login', { clearPreviousHistory : true })
					}
				})
			})
	  }
  }
}
</script>
<style scoped>
.logo { width: 100%; height: 200px; text-align: center; font-size: 0; margin-top: 30px; }
.logo img{ width: 100%; height:200px; display: inline-block; }

</style>