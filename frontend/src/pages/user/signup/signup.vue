<template>
    <f7-page>
        <f7-block class="singup-wrapper">
            <f7-row no-gap>
                <f7-col width="15"></f7-col>
                <f7-col width="70"></f7-col>
                <f7-col width="15">
                <f7-button color="black">
                    <f7-icon f7="home"></f7-icon>
                </f7-button>
                </f7-col>
            </f7-row> <!-- navbar END -->

            <logo></logo>
            <f7-button 
                fill 
                large
                href="/user/signup/baram"
                class="write-btn">
                회원가입
            </f7-button>

            <f7-block-title medium>SNS 계정 회원가입</f7-block-title>
            <f7-block>
                <f7-block-header>SNS 아이디를 이용하여 회원으로 가입합니다</f7-block-header>

                <f7-list inset>
                    <f7-list-button class="btn-kakao"    color="transparent" title="Kakao로 가입"></f7-list-button>
                    <f7-list-button class="btn-facebook" color="white" title="facebook으로 가입"></f7-list-button>
               </f7-list>
            </f7-block>
        </f7-block>

        <v-facebook-login 
            app-id="329212417704524">
            <template v-slot:login>페이스북으로 회원가입</template>
        </v-facebook-login>
            
        
    </f7-page>
</template>

<script>
import VFacebookLogin from 'vue-facebook-login-component'
import logo from '{COMPONENT}/logo/logo.vue'
export default { 
  components : {
    logo,
    VFacebookLogin
  },
}
</script>

<style scoped>
    .singup-wrapper { width: 100%; height:100%; }
    .singup-wrapper .btn-facebook{background-color: #3b5998; }
    .singup-wrapper .btn-kakao   {background-color: #f7e600; color: brown !important;}
</style>