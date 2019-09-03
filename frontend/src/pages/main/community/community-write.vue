<template>
  <f7-page>
    <f7-appbar>
      <f7-navbar 
          title="게시글 등록하기" 
          back-link="Back">

          <f7-nav-right>
              <f7-link href="#" @click="registerBBS">글 작성</f7-link>
          </f7-nav-right>
      </f7-navbar>
    </f7-appbar>
    
    <f7-list inline-labels no-hairlines style="height: 40%;">
      <f7-list-input
        style="height: 30%;"
        label="제목"
        type="text"
        placeholder="제목은 30자 제한입니다."
        :value="form.title"
        @input="form.title = $event.target.value"
        clear-button
      ></f7-list-input>
      
      <f7-list-input
        style="height: 70%;"
        label="내용"
        type="textarea"
        :value="form.content"
        @input="form.content = $event.target.value"
        resizable
      ></f7-list-input>
    </f7-list>

    <f7-block>
        <file-pond
          name="img"
          ref="pond"
          label-idle="이곳을 눌러서 파일을 올려주세요"
          allow-multiple="true"
          accepted-file-types="image/jpeg, image/png, image/gif"
          :server="{ process: saveTempImage, revert: revertTempSaveImg }"
        />
    </f7-block>
  </f7-page>
</template>
<script>
import vueFilePond, { setOptions }     from 'vue-filepond';
import FilePondPluginFileValidateType  from 'filepond-plugin-file-validate-type';
import FilePondPluginImagePreview      from 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.esm.js';
const FilePond = vueFilePond(FilePondPluginFileValidateType, FilePondPluginImagePreview );
export default {
  components: { FilePond },
  data() {
    return {
      form : {
        title    : '',
        content  : '',
        imgList  : []
      }
    };
  },
  methods: {  
    registerBBS() {
      let data = new URLSearchParams();
      data.append('title'     , this.form.title);
      data.append('content'   , this.form.content);
      data.append('imgListStr', this.form.registerImg);
      data.append('USER_JWT'  , this.$store.state.userJwt);

      this.$http.post('api/community/insert', data)
                .then( (res) => {
                    const response = res.data.response;
                    let REPL_MSG = response.REPL_CD == '000000' ? '위치 등록에 성공하였습니다.' :
																                                  '위치 등록에 실패하였습니다.'
							
                    this.$f7.dialog.alert(REPL_MSG, '위치 등록', () => {
                      if(response.REPL_CD == '000000') {
                        this.$f7router.navigate('/community/list', { clearPreviousHistory : true })
                      }
                    })
                })
    },
    // ##################### file-pond config START #######################
    saveTempImage(fieldName, file, metadata, load, error, progress, abort) {

      const formData = new FormData();
      formData.append(fieldName, file, file.name);

      const request = new XMLHttpRequest();
      request.open('POST', 'api/community/temp/img');

      request.upload.onprogress = (e) => progress(e.lengthComputable, e.loaded, e.total);

      request.onload = () => {
          if( request.status >= 200 && request.status < 300) {
              load(request.responseText)
              const data = JSON.parse(request.responseText);
              imgList.push(data.response.fileName);
          } else {
              error('oh no');
          }
      };

      request.send(formData);
      return { abort: () => { request.abort(); abort();}};
    }, // process END
    revertTempSaveImg(uniqueFileId, load, error) {
      const data    = JSON.parse(uniqueFileId);
      const imgName = data.response.fileName;

      let index = imgList.indexOf(imgName);
      
      if (index !== -1) imgList.splice(index, 1);
      load();
    },
    // ##################### file-pond config END #######################
  },
};
</script>
<style scoped>

</style>