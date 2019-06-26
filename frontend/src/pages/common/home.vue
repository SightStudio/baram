<!--
   메인 페이지

    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
  <f7-page class="index">

    <f7-block class="logo">
      <img src="@/assets/image/common/logo.png" alt="">
    </f7-block>

    <f7-list class="login-form" no-hairlines-ios>
      <f7-list-input 
        label="아이디"
        name="username"
        placeholder="Username"
        type="text"
        color="white"
      />
      
      <f7-list-input
        label="비밀번호"
        name="password"
        type="password"
        placeholder="Password"
      />
    </f7-list>

    <f7-block strong no-hairlines-ios>

      <f7-row>
        <f7-col>
          <f7-button fill raised >로그인 하기</f7-button>
        </f7-col>
      </f7-row>

      <f7-row>
        <f7-col>
          <!-- <f7-button fill raised href="/gmap">로그인 없이 시작</f7-button> -->
          <f7-button fill raised href="/main">로그인 없이 시작</f7-button>
        </f7-col>
      </f7-row>

      <f7-row>
        <f7-col>
          <f7-button fill raised href="/user/signup">회원 가입</f7-button>
        </f7-col>
      </f7-row>
    </f7-block>
  </f7-page>
</template>
<script>
export default {
  
  methods : {
    initCss : () => {
      let root = document.documentElement;
      root.style.setProperty('--f7-list-bg-color', 'transparent');
    }
  },
  mounted(){
    this.initCss();
  }
}
</script>
<style scoped>
  .index { background: center / contain no-repeat url("~@/assets/image/common/bg_home.jpg");}
  .index .block-strong {background-color: transparent; border:none; padding-top: 0 ; margin-top: 10px;}
  .index .block-strong .row{margin-top: 25px;}
  .index .login-form {background-color: transparent; margin: 0;}
  .index .login-form ul{background-color: transparent;}
  .index .login-form input {background-color: transparent;}
  .index .logo {width : 100%; height :200px; text-align: center; font-size: 0; margin-top: 30px;}
  .index .logo img{width : 80%; height :200px; display: inline-block;}
</style>
