<template>
  <f7-page>
    <f7-block class="smoke-register-wrapper">
      <f7-block-title medium>위치 등록</f7-block-title>
      
      <f7-block class="logo">
          <img src="@/assets/image/common/logo.png" alt="">
      </f7-block>
      
      <f7-block class="form-list">
        <fileInput
          name="ss"
          placeholder="1. 위치를 알려주세요!"
          bottomDesc="( 필수 사항입니다. )">
        </fileInput>

        <fileInput
          name="ss"
          placeholder="2. 상세정보를 입력해주세요">
        </fileInput>

        <fileInput
          name="ss"
          placeholder="3. 사진을 등록해주세요"
          bottomDesc="( 필수 사항입니다. )">
        </fileInput>
        
      </f7-block> <!-- .form-list END -->
    </f7-block> <!-- .smoke-register-wrapper END -->

  </f7-page> 
</template>

<script>
import fileInput   from '{COMPONENT}/form/file/fileInput.vue'
import radioSwitch from '{COMPONENT}/form/switch/switch-radio.vue'
export default { 
  components: {
    fileInput,
    radioSwitch
  },
  data () {
    return {
      radioList : [
        { desc : '금연구역', name : 'isSmoke', val : 'smoke' },
        { desc : '흡연구역', name : 'isSmoke', val : 'nonSmoke' }
      ],
    }
  }
}
</script>

<style scoped>
.smoke-register-wrapper {width: 100%; height: 100%;}
.smoke-register-wrapper .logo { width: 90%; height: 100px; text-align: center; font-size: 0; margin-top: 30px;}
.smoke-register-wrapper .logo img{ width: 100%; height:100px; display: inline-block;}
</style>