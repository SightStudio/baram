<template>
  <f7-page @page:beforein="loadBBS">
    <f7-navbar 
        title="자유 게시판" 
        back-link="Back">
        
        <f7-nav-right>
            <f7-link href="/community/write">글 작성</f7-link>
        </f7-nav-right>
    </f7-navbar>
    <br><br>
    <f7-list inline-labels no-hairlines>
      <f7-list-input
        type="text"
        label="제목"
        :value="bbs.title"
        readonly
      ></f7-list-input>
      
      <f7-list-input
        type="textarea"
        label="내용"
        :value="bbs.content"
        readonly
      ></f7-list-input>

      <f7-swiper :param="{spaceBetween: 0, slidesPerView: 3}">
        <f7-swiper-slide v-for="(imgURL, idx) in images" v-bind:key="idx" >
          <f7-link @click="$refs.imgViewer.open()">
            <img :src="`${imgURL}`">
          </f7-link>
        </f7-swiper-slide>
      </f7-swiper>

      <f7-photo-browser
        ref="imgViewer"
        theme="dark"
        back-link-text="돌아가기"
        :photos="images"
      ></f7-photo-browser>
    </f7-list>
  </f7-page>
</template>
<script>
  export default {
    components: { },
    data() {
      return {
        bbs   : {
          writer   : '',
          title    : '',
          content  : '',
          reg_time : '',
        },
        images: []
      };
    },
    methods: {
      async loadBBS() {

        // [1] 게시글 로딩
        this._loadBBS();

        // [2] 게시글 이미지 로딩
        this._loadBBSImage();
      },// loadBBS() end
      /**
       * 게시글 데이터 가져오기 함수
       * @author Dong-Min Seol 
       */
      _loadBBS() {
        const bbsSeq = this.$f7route.params.seq;
        const URI    = `api/community/view/${bbsSeq}`;
        const self   = this
        this.$http.get(URI)
                  .then( data => {
                      const result = data.data.response.bbs;
                      self.bbs.writer   = result.USER_ID;
                      self.bbs.title    = result.TITLE;
                      self.bbs.content  = result.CONTENT;
                      self.bbs.reg_time = result.REG_TIME;
                  });
      },
      _loadBBSImage() {      
        const bbsSeq = this.$f7route.params.seq;
        const URI = `api/community/image/${bbsSeq}`;
        const self   = this
        this.$http.get(URI)
                  .then( data => {
                      const list = data.data.response.bbsImgList;
                      list.forEach( imgEL => this.images.push(imgEL.IMG_SRC) )
                  })
      }
    },
  };
</script>