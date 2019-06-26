<!--
    회원 가입 페이지

    @author Dong-Min Seol
    @since  2019.05.16
-->
<template>
  <f7-page>

    <f7-block class="radio-wrapper">
      <f7-block>
        <label class="isSmoker">
          <img src="@/assets/image/user/smoker.png" alt="비흡연자">
          <input type="radio" name="isSmoker" value="smoker">
          <p class="bottom">흡연자</p>
        </label>

        <label class="isSmoker">
          <img src="@/assets/image/user/nonSmoker.png" alt="비흡연자">
          <input type="radio" name="isSmoker" value="nonSmoker">
          <p class="bottom">비흡연자</p>
        </label>
      </f7-block>
    </f7-block>

    <f7-list>
      <f7-list-input
        label="E-mail"
        type="text"
        placeholder="E-mail을 입력해주세요"
        clear-button
      > </f7-list-input>

      <f7-list-input
        label="Name"
        type="text"
        placeholder="Your name"
        clear-button
      > </f7-list-input>

      <f7-list-input
        label="Name"
        type="text"
        placeholder="Your name"
        clear-button
      > </f7-list-input>

      <f7-list-input
        label="Name"
        type="text"
        placeholder="Your name"
        clear-button
      > </f7-list-input>
      
    </f7-list>

    <f7-block strong no-hairlines-ios>
      <f7-row>
        <f7-col>
          <f7-button fill raised>회원 가입하기</f7-button>
        </f7-col>
      </f7-row>
    </f7-block>
  </f7-page>
</template>

<script>
export default {}
</script>
<style scoped>
.radio-wrapper {margin : 20px 0; text-align: center;}
.radio-wrapper .isSmoker{ display : inline-block; width: 49%;} 
.radio-wrapper .isSmoker input {display:none;}
.radio-wrapper .isSmoker img {width : 90%;}
.radio-wrapper .isSmoker .bottom {text-align: center; font-size: 2em; font-weight: bold;}
</style>