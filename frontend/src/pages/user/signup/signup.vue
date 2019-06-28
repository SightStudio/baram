<template>
    <f7-page>
        
        <f7-block class="singup-wrapper">

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
                    <f7-list-button title="facebook으로 회원가입"></f7-list-button>
                    <f7-list-button title="Kakao로 회원가입"></f7-list-button>
                    <f7-list-button title="Naver로 회원가입"></f7-list-button>
               </f7-list>
            </f7-block>
        </f7-block>
    </f7-page>
</template>

<script>
import logo from '{COMPONENT}/logo/logo.vue'
export default { 
    components : {
    logo
  },
}
</script>

<style scoped>
    .singup-wrapper { width: 100%; height:100%; }
</style>