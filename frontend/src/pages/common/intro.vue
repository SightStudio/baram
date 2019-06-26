<!--
    앱 시작시 실행되는 인트로 페이지

    @author Dong-Min Seol
    @since  2019.05.10
-->
<template>
    <f7-page class="intro">
        <f7-link href="/index" class="start">
            <p>클릭하여 시작</p>
        </f7-link>
    </f7-page>
</template>
<script>
    export default {}
</script>
<style scoped>
    .intro { width  :100%; height : 100%; background: center / contain no-repeat url('~@/assets/image/common/intro.png'); }
    .start{ display: block; text-align: center; font-size : 2em; margin-top: 100%; }
</style>
